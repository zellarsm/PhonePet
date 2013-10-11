package com.example.phonepet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class HomeView extends View {

	private Bitmap mBoard = null;
	private Bitmap mPet = null;
	private Point petPoint = null;
	
	public HomeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		petPoint = new Point();
		
		// Default mBoard to the background image.
		mBoard = BitmapFactory.decodeResource(getResources(), R.drawable.templatebackground);
		
		// Default mPet to the pet image.
		mPet = BitmapFactory.decodeResource(getResources(), R.drawable.smileyface);
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		// Set the layout parameters
		this.setLayoutParams(new LinearLayout.LayoutParams(HomeActivity.playgroundWidth, HomeActivity.playgroundHeight));
		
		// Build board & scale it to the size of our screen.
    	mBoard = Bitmap.createScaledBitmap(mBoard, HomeActivity.playgroundWidth, HomeActivity.playgroundHeight, true);
		
		canvas.drawBitmap(mBoard, 0, 0, null);
		
		canvas.drawBitmap(Bitmap.createScaledBitmap(mPet, (int)this.getWidth()/4, (int)this.getHeight()/6, true),
				petPoint.x, petPoint.y, null);
	}
	
	// Draw the pet on the bitmap
	public void drawPet(float x, float y) {
		petPoint.x = x;
		petPoint.y = y;
		
		// Calls onDraw; redraws the board and then deletes the old one.
		this.invalidate();
			
	}

	// Update the pet on the bitmap if it's moved.
	public void dragPet(float x, float y) {
		petPoint.x = x;
		petPoint.y = y;
				
		this.invalidate();
	}
	
	public void drawMenuButtons() {
		
	}
	
}
