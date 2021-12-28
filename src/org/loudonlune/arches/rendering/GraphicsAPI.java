package org.loudonlune.arches.rendering;

import java.util.logging.Logger;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.vulkan.VK;
import org.lwjgl.vulkan.VK12;

public enum GraphicsAPI {
	GL_33,
	GL_LATEST,
	VK_LATEST;
	
	private boolean available;
	
	public static void collectAvailableAPIs() {
		Logger logger = Logger.getLogger("Graphics API");
		
		VK.create();
		long vkVersion = VK.getInstanceVersionSupported();
		if (vkVersion == VK12.VK_API_VERSION_1_2) {
			logger.info("Vulkan 1.2 found.");
			VK_LATEST.available = true;
		}
		
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		long screeningWindowHandle = GLFW.glfwCreateWindow(128, 128, "screening OpenGL context", 0, 0);
		GLFW.glfwMakeContextCurrent(screeningWindowHandle);
		
		String version = GL11.glGetString(GL11.GL_VERSION);
		logger.info("OpenGL version found: " + version);
		GL_LATEST.available = true;
		GL_33.available = true;
	}
	
	public boolean isAvailable() {
		return available;
	}
	
	public Renderer createRendererForAPI() {
		return null;
	}
}
