package com.example.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.vos.PetVo;

public class AccessorizeController extends Controller {

	// Message types
	public static final int MESSAGE_SETPET = 0;
	public static final int MESSAGE_MODEL_UPDATED = 1;
	public static final int MESSAGE_TOPHAT = 2;
	public static final int MESSAGE_UNICORN = 3;
	public static final int MESSAGE_GLASSES = 4;
	public static final int MESSAGE_BLACKTIE = 5;
	public static final int MESSAGE_REDTIE = 6;
	public static final int MESSAGE_BLUETIE = 7;
	public static final int MESSAGE_BOWTIE = 8;
	
	/*
	 * Phone screen sizes are different, these constants are used to handle this.
	 */
	private int ACC_BACKGROUND_WIDTH;
	private int ACC_BACKGROUND_HEIGHT;
	
	String fileName = "preferences";
	
	private PetVo model;
	private Context accessorizeContext;
	
	public PetVo getModel() {
		return model;
	}
	
	// Class constructor
	public AccessorizeController(PetVo model, Context aActivityContext) {
		this.model = model;
		this.accessorizeContext = aActivityContext;
	}
	public Context getHomeContext() {
		return this.accessorizeContext;
	}
	
	@Override
	public boolean handleMessage(int what, Object data) {
		switch (what) {
		case MESSAGE_SETPET:
			Log.v("inside handleMessage", "setting pet");
			setPetLocation();
			return true;
		case MESSAGE_MODEL_UPDATED:
			return true;
		case MESSAGE_TOPHAT:
			Log.v("inside topHat", "top hat");
			return true;
		case MESSAGE_UNICORN:
			Log.v("inside unicorn", "unicorn");
			return true;
		case MESSAGE_GLASSES:
			Log.v("inside glasses", "glasses");
			return true;
		case MESSAGE_BLACKTIE:
			Log.v("inside blacktie", "blacktie");
			return true;
		case MESSAGE_REDTIE:
			Log.v("inside redtie", "redtie");
			return true;
		case MESSAGE_BLUETIE:
			Log.v("inside bluetie", "bluetie");
			return true;
		case MESSAGE_BOWTIE:
			Log.v("inside bowtie", "bowtie");
			return true;
		}
		return false;
	}
	
	void setPetLocation()
	{
		SharedPreferences sharedPref = accessorizeContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);

		// Set constant values
		float num;
		ACC_BACKGROUND_WIDTH = sharedPref.getInt("backgroundWidth", 10);
		ACC_BACKGROUND_HEIGHT = sharedPref.getInt("backgroundHeight", 10);
		
		Log.v("width", Integer.toString(ACC_BACKGROUND_WIDTH));
		Log.v("height", Integer.toString(ACC_BACKGROUND_HEIGHT));
		
		//
		model.setXYCoord(ACC_BACKGROUND_WIDTH/2 , ACC_BACKGROUND_HEIGHT - (ACC_BACKGROUND_HEIGHT/4));
	}
	
	
}
