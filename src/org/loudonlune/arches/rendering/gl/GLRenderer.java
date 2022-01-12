package org.loudonlune.arches.rendering.gl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.loudonlune.arches.ArchesOfDover;
import org.loudonlune.arches.assets.Asset;
import org.loudonlune.arches.assets.LoadedAsset;
import org.loudonlune.arches.rendering.AssetPreInitializationEvent;
import org.loudonlune.arches.rendering.ContextValidateEvent;
import org.loudonlune.arches.rendering.GraphicsAPI;
import org.loudonlune.arches.rendering.Renderable;
import org.loudonlune.arches.rendering.Renderer;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GLCapabilities;

public class GLRenderer extends Renderer {
	
	private static Logger logger = Logger.getLogger("GLRenderer");
	private HashMap<Asset, LoadedAsset> assets;
	private GLRenderContext renderContext;
	private GLCapabilities contextCapabilities;
	
	public static Logger getLogger() {
		return logger;
	}

	public static void checkErrors() {
		int error = GL33.glGetError();
		
		if (error != GL33.GL_NO_ERROR)
			logger.warning("OpenGL error thrown: " + error);
	}
	
	public GLRenderer(GraphicsAPI api, Renderable root) {
		super(api, root);
		
		assets = new HashMap<>();
		renderContext = new GLRenderContext(this);
	}

	public GLCapabilities getContextCapabilities() {
		return contextCapabilities;
	}
	
	/**
	 * This function returns a copy of the internal loaded asset map.
	 * @return A set of Asset classes that have been loaded by this renderer.
	 */
	public Set<Asset> getLoadedAssets() {
		return new HashSet<Asset>(assets.keySet());
	}
	
	@Override
	public void invalidateContext() {
		// unload all assets loaded on the GL context
		for (LoadedAsset la : assets.values())
			la.unload();
	}

	@Override
	public void validateContext() {
		renderContext.applySettings();
		
		ArchesOfDover.getInstance()
		.fireEvent(new ContextValidateEvent(renderContext));
	}

	@Override
	public void initializeAssets(List<Asset> assets) {
		ArchesOfDover.getInstance()
		.fireEvent(new AssetPreInitializationEvent(this, assets));
		
		// TODO: initialize assets
	}

	@Override
	public void destroyAssets(List<Asset> toUnload) {
		for (Asset a : toUnload)
			if (assets.containsKey(a)) {
				LoadedAsset la = assets.get(a);
				la.unload();
				assets.remove(a);
			}
	}

	@Override
	public void render() {
		renderContext.applySettings();
		GL33.glClear(GL33.GL_COLOR_BUFFER_BIT | GL33.GL_STENCIL_BUFFER_BIT | GL33.GL_DEPTH_BUFFER_BIT);
		this.renderClient.render(renderContext);
	}
}
