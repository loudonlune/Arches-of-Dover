package org.loudonlune.arches.rendering;

import java.util.List;

import org.loudonlune.arches.assets.Asset;

public abstract class Renderer {
	protected final GraphicsAPI implAPI;
	protected Renderable renderClient;
	protected RenderContext renderContext;
	
	protected Renderer(GraphicsAPI implementedWith, Renderable client) {
		renderContext = null;
		this.renderClient = client;
		this.implAPI = implementedWith;
	}
	
	public GraphicsAPI getGraphicsAPI() {
		return implAPI;
	}
	
	/**
	 * Called when the renderer is being removed from a canvas.
	 */
	public abstract void invalidateContext();
	
	/**
	 * Called when this renderer is being applied to a canvas.
	 */
	public abstract void validateContext();
	
	/**
	 * Tells the renderer to initialize these assets for internal use.
	 * @param graphicsAsssets List of assets to initialize.
	 */
	public abstract void initializeAssets(List<Asset> graphicsAssets);
	
	/**
	 * Tells the renderer to destroy this list of assets.
	 * @param graphicsAssets List of assets to destroy.
	 */
	public abstract void destroyAssets(List<Asset> graphicsAssets);
	
	/**
	 * Invokes the renderer to paint the renderer to the canvas.
	 */
	public abstract void render();
}
