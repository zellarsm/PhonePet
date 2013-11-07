package com.example.utils;

import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;

public class RunawayCountdownTimer extends CountDownTimer
{
	private long timeElapsed;
	private long startTime = 60000;
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
		Log.v("getTimeLeft", Long.toString(this.timeLeft));
		return this.timeLeft;
	}
	

	// Constructor
    public RunawayCountdownTimer(long startTime, long interval)
    {
        super(startTime, interval);
    }

    @Override
    public void onFinish()
    {
    	// Pet runs away
    	Log.v("onFinish", "countdown has completed!!");
        timerCompleted = true;
    	
    	/*
        Intent i = new Intent();
    	i.setClassName("com.example.phonepet", "com.example.phonepet.RunawayActivity");
    	startActivityForResult(i, 0);
    	*/
    	
    }

    @Override
    public void onTick(long millisUntilFinished)
    {
    	timeLeft = millisUntilFinished;
        timeElapsed = startTime - millisUntilFinished;
    	Log.v("ticking, time left = ", Long.toString(startTime - timeElapsed));
    }

}