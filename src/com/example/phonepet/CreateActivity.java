package com.example.phonepet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
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
		boolean poopExists = true;
		
		// User creates pet
		
		// Pet has been created. Save all new information
		editor.putBoolean("pet_exists", true);
		editor.putInt("screenWidth", screenWidth);
		editor.putInt("screenHeight", screenHeight);
		editor.putInt("backgroundWidth", backgroundWidth);
		editor.putInt("backgroundHeight", backgroundHeight);
		editor.putInt("petType", 1); // Pet is a fox
		editor.putInt("petWidth", petWidth);
		editor.putInt("petHeight", petHeight);
		editor.putInt("petX", (backgroundWidth/2)-(petWidth/2));
		editor.putInt("petY", backgroundHeight*8/10);
		//editor.putBoolean("poop", poopExists);
		
		editor.commit();
		
		// Create the bitmap of the pet the user chose and save it to sd card.
		Bitmap petBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.foxx);
		storeImage(petBitmap);

		// Go to Home activity
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		finish();
	}

	private boolean storeImage(Bitmap imageData) {
		// Save bitmap of pet to sd card.
		// Get path to sd card.
		String petStoragePath = Environment.getExternalStorageDirectory().toString();
		File petStorageDirectory = new File(petStoragePath + "/PhonePet/petBitmap/");
		
		// Create storage directories, if they don't exist.
		petStorageDirectory.mkdirs();
		String fname = "pet";
		File file = new File(petStorageDirectory, fname);
		
		if(file.exists()) file.delete(); // Allows overwriting old bitmap
		try { 
			FileOutputStream out = new FileOutputStream(file);

			imageData.compress(CompressFormat.PNG, 100, out);
		
			out.flush();
			out.close();
			
		} catch (FileNotFoundException e) {
			Log.v("Error:", "Error saving image file: " + e.getMessage());
			return false; 
		} catch (IOException e) {
			Log.v("Error:", "Error saving image file: " + e.getMessage());
			return false;
		}
		
		return true;
	} 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create, menu);
		return true;
	}

}
