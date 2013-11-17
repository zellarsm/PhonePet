package com.example.phonepet;

import com.example.vos.PetVo;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.view.Menu;

public class SleepActivity extends Activity {

	CountDownTimer sleepTimer;
	private PetVo pet; // Pet
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sleep);
		
		pet = PetVo.getInstance();
		
		
		// Duration of four hours, tick every minute.
		sleepTimer = new CountDownTimer(pet.getSleepDuration(), 1000)
		{
			public void onFinish()
			{
				pet.setPetIsSleeping(false);
		        finish();
			}

			@Override
			public void onTick(long arg0){}
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
