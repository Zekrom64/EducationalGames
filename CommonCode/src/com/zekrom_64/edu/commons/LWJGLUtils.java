package com.zekrom_64.edu.commons;

import javax.swing.JOptionPane;

import org.lwjgl.system.Library;

public class LWJGLUtils {

	public static void extractAndLoadNatives(String ... libnames) {
		for(String name : libnames) {
			try {
				Library.loadSystem(name);
			} catch (UnsatisfiedLinkError e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Failed to load native library \"" + name + '\"', "Error", JOptionPane.ERROR_MESSAGE);
				throw e;
			}
		}
	}
	
}
