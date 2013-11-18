package com.example.vos;

public class Ball {

	private float ballX;
	private float ballY;
	private int ID;
	
	public Ball(int id, float x, float y) {
		
		ID = id;
		ballX = x;
		ballY = y;	
	}
	
	public Ball() {
	}
	
	// Ball is currently 30x30 pixels
	public Ball(float x, float y) {
		
		ballX = x -15;
		ballY = y -15;
	}

	public float getX() {
		return this.ballX;
	}
	
	public float getY() {
		return this.ballY;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void setX(int x) {
		ballX = (float)x;
	}
	
	public void setY(int y) {
		ballY = (float)y;
	}
	
	public void addPos(float x, float y)
	{
		ballX += x;
		ballY += y;
	}
	
	public void setID(int id) {
		ID = id;
	}
	
	public boolean CloseEnoughTo(Ball other)
	{
		if(Math.abs(this.getX() - other.getX()) < 10)
			if(Math.abs(this.getY() - other.getY()) < 10)
				return true;
		
		return false;
	}
}
