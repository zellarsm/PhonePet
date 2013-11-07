package com.example.vos;

public class Poop {

	private int poopWidth;
	private int poopHeight;
	private int poopX;
	private int poopY;
	private int ID = 0;
	
	public Poop(int height, int width, int x, int y) {
		
		poopHeight = height;
		poopWidth = width;
		poopX = x;
		poopY = y;
		ID = ID + 1;
	}
	
	public int getHeight() {
		return poopHeight;
	}
	
	public int getWidth() {
		return poopWidth;
	}
	
	public int getX() {
		return poopX;
	}
	
	public int getY() {
		return poopY;
	}
	
	public int getID() {
		return ID;
	}
}
