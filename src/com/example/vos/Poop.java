package com.example.vos;

public class Poop {

	private int poopX;
	private int poopY;
	private int ID;
	
	public Poop() {
	}
	
	public Poop(int id, int x, int y) {
		
		this.ID = id;
		this.poopX = x;
		this.poopY = y;	
	}
	
	public Poop(int x, int y) {
		
		this.poopX = x;
		this.poopY = y;
	}

	public int getX() {
		return this.poopX;
	}
	
	public int getY() {
		return this.poopY;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void setX(int x) {
		poopX = x;
	}
	
	public void setY(int y) {
		poopY = y;
	}
	
	public void setID(int id) {
		ID = id;
	}
}
