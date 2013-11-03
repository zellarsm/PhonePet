package com.example.phonepet;

import com.example.controllers.PetController;
import com.example.views.AccessorizeView;
import com.example.views.HomeView;
import com.example.vos.OnChangeListener;
import com.example.vos.PetVo;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class AccessorizeActivity extends Activity implements OnChangeListener<PetVo> {

	
	private AccessorizeView aView;
	
	private PetController controller;
	private PetVo pet;
	
	void addAccessoryToPet() {};
	void saveAccessories() {};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accessorize);
	

		// Instantiate pet model and set the HomeActivity as an observer.
		// Now anytime the model changes, the onChange method gets called. 
		//pet = new PetVo();
		//pet.addListener(this);
		//controller = new PetController(pet, getApplicationContext());

		// Retrieve home view
		final AccessorizeView accessview = (AccessorizeView)findViewById(R.id.Accessorize);
		this.aView = accessview;
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accessorize, menu);
		return true;
	}
	@Override
	public void onChange(PetVo modelPet) {
		// TODO Auto-generated method stub
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				updateView();
			}
		});
	}
	
	private void updateView() {
		// Update pet on the screen.
		// this.aView.drawPet(pet.getXCoord(), pet.getYCoord());
	}

}
