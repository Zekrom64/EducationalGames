package com.zekrom_64.edu.goldberg.util;

import javax.swing.JOptionPane;

public class Errors {

	public static void displayAndQuit(Exception ex) {
		ex.printStackTrace();
		JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static void displayAndQuit(String msg) {
		System.err.print(msg);
		Thread.dumpStack();
		JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
}
