package com.example.views;

import com.example.phonepet.R;
import com.example.utils.Point;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class HomeView extends View {

	private Bitmap mBackground = null;
	private Bitmap mPet = null;
	private Point petPoint = null;
	private String fileName = "preferences";
	private SharedPreferences sharedPref;
	
	public HomeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		petPoint = new Point();
		
		// Default mBoard to the background image.
		mBackground = BitmapFactory.decodeResource(getResources(), R.drawable.templatebackground);
		
		// Default mPet to the pet image.
		mPet = BitmapFactory.decodeResource(getResources(), R.drawable.smileyface);
		
		// Get preferences file
		sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

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
		this.setLayoutParams(new LinearLayout.LayoutParams(sharedPref.getInt("backgroundWidth", 0), sharedPref.getInt("backgroundHeight", 0)));
		
		// Build environment & scale it to the size of our screen.
    	mBackground = Bitmap.createScaledBitmap(mBackground, sharedPref.getInt("backgroundWidth", 0), sharedPref.getInt("backgroundHeight", 0), true);
		
		canvas.drawBitmap(mBackground, 0, 0, null);
		
		canvas.drawBitmap(Bitmap.createScaledBitmap(mPet, sharedPref.getInt("petWidth", 0), sharedPref.getInt("petHeight", 0), true),
				petPoint.x, petPoint.y, null);
	}
	
	// Draw the pet on the bitmap
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
	
	public void drawMenuButtons() {
		
	}
	
}
