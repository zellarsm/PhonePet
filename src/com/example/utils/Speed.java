package com.example.utils;

public class Speed {
	
	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_LEFT = -1;
	public static final int DIRECTION_UP = -1;
	public static final int DIRECTION_DOWN = 1;
	
	private float xv = 1;
	private float yv = 1;
	
	private int xDirection = DIRECTION_RIGHT;
	private int yDirection = DIRECTION_DOWN;

	public Speed() {
		this.xv = 0;
		this.yv = 0;
	}
	
	public Speed(float xv, float yv) {
		this.xv = xv;
		this.yv = yv;	
	}
	
	public float getXv() {
		return xv;
	}
	
	public float getYv() {
		return yv;
	}
	
	public void setXv(float xv) {
		this.xv = xv;
	}
	
	public void setYv(float yv) {
		this.yv = yv;
	}
	
	public int getxDirection() {
		return xDirection;
   }

	public int getyDirection() {
		return yDirection;
   }
	
	 public void setyDirection(int yDirection) {
	    this.yDirection = yDirection;
	}
	 
	 public void setxDirection(int xDirection) {
		this.xDirection = xDirection;
	}
}
