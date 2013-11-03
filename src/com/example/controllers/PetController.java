package com.example.controllers;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.phonepet.AccessorizeActivity;
import com.example.phonepet.HomeActivity;
import com.example.vos.PetVo;

/*
 * Remember, the model is nothing more than a glorified name-value object
 * and a good view is dumb and just updates when the model changes and 
 * does whatever the controller says.
 * 
 * The controller does 3 things:
 * 1. Updates the model
 * 2. Handles messages from the view
 * 3. Sends messages to the view.
 * 
 */

public class PetController extends Controller {
	public static final int MESSAGE_LOAD = 0;
	public static final int MESSAGE_MODEL_UPDATED = 1;
	public static final int MESSAGE_ACCESSORIZE = 2;
	public static final int MESSAGE_SCOOP_POOP = 3;
	public static final int MESSAGE_FEED = 4;
	public static final int MESSAGE_CLEAN = 5;
	public static final int MESSAGE_PLAY = 6;
	public static final int MESSAGE_KEY_EVENT = 7;
	public static final int MESSAGE_TAPPED = 8;
	
	/*
	 * Phone screen sizes are different, these constants are used to handle this.
	 */
	private int BACKGROUND_WIDTH;
	private int BACKGROUND_HEIGHT;
	
	// AREA values represent the rectangle contained in the fence.
	// Pet can NEVER move outside of AREA.
	private int AREA_MIN_Y; // 2/3 of background height
	private int AREA_MAX_Y; // Background height
	private int AREA_MIN_X; // 0
	private int AREA_MAX_X; // Background width
	
	private int CENTER_HOUSE_X;	// The center of the opening of the pet house width is 7/10 of background width
	private int CENTER_HOUSE_Y; // The center of the opening of the pet house height is 2/3 background height.
	
	String fileName = "preferences";
	
	private PetVo model;
	private Context homeContext;
	
	public PetVo getModel() {
		return model;
	}
	
	public PetController(PetVo model, Context hActivityContext) {
		this.model = model;
		this.homeContext = hActivityContext;
	}
	public Context getHomeContext() {
		return this.homeContext;
	}
	
	/**
	 * This is the message system used to communicate between view and controller. The view needs
	 * a synchronous response to whether or not the event was handled.
	 * 
	 * @param what: Represents which action should be taken
	 * @param data: Generic data object that can be used to send extra information
	 * 				along with the message.
	 */
	@Override
	public boolean handleMessage(int what, Object data) {
		switch (what) {
		case MESSAGE_LOAD:
			loadPet();
			return true;
		case MESSAGE_ACCESSORIZE:
			accessorize();
			return true;
		case MESSAGE_SCOOP_POOP:
			scoopPoop();
			return true;
		case MESSAGE_FEED:
			feed();
			return true;
		case MESSAGE_CLEAN:
			clean();
			return true;
		case MESSAGE_PLAY:
			play();
			return true;
		case MESSAGE_TAPPED:
			handleTap(data);
			return true;
		}
		return false;
	}

	
	private void handleTap(Object data) {
		// Determine what the user tapped.
	}

	// Get pet's information
	private void loadPet() {
		SharedPreferences sharedPref = homeContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);

		// Set constant values
		float num;
		BACKGROUND_WIDTH = sharedPref.getInt("backgroundWidth", 10);
		BACKGROUND_HEIGHT = sharedPref.getInt("backgroundHeight", 10);
		
		// Bottom of fence is 2/3 of the background height.
		num = BACKGROUND_HEIGHT;
		AREA_MIN_Y =  (int)(num * (2f/3f));
		AREA_MAX_Y = BACKGROUND_HEIGHT;
		AREA_MIN_X = 0;
		AREA_MAX_X = BACKGROUND_WIDTH;
		
		// Center of the pet house opening is
		// x coord: 7/10 of the background width.
		// y coord: 2/3 of background height
		num = BACKGROUND_WIDTH;
		CENTER_HOUSE_X = (int)(num * (7f/10f));
		CENTER_HOUSE_Y = AREA_MIN_Y;

		// Load pet coordinates from previous app state.
		model.loadPet(
					sharedPref.getInt("petWidth", 0), sharedPref.getInt("petHeight", 0),
					sharedPref.getInt("petX", 0), sharedPref.getInt("petY", 0)
					);
		
		// Create and start a thread to control pet's actions and feelings.
		PetLife life = new PetLife();
		life.start();
		
		// Place the pet in the middle of the play area.
		model.setXYCoord(AREA_MAX_X/2-(model.getWidth()/2), 		
						(AREA_MIN_Y + (AREA_MAX_Y-AREA_MIN_Y)/2) - (model.getHeight()/2));
	}


	/**
	 * Make the pet move in a specified direction. A complete movement
	 * takes 20 frames.
	 * 
	 *	@param direction: Integer from 1-8 used to specify movement direction
	 *
		  	Values:
		  	5 3 6
		  	1 * 2
		  	7 4 8	
	 */
	// TODO: Diagonal movements
	private boolean move(int direction) {
		int destX, destY;
		// No matter what the movement, the distance will be 1/10th the 
		// width of screen in x or y or both.
		int distance = BACKGROUND_WIDTH/10;
		
		// Move left 1/10 background width	
		if (direction == 1) {
			destX = model.getXCoord() - distance;

			if (destX <= AREA_MIN_X)
				// No room to move left, return.
				return false;

			// A complete movement takes 20 frames.
			int newY;
			int dx = Math.round(distance/20);

			for (int i=0; i<20; i++) {
				if (i<10) 
					// Jumping up
					newY = model.getYCoord() - dx;
				else 
					// Jumping down
					newY = model.getYCoord() + dx;
				
				model.setXYCoord(model.getXCoord() - dx, newY);
				sleepThread(10); // Sleep for 10 milliseconds to implement animation.
			}
		}
		// Move right
		else if (direction == 2) {
			destX = model.getXCoord() + distance;

			if (destX+model.getWidth() >= AREA_MAX_X)
				// Cannot move right
				return false;
			
			// Move occurs in 20 frames
			int newY;
			int dx = Math.round(distance/20);

			for (int i=0; i<20; i++) {
				if (i<10) newY = model.getYCoord() - dx;
				else newY = model.getYCoord() + dx;
				
				model.setXYCoord(model.getXCoord() + dx, newY);
				sleepThread(10);
			}
			
		}
		
		// Move up
		else if (direction == 3) {
			destY = model.getYCoord() - distance;

			if (destY+model.getHeight() < AREA_MIN_Y)
				// Cannot move up
				return false;
			
			// Move occurs in 20 frames
			int dx = Math.round(distance/20);

			for (int i=0; i<20; i++) {	
				model.setYCoord(model.getYCoord() - dx);
				sleepThread(10);
			}
		}
		// Move down
		else if (direction == 4) {
			destY = model.getYCoord() + distance;
 
			if (destY+model.getHeight() > AREA_MAX_Y)
				// Cannot move down
				return false;
			
			// Move occurs in 20 frames
			int dx = Math.round(distance/20);

			for (int i=0; i<20; i++) {
				model.setYCoord(model.getYCoord() + dx);
				sleepThread(10);
			}
			
		}
		// Move up left
				/*
				else if (direction == 5) {
					destX = model.getXCoord() - distance;
					destY = model.getYCoord() - distance;

					if ((destX < AREA_MIN_X) || (destY+model.getHeight() < AREA_MIN_Y))
						// Cannot move up left
						return false;

					// Move occurs in 20 frames
					int dx = Math.round(distance/20);

					for (int i=0; i<20; i++) {
						model.setXYCoord(model.getXCoord() - dx, model.getYCoord() - dx);
						sleepThread(10);
					}
				}
				*/
		/*
		// Move up right
		else if (direction == 6) {
			destX = model.getXCoord() + distance;
			destY = model.getYCoord() - distance;

			if ((destX+model.getWidth() > AREA_MAX_X) || (destY+model.getHeight() < AREA_MIN_Y))
				// Cannot move up right
				return false;
			
			// Move occurs in 20 frames
			int dx = Math.round(distance/20);

			for (int i=0; i<20; i++) {
				model.setXYCoord(model.getXCoord() + dx, model.getYCoord() - dx);
				sleepThread(10);
			}
			
		}
		// Move down left
		else if (direction == 7) {
			destX = model.getXCoord() - distance;
			destY = model.getYCoord() + distance;

			if ((destX < AREA_MIN_X) || (destY+model.getHeight() > AREA_MAX_Y))
				// Cannot move down left
				return false;
			
			// Move occurs in 20 frames
			int dx = Math.round(distance/20);

			for (int i=0; i<20; i++) {
				model.setXYCoord(model.getXCoord() - dx, model.getYCoord() + dx);
				sleepThread(10);
			}
		}
		*/
		
		
		// Move down right
		/*
		else {
			destX = model.getXCoord() + distance;
			destY = model.getYCoord() + distance;

			if ((destX + model.getWidth() > AREA_MAX_X) || (destY+model.getHeight() > AREA_MAX_Y))
				// Cannot move down right
				return false;
			
			// Move occurs in 20 frames
			int dx = Math.round(distance/20);

			for (int i=0; i<20; i++) {
				model.setXYCoord(model.getXCoord() + dx, model.getYCoord() + dx);
				sleepThread(10);
			}
		}
		*/

		return true;
		
	}

	/**
	 * Puts the main thread to sleep
	 * 
	 * @param millis: The number of milliseconds the thread will sleep for
	 */
	private void sleepThread(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	private void play() {
		// Testing: Put pet in top left corner
		model.setXYCoord(AREA_MIN_X, AREA_MIN_Y-model.getHeight()/2);
		
	}

	private void clean() {
		// Testing: put pet in its house
		model.setXYCoord(CENTER_HOUSE_X - model.getWidth()/2, CENTER_HOUSE_Y - model.getHeight()/2);
		
	}

	private void feed() {
		// Testing: Put pet in top right corner
		model.setXYCoord(AREA_MAX_X - model.getWidth(), AREA_MIN_Y - model.getHeight()/2);
	}

	private void scoopPoop() {
		// Testing: Put pet in bottom left corner
		model.setXYCoord(AREA_MIN_X, AREA_MAX_Y - model.getHeight());
		
	}

	private void accessorize() {
		// Testing: Put pet in bottom right corner
		model.setXYCoord(AREA_MAX_X - model.getWidth() , AREA_MAX_Y - model.getHeight());
		
		// Launch new activity.
		Intent myIntent = new Intent(this.getHomeContext(), AccessorizeActivity.class);
		myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PetController.this.homeContext.startActivity(myIntent);
	}
	
	/**
	 *  This thread is used to control the pet's actions and feelings. 
	 */
	// TODO: A lot. Right now this thread is only used to move the pet around randomly.
	private class PetLife extends Thread {
		public void run() {
			while(true) {
				// Sleep for 3 seconds.
				try {
					sleep(3000);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				// Move the pet randomly.
				int direction = 1 + (int)(Math.random() * ((4 - 1) + 1));
							//  min + ................... ((max - min) + 1));
				move(direction);
				
			}
			
		}
		
	}

}
