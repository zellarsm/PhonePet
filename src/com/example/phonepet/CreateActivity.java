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

		// Theoretically there should be no saved information at this point. Clear for development purposes.
		editor.clear();
		editor.commit();
		
		// This is a good place to save the user's display metrics.
		// Get metrics of display
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int screenWidth = metrics.widthPixels;
		int screenHeight = metrics.heightPixels;
		int backgroundWidth = screenWidth;
		int backgroundHeight = screenHeight * 8/10;	
		int petWidth = backgroundWidth/10;
		int petHeight = screenHeight/10;
		
		// User creates pet
//		Log.v("crscreenWidth", Integer.toString(screenWidth));
//		Log.v("crscreenHeight", Integer.toString(screenHeight));;
//		Log.v("crbackgroundWidth", Integer.toString(backgroundWidth));
//		Log.v("crbackgroundHeight", Integer.toString(backgroundHeight));
//		Log.v("crpetWidth", Integer.toString(petWidth));
//		Log.v("crpetHeight", Integer.toString(petHeight));
		
		// Pet has been created. Save all new information
		editor.putBoolean("pet_exists", true);
		editor.putInt("screenWidth", screenWidth);
		editor.putInt("screenHeight", screenHeight);
		editor.putInt("backgroundWidth", backgroundWidth);
		editor.putInt("backgroundHeight", backgroundHeight);
		editor.putInt("petWidth", petWidth);
		editor.putInt("petHeight", petHeight);
		editor.putInt("petX", (backgroundWidth/2)-(petWidth/2));
		editor.putInt("petY", backgroundHeight*8/10);
		
		// Accessorize Activity, currently hardcoded values.
		// TODO: Figure out how to make this dynamic.
		editor.putInt("hatWidth", 50);
		editor.putInt("hatHeight", 50);
		editor.putFloat("hat_xCoord", 15);
		editor.putFloat("hat_yCoord", 135);
		
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
