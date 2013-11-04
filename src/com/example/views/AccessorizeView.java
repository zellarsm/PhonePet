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

public class AccessorizeView extends View {

	private Bitmap mBackground = null;
	private Bitmap mPet = null;
	private Point petPoint = null;
	private String fileName = "preferences";
	private SharedPreferences sharedPref;
	
	public AccessorizeView(Context context, AttributeSet attrs) {
		super(context, attrs); 
		
		petPoint = new Point();
		
		// Default mBoard to the background image.
		mBackground = BitmapFactory.decodeResource(getResources(), R.drawable.red_curtain);
		
		// Default mPet to the pet image.
		mPet = BitmapFactory.decodeResource(getResources(), R.drawable.foxx);
		
		// Get preferences file
		sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
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
		
		canvas.drawBitmap(Bitmap.createScaledBitmap(mPet, 2*sharedPref.getInt("petWidth", 0), 2*sharedPref.getInt("petHeight", 0), true),
				petPoint.x, petPoint.y, null);
	}
	
	// Draw the pet on the bitmap
	public void drawPet(int x, int y) {
		petPoint.x = x;
		petPoint.y = y;
		
		// Calls onDraw; redraws the board and then deletes the old one.
		this.invalidate();
	}
	
	public void drawMenuButtons() {
		
	}
	
}
