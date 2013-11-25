package com.example.phonepet;

import com.example.views.HatchView;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class HatchActivity extends Activity {

	private HatchView hatchview;
	private int j=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hatch);
		
		final HatchView hatchview = (HatchView)findViewById(R.id.HatchView); 
		this.hatchview = hatchview;
		
	}
	@SuppressLint({ "ValidFragment", "NewApi" })
	public class CongratsDialogFragment extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage("Your pet has been born!")
					.setPositiveButton("Go Home", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {	
							home();
						}
					});
			// Create the AlertDialog and return it.
			return builder.create();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hatch, menu);
		return true;
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void goHome() {
		TextView textView = (TextView)findViewById(R.id.txt_egg_hatching);
		textView.setText("Your pet has been born!");
		CongratsDialogFragment dialog = new CongratsDialogFragment();
		dialog.show(getFragmentManager(), "BroadcastDialogFragment");	
	} 
	public void home() 
	{
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		finish();
	}
}
