package com.example.views;

import java.util.Hashtable;

import com.example.phonepet.HatchActivity;
import com.example.phonepet.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Environment;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;  
 
public class HatchView extends View {
    private Context mContext;
    int x = -1;
    int y = -1;
    private int backgroundWidth, backgroundHeight, eggPosX, eggPosY;
    private Handler h;
    private final int FRAME_RATE = 30;
    private Matrix matrix, cmatrix, ematrix;
    private Bitmap background, pet, fullEgg, crackedEggBottom, crackedEggTop, crack, crack2;
	private int stage=1, frame=1, loop=1;
	private Hashtable<Integer, Integer> hash = new Hashtable<Integer, Integer>();
	private boolean isHatched = false;
	HatchActivity hatchActivity;
    public HatchView(Context context, AttributeSet attrs)  {
    	super(context, attrs);
        mContext = context;
        h = new Handler();
        hatchActivity = (HatchActivity)mContext;
        
        // Get preferences file
        SharedPreferences sharedPref = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    		
		// Get constant values from preference file
		backgroundWidth = sharedPref.getInt("backgroundWidth", 0);
		backgroundHeight = sharedPref.getInt("backgroundHeight", 0);
		int petColor = sharedPref.getInt("petColor", 1);
		
		eggPosX = backgroundWidth/2 - backgroundWidth/10;
		eggPosY = backgroundHeight/2;

		matrix = new Matrix();
		cmatrix = new Matrix();
		ematrix = new Matrix();

		// Build frame-rotation hash table.
		buildHash();
		
		// Load bitmaps
		loadBitmaps(petColor);

    }
     private Runnable r = new Runnable() {
             @Override
             public void run() {
                     invalidate();
             }
     };
     protected void onDraw(Canvas c) {
    	// Draw environment
  		c.drawBitmap(background, 0, 0, null);
    	 
  		if (frame>20) { 
  			frame=1;
  			loop++;
  		}
  		if (loop%5 == 0) {
  			stage++; // Enter next stage
  			loop++;
  		}
  		
 		// Drawing full egg
 		if (stage == 1) {
 			// Set rotate
 			handleMatrix();
 			
 			c.drawBitmap(fullEgg, matrix, null); // Draw the egg
 		}
 		// Drawing cracked egg
 		else if (stage == 2) {
 			handleMatrix();
 			c.drawBitmap(fullEgg, matrix, null); // Draw the egg
 			c.drawBitmap(crack, cmatrix, null); // Draw a crack
 			c.drawBitmap(crack2, cmatrix, null); // Draw another crack
 		}
 		// Draw still egg
 		else if (stage == 3) {
 			c.drawBitmap(fullEgg, matrix, null); // Draw the egg
 			c.drawBitmap(crack, cmatrix, null); // Draw a crack
 			c.drawBitmap(crack2, cmatrix, null); // Draw another crack
 		}
 		// Draw egg explosion
 		else if (stage == 4) {
 			handleEMatrix();
 			c.drawBitmap(pet, backgroundWidth/2 - pet.getWidth()/2, backgroundHeight/2 - 5, null); // Draw pet
 			c.drawBitmap(crackedEggBottom, eggPosX,  eggPosY + fullEgg.getHeight()/2, null); // Draw bottom crack
 			c.drawBitmap(crackedEggTop, ematrix, null); // Draw top crack
	 
 		}
 		else {
 			c.drawBitmap(pet, backgroundWidth/2 - pet.getWidth()/2, backgroundHeight/2 - 5, null); // Draw pet
 			h.removeCallbacks(r);
 			hatchActivity.goHome();
 			return;
 		}
 		frame++;
        h.postDelayed(r, FRAME_RATE); 
  }
     


	private void loadBitmaps(int petColor) {
 		// Get pet image from sd card.
 		String imageInSD = Environment.getExternalStorageDirectory() + "/PhonePet/petBitmap/pet";
 		pet = BitmapFactory.decodeFile(imageInSD);
 		background = BitmapFactory.decodeResource(getResources(), R.drawable.egg_hatch_background);
 		
 		fullEgg = getEgg(petColor);
 		crackedEggBottom = getCrackedEggBottom(petColor);
 		crackedEggTop = getCrackedEggTop(petColor);
 		
 		crack = BitmapFactory.decodeResource(getResources(), R.drawable.crack);
 		crack2 = BitmapFactory.decodeResource(getResources(), R.drawable.crack2);

 		// Scale the bitmaps
 		background = Bitmap.createScaledBitmap(background, backgroundWidth, backgroundHeight, true); // Environment
 		pet = Bitmap.createScaledBitmap(pet, backgroundWidth/5, backgroundHeight/5, true);
 		fullEgg = Bitmap.createScaledBitmap(fullEgg, backgroundWidth/5, backgroundHeight/5, true);
 		crackedEggBottom = Bitmap.createScaledBitmap(crackedEggBottom, backgroundWidth/5, backgroundHeight/10, true);
 		crackedEggTop = Bitmap.createScaledBitmap(crackedEggTop, backgroundWidth/5, backgroundHeight/10, true);
 		crack = Bitmap.createScaledBitmap(crack, backgroundWidth/10, backgroundHeight/10, true);
 		crack2 = Bitmap.createScaledBitmap(crack2, backgroundWidth/10, backgroundHeight/10, true); 	 
 	}  
 	 
 	public void setStage(int stage) {
 		this.stage = stage;
 	}
 	
 	private Bitmap getEgg(int petColor) {
 		int num;		
 		if (petColor == 1) 		num =  R.drawable.red_egg;
 		else if (petColor == 2) num = R.drawable.green_egg;
 		else if (petColor == 3) num = R.drawable.blue_egg;
 		else if (petColor == 4) num = R.drawable.orange_egg;
 		else if (petColor == 5) num = R.drawable.brown_egg;
 		else if (petColor == 6) num = R.drawable.white_egg;
 		else if (petColor == 7) num = R.drawable.yellow_egg;
 		else if (petColor == 8) num = R.drawable.black_egg;
 		else 					num = R.drawable.purple_egg;
 		
 		return BitmapFactory.decodeResource(getResources(), num);
 	}
 	private Bitmap getCrackedEggBottom(int petColor) {
 		int num;		
 		if (petColor == 1) 		num =  R.drawable.red_bottom;
 		else if (petColor == 2) num = R.drawable.green_bottom;
 		else if (petColor == 3) num = R.drawable.blue_bottom;
 		else if (petColor == 4) num = R.drawable.orange_bottom;
 		else if (petColor == 5) num = R.drawable.brown_bottom;
 		else if (petColor == 6) num = R.drawable.white_bottom;
 		else if (petColor == 7) num = R.drawable.yellow_bottom;
 		else if (petColor == 8) num = R.drawable.black_bottom;
 		else 					num = R.drawable.purple_bottom;
 		
 		return BitmapFactory.decodeResource(getResources(), num);
 	}
 	private Bitmap getCrackedEggTop(int petColor) {
 		int num;		
 		if (petColor == 1) 		num =  R.drawable.red_top;
 		else if (petColor == 2) num = R.drawable.green_top;
 		else if (petColor == 3) num = R.drawable.blue_top;
 		else if (petColor == 4) num = R.drawable.orange_top;
 		else if (petColor == 5) num = R.drawable.brown_top;
 		else if (petColor == 6) num = R.drawable.white_top;
 		else if (petColor == 7) num = R.drawable.yellow_top;
 		else if (petColor == 8) num = R.drawable.black_top;
 		else 					num = R.drawable.purple_top;  
 		
 		return BitmapFactory.decodeResource(getResources(), num);
 	}
 	 
 	private void handleMatrix() {
		matrix.reset(); 
		int newX = eggPosX + (int) (eggPosX * Math.cos(Math.toRadians(hash.get(frame))));
		
		matrix.postRotate(hash.get(frame)-90, eggPosX, eggPosY);	
		matrix.postTranslate(newX, eggPosY);
		
		if (stage == 2 || stage==3) {
			cmatrix.reset();
			cmatrix.postRotate(hash.get(frame)-90, eggPosX, eggPosY);
			cmatrix.postTranslate(newX + fullEgg.getWidth()/2 - crack.getWidth()/2, eggPosY+fullEgg.getHeight()/2-crack.getHeight()/2);
		}
	}
 	
 	private void handleEMatrix() {
 		ematrix.reset();
 		eggPosY -= backgroundHeight/15;
 		
 		ematrix.postRotate(-15);
 		ematrix.postTranslate(eggPosX, eggPosY);
 	}
 	
 	private void buildHash() {
		hash.put(1, 92);
		hash.put(2, 94);
		hash.put(3, 96);
		hash.put(4, 98);
		hash.put(5, 100);
		hash.put(6, 98);
		hash.put(7, 96);
		hash.put(8, 94);
		hash.put(9, 92);
		hash.put(10, 90);
		hash.put(11, 88);
		hash.put(12, 86);
		hash.put(13, 84);
		hash.put(14, 82);
		hash.put(15, 80);
		hash.put(16, 82);
		hash.put(17, 84);
		hash.put(18, 86);
		hash.put(19, 88);
		hash.put(20, 90);
 	}
 	
 	public boolean isHatched() {
 		return this.isHatched;
 	}
}
