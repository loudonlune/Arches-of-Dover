package org.loudonlune.arches.math;

public class Vec2 {
	private float x;
	private float y;
	
	public Vec2() {
		x = 0.0f;
		y = 0.0f;
	}
	
	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public Vec2 add(Vec2 other) {
		x += other.x;
		y += other.y;
		
		return this;
	}
	
	public Vec2 sub(Vec2 other) {
		x -= other.x;
		y -= other.y;
		
		return this;
	}
	
	public Vec2 mul(Vec2 other) {
		x *= other.x;
		y *= other.y;
		
		return this;
	}
	
	public Vec2 div(Vec2 other) {
		x /= other.x;
		y /= other.y;
		
		return this;
	}
	
	public float dot(Vec2 other) {
		return other.x * x + other.y * y;
	}
	
	public double distance(Vec2 other) {
		float deltaX = Math.abs(x - other.x);
		float deltaY = Math.abs(y - other.y);
		
		return Math.sqrt(deltaX*deltaX  + deltaY*deltaY);
	}
	
}
