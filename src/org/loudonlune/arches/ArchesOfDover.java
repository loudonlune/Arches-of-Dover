package org.loudonlune.arches;

import java.util.Properties;
import java.util.logging.Logger;

import org.loudonlune.arches.event.Event;
import org.loudonlune.arches.event.EventBus;
import org.loudonlune.arches.event.GameCloseEvent;
import org.loudonlune.arches.event.GameInitializationEvent;
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
		
		GraphicsAPI.scan();
		
		logger.info("API availability on this system: ");
		for (GraphicsAPI api : GraphicsAPI.values()) {
			logger.info("\t" + api.toString() + ": " + api.isAvailable());
		}
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
		
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$s] [%3$s] %5$s %n");
		
		// initialize logger
		logger = Logger.getGlobal();
		logger.info("Arches of Dover version " + version + " starting...");
		logger.info("Loading libraries...");
		initializeLibraries();
		
		instance = new ArchesOfDover();
		logger.info("Construction complete.");
		instance.initialize();
		logger.info("Initialization complete.");
		instance.run();
		logger.info("Shutting down.");
	}
	
	private EngineState state;
	private EventBus eventBus;
	private AcceleratedCanvas canvas;
	private Renderer currentRenderer;
	private boolean exit;
	
	private final double updateRate = 20.0;
	
	public ArchesOfDover() {
		state = EngineState.CONSTRUCTION;
		eventBus = new EventBus();
		canvas = new AcceleratedCanvas(1366, 768, "Arches of Dover");
		currentRenderer = GraphicsAPI.GL_LATEST.createRendererForAPI();
		exit = false;
	}

	public EventBus getEventBus() {
		return eventBus;
	}
	
	/**
	 * Depending on engine state, either queues event for firing for fires immediately.
	 * @param e Event to fire.
	 */
	public void fireEvent(Event e) {
		if (state == EngineState.RUNTIME)
			eventBus.queueEvent(e);
		eventBus.forceFireEvent(e);
	}

	public AcceleratedCanvas getCanvas() {
		return canvas;
	}

	public Renderer getRenderer() {
		return currentRenderer;
	}
	
	public void initialize() {
		state = EngineState.INITIALIZATION;
		eventBus.queueEvent(new GameInitializationEvent());
	}
	
	/*
	 * Once engine is bootstrapped, this is called by the thread tasked with updating.
	 */
	public void run() {
		state = EngineState.RUNTIME;
		double init = System.currentTimeMillis();
		double now = System.currentTimeMillis();
		double target = 1000.0 / updateRate;
		
		canvas.makeVisible();
		
		while (!exit) {
			GLFW.glfwPollEvents();
			exit = canvas.shouldClose();
			
			if (exit) {
				fireEvent(new GameCloseEvent());
			}
			
			double delta = now - init;
			
			eventBus.fireEvents();
			
			try {
				long sleepTime = (long) (target - delta + 1);
				if (sleepTime > 0)
					Thread.sleep(sleepTime);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// TODO: once renderer must be initialized, remove not null check
			if (currentRenderer != null) {
				currentRenderer.render();
			}
			
			canvas.swapBuffers();
			now = System.currentTimeMillis();
			init = now;
		}
	}
	
}
