package com.example.phonepet;

import com.example.controllers.AccessorizeController;
import com.example.controllers.PetController;
import com.example.views.AccessorizeView;
import com.example.views.HomeView;
import com.example.vos.OnChangeListener;
import com.example.vos.PetVo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

public class AccessorizeActivity extends Activity implements OnChangeListener<PetVo> {

	
	private AccessorizeView aView;
	
	private AccessorizeController controller;
	private PetVo pet;
	
	private ImageButton topHatButton;
	private ImageButton unicornButton;
	private ImageButton glassesButton;
	private ImageButton blackTieButton;
	private ImageButton redTieButton;
	private ImageButton blueTieButton;
	private ImageButton bowTieButton;
	
	
	
	void addAccessoryToPet() {};
	void saveAccessories() {};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accessorize);
	

		// Instantiate pet model and set the AccessorizeActivity as an observer.
		// Now any time the model changes, the onChange method gets called. 
		pet = new PetVo();
		pet.addListener(this);
		controller = new AccessorizeController(pet, getApplicationContext());
		
		topHatButton = (ImageButton)findViewById(R.id.topHat);
		unicornButton = (ImageButton)findViewById(R.id.unicornHorn);
		glassesButton = (ImageButton)findViewById(R.id.glasses);
		blackTieButton = (ImageButton)findViewById(R.id.blackTie);
		redTieButton = (ImageButton)findViewById(R.id.redTie);
		blueTieButton = (ImageButton)findViewById(R.id.blueTie);
		bowTieButton = (ImageButton)findViewById(R.id.bowtie);
		
		topHatButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.handleMessage(AccessorizeController.MESSAGE_TOPHAT);
			}
		});
		
		unicornButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.handleMessage(AccessorizeController.MESSAGE_UNICORN);
			}
		});
		
		glassesButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.handleMessage(AccessorizeController.MESSAGE_GLASSES);
			}
		});
		
		blackTieButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.handleMessage(AccessorizeController.MESSAGE_BLACKTIE);
			}
		});
		
		redTieButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.handleMessage(AccessorizeController.MESSAGE_REDTIE);
			}
		});
		
		blueTieButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.handleMessage(AccessorizeController.MESSAGE_BLUETIE);
			}
		});
		
		bowTieButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.handleMessage(AccessorizeController.MESSAGE_BOWTIE);
			}
		});
		
		
		// Retrieve view 
		final AccessorizeView accessview = (AccessorizeView)findViewById(R.id.AccessorizeView);
		this.aView = accessview;
		pet.setWidth(aView.aPetwidth);
		pet.setHeight(aView.aPetheight);
		
		Log.v("about to call setpet", "tralala");
		controller.handleMessage(AccessorizeController.MESSAGE_SETPET);
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accessorize, menu);
		return true;
	}


	/*
	 * Simply delegate the logic to the controller by sending a message asking it to handle the
	 * KeyEvent for us.  Our controller returns a boolean indicating whether or not the message
	 * handled. 
	 */
		@Override
		public boolean dispatchKeyEvent(KeyEvent event) 
		{
			Log.v("dispatchingKeyEvent", "accessorize");
			boolean handled = controller.handleMessage(PetController.MESSAGE_KEY_EVENT, event);
			if (!handled) {
				// If the controller didn't handle the KeyEvent the method calls its super.
				return super.dispatchKeyEvent(event);
			}
			return handled;
			
		}
	
	/**
	 * Once the model notifies the view of a change, we just update the views on the UI thread.
	 * @param pet
	 */
	@Override
	public void onChange(PetVo pett) {
		/* Since a change has occurred we need to update the view. All views must be modified
		 * on the UI Thread. Since we don't know what thread called onChange, we need to switch
		 * over to the UI thread before making a modification. The updateView() method is the
		 * one that is responsible for syncing all of our widgets to our model's data.
		 * 
		 * This implements data binding. Our activity is registered as an observer and whenever 
		 * our model is updated we know our UI is going to stay right in-sync.
		*/ 
		runOnUiThread(new Runnable() {
			@Override
			public void run() 
			{
				Log.v("Updating the view", "onChange Accessorize");
				updateView();
			}
		});
	}
	
	private void updateView() {
		// Update pet on the screen.
		Log.v("call drawpet", "msgplz");
		Log.v("gettX", Integer.toString(pet.getXCoord()));
		Log.v("gettY", Integer.toString(pet.getYCoord()));
		Log.v("gettWidth", Integer.toString(pet.getWidth()));
		
		this.aView.drawPet((pet.getXCoord()-(pet.getWidth())/2), pet.getYCoord()-(pet.getHeight()/6));
	}

}
