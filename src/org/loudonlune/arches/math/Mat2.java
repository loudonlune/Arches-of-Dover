package org.loudonlune.arches.math;

public class Mat2 extends Matrix {	
	
	public static Mat2 translate(float x, float y) {
		return new Mat2(x, 1.0f, 1.0f, y);
	}
	
	public static Mat2 scale(float x, float y) {
		return new Mat2(1.0f, 1.0f, x, y);
	}
	
	public static Mat2 rotate(float degrees) {
		return new Mat2(
					(float) Math.cos(degrees), 
					(float) Math.sin(degrees), 
					- (float) Math.sin(degrees), 
					(float) Math.cos(degrees)
				);
	}
	
	/**
	 * Initializes an identity matrix.
	 */
	public Mat2() {
		this(1.0f, 0.0f, 0.0f, 1.0f);
	}
	
	/**
	 * Initializes a matrix from 2 vectors, where the 2 vectors are rows.
	 * @param col1 Column 1, as a vector.
	 * @param col2 Column 2, as a vector.
	 */
	public Mat2(Vec2 col1, Vec2 col2) {
		this(col1.getX(), col1.getY(), col2.getX(), col2.getY());
	}
	
	/**
	 * Initializes a matrix from 4 float params.
	 * @param r1c1 Row 1 column 1
	 * @param r1c2 Row 1 column 2
	 * @param r2c1 Row 2 column 1
	 * @param r2c2 Row 2 column 2
	 */
	public Mat2(float r1c1, float r1c2, float r2c1, float r2c2) {
		data = new float[] { r1c1, r2c1, r1c2, r2c2 };
		dimension = 2;
	}
}
