package com.example.vos;

public class Food {

	private int foodX;
	private int foodY;
	private int ID;
	
	public Food(int id, int x, int y) {
		
		ID = id;
		foodX = x;
		foodY = y;	
	}
	
	public Food() {
	}
	
	public Food(int x, int y) {
		
		foodX = x;
		foodY = y;
	}

	public int getX() {
		return this.foodX;
	}
	
	public int getY() {
		return this.foodY;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void setX(int x) {
		foodX = x;
	}
	
	public void setY(int y) {
		foodY = y;
	}
	
	public void setID(int id) {
		ID = id;
	}
}
