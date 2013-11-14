package com.example.phonepet;

import java.util.List;

import com.example.connect4.Connect4Activity;
import com.example.controllers.PetController;
import com.example.utils.DatabaseHelper;
import com.example.views.HomeView;
import com.example.vos.OnChangeListener;
import com.example.vos.PetVo;
import com.example.vos.Poop;

import android.os.Bundle;
import android.provider.Settings.System;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.MotionEventCompat;
import android.text.format.Time;
import android.util.Log;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class HomeActivity extends Activity implements OnChangeListener<PetVo> {

	private static final int INVALID_POINTER_ID = 0;

	public HomeActivity() {

	}

	private PetController controller;
	private PetVo pet; // Pet
	
	private HomeView hView;
	
	// Window parameters
	private int windowWidth;
	private int windowHeight;
	private int playgroundHeight;
	private int playgroundWidth;
	private int menuWidth;
	private int menuHeight;
	
	// The �active pointer� is the one currently moving our object.
	private int mActivePointerId = INVALID_POINTER_ID;
	private float mLastTouchX = 0;
	private float mLastTouchY = 0;
	private float mPosX = 0;
	private float mPosY = 0;
	//boolean petIsClicked = false;
	private String fileName = "preferences";
	DatabaseHelper db;
	private boolean testButtonJustHeld = false;
	private boolean poopIsClicked;
	private int id;
	private Poop temp, temp2;
	List<Poop> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		// Instantiate pet model and set the HomeActivity as an observer.
		// Now any time the model changes, the onChange method gets called. 
		pet = PetVo.getInstance();
		pet.addListener(this);
		
		db = new DatabaseHelper(this);
		final HomeView homeview = (HomeView)findViewById(R.id.HomeView);
		this.hView = homeview;
		
		controller = new PetController(pet, getApplicationContext());
		
		ImageButton playButton = (ImageButton)findViewById(R.id.Play);
		ImageButton accessorizeButton = (ImageButton)findViewById(R.id.Accessorize);
		ImageButton poopButton = (ImageButton)findViewById(R.id.Poop);
		ImageButton feedButton = (ImageButton)findViewById(R.id.Feed);
		ImageButton cleanButton = (ImageButton)findViewById(R.id.Sponge);
		//Button testButton = (Button)findViewById(R.id.TestButton);
		
		playButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.handleMessage(PetController.MESSAGE_PLAY);
				showPlayOptions(v);
			}
		});
		
		accessorizeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Send message to the controller asking it to handle event.
				// When the event is handled, the controller updates the model.
				controller.handleMessage(PetController.MESSAGE_ACCESSORIZE);
				
			}
		});
		
		poopButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				  
				poopCleaner();
				//db.deleteDatabse();
				
				//hView.removePoop();
			}
		});
		
		feedButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				int numPoop = (int)(Math.random() * 3) + 1;
				int tempX, tempY;
				Poop poop;
				int width, height;
				
				width = hView.getBackgroundWidth() - hView.getPetWidth();
				height = hView.getBackgroundHeight();
				
				for(int i = 0; i < numPoop; i ++){
					
						tempX = (int)(Math.random() * width);
						tempY = (int)(Math.random() * (height/2) + (height/2));
						
						if(tempY > height - pet.getHeight()) {
							tempY = tempY - height/12;
						}
						poop = new Poop(tempX, tempY);
						db.addPoop(poop);
						
				}
		        list = db.getAllPoop();			
				hView.drawPoop(list);
			  
			}
		});
		
		cleanButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.handleMessage(PetController.MESSAGE_CLEAN);				
				
				
				hView.cleaning();
				
				
			}
		});
		
	/**
	 * Run a test when clicked and run a test when held
	 */
		/*
<<<<<<< HEAD
//		testButton.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (!testButtonJustHeld) // Prevents running both hold test and click test
//					// Will return false when test is complete
//					controller.handleMessage(PetController.MESSAGE_TEST_BUTTON_CLICKED);
//				
//				testButtonJustHeld = false;
//			}
//		});
//		// Hold button for about 1.5 seconds
//		testButton.setOnLongClickListener(new View.OnLongClickListener() {
//			
//			@Override
//			public boolean onLongClick(View v) {
//				// Will return true when test is complete
//				testButtonJustHeld = controller.handleMessage(PetController.MESSAGE_TEST_BUTTON_HELD);
//				return false;
//			}
//		});
=======
		/*testButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!testButtonJustHeld) // Prevents running both hold test and click test
					// Will return false when test is complete
					controller.handleMessage(PetController.MESSAGE_TEST_BUTTON_CLICKED);
				
				testButtonJustHeld = false;
			}
		});
		// Hold button for about 1.5 seconds
		testButton.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// Will return true when test is complete
				testButtonJustHeld = controller.handleMessage(PetController.MESSAGE_TEST_BUTTON_HELD);
				return false;
			}
		});*/

		
		// Load pet information.
		controller.handleMessage(PetController.MESSAGE_LOAD, getApplicationContext());
	}
	/*
	 * Simply delegate the logic to the controller by sending a message asking it to handle the
	 * KeyEvent for us.  Our controller returns a boolean indicating whether or not the message
	 * handled. 
	 */
	public void poopCleaner() {
		
		hView.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				
			final int action = MotionEventCompat.getActionMasked(event); 
				
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: {
					final int pointerIndex = MotionEventCompat.getActionIndex(event); 
			        final float userX = MotionEventCompat.getX(event, pointerIndex); 
			        final float userY = MotionEventCompat.getY(event, pointerIndex); 
			            
			        // Check if something is clicked.
		       id = isPoopClicked(userX, userY);
		       
			        //Log.v("petIsClicked, down", Boolean.toString(petIsClicked));
			        
			        // Remember where we started (for dragging)
			        mLastTouchX = userX;
			        mLastTouchY = userY;
		        
			        // Save the ID of this pointer (for dragging)
		       mActivePointerId = MotionEventCompat.getPointerId(event, 0);
		        break;
					
				} // End case MotionEvent.ACTION_DOWN
				
			case MotionEvent.ACTION_MOVE: {
			        // Find the index of the active pointer and fetch its position
			        final int pointerIndex = 
			                MotionEventCompat.findPointerIndex(event, mActivePointerId);  
			            
			        final float userX = MotionEventCompat.getX(event, pointerIndex);
			        final float userY = MotionEventCompat.getY(event, pointerIndex);
			            
			        // Calculate the distance moved
			       final float dx = userX - mLastTouchX;
			       final float dy = userY - mLastTouchY;

			       mPosX += dx;
			       mPosY += dy;
			       list = db.getAllPoop();
			      
			        // Invalidate
			        if (poopIsClicked && id != -1) {
			        
			        	temp = list.get(id);  
			        	list.remove(temp.getID());
			        	temp.setX((int)userX);
			        	temp.setY((int)userY);
			        	temp.setID(id);
			        	list.add(temp);
			        	hView.dragPoop(list);	        	
			        }
			        
			        // Remember this touch position for the next move event
			       mLastTouchX = userX;
		           mLastTouchY = userY;

			        break;
				}
				
				case MotionEvent.ACTION_UP: {
					
					db.deleteDatabse();
					for(Poop e: list) {
						db.addPoop(e);
						
					}
			        mActivePointerId = INVALID_POINTER_ID;
			        break;
				}	
			            
			    case MotionEvent.ACTION_CANCEL: {
			        mActivePointerId = INVALID_POINTER_ID;
			        break;
			    }
			        
			    case MotionEvent.ACTION_POINTER_UP: {
			           
			        final int pointerIndex = MotionEventCompat.getActionIndex(event); 
			        final int pointerId = MotionEventCompat.getPointerId(event, pointerIndex); 
			        if (pointerId == mActivePointerId) {
			            // This was our active pointer going up. Choose a new
			            // active pointer and adjust accordingly.
			            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
			            mLastTouchX = MotionEventCompat.getX(event, newPointerIndex); 
			            mLastTouchY = MotionEventCompat.getY(event, newPointerIndex); 
			            mActivePointerId = MotionEventCompat.getPointerId(event, newPointerIndex);
			        }
			       break;
			    }
			
					
				}
				return true;

			
			
			}	
			}); // End function onTouch
		
		 // Set onTouchListener
	

		
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
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
	public void onChange(PetVo pet) {
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
			public void run() {
				updateView();
			}
		});
	}
	
	private void updateView() {
		// Update pet on the screen.
		this.hView.drawPet(pet.getXCoord(), pet.getYCoord());
	}

	@Override
	protected void onStop() {
		super.onStop();
		
		
		for(Poop e: list) {
			db.addPoop(e);
		}
		///*
		// Get current time
		Time currTime = new Time();
		currTime.setToNow();
		Log.v("stop current time", currTime.toString());
		
		// Get the preferences file and create the editor
		SharedPreferences sharedPref = getSharedPreferences(fileName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		
		// Put data into preferences file
		//editor.putString("lastSavedTime", currTime.toString());
		editor.putLong("lastSavedTimeMillis", currTime.toMillis(true)); // ignore daylight savings time
		long t = controller.getCountdownTimeLeft();
		Log.v("getTimeLeft", Long.toString(t));
		editor.putLong("runawayTimeLeft", t);
		//editor.putLong("runawayTimeLeft", controller.getCountdownTimeLeft());
		
		editor.commit();
		//*/
		
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		Log.v("got here", "yay");
		
		/* Problems: close the app. On resume, a new petController is created, and this method
		 * makes RunawayActivity start. Thus have two counters and an infinite loop of BADOWNER.
		 * Need to lock this method??
		 */
		
		
		
		// Reset countdown timer
		SharedPreferences sharedPref = getSharedPreferences(fileName, Context.MODE_PRIVATE);
		long lastTimeSaved = sharedPref.getLong("lastSavedTimeMillis", 0);
		long timerRemaining = sharedPref.getLong("runawayTimeLeft", controller.DEFAULT_RUNAWAY_TIME_START);
		
		// Get current time
		Time currTime = new Time();
		currTime.setToNow();
		Log.v("resume current time", Long.toString(currTime.toMillis(true)));
		Log.v("resume last time saved", Long.toString(lastTimeSaved));
		
		// Calculate time elapsed
		long timeElapsed = currTime.toMillis(true) - lastTimeSaved;
		Log.v("resume timeElapsed", Long.toString(timeElapsed));
		Log.v("resume timerRemaining", Long.toString(timerRemaining));
		
		// If timeElapsed == currTime, there was no data in preferences file.
		  // Do not change timer / runaway if you just created a brand new pet.
		if(timeElapsed != currTime.toMillis(true)) 
		{
			// Pet ran away while the app was closed!
			if(timeElapsed >= timerRemaining)
			{
				// GOTO RunawayActivity
				//controller.handleMessage(controller.MESSAGE_PET_RUNAWAY);
				// Launch RunawayActivity.
				Intent myIntent = new Intent(getBaseContext(), RunawayActivity.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
				return;
			}
			else
			{
				// Update counter to reflect time elapsed. Pet hasn't run away yet.
				controller.continueCountdownTimer(timerRemaining - timeElapsed);
			}
			
		}
		
		
		controller.handleMessage(PetController.MESSAGE_PET_RETURNING);
		// Redraw 
		this.hView.loadBitmaps();
		
		updateView();
		list = db.getAllPoop();
		hView.drawPoop(list);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	public int getWindowWidth() {
		return this.windowWidth;
	}
	public int getWindowHeight() {
		return this.windowHeight;
	}
	public int getPlaygroundWidth() {
		return this.playgroundWidth;
	}
	public int getPlaygroundHeight() {
		return this.playgroundHeight;
	}
	public int getMenuWidth() {
		return this.menuWidth;
	}
	public int getMenuHeight() {
		return this.menuHeight;
	}
	
	private void showPlayOptions(final View v)
	{
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		final String[] playOptions = {"Fetch", "Do nothing", "Connect 4", "Quit Life"};
		dialogBuilder.setTitle("What would you like to play");
		dialogBuilder.setItems(playOptions, new DialogInterface.OnClickListener(){
		
			@Override
			public void onClick(DialogInterface dialog, int which){
			
				switch(which)
				{
					case 0: Intent intent0 = new Intent(v.getContext(), PlayActivity.class);
							startActivityForResult(intent0, 0);
							break;
							
					case 1: break;
					
					case 2: Intent intent2 = new Intent(v.getContext(), Connect4Activity.class);
							startActivityForResult(intent2, 0);
							break;
				}
				Toast.makeText(getApplicationContext(), playOptions[which], Toast.LENGTH_SHORT).show();
			}
		});
		AlertDialog alertDialog = dialogBuilder.create();
		alertDialog.show();
	}
	
	
	public int isPoopClicked(float userX, float userY) {
		
		int x, y, count = 0;
		List<Poop> myList = db.getAllPoop(); 
		for(Poop e: myList) {
			x = e.getX();
			y = e.getY();
			
			// Is click within pet image X-range
			if ((userX >= x)
					&& (userX <= x + hView.getPetWidth()/2)) {
				Log.v("userx", Float.toString(userX));
				Log.v("x", Integer.toString(x));
			
			    // Is click within pet image Y-range
				if ((userY >= y) 
						&& (userY <= y + hView.getPetHeight())) {
				
					// Pet has been selected
					Log.v("userY", Float.toString(userY));
					Log.v("y", Integer.toString(y));
					poopIsClicked = true;
					return count;
				}
			}
			count++;
		}
		
		poopIsClicked = false;
		return -1;
		
	} // End method 
	
	//void feedPet() {};
	//void bathePet() {};
	//void removePoop() {};
	//void notifyOwnerOfNeglect(){};
	//void runawayPet(Pet pet) {};

} // End class HomeActivity
