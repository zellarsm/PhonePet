package com.example.views;

import java.util.ArrayList;
import java.util.List;

import com.example.phonepet.R;
import com.example.utils.Point;
import com.example.vos.Poop;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
 
public class HomeView extends View {

	private Bitmap mBackground, mPet, mCloud, mPoop;
	private Point petPoint = null;
	private String fileName = "preferences";
	private SharedPreferences sharedPref;
	private int backgroundWidth, backgroundHeight, petWidth, petHeight;
	private int cloud1X, cloud2X;
	private List<com.example.vos.Poop> myList = null;
	private boolean poopExists;
	public int poop1x,poop1y, poop2x, poop2y, poop3x, poop3y;
	Thread moveT;
	
	public HomeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		petPoint = new Point();
		
		// Get preferences file
		sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		
		// Get constant values from preference file
		backgroundWidth = sharedPref.getInt("backgroundWidth", 0);
		backgroundHeight = sharedPref.getInt("backgroundHeight", 0);
		
		petWidth = sharedPref.getInt("petWidth", 0);
		petHeight = sharedPref.getInt("petHeight", 0);
		
		// Set the cloud positions
		cloud1X = backgroundWidth / 2;
		cloud2X = 10;
		poopExists = false;
		
		// Load bitmaps
		loadBitmaps();
		
		// Start a thread that simulates cloud movement.
		Thread moveThread = new MoveClouds();
		moveThread.start();
		
//		Log.v("hvscreenWidth", Integer.toString(sharedPref.getInt("screenWidth", 10)));
//		Log.v("hvscreenHeight", Integer.toString(sharedPref.getInt("screenHeight", 10)));
//		Log.v("hvmenuWidth", Integer.toString(sharedPref.getInt("menuWidth", 10)));
//		Log.v("hvmenuHeight", Integer.toString(sharedPref.getInt("menuHeight", 10)));
//		Log.v("hvplaygroundWidth", Integer.toString(sharedPref.getInt("playgroundWidth", 10)));
//		Log.v("hvplaygroundHeight", Integer.toString(sharedPref.getInt("playgroundHeight", 10)));
//		Log.v("hvpetWidth", Integer.toString(sharedPref.getInt("petWidth", 10)));
//		Log.v("hvpetHeight", Integer.toString(sharedPref.getInt("petHeight", 10)));
		//Log.v("hvpetWidth", Integer.toString(sharedPref.getInt("petWidth", 10)));
		//Log.v("hvpetHeight", Integer.toString(sharedPref.getInt("petHeight", 10)));
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		// Set the layout parameters
		this.setLayoutParams(new LinearLayout.LayoutParams(backgroundWidth, backgroundHeight));
		
		// Draw enviroment
		canvas.drawBitmap(mBackground, 0, 0, null);

		// Draw clouds.
		canvas.drawBitmap(mCloud, cloud1X, 10, null);
		canvas.drawBitmap(mCloud, cloud2X, 30, null);
		
		// Draw pet
		canvas.drawBitmap(mPet, petPoint.x, petPoint.y, null);
		
		//canvas.drawBitmap(mPoop,e.getX(), e.getY(), null);
		if(poopExists) {
			canvas.drawBitmap(mPoop, poop1x, poop1y, null);
			canvas.drawBitmap(mPoop, poop2x, poop2y, null);
			canvas.drawBitmap(mPoop, poop3x, poop3y, null);
		}
	}
	
	/**
	 * Handles cloud movement at a time interval.
	 * If a cloud moves off screen it will eventually be redrawn on the opposite side.
	 * 
	 * @author Michael
	 *
	 */
	private class MoveClouds extends Thread {
		@Override
		public void run() {
			while (true) {
				// Cloud 1 just went off screen
				if (cloud1X > backgroundWidth + backgroundWidth/20)
					// Reset the cloud's position to the left off-screen only if the other cloud is visible (x > 0).
					// This prevents clouds from being drawn on top of each other which creates ugly bitmap problems.
					if (cloud2X > 0)
						// Place cloud somewhere randomly to the left offscreen.
						cloud1X = -1 * (backgroundWidth/4 + (int)(Math.random() * ((backgroundWidth/1.5 - backgroundWidth/4) + 1)));

				// Cloud 2 just went off screen
				if (cloud2X > backgroundWidth - backgroundWidth/20)
					if (cloud1X > 0)
						cloud2X = -1 * (backgroundWidth/4 + (int)(Math.random() * ((backgroundWidth/1.5 - backgroundWidth/4) + 1)));
				
				// Move clouds 2px to the left. 
				cloud1X += 2;
				cloud2X += 2;
				
				// Sleep thread for 2 seconds.
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	// Draw everything on the bitmap
	public void drawPet(int x, int y) {
		petPoint.x = x;
		petPoint.y = y;
		
		// Calls onDraw; redraws the board and then deletes the old one.
		this.invalidate();
	}


	// Update the pet on the bitmap if it's moved.
	public void dragPet(int x, int y) {
		petPoint.x = x;
		petPoint.y = y;
				
		this.invalidate();
	}
	
	// Load pet bitmap from file
	public void loadBitmaps() {
		// Get pet image from sd card.
		String imageInSD = Environment.getExternalStorageDirectory() + "/PhonePet/petBitmap/pet";
		mPet = BitmapFactory.decodeFile(imageInSD);
		mPoop = BitmapFactory.decodeResource(getResources(), R.drawable.poop);
		// Default mBoard to the background image.
		mBackground = BitmapFactory.decodeResource(getResources(), R.drawable.templatebackground);
				
		// Default mCloud to cloud image
		mCloud = BitmapFactory.decodeResource(getResources(), R.drawable.cloud);
		
		// Scale the bitmaps
		mBackground = Bitmap.createScaledBitmap(mBackground, backgroundWidth, backgroundHeight, true); // Environment
		mCloud = Bitmap.createScaledBitmap(mCloud, backgroundWidth/4, backgroundHeight/10, true); // Cloud
		mPet = Bitmap.createScaledBitmap(mPet, petWidth, petHeight, true); // Pet
		mPoop = Bitmap.createScaledBitmap(mPoop, petWidth, petHeight, true);
		
	}

	public void drawPoop() {
		
		//int numPoop = (int)Math.random() * 5;
		
//		Poop temp;
//		myList = new ArrayList<Poop>();
//		for(int i = 0; i < 3; i ++) {
//			
//			temp = new Poop(petHeight, petWidth, (int)Math.random() * backgroundWidth, (int)Math.random() * backgroundHeight);
//			myList.add(temp);
//			
//		}
		
		poop1x = (int)(Math.random() * backgroundWidth);
		poop1y = (int)(Math.random() * backgroundHeight);
		poop2x = (int)(Math.random() * backgroundWidth);
		poop2y = (int)(Math.random() * backgroundHeight);
		poop3x = (int)(Math.random() * backgroundWidth);
		poop3y = (int)(Math.random() * backgroundHeight);
		poopExists = true;		
		
		this.invalidate();
	}
	
	public void removePoop() {
				
		moveT = new MovePoop();
		moveT.start();
		if(!poopExists) {
			moveT.interrupt();
		}
		this.invalidate();
		
	}
	
	public void dragPoop(int poop1x,int poop1y, int poop2x, int poop2y, int poop3x, int poop3y) {
		
		this.poop1x = poop1x;
		this.poop1y = poop1y;
		this.poop2x = poop2x;
		this.poop2x = poop2x;
		this.poop3x = poop3x;
		this.poop3x = poop3x;
		
		this.invalidate();
		
	}
	private class MovePoop extends Thread {
			
		public void run() {
			while (poopExists) {
				
				poop1y = poop1y - 50;
				poop2y = poop2y - 50;
				poop3y = poop3y - 50;
				
				if(poop1y > backgroundHeight && poop2y > backgroundHeight && poop3y > backgroundHeight)
					poopExists = false;
				// Sleep thread for 2 seconds.
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		
	}
}
