package com.example.vos;

import android.text.format.Time;

/*
 * The Vo stands for Value Object. Value Objects are a type of model.
 * This is nothing more than a name-value object.
 * 
 * This model does two things: 
 * 1. It holds the state. All the variables in here represent the pet object.
 * 2. It notifies its observers when something has happened. The workhorse of 
 * 	  this is done in the subclass, SimpleObservable.
 * 
 * Objects that want to know when the model changes can implement our interface
 * OnChangeListener and register itself as an observer. When any property in 
 * our model changes, it calls notifyObservers() which loops through all objects 
 * that have registered themselves and notifies them of a change.
 * 
 */

public class PetVo extends SimpleObservable<PetVo> {
	
	// Initialize the pet for Singleton purposes (One instance of pet throughout entire application).
	private static PetVo pet = new PetVo();
	
	/* Pet type
	 * fox = 1
 	 * panda = 2 
 	 * dog = 3 */
	private int petType;
	private boolean petIsHome;
	/* Pet color
	 * orange = 1
	 * red = 2 */
	private int petColor;
	
	// Pet size and location
	private int width;
	private int height;
	private int xCoord;
	private int yCoord;
	
	// Pet status levels
	private int hungerLevel;
	private int energyLevel;
	private int happinessLevel;
	private int cleanliness;
	private int age;
	
	// Pet last activities
	private Time lastTimeAte;
	private Time lastTimeCleaned;
	private Time lastTimeSlept;
	private Time lastTimePlayedWith;
	
	private Time lastCountDownTime;
	private long timeLeftBeforeRunaway;
	
	// We only want one instance of pet through the entire project. This is known as a Singleton.
	// A private constructor prevents any other class from instantiating PetVo.
	private PetVo() {}
	
	/** Static 'instance' method 
	 * Returns the single existing instance of pet */
	public static PetVo getInstance() {
		return pet;
	}
	
	public int getPetType() {
		return this.petType;
	}
	
	public int getPetColor() {
		return this.petColor;
	}
	public boolean getPetIsHome() {
		return this.petIsHome;
	}

	// Pet has been initially loaded, notify the view.
	public void loadPet(int width, int height, int xCoord, int yCoord, int petType) {
		// Remember that width and height is NOT the center of the pet bitmap, it's the top left.
		this.width = width;
		this.height = height;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.petType = petType;
		
		// Pet is loaded.
		notifyObservers(this);
	}
	
	public void setPetIsHome(boolean bool) {
		this.petIsHome = bool;
	}
	public void setXCoord(int x) {
		this.xCoord = x;
		notifyObservers(this);
	}
	public void setYCoord(int y) {
		this.yCoord = y;
		notifyObservers(this);
	}
	public void setXYCoord(int x, int y) {
		this.xCoord = x;//-(width/2);
		this.yCoord = y;//-(height/2);
		notifyObservers(this);
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getXCoord() {
		return this.xCoord;
	}
	
	public int getYCoord() {
		return this.yCoord;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void setCurrentTime(Time t)
	{
		this.lastCountDownTime = t;
	}
	
	public Time getLastSavedTime()
	{
		return this.lastCountDownTime;
	}
	public void setCurrentRunawayTimeLeft(long s)
	{
		this.timeLeftBeforeRunaway = s;
	}
	
	public long getRunawayTimeLeft()
	{
		return this.timeLeftBeforeRunaway;
	}
	
	// Chow down
	public void eatFood(int speed) {
		notifyObservers(this);
	}
	
	// Go to sleep
	public void sleep(int desiredSleepTime) {
		notifyObservers(this);
	}
	
	// Wake up
	public void wakeUp() {
		notifyObservers(this);
	}
	
	// Take a poop
	public void poop(int quantity) {
		notifyObservers(this);
	}
	
	// Move to a random location
	public void move(int x, int y) {
		notifyObservers(this);
	}
	
	public void jump(int x, int height) {
		notifyObservers(this);
	}
	
	public void roll(int x) {
		notifyObservers(this);
	}
	
	public void justDraw() {
		notifyObservers(this);
	}

}