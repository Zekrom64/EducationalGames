package com.zekrom_64.edu.goldberg;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import com.zekrom_64.edu.goldberg.util.Errors;

public class Goldberg {
	
	private static long window = 0;
	private static int width, height;
	
	private static double minFrameTime = 0;
	private static int fpsCounter = 0;
	private static double fpsAccum = 0;
	
	public static void main(String[] args) {
		LaunchDialog launch = new LaunchDialog();
		launch.setVisible(true);
		try {
			synchronized(launch.dialogWaitObj) {
				launch.dialogWaitObj.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}
		if (launch.doLaunch) run();
	}
	
	private static void run() {
		width = Config.getConfigInt("window.width", 800);
		height = Config.getConfigInt("window.height", 600);
		minFrameTime = 1.0 / Config.getConfigInt("render.fpslimit", 60);
		
		if (!GLFW.glfwInit()) Errors.displayAndQuit("Failed to initialize GLFW");
		
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		window = GLFW.glfwCreateWindow(width, height, "Goldberg", 0, 0);
		
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		GL11.glViewport(0, 0, width, height);
		GL11.glOrtho(0, width, 0, height, -1, 1);
		
		GLFW.glfwShowWindow(window);
		
		double lastTime = GLFW.glfwGetTime();
		double currentTime = lastTime;
		double deltaTime;
		
		while (!GLFW.glfwWindowShouldClose(window)) {
			currentTime = GLFW.glfwGetTime();
			deltaTime = currentTime - lastTime;
			lastTime = currentTime;
			
			GLFW.glfwPollEvents();
			
			GL11.glClearColor(0, 0, 0, 0);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glColor3f(1, 0, 0);
			GL11.glRectd(200, 150, 600, 450);
			
			GLFW.glfwSwapBuffers(window);
			
			fpsCounter++;
			fpsAccum += deltaTime;
			if (fpsAccum >= 1) {
				fpsAccum -= 1;
				GLFW.glfwSetWindowTitle(window, "Goldberg: " + fpsCounter + " FPS");
				fpsCounter = 0;
			}
			
			double latentCurrentTime = GLFW.glfwGetTime();
			double timeRemaining = minFrameTime - (latentCurrentTime - currentTime);
			if (timeRemaining > 0) {
				try {
					Thread.sleep((long) (timeRemaining * 1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.err.println("Unexpectedly interrupted waiting for FPS limit");
				}
			}
		}
		
		GLFW.glfwDestroyWindow(window);
		GLFW.glfwTerminate();
	}

}
