package org.loudonlune.arches.rendering.gl;

import org.loudonlune.arches.assets.Asset;
import org.loudonlune.arches.assets.LoadedAsset;
import org.loudonlune.arches.assets.ShaderProgramAsset;
import org.lwjgl.opengl.GL33;

public class GLShaderProgram extends LoadedAsset {
	private int program;
	
	public GLShaderProgram(ShaderProgramAsset asset) {
		super((Asset) asset);
		
		program = GL33.glCreateProgram();
		GLRenderer.checkErrors();
	}

	@Override
	public void unload() {
		GL33.glDeleteProgram(program);
	}
	
	
	
}
