package com.example.phonepet;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class PlayActivity extends Activity {

	Pet pet;
	
	void selectGame() {};
	void startGame1() {};
	void startGame2() {};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		
		Log.v("Play Activity", "This works!");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play, menu);
		return true;
	}

}

