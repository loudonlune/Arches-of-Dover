package org.loudonlune.arches.rendering;

import org.loudonlune.arches.ArchesOfDover;
import org.loudonlune.arches.event.KeyPressEvent;
import org.loudonlune.arches.event.KeyReleaseEvent;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWVulkan;
import org.lwjgl.opengl.GL;

public class AcceleratedCanvas {
	private Renderer renderer; 
	private long windowHandle;
	
	private void registerCallbacks() {
		GLFW.glfwSetKeyCallback(windowHandle, new GLFWKeyCallbackI() {
			
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {				
				switch (action) {
				case GLFW.GLFW_PRESS:
					ArchesOfDover.getInstance()
					.getEventBus()
					.queueEvent(new KeyPressEvent(key));
					
					break;
					
				case GLFW.GLFW_RELEASE:
					ArchesOfDover.getInstance()
					.getEventBus()
					.queueEvent(new KeyReleaseEvent(key));
					break;
				}
				
			}
			
		});
	}
	
	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(windowHandle);
	}
	
	public AcceleratedCanvas(int width, int height, String name) {
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		windowHandle = GLFW.glfwCreateWindow(width, height, name, 0, 0);
		renderer = null;
	
		registerCallbacks();
	}
	
	public void makeVisible() {
		GLFW.glfwShowWindow(windowHandle);
	}
	
	private void destroyContext(GraphicsAPI api) {
		switch (api) {
		case GL_33:
		case GL_LATEST:
			GLFW.glfwMakeContextCurrent(0);
			break;
		case VK_LATEST:
			throw new RuntimeException("Not implemented.");
		}
	}
	
	private void createContext(GraphicsAPI api) {
		switch (api) {
		case GL_LATEST:
			GLFW.glfwMakeContextCurrent(windowHandle);
			if (!GL.getCapabilities().OpenGL46)
				throw new RuntimeException("OpenGL 4.6 not supported, OpenGL latest renderer was requested.");
			break;
		case GL_33:
			GLFW.glfwMakeContextCurrent(windowHandle);
			if (!GL.getCapabilities().OpenGL33)
				throw new RuntimeException("OpenGL 3.3 is not supported on this system.");
			break;
		case VK_LATEST:
			// only a check, VkRenderer does context setup
			if (!GLFWVulkan.glfwVulkanSupported())
				throw new RuntimeException("Vulkan is not supported, and a vulkan renderer was requested.");			
			break;
		}
	}
	
	/**
	 * Replaces the renderer in this frame with a new one.
	 * <br><b>WARNING! This is expensive, and will cause all used graphics assets to be reloaded!</b><br>
	 * @param newRenderer The new renderer to validate a context for.
	 */
	public void setRenderer(Renderer newRenderer) {
		if (renderer != null) {
			renderer.invalidateContext();
			destroyContext(renderer.getGraphicsAPI());
		}
		
		createContext(newRenderer.getGraphicsAPI());
		newRenderer.validateContext();
		renderer = newRenderer;
	}
	
	public Renderer getRenderer() {
		return renderer;
	}
	
	public void swapBuffers() {
		GLFW.glfwSwapBuffers(windowHandle);
	}
	
	public void pollEvents() {
		GLFW.glfwPollEvents();
	}
}
