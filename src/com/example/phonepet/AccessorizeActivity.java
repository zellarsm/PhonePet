package com.example.phonepet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.example.vos.PetVo;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class AccessorizeActivity extends Activity {
	private PetVo pet;
	private ImageView petImage;
	private LayerDrawable layerDrawable;
	
	// Layer 0: Original pet bitmap
	// Layer 1: Tie region
	// Layer 2: glasses region
	// Layer 3: hat region
	// Layer 4: mustache region
	Drawable[] layers = new Drawable[5];
	Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT); // Use for placeholder for non-used layers
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accessorize);

		pet = PetVo.getInstance();
		
		ImageButton topHatButton = (ImageButton)findViewById(R.id.topHat);
		ImageButton unicornButton = (ImageButton)findViewById(R.id.unicornHorn);
		ImageButton glassesButton = (ImageButton)findViewById(R.id.glasses);
		ImageButton blackTieButton = (ImageButton)findViewById(R.id.blackTie);
		ImageButton redTieButton = (ImageButton)findViewById(R.id.redTie);
		ImageButton blueTieButton = (ImageButton)findViewById(R.id.blueTie);
		ImageButton bowTieButton = (ImageButton)findViewById(R.id.bowtie);
		Button saveButton = (Button)findViewById(R.id.saveButton);
		Button clearButton = (Button)findViewById(R.id.clearButton);

		blackTieButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				layers[1] = getResources().getDrawable(R.drawable.acc_black_tie);
				updateView();
			}
		});
		redTieButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				layers[1] = getResources().getDrawable(R.drawable.acc_red_tie);
				updateView();
			}
		});
		blueTieButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				layers[1] = getResources().getDrawable(R.drawable.acc_blue_tie);
				updateView();
			}
		});
		bowTieButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				layers[1] = getResources().getDrawable(R.drawable.acc_black_bowtie);
				updateView();
			}
		});
		
		unicornButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				layers[2] = getResources().getDrawable(R.drawable.acc_unicorn_horn);
				updateView();
			}
		});
		
		topHatButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Bitmap b = ((BitmapDrawable)getResources().getDrawable(R.drawable.acc_top_hat)).getBitmap();
				//b= Bitmap.createScaledBitmap(b, pet.getWidth()/4, pet.getHeight()/4, true);
				layers[2] = getResources().getDrawable(R.drawable.acc_top_hat);//new BitmapDrawable(getResources(), b);
				updateView();
			}
		});
		
		glassesButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				layers[3] = getResources().getDrawable(R.drawable.acc_glasses);
				updateView();
			}
		});
		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.v("goite", " ");
				boolean saved = saveImage();
				
				if (saved) {
					Log.v("save successful", ":)");
					// Save successful, return to Home.
					finish();
				}
				else {
					Log.v("save unsuccessul", "=(");
				}
			}
		});
		
		clearButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				layers[1] = transparentDrawable;
				
				// Get selected hat accessory, if any.
				layers[2] = transparentDrawable;
				
				// Get selected glasses accessory. if any.
				layers[3] = transparentDrawable;
				
				// Get selected mustache accessory, if any.
				layers[4] = transparentDrawable;
				
				updateView();
			}
		});
		// Set up the image view.
		petImage = (ImageView)findViewById(R.id.pet_image);
//petImage.setLayoutParams(new RelativeLayout.LayoutParams(pet.getWidth()*4, pet.getHeight()*4));
		// Get the original bitmap image
		layers[0] = new BitmapDrawable(
						getResources(),
						Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), getPetDrawableInt()),
						pet.getWidth() * 2,
						pet.getHeight() * 2,
						true));
		
		// Get selected tie accessory, if any.
		layers[1] = transparentDrawable;
		
		// Get selected hat accessory, if any.
		layers[2] = transparentDrawable;
		
		// Get selected glasses accessory. if any.
		layers[3] = transparentDrawable;
		
		// Get selected mustache accessory, if any.
		layers[4] = transparentDrawable;
		
		// Display the naked fox.
		updateView();
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accessorize, menu);
		return true;
	}

	
	private void updateView() {
		// Create new layer drawable to hold all layers.
		layerDrawable = new LayerDrawable(layers);
		
		// TODO: Set location of each layer when we have good pet images
		// Set location of each layer.
		// Layer 1: tie
		layerDrawable.setLayerInset(1, 0, pet.getHeight()+pet.getHeight()/10, pet.getHeight()/10, pet.getHeight()/6); //pet.getWidth()/2, pet.getHeight()+pet.getHeight()/10, 0, pet.getHeight()/8);
		
		// Layer 2: hat
		layerDrawable.setLayerInset(2, 0, -pet.getHeight(), 0, pet.getHeight());
		
		// Layer 3: glasses
		layerDrawable.setLayerInset(3, pet.getWidth()/4, 30, pet.getWidth()/2,  pet.getHeight());
		
		petImage.setImageDrawable(layerDrawable);
		
	}
	

	private int getPetDrawableInt() {
		int type = pet.getPetType();
		if (type == 1) {
			// Pet is a fox
			return R.drawable.foxx;
		}
		else if (type == 2) {
			// Pet is a panda
			
		}
		
		return 0;
	}
	
	/**
	 * Saves accessorized bitmap to SD card
	 * 
	 * @return true if save was successful
	 */
	private boolean saveImage() {		
		
		// Create the bitmap using the next 4 lines of code.
		Bitmap bitmap = Bitmap.createBitmap(layerDrawable.getIntrinsicWidth(), layerDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		layerDrawable.setBounds(0,0, canvas.getWidth(), canvas.getHeight());
		layerDrawable.draw(canvas);
		
		// Save bitmap of pet to sd card.
		String petStoragePath = Environment.getExternalStorageDirectory().toString(); // Get path
		File petStorageDirectory = new File(petStoragePath + "/PhonePet/petBitmap/"); // Get directory
		petStorageDirectory.mkdirs();
		
		String fname = "pet";
		File file = new File(petStorageDirectory, fname);
		if (file.exists()) file.delete(); // Allows overwriting
		try { 
			FileOutputStream out = new FileOutputStream(file);
			bitmap.compress(CompressFormat.PNG, 100, out); // Save it
		
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

}
