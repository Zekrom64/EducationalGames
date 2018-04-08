package com.zekrom_64.edu.goldberg.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

import com.zekrom_64.edu.goldberg.Config;

public class Log {

	private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd kk:mm:ss.SSS");
	
	private static PrintStream outputStream = System.out;
	private static PrintStream fileStream = null;
	
	public static void init() {
		if (!Config.getConfigBool("log.disable", true)) {
			Instant time = Instant.now();
			StringBuilder sb = new StringBuilder();
			sb.append(time.get(ChronoField.YEAR_OF_ERA)).append('.');
			sb.append(time.get(ChronoField.MONTH_OF_YEAR)).append('.');
			sb.append(time.get(ChronoField.DAY_OF_MONTH)).append('-');
			sb.append(time.get(ChronoField.HOUR_OF_DAY)).append(',');
			sb.append(time.get(ChronoField.MINUTE_OF_HOUR)).append(',');
			sb.append(time.get(ChronoField.SECOND_OF_MINUTE));
			sb.append("-log.txt");
			
			File logDir = new File("logs");
			logDir.mkdirs();
			File logFile = new File(logDir, sb.toString());
			try {
				fileStream = new PrintStream(logFile);
			} catch (FileNotFoundException e) {
				System.err.println("Failed to open log file!");
				e.printStackTrace();
			}
		}
	}
	
	private static String getLinePrefix() {
		Instant timestamp = Instant.now();
		Thread t = Thread.currentThread();
		StackTraceElement[] trace = t.getStackTrace();
		StackTraceElement caller = trace[3]; // <caller> -> log() -> getLinePrefix() -> getStackTrace()
		
		StringBuilder sb = new StringBuilder();
		sb.append(timeFormat.format(timestamp));
		sb.append(" [");
		sb.append(t.getName());
		sb.append("][");
		sb.append(caller.getClassName());
		sb.append(':');
		sb.append(caller.getMethodName());
		sb.append(':');
		sb.append(caller.getLineNumber());
		sb.append("]: ");
		return sb.toString();
	}
	
	public static void log(Object ... objects) {
		String prefix = getLinePrefix();
		for(Object obj : objects) {
			String str = obj.toString();
			for(String line : str.split("\\\n")) {
				logInternal(prefix + line.replace("\\\t", "    "));
			}
		}
	}
	
	private static void logInternal(String text) {
		outputStream.println(text);
		if (fileStream != null) fileStream.println(text);
	}
	
}
