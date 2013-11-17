package com.example.vos;

public class Ball {

	private int ballX;
	private int ballY;
	private int ID;
	
	public Ball(int id, int x, int y) {
		
		ID = id;
		ballX = x;
		ballY = y;	
	}
	
	public Ball() {
	}
	
	public Ball(int x, int y) {
		
		ballX = x;
		ballY = y;
	}

	public int getX() {
		return this.ballX;
	}
	
	public int getY() {
		return this.ballY;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void setX(int x) {
		ballX = x;
	}
	
	public void setY(int y) {
		ballY = y;
	}
	
	public void setID(int id) {
		ID = id;
	}
}
