package com.example.vos;

import android.text.format.Time;
import android.util.Log;

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
	private String petName;
	
	private boolean petIsHome;
	private boolean petIsSleeping;
	private boolean petIsEating;
	private boolean petIsPooping;
	private boolean petIsTransparent;
	private boolean petHasBall;
	
	/* Pet color
	 * orange = 1
	 * red = 2 */
	private int petColor;
	private int drawableNum; // The integer of the pet's drawable png
	
	// Pet size and location
	private int width;
	private int height;
	private int xCoord;
	private int yCoord;
	
	// Pet status levels
	private float hungerLevel; // 100% hunger, pet is full.
	private float happinessLevel; // 100% happiness, pet is happy.
	private int  petDirtAmt, maxDirt = 10;



	private int moveCount=1, moveLimit = 15;

	private boolean cleaning;
	private int age;
	
	// Pet last activities
	private long lastTimeAte;
	private Time lastTimeCleaned;
	private Time lastTimeSlept;
	private long lastTimePlayedWith;
	
	private Time lastCountDownTime;
	private long timeLeftBeforeRunaway;
	
	private final int TIME_UNITL_NEXT_SLEEP = 10 * 60 * 1000; // Pet sleeps every ten hours.
	private final int SLEEP_DURATION = 4 * 60 * 1000; // Pet sleeps for four hours.
	private final long DEFAULT_RUNAWAY_TIME_START = (60*60*24* 1000)*7/2; //3.5days
	private final long DEFAULT_RUNAWAY_NOTIF_TIME = (60*60*24* 1000)*3/2; // 1.5 days remaining on clock.
	private final long DEFAULT_STATUS_TIMER_LENGTH = 21600000; // 6 hours.
	private final long FEED_PET_TIMER_INCREMENT = DEFAULT_STATUS_TIMER_LENGTH/4;
	
	private int happyNotification;
	private int hungryNotification;
	private int runawayNotification;
	private int bathNotification;
	private int poopNotification;
	
	
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
	public boolean getPetIsSleeping() {
		return this.petIsSleeping;
	}
	public boolean getPetIsEating() {
		return this.petIsEating;
	}
	public boolean getPetIsPooping() {
		return this.petIsPooping;
	}
	public boolean getPetHasBall() {
		return this.petHasBall;
	}
	
	// Pet has been initially loaded, notify the view.
	public void loadPet(int width, int height, int xCoord, int yCoord, int petType, int drawable, String name) {
		// Remember that width and height is NOT the center of the pet bitmap, it's the top left.
		this.petName = name;
		this.width = width;
		this.height = height;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.petType = petType;
		this.drawableNum = drawable;
		
		this.petIsEating = false;
		this.petIsPooping = false;
		this.petDirtAmt = 0;
		this.moveCount = 1;
		
		this.happyNotification = -1;
		this.hungryNotification = -1;
		this.runawayNotification = -1;
		this.bathNotification = -1;
		this.poopNotification = -1;
		// Pet is loaded.
		notifyObservers(this);
	}
	
	public String getPetName()
	{
		return this.petName;
	}
	
	public void setPetIsHome(boolean bool) {
		this.petIsHome = bool;
	}
	public void setPetIsSleeping(boolean bool) {
		this.petIsSleeping = bool;
	}
	public void setPetIsEating(boolean bool) {
		this.petIsEating = bool;
	}
	public void setPetIsPooping(boolean bool) {
		this.petIsPooping = bool;
	}
	public void setPetHasBall(boolean bool) {
		this.petHasBall = bool;
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
		this.xCoord = x;
		this.yCoord = y;
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
	
	/** Pet status getters */
	public float getPetHappiness()
	{
		return this.happinessLevel;
	}
	public float getPetHunger()
	{
		return this.hungerLevel;
	}
	/** Pet status setters */
	public void setPetHappiness(float ha)
	{
		this.happinessLevel = ha;
	}
	public void setPetHunger(float hu)
	{
		this.hungerLevel = hu;
	}

	
	/** Time getters */
	public int getTimeUntilNextSleep()
	{
		return this.TIME_UNITL_NEXT_SLEEP;
	}
	public int getSleepDuration()
	{
		return this.SLEEP_DURATION;
	}
	public long getDefaultRunawayTime()
	{
		return this.DEFAULT_RUNAWAY_TIME_START;
	}
	public long getDefaultRunawayNoticiationTime()
	{
		return this.DEFAULT_RUNAWAY_NOTIF_TIME;
	}
	public long getDefaultStatusTime()
	{
		return this.DEFAULT_STATUS_TIMER_LENGTH;
	}
	public long getFeedPetTimerIncrement()
	{
		return this.FEED_PET_TIMER_INCREMENT;
	}
	public long getLastTimeAte()
	{
		return this.lastTimeAte;
	}
	public long getLastTimePlayedWith()
	{
		return this.lastTimePlayedWith;
	}
	
	
	/** Time setters */
	public void setLastTimeAte(long t)
	{
		this.lastTimeAte = t;
	}
	public void setLastTimePlayedWith(long t)
	{
		this.lastTimePlayedWith = t;
	}
	
	/** Notification getters */
	public int getHappyNotif()
	{
		return this.happyNotification;
	}
	public int getHungryNotif()
	{
		return this.hungryNotification;
	}
	public int getRunawayNotif()
	{
		return this.runawayNotification;
	}
	public int getBathNotif()
	{
		return this.bathNotification;
	}
	public int getPoopNotif()
	{
		return this.poopNotification;
	}
	
	/** Notification setters */
	public void setHappyNotif(int i)
	{
		this.happyNotification = i;
	}
	public void setHungryNotif(int i)
	{
		this.hungryNotification = i;
	}
	public void setRunawayNotif(int i)
	{
		this.runawayNotification = i;
	}
	public void setBathNotif(int i)
	{
		this.bathNotification = i;
	}
	public void setPoopNotif(int i)
	{
		this.poopNotification = i;
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
	public int getPetDrawable() {
		return this.drawableNum;
	}


	/** Cleaning functions */

	public void makeDirty(){
		if(moveCount%moveLimit == 0 ){
			if(petDirtAmt < maxDirt){
				petDirtAmt++;		
			}
		}
	}
	public void makeClean(){
		if(petDirtAmt > 0){
			petDirtAmt--;
		}
	}

	public boolean isCleaning(){

		return cleaning;
	}

	public void cleaning(){
		cleaning = true;
	}

	public int dirtyness(){
		return petDirtAmt;
	}

	public void moveInc(){
		moveCount++;
		makeDirty();
	}

	public int getMoves(){
		return moveCount;
	}

	public void notCleaning(){
		cleaning = false;
	}

	public void setPetIsTransparent(boolean b) {
		petIsTransparent = true;	
	}

	/** End Cleaning Functions */
}