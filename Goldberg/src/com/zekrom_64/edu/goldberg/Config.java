package com.zekrom_64.edu.goldberg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

	private static Properties props = new Properties();
	
	public static final File configFile = new File("config.properties");
	
	static {
		if (configFile.exists() && configFile.isFile()) {
			try (FileInputStream fis = new FileInputStream(configFile)) {
				props.load(fis);
			} catch (IOException e) {
				System.err.println("Failed to load configuration!");
				e.printStackTrace();
			}
		}
		Runtime.getRuntime().addShutdownHook(new Thread() {
			
			@Override
			public void run() {
				try (FileOutputStream fos = new FileOutputStream(configFile)) {
					props.store(fos, "Goldberg program settings");
				} catch (IOException e) {
					System.err.println("Failed to save configuration!");
					e.printStackTrace();
				}
			}
			
		});
	}
	
	public static String getConfigString(String key, String defaultVal) {
		String str = props.getProperty(key);
		if (str == null) {
			props.setProperty(key, defaultVal);
			return defaultVal;
		} else return str;
	}
	
	public static int getConfigInt(String key, int defaultVal) {
		String str = props.getProperty(key);
		if (str == null) {
			props.setProperty(key, Integer.toString(defaultVal));
			return defaultVal;
		}
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return defaultVal;
		}
	}
	
	public static float getConfigFloat(String key, float defaultVal) {
		String str = props.getProperty(key);
		if (str == null) {
			props.setProperty(key, Float.toString(defaultVal));
			return defaultVal;
		}
		try {
			return Float.parseFloat(str);
		} catch (NumberFormatException e) {
			return defaultVal;
		}
	}
	
	public static boolean getConfigBool(String key, boolean defaultVal) {
		String str = props.getProperty(key);
		if (str == null) {
			props.setProperty(key, Boolean.toString(defaultVal));
			return defaultVal;
		}
		if ("true".equalsIgnoreCase(str)) return true;
		if ("false".equalsIgnoreCase(str)) return false;
		props.setProperty(key, Boolean.toString(defaultVal));
		return defaultVal;
	}
	
	public static void setConfig(String key, Object value) {
		props.setProperty(key, value.toString());
	}
	
}
