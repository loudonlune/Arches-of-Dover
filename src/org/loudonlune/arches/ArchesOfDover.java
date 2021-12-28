package org.loudonlune.arches;

import java.util.Properties;
import java.util.logging.Logger;

import org.loudonlune.arches.event.EventBus;
import org.loudonlune.arches.rendering.AcceleratedCanvas;
import org.loudonlune.arches.rendering.GraphicsAPI;
import org.loudonlune.arches.rendering.Renderer;
import org.lwjgl.glfw.GLFW;

/**
 * The Arches of Dover engine main class.
 * @author Adam (loudonlune) Hassick
 */
public final class ArchesOfDover implements Runnable {
	private static ArchesOfDover instance;
	public static Logger logger;
	public static String version;
	
	private EventBus eventBus;
	private AcceleratedCanvas canvas;
	private Renderer currentRenderer;
	
	public ArchesOfDover() {
		eventBus = new EventBus();
		canvas = new AcceleratedCanvas(1366, 768, "Arches of Dover");
		currentRenderer = GraphicsAPI.GL_LATEST.createRendererForAPI();
	}
	
	/**
	 * @return The singleton instance of the engine.
	 */
	public static ArchesOfDover getInstance() {
		return instance;
	}
	
	/**
	 * Initializes libraries this application uses.
	 */
	private static void initializeLibraries() {
		if (!GLFW.glfwInit())
			throw new RuntimeException("Failed to load GLFW");
	}
	
	/**
	 * Populates variables in this class whose values are obtained from project.properties.
	 */
	private static void populateProperties() {
		final Properties properties = new Properties();
		version = properties.getProperty("version");
	}
	
	/**
	 * Function to get the build version from pom.xml.
	 */
	public static String getVersion() {
		return version;
	}
	
	/**
	 * Program entry point.
	 */
	public static void main(String[] args) {
		// read required properties into main class from project.properties
		populateProperties();
		
		// initialize logger
		logger = Logger.getGlobal();
		logger.info("Arches of Dover version " + version + " starting...");
		logger.info("Loading libraries...");
		initializeLibraries();
		
		instance = new ArchesOfDover();
		instance.initialize();
		instance.run();
	}

	public EventBus getEventBus() {
		return eventBus;
	}

	public AcceleratedCanvas getCanvas() {
		return canvas;
	}

	public Renderer getRenderer() {
		return currentRenderer;
	}
	
	public void initialize() {
		
	}
	
	public void run() {
		
	}
	
}
