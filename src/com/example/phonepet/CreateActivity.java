package com.example.phonepet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
		
		// User creates pet
		
		
		// Pet has been created.
		editor.putBoolean("pet_exists", true);
		editor.commit();
		
		// Go to Home activity
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create, menu);
		return true;
	}

}
