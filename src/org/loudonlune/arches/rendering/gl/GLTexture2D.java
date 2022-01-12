package org.loudonlune.arches.rendering.gl;

import org.loudonlune.arches.assets.Asset;
import org.lwjgl.opengl.GL33;

public class GLTexture2D extends GLAsset {

	private int textureHandle;
	
	protected GLTexture2D(Asset asset) {
		super(asset);
		
		textureHandle = GL33.glGenTextures();
	}

	@Override
	public void unload() {
		GL33.glDeleteTextures(textureHandle);
	}

}
