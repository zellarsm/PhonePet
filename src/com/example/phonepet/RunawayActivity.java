package com.example.phonepet;

import com.example.controllers.PetController;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class RunawayActivity extends Activity {

	String fileName = "preferences";
	Button restartB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_runaway);
		
		restartB = (Button)findViewById(R.id.RunawayOKButton);
		
		Log.v("OH NO! YOUR PET RAN AWAY!", "TRY AGAIN BAD OWNER!");
		
		
		// Button listener
		restartB.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// Clear the preferences file.
		    	SharedPreferences sharedPref = getSharedPreferences(fileName, Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.clear();
				editor.commit();
				
				// Restart Application
				Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.runaway, menu);
		return true;
	}

}
