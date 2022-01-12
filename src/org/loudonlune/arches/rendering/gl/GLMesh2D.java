package org.loudonlune.arches.rendering.gl;

import org.loudonlune.arches.assets.Asset;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL33;

public class GLMesh2D extends GLAsset {

	private int buffer;
	private int vertexArray;
	
	protected GLMesh2D(Asset asset) {
		super(asset);
		
		buffer = GL15.glGenBuffers();
		vertexArray = GL33.glGenVertexArrays();
		
		// TODO: load the vertex data using assimp and interleave it
	}

	@Override
	public void unload() {
		GL15.glDeleteBuffers(buffer);
		GL33.glDeleteVertexArrays(vertexArray);
	}

}
