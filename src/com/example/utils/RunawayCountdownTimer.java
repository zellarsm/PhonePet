package com.example.utils;

import android.os.CountDownTimer;
import android.util.Log;

public class RunawayCountdownTimer extends CountDownTimer
{
	private long timeElapsed;
	private long startT;
	private long timeLeft;
	
	private boolean timerCompleted = false;
	private boolean restartInitiated = false;
	
	// timerCompleted get method
	public boolean isTimerCompleted()
	{
		return this.timerCompleted;
	}
	
	// restartInitiated get method
	public boolean isRestartInitiated()
	{
		return this.restartInitiated;
	}
	
	// restartInitiated set method
	public void setRestartInitiated()
	{
		this.restartInitiated = true;
	}
	
	public long getTimeLeft()
	{
		return this.timeLeft;
	}
	

	// Constructor
    public RunawayCountdownTimer(long startTime, long interval)
    {
    	super(startTime, interval);
    	this.startT = startTime;
    }
    
    @Override
    public void onFinish()
    {
    	// Pet runs away
    	Log.v("onFinish", "countdown has completed!!");
        timerCompleted = true;
    	
    }

    @Override
    public void onTick(long millisUntilFinished)
    {
    	timeLeft = millisUntilFinished;
        timeElapsed = startT - millisUntilFinished;
    	Log.v("ticking, time left = ", Long.toString(startT - timeElapsed));
    }

}