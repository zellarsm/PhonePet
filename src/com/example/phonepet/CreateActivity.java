package com.example.phonepet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ViewFlipper;

public class CreateActivity extends Activity {
	
	private ViewFlipper viewFlipper;
	private int animalSelection = 1; // 1 fox, 2 panda, 3 dog
	private int colorSelection = 4; // Default orange fox
	private int genderSelection = 1; // Default male
	private String nameSelection = "BUDDY"; // Default Mike
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);
		
		// ViewFlipper handles different layouts
		viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
		viewFlipper.setInAnimation(this, R.anim.in_from_right); // Give it animation
        viewFlipper.setOutAnimation(this, R.anim.out_to_left);
		 
        Button foxButton = (Button)findViewById(R.id.fox_button);
        Button pandaButton = (Button)findViewById(R.id.panda_button);
        Button dogButton = (Button)findViewById(R.id.dog_button);
        Button redButton = (Button)findViewById(R.id.red_color_btn);
        Button greenButton = (Button)findViewById(R.id.green_color_btn);
        Button blueButton = (Button)findViewById(R.id.blue_color_btn);
        Button orangeButton = (Button)findViewById(R.id.orange_color_btn);
        Button brownButton = (Button)findViewById(R.id.brown_color_btn);
        Button whiteButton = (Button)findViewById(R.id.white_color_btn);
        Button yellowButton = (Button)findViewById(R.id.yellow_color_btn);
        Button blackButton = (Button)findViewById(R.id.black_color_btn);
        Button purpleButton = (Button)findViewById(R.id.purple_color_btn);
        Button maleButton = (Button)findViewById(R.id.male_button);
        Button femaleButton = (Button)findViewById(R.id.female_button);
        
        final EditText petNameEditText = (EditText)findViewById(R.id.pet_name);
        Button finishButton = (Button)findViewById(R.id.finish_button);
		
		// User creates pet
        
        // These button are located in 1st view
        foxButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				animalSelection = 1;
				viewFlipper.showNext();
			}
		});
        pandaButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				animalSelection = 2;
				viewFlipper.showNext();
			}
		});
        dogButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				animalSelection = 3;
				viewFlipper.showNext();
			}
		});
        
        // These buttons are located in 2nd view
        redButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				colorSelection = 1;
				viewFlipper.showNext();
			}
		});
        greenButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				colorSelection = 2;  
				viewFlipper.showNext();
			}
		});
        blueButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				colorSelection = 3;
				viewFlipper.showNext();
			}
		});
        orangeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				colorSelection = 4;
				viewFlipper.showNext();
			}
		});
        brownButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				colorSelection = 5;
				viewFlipper.showNext();
			}
		});
        whiteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				colorSelection = 6;
				viewFlipper.showNext();
			}
		});
        yellowButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				colorSelection = 7;
				viewFlipper.showNext();
			}
		});
        blackButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				colorSelection = 8;
				viewFlipper.showNext();
			}
		});
        purpleButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				colorSelection = 9;
				viewFlipper.showNext();
			}
		});
        
        // These buttons are located in 3rd view
        maleButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				genderSelection = 1;
				viewFlipper.showNext();
			}
		});
        
        femaleButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				genderSelection = 2;
				viewFlipper.showNext();
			}
		});
        
        
        finishButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// User has made their pet selection. Save all information and bring them home.

				if (!petNameEditText.getText().toString().equals("")) {
					nameSelection = petNameEditText.getText().toString();
					nameSelection = nameSelection.toUpperCase(Locale.getDefault());
				}
				
				// Get the preferences file and create the editor
				SharedPreferences sharedPref = getSharedPreferences("preferences", Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPref.edit();
				
				// This is a good place to save the user's display metrics.
				// Get metrics of display
				DisplayMetrics metrics = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(metrics);

				int screenWidth = metrics.widthPixels;
				int screenHeight = metrics.heightPixels;
				int backgroundWidth = screenWidth;
				int backgroundHeight = screenHeight * 8/10;	
				int petWidth = backgroundWidth/6;
				int petHeight = screenHeight/6;
				
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
				editor.putInt("petType", animalSelection);
				editor.putInt("petColor", colorSelection);
				editor.putInt("petGender", genderSelection);
				editor.putString("petName", nameSelection);
				editor.putInt("petDrawable", getPetDrawable());
				
				// Pet status stuff.
				editor.putBoolean("petCreation", true);
				
				editor.commit();
				
				// Create the bitmap of the pet the user chose and save it to SD card.
				Bitmap petBitmap = BitmapFactory.decodeResource(getResources(), getPetDrawable());
				// need function to switch between pets?? -CL
				storeImage(petBitmap);

				// Go to Home activity
				goHatch();
			}

		});
        
	}
	
	/**
	 * Returns the integer of the animal drawable requested by the user.
	 *
	 */
	private int getPetDrawable() {
		// Fox
		if (animalSelection == 1) {
			if (colorSelection == 1)
				return R.drawable.acc_fox_red;
			else if (colorSelection == 2)
				return R.drawable.acc_fox_green;
			else if (colorSelection == 3)
				return R.drawable.acc_fox_blue;
			else if (colorSelection == 4)
				return R.drawable.acc_fox_orange;
			else if (colorSelection == 5)
				return R.drawable.acc_fox_brown;
			else if (colorSelection == 6)
				return R.drawable.acc_fox_white;
			else if (colorSelection == 7)
				return R.drawable.acc_fox_yellow;
			else if (colorSelection == 8)
				return R.drawable.acc_fox_black;
			else
				return R.drawable.acc_fox_purple;
		}
		// Panda
		else if (animalSelection == 2) {
			if (colorSelection == 1)
				return R.drawable.acc_panda_red;
			else if (colorSelection == 2)
				return R.drawable.acc_panda_green;
			else if (colorSelection == 3)
				return R.drawable.acc_panda_blue;
			else if (colorSelection == 4)
				return R.drawable.acc_panda_orange;
			else if (colorSelection == 5)
				return R.drawable.acc_panda_brown;
			else if (colorSelection == 6)
				return R.drawable.acc_panda;
			else if (colorSelection == 7)
				return R.drawable.acc_panda_yellow;
			else if (colorSelection == 8)
				return R.drawable.acc_panda;
			else
				return R.drawable.acc_panda_purple;
		}
		// Dog
		else {
			if (colorSelection == 1)
				return R.drawable.acc_dog_red;
			else if (colorSelection == 2)
				return R.drawable.acc_dog_green;
			else if (colorSelection == 3)
				return R.drawable.acc_dog_blue;
			else if (colorSelection == 4)
				return R.drawable.acc_dog_orange;
			else if (colorSelection == 5)
				return R.drawable.acc_dog_brown;
			else if (colorSelection == 6)
				return R.drawable.acc_dog_white;
			else if (colorSelection == 7)
				return R.drawable.acc_dog_yellow;
			else if (colorSelection == 8)
				return R.drawable.acc_dog_black;
			else
				return R.drawable.acc_dog_purple;
		}
	}
	
	private void goHatch() {
		Intent intent = new Intent(this, HatchActivity.class);
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
	public void onBackPressed() {
		if (viewFlipper.getDisplayedChild() == 0) {
			// Cannot go back further. Do nothing
		}
		else {
			// Go back to previous layout
			viewFlipper.setInAnimation(this, R.anim.in_from_left);
	        viewFlipper.setOutAnimation(this, R.anim.out_to_right);
			
	        viewFlipper.showPrevious();
			
			viewFlipper.setInAnimation(this, R.anim.in_from_right);
	        viewFlipper.setOutAnimation(this, R.anim.out_to_left);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create, menu);
		return true;
	}

}
