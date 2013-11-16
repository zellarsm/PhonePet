package com.example.phonepet;

import com.example.controllers.PetController;
import com.example.utils.RunawayCountdownTimer;
import com.example.vos.PetVo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

public class SleepActivity extends Activity {

	RunawayCountdownTimer sleepTimer;
	private PetVo pet; // Pet
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sleep);
		
		pet = PetVo.getInstance();
		
		
		// Duration of four hours, tick every minute.
		sleepTimer = new RunawayCountdownTimer(pet.SLEEP_DURATION, 1000)
		{
			public void onFinish()
			{
		        finish();
			}
		};
		sleepTimer.start();

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sleep, menu);
		return true;
	}
	
} // End class SleepActivity
