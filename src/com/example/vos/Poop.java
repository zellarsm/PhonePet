package com.example.vos;

import com.example.utils.Speed;

public class Poop {

	private int poopX;
	private int poopY;
	private int ID;
	private Speed speed;
	
	public Poop() {
	}
	
	public Poop(int id, int x, int y) {
		
		this.ID = id;
		this.poopX = x;
		this.poopY = y;	
		this.speed = new Speed();
	}
	
	public Poop(int x, int y) {
		
		this.poopX = x;
		this.poopY = y;
		this.speed = new Speed();
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
	
	public Speed getSpeed() {
		return speed;
	}

	public void setSpeed(Speed speed) {
		this.speed = speed;
	}

	public void update() {
		poopX += (poopX * speed.getxDirection()); 
		poopY += (poopX * speed.getyDirection());
	}
}
