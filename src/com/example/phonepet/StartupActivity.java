package com.example.phonepet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;

public class StartupActivity extends Activity {
	boolean checkForPreviousPet(){return true;};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startup);
		
		String fileName = "preferences";
		Intent intent;
		
		// Check if a pet exists on the user's phone
		SharedPreferences sharedPref = getSharedPreferences(fileName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.clear();
		//editor.commit(); // Clear for development purposes
		
		Boolean petExists = sharedPref.getBoolean("pet_exists", false);
		
		if (petExists)
		{
			Log.v("startupp", "going to home activity");
			// Pet exists, go Home
			intent = new Intent(this, HomeActivity.class);
			startActivity(intent);
		}
		else
		{
			Log.v("startupp2", "going to create new pet");
			// User does not own a pet, allow them to create one.	
			intent = new Intent(this, CreateActivity.class);
			startActivity(intent);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.startup, menu);
		return true;
	}

}
