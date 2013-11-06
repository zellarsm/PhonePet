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
	
	public int aPetwidth;
	public int aPetheight;
	
	private Bitmap acc_unicorn;
	
	private boolean hitbox_head = false;
	private String h_head;
	private boolean hitbox_neck = false;
	private String h_neck;
	
	public AccessorizeView(Context context, AttributeSet attrs) {
		super(context, attrs); 
		
		petPoint = new Point();
		
		// Default mBoard to the background image.
		mBackground = BitmapFactory.decodeResource(getResources(), R.drawable.red_curtain_light);
		
		// Default mPet to the pet image.
		mPet = BitmapFactory.decodeResource(getResources(), R.drawable.foxx);
		
		// Set all the accessories here.
		acc_unicorn = BitmapFactory.decodeResource(getResources(), R.drawable.acc_unicorn_horn);
		
		// Get preferences file
		sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		aPetwidth = 2*sharedPref.getInt("petWidth", 0);
		aPetheight = 2*sharedPref.getInt("petHeight", 0);
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Log.v("super ondraw", "accessorizeView");
		
		// Set the layout parameters
		this.setLayoutParams(new LinearLayout.LayoutParams(sharedPref.getInt("backgroundWidth", 0), sharedPref.getInt("backgroundHeight", 0)));
		
		// Build environment & scale it to the size of our screen.
    	mBackground = Bitmap.createScaledBitmap(mBackground, sharedPref.getInt("backgroundWidth", 0), sharedPref.getInt("backgroundHeight", 0), true);
		
		canvas.drawBitmap(mBackground, 0, 0, null);
		
		canvas.drawBitmap(Bitmap.createScaledBitmap(mPet, 2*sharedPref.getInt("petWidth", 0), 2*sharedPref.getInt("petHeight", 0), true),
				petPoint.x, petPoint.y, null);
		
		Log.v("hitbox_head bool", Boolean.toString(hitbox_head));
		if(hitbox_head)
		{
			Log.v("hatwidth", Integer.toString(sharedPref.getInt("hatWidth", 0)));
			canvas.drawBitmap(Bitmap.createScaledBitmap(acc_unicorn, sharedPref.getInt("hatWidth", 0), sharedPref.getInt("hatHeight", 0), true),
					(2*aPetwidth)+sharedPref.getFloat("hat_xCoord", 0), (2*aPetheight)+sharedPref.getFloat("hat_yCoord", 0), null);
		}
		
	}
	
	// Draw the pet on the bitmap
	public void drawPet(int x, int y) {
		petPoint.x = x;
		petPoint.y = y;
		
		// Calls onDraw; redraws the board and then deletes the old one.
		this.invalidate();
	}
	
	public void drawAccessories(String head, String neck) 
	{
		if(head == null) hitbox_head = false;
		else { 
			Log.v("head", head);
			hitbox_head = true;
		}
		if(neck == null) hitbox_neck = false;
		Log.v("bitch", "Draw accessories!");
		
		this.invalidate();
	}
	
	public void drawMenuButtons() {
		
	}
	
}
