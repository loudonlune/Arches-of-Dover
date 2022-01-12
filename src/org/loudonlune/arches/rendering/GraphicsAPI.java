package org.loudonlune.arches.rendering;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;
import org.lwjgl.vulkan.VK;
import org.lwjgl.vulkan.VK12;

public enum GraphicsAPI implements Comparable<GraphicsAPI> {
	VK_LATEST,
	GL_LATEST,
	GL_33;
	
	private static PriorityQueue<GraphicsAPI> apiQueue = null;
	private boolean available;
	
	public static void scan() {
		if (apiQueue != null)
			throw new IllegalStateException("GraphicsAPI.scan() may not be called twice.");
		
		Logger logger = Logger.getLogger("Graphics API");
		
		long vkVersion = VK.getInstanceVersionSupported();
		if (vkVersion == VK12.VK_API_VERSION_1_2) {
			logger.info("Vulkan 1.2 found.");
			VK_LATEST.available = true;
		}
		
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		long screeningWindowHandle = GLFW.glfwCreateWindow(128, 128, "screening OpenGL context", 0, 0);
		GLFW.glfwMakeContextCurrent(screeningWindowHandle);
		GL.createCapabilities();
		
		String version = GL33.glGetString(GL33.GL_VERSION);
		
		try {
			String[] glv_array = version.split(" ")[0].split("\\.");
			
			int major = Integer.parseInt(glv_array[0]);
			int minor = Integer.parseInt(glv_array[1]);
			int patch = Integer.parseInt(glv_array[2]);
			
			logger.info("OpenGL version found: " + major + "." + minor + "." + patch);
			
			if (major >= 4 && minor >= 6 && patch >= 0)
				GL_LATEST.available = true;
			if (major >= 3 && minor >= 3 && patch >= 0)
				GL_33.available = true;
		} catch (Exception e) {
			logger.warning("Failed to parse OpenGL string: " + version);
		}
		
		apiQueue = new PriorityQueue<>(
				Arrays.asList(
						GraphicsAPI.values()
						)
				.stream()
				.filter(gapi -> gapi.available)
				.collect(Collectors.toList()));
		
		GLFW.glfwDestroyWindow(screeningWindowHandle);
	}
	
	public static GraphicsAPI getBestAvailableAPI() {		
		return apiQueue.peek();
	}
	
	public boolean isAvailable() {
		return available;
	}
	
	public Renderer createRendererForAPI() {
		return null;
	}
}
