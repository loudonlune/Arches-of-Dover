package org.loudonlune.arches.rendering;

import java.util.Stack;

import org.loudonlune.arches.math.Mat2;

public abstract class RenderContext {
	private Stack<Mat2> matrixStack;
	private Renderer renderer;
	
	public RenderContext(Renderer parent) {
		matrixStack = new Stack<>();
		renderer = parent;
	}
	
	public int getMatrixStackSize() {
		return matrixStack.size();
	}
	
	public void pushMatrix(Mat2 mat) {
		matrixStack.push(mat);
	}
	
	public Mat2 popMatrix() {
		return matrixStack.pop();
	}
	
	public Mat2 compileMatrixStack() {
		Mat2 mat = new Mat2();
		
		for (Mat2 m : matrixStack)
			mat.mul(m);
		
		return mat;
	}
	
	public Renderer getRenderer() {
		return renderer;
	}
}
