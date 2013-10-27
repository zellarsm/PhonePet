package com.example.phonepet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;

public class CreateActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);
		
		// Variables
		String fileName = "preferences";
		
		// Get the preferences file and create the editor
		SharedPreferences sharedPref = getSharedPreferences(fileName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		Log.v("got here", "10");
		// Theoretically there should be no saved information at this point. Clear for development purposes.
		editor.clear();
		editor.commit();
		
		// This is a good place to save the user's display metrics.
		// Get metrics of display
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int screenWidth = metrics.widthPixels;
		int screenHeight = metrics.heightPixels;
		int playgroundWidth = screenWidth;
		int playgroundHeight = (int)(screenHeight * 0.8);	
		int petWidth = playgroundWidth/10;
		int petHeight = screenHeight/10;
		
		// User creates pet
	//	Log.v("crscreenWidth", Integer.toString(screenWidth));
	//	Log.v("crscreenHeight", Integer.toString(screenHeight));;
	//	Log.v("crplaygroundWidth", Integer.toString(playgroundWidth));
	//	Log.v("crplaygroundHeight", Integer.toString(playgroundHeight));
	//	Log.v("crpetWidth", Integer.toString(petWidth));
	//	Log.v("crpetHeight", Integer.toString(petHeight));
		
		// Pet has been created. Save all new information
		editor.putBoolean("pet_exists", true);
		editor.putInt("screenWidth", screenWidth);
		editor.putInt("screenHeight", screenHeight);
		editor.putInt("playgroundWidth", playgroundWidth);
		editor.putInt("playgroundHeight", playgroundHeight);
		editor.putInt("petWidth", petWidth);
		editor.putInt("petHeight", petHeight);
		editor.putInt("petX", (playgroundWidth/2)-(petWidth/2));
		editor.putInt("petY", (int)(playgroundHeight*.8));
		
		editor.commit();
		
		// Go to Home activity
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create, menu);
		return true;
	}

}
