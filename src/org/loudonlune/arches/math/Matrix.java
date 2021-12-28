package org.loudonlune.arches.math;

/**
 * Abstract representation of a square matrix.
 * @author Adam (loudonlune) Hassick
 */
public abstract class Matrix {
	protected float[] data;
	protected int dimension;
	
	/**
	 * Adds two matrices.
	 * Currently, only two matrices with the same row/col dimension are supported.
	 * @param other Matrix to add values to this one.
	 * @return This matrix, after the operation.
	 */
	public Matrix add(Matrix other) {
		if (dimension != other.dimension) // fail
			return null;
		
		for (int i = 0; i < dimension; i++)
			data[i] += other.data[i];
		
		return this;
	}
	
	/**
	 * Subtracts two matrices.
	 * Currently, only two matrices with the same row/col dimension are supported.
	 * @param other Matrix to subtract from this one.
	 * @return This matrix, after the operation.
	 */
	public Matrix sub(Matrix other) {
		if (dimension != other.dimension) // fail
			return null;
		
		for (int i = 0; i < dimension; i++)
			data[i] -= other.data[i];
		
		return this;
	}
	
	/**
	 * Multiplies two matrices.
	 * Currently, only two matrices with the same row/col dimension are supported.
	 * @param other Other matrix to multiply.
	 * @return This matrix, after the operation.
	 */
	public Matrix mul(Matrix other) {
		if (dimension != other.dimension) // fail
			return null;
		
		float[] new_data = new float[] { 0.0f, 0.0f, 0.0f, 0.0f };
		
		/*
		new_data[0] = data[0] * other.data[0] + data[2] * other.data[1];
		new_data[1] = data[1] * other.data[0] + data[3] * other.data[1];
		new_data[2] = data[0] * other.data[2] + data[2] * other.data[3];
		new_data[3] = data[1] * other.data[2] + data[3] * other.data[3];
		*/
		
		final int dim = dimension;
		
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				new_data[i * dim + j] = data[j] * other.data[i] + data[j + dim] * other.data[i + 1];
			}
		}
		
		data = new_data;
		return this;
	}
	
	public int getDimension() {
		return dimension;
	}
	
	public float[] getData() {
		return data;
	}
}
