package com.example.phonepet;

import com.example.controllers.PetController;
import com.example.views.HomeView;
import com.example.vos.OnChangeListener;
import com.example.vos.PetVo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
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
import android.widget.ImageButton;

public class HomeActivity extends Activity implements OnChangeListener<PetVo> {

	private static final int INVALID_POINTER_ID = 0;

	public HomeActivity() {

	}
	
	private ImageButton playButton;
	private ImageButton accessorizeButton;
	private ImageButton poopButton;
	private ImageButton feedButton;
	private ImageButton cleanButton;
	
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
	
	// The ‘active pointer’ is the one currently moving our object.
	private int mActivePointerId = INVALID_POINTER_ID;
	private float mLastTouchX = 0;
	private float mLastTouchY = 0;
	private float mPosX = 0;
	private float mPosY = 0;
	//boolean petIsClicked = false;
	private String fileName = "preferences";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		// Instantiate pet model and set the HomeActivity as an observer.
		// Now anytime the model changes, the onChange method gets called. 
		pet = PetVo.getInstance();
		pet.addListener(this);
		
		controller = new PetController(pet, getApplicationContext());
		
		playButton = (ImageButton)findViewById(R.id.Play);
		accessorizeButton = (ImageButton)findViewById(R.id.Accessorize);
		poopButton = (ImageButton)findViewById(R.id.Poop);
		feedButton = (ImageButton)findViewById(R.id.Feed);
		cleanButton = (ImageButton)findViewById(R.id.Sponge);
		
		playButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.handleMessage(PetController.MESSAGE_PLAY);
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
				//controller.handleMessage(PetController.MESSAGE_SCOOP_POOP);
				
				hView.removePoop();
			}
		});
		
		feedButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				hView.drawPoop();
				//controller.handleMessage(PetController.MESSAGE_FEED);
			}
		});
		
		cleanButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.handleMessage(PetController.MESSAGE_CLEAN);
				
			}
		});
		
		
		// Retrieve home view
		final HomeView homeview = (HomeView)findViewById(R.id.HomeView);
		this.hView = homeview;
		// Load pet information.
		controller.handleMessage(PetController.MESSAGE_LOAD, getApplicationContext());
		
		homeview.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				
			final int action = MotionEventCompat.getActionMasked(event); 
				
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: {
					final int pointerIndex = MotionEventCompat.getActionIndex(event); 
			        final float userX = MotionEventCompat.getX(event, pointerIndex); 
			        final float userY = MotionEventCompat.getY(event, pointerIndex); 
			            
//			        // Check if something is clicked.
//			       isPoopClicked(userX, userY);
//			        //Log.v("petIsClicked, down", Boolean.toString(petIsClicked));
//			        
//			        // Remember where we started (for dragging)
//			        mLastTouchX = userX;
//			        mLastTouchY = userY;
//			        
//			        // Save the ID of this pointer (for dragging)
//			       mActivePointerId = MotionEventCompat.getPointerId(event, 0);
//			        break;
//					
//				} // End case MotionEvent.ACTION_DOWN
//				
//				case MotionEvent.ACTION_MOVE: {
//			        // Find the index of the active pointer and fetch its position
//			        final int pointerIndex = 
//			                MotionEventCompat.findPointerIndex(event, mActivePointerId);  
//			            
//			        final float userX = MotionEventCompat.getX(event, pointerIndex);
//			        final float userY = MotionEventCompat.getY(event, pointerIndex);
//			            
//			        // Calculate the distance moved
//			       final float dx = userX - mLastTouchX;
//			       final float dy = userY - mLastTouchY;
//
//			       mPosX += dx;
//			       mPosY += dy;
//			        
//			        // Invalidate
//			        if (petIsClicked)
//			        {
//			        	if((userX > 0) && (userX < (windowWidth - pet.width)) 
//			        			&& (userY > 0) && (userY < (windowHeight - pet.height - menuHeight))) 
//			        	{
//			        		homeview.dragPet(userX, userY);
//			        		pet.xCoordinate = userX;
//			        		pet.yCoordinate = userY;
//			        	}
//			        }
//			        
//			        // Remember this touch position for the next move event
//			       mLastTouchX = userX;
//		           mLastTouchY = userY;
//
//			        break;
//				}
				
			//	case MotionEvent.ACTION_UP: {
			//        mActivePointerId = INVALID_POINTER_ID;
			//        break;
			//    }
			            
			//    case MotionEvent.ACTION_CANCEL: {
			//        mActivePointerId = INVALID_POINTER_ID;
			//        break;
			//    }
			        
			//    case MotionEvent.ACTION_POINTER_UP: {
			            
			//        final int pointerIndex = MotionEventCompat.getActionIndex(event); 
			//        final int pointerId = MotionEventCompat.getPointerId(event, pointerIndex); 

			//        if (pointerId == mActivePointerId) {
			            // This was our active pointer going up. Choose a new
			            // active pointer and adjust accordingly.
			//            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
			//            mLastTouchX = MotionEventCompat.getX(event, newPointerIndex); 
			//            mLastTouchY = MotionEventCompat.getY(event, newPointerIndex); 
			//            mActivePointerId = MotionEventCompat.getPointerId(event, newPointerIndex);
			//        }
			 //       break;
			    }
					
				} // End switch
			
				return true;
				
			} // End function onTouch

		}); // Set onTouchListener
	}
	
	/*
	 * Simply delegate the logic to the controller by sending a message asking it to handle the
	 * KeyEvent for us.  Our controller returns a boolean indicating whether or not the message
	 * handled. 
	 */
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
		
		/*
		// Get current time
		Time currTime = new Time();
		currTime.setToNow();
		Log.v("current time", currTime.toString());
		
		// Get the preferences file and create the editor
		SharedPreferences sharedPref = getSharedPreferences(fileName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		
		// Put data into preferences file
		//editor.putString("lastSavedTime", currTime.toString());
		editor.putLong("lastSavedTimeMillis", currTime.toMillis(true)); // ignore daylight savings time
		editor.putLong("runawayTimeLeft", controller.getCountdownTimeLeft());
		
		editor.commit();*/
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.v("got here", "yay");
		
		controller.handleMessage(PetController.MESSAGE_PET_RETURNING);
		// Redraw 
		this.hView.loadBitmaps();
		
		updateView();
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
	/*
	public void isPetClicked(float userX, float userY) {
		
		// Is click within pet image X-range
		if ((userX >= pet.xCoordinate)
				&& (userX <= pet.xCoordinate + pet.width)) 
		{
		    // Is click within pet image Y-range
			if ((userY >= pet.yCoordinate) 
					&& (userY <= pet.yCoordinate + pet.height))
			{
				// Pet has been selected
				petIsClicked = true;
				return;
			}
		}
		
		// Pet not clicked
		petIsClicked = false;
		
	} // End method isPetClicked
	*/
	//void feedPet() {};
	//void bathePet() {};
	//void removePoop() {};
	//void notifyOwnerOfNeglect(){};
	//void runawayPet(Pet pet) {};

} // End class HomeActivity
