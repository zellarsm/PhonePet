package com.example.utils;

import android.os.CountDownTimer;
import android.util.Log;

public class StatusCountdownTimer extends CountDownTimer
{
	private long timeElapsed;
	private long startTime;
	private long timeLeft;
	private long defaultStartTime;
	
	// Constructor
	public StatusCountdownTimer(long millisInFuture, long defaultTime, long countDownInterval)
	{
		super(millisInFuture, countDownInterval);
		this.startTime = millisInFuture;
		this.defaultStartTime = defaultTime;
	}

	@Override
	public void onFinish()
	{
		this.timeLeft = 0;
	}

	@Override
	public void onTick(long millisUntilFinished)
	{
		this.timeLeft = millisUntilFinished;
        this.timeElapsed = this.defaultStartTime - millisUntilFinished;
        
        if(this.timeLeft >= getThreeQuartersTime())
		{
			// Timer barely started.
        	highTime();
		}
		else if(this.timeLeft >= getHalfTime())
		{
			// Timer is not even half completed.
			halfTime();
		}
		else if(this.timeLeft >= getOneQuarterTime())
		{
			// Getting lower.
			lowTime();
		}
		else
		{
			// OMG ATTEND TO ME.
			nowTime();
		}
	
	
	}

	public long getStartTime()
	{
		return this.startTime;
	}
	public long getTimeElapsed()
	{
		return this.timeElapsed;
	}
	public long getTimeLeft()
	{
		return this.timeLeft;
	}
	
	public float calculatePercentage()
	{
		float temp = this.timeLeft * 100;
		temp /= this.defaultStartTime;
		return temp;
	}
	
	
	private void highTime()
	{
		//Log.v("hightime", "good owner means happy pet");
	}
	private void halfTime()
	{
		//Log.v("halfTime", "hey where are you");
	}
	private void lowTime()
	{
		//Log.v("lowTime", "ARE YOU EVER COMING BACK?!");
	}
	private void nowTime()
	{
		//Log.v("nowtime", "ATTENTION NAOOOOOOOOOOO");
	}
	
	private float getThreeQuartersTime()
	{
		return (float)(3*((float)(this.defaultStartTime) / 4));
	}
	private float getHalfTime()
	{
		return (float)((float)(this.defaultStartTime) / 2);
	}
	private float getOneQuarterTime()
	{
		return (float)((float)(this.defaultStartTime) / 4);
	}
	
} // End class StatusCountdownTimer
