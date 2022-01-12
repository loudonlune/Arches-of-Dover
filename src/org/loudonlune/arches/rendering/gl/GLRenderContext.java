package org.loudonlune.arches.rendering.gl;

import java.awt.Color;

import org.loudonlune.arches.rendering.RenderContext;
import org.lwjgl.opengl.GL33;

public class GLRenderContext extends RenderContext {
	
	// OpenGL context settings
	// These cost a lot to change, so we will use a flag to track when they are
	// so we can completely avoid changing these without necessity
	private Color clearColor;
	private boolean cullFaceEnabled;
	private boolean settingsChanged;
	private int frontFace;
	private int cullFace;
	
	public GLRenderContext(GLRenderer renderer) {
		super(renderer);
		
		setCullFaceEnabled(true);
		setCullFace(GL33.GL_BACK);
		setClearColor(Color.BLACK);
	}
	
	public void applyModelView(GLShader polygonShader) {
		// TODO: impl feeding compiled matrix into shader
		
	}
	
	public void applySettings() {
		if (settingsChanged) { // if settings changed flag is raised
			// apply settings (call high overhead OpenGL functions only when needed)
			GL33.glClearColor(
					((float) clearColor.getRed()) / 255.0f, 
					((float) clearColor.getGreen()) / 255.0f, 
					((float) clearColor.getBlue()) / 255.0f, 
					((float) clearColor.getAlpha()) / 255.0f);
			
			if (cullFaceEnabled) 
				GL33.glEnable(GL33.GL_CULL_FACE);
			else 
				GL33.glDisable(GL33.GL_CULL_FACE);
			
			// clear flag value
			settingsChanged = false;
		}
	}

	public Color getClearColor() {
		return clearColor;
	}

	public void setClearColor(Color clearColor) {
		settingsChanged = true;
		this.clearColor = clearColor;
	}

	public boolean isCullFaceEnabled() {
		return cullFaceEnabled;
	}

	/**
	 * Sets the flag for enabling face culling. 
	 * Making this call in a render function is strongly discouraged.
	 * @param cullFace State of face culling to set.
	 */
	public void setCullFaceEnabled(boolean cullFace) {
		settingsChanged = true;
		this.cullFaceEnabled = cullFace;
	}

	public int getCullFace() {
		return cullFace;
	}

	/**
	 * Making this call in a render function is strongly discouraged.
	 * @param cullFace Face to cull. Must be either GL_FRONT, GL_BACK, or GL_FRONT_AND_BACK
	 * @return true if cullFace is valid, false otherwise
	 */
	public boolean setCullFace(int cullFace) {
		if (cullFace != GL33.GL_FRONT && cullFace != GL33.GL_BACK && cullFace != GL33.GL_FRONT_AND_BACK)
			return false;
		
		settingsChanged = true;
		this.cullFace = cullFace;
		return true;
	}

	public int getFrontFace() {
		return frontFace;
	}

	/**
	 * Accepts GL_CCW or GL_CW as valid values.
	 * @param frontFace GL_CCW or GL_CW
	 * @return true if value is valid, false otherwise
	 */
	public boolean setFrontFace(int frontFace) {
		if (frontFace != GL33.GL_CCW || frontFace != GL33.GL_CW)
			return false;
		
		settingsChanged = true;
		this.frontFace = frontFace;
		return true;
	}
	
}
