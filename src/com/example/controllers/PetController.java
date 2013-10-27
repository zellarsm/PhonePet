package com.example.controllers;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;

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
	
	//HomeActivity hActivity = new HomeActivity();

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

//		Log.v("cscreenWidth", Integer.toString(sharedPref.getInt("screenWidth", 10)));
//		Log.v("cscreenHeight", Integer.toString(sharedPref.getInt("screenHeight", 10)));
//		Log.v("cplaygroundWidth", Integer.toString(sharedPref.getInt("playgroundWidth", 10)));
//		Log.v("cplaygroundHeight", Integer.toString(sharedPref.getInt("playgroundHeight", 10)));
//		Log.v("cpetWidth", Integer.toString(sharedPref.getInt("petWidth", 10)));
//		Log.v("cpetHeight", Integer.toString(sharedPref.getInt("petHeight", 10)));

		// Load pet coordinates from previous app state.
		model.loadPet(
					sharedPref.getInt("petWidth", 0),sharedPref.getInt("petHeight", 0),
					sharedPref.getInt("petX", 0), sharedPref.getInt("petY", 0)
					);
		
		/**
		 *  Start a new thread that controls the pet's actions and feelings. 
		 */
		// (Temporary demo of thread)
		int i=0;
		while(true) {
			// Make the pet jump
			while (i != 1000) {
				i++;
				if (i == 1000) {
					i=0;
					randomJump();
				}
			}
			
		}
	}

	/**
	 * Make the pet jump in a random direction.
	 */
	private void randomJump() {
		
		
	}

	private void play() {
		// TODO Auto-generated method stub
		
	}

	private void clean() {
		// TODO Auto-generated method stub
		
	}

	private void feed() {
		// TODO Auto-generated method stub
		
	}

	private void scoopPoop() {
		// TODO Auto-generated method stub
		
	}

	private void accessorize() {
		// TODO Auto-generated method stub
		
	}

}
