package com.i360.estimotedemo;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.connection.BeaconConnection;
import com.i360.estimotedemo.model.beaconLink;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ModelDetails extends Activity {
	private beaconLink selectedModel;
	private ImageView modelImageView;
	private TextView modelNameView;
	private TextView modelYearView;
	private TextView modelPriceView;
	private TextView targetUrlView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.model_details);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		selectedModel = getIntent().getParcelableExtra(ListModels.EXTRAS_BEACON_LINK);
		modelImageView = (ImageView) findViewById(R.id.modelimage);
	    modelNameView = (TextView) findViewById(R.id.modelname);
	    modelYearView = (TextView) findViewById(R.id.modelyear);
	    modelPriceView = (TextView) findViewById(R.id.modelprice);
	    targetUrlView = (TextView) findViewById(R.id.targeturl);
	    
	    modelNameView.setText(selectedModel.getModelName());
	    modelYearView.setText(selectedModel.getModelYear());
	    modelPriceView.setText(selectedModel.getModelPrice());
	    targetUrlView.setText(selectedModel.getTargetUrl());
	    
	    float dest = 360;
	    int imageid = getResources()
			       .getIdentifier("com.i360.estimotedemo:drawable/" + selectedModel.getModelImage(), null, null);
		modelImageView.setImageResource(imageid);
	
//		Display display = getWindowManager().getDefaultDisplay();
//		Point size = new Point();
//		display.getSize(size);
//		modelImageView.setLeft(size.x);
//		TranslateAnimation animate = new TranslateAnimation(0,-1 * size.x/2,0,0);
//		animate.setDuration(500);
//		animate.setFillAfter(true);
//		modelImageView.startAnimation(animate);
		
//		ObjectAnimator animation1 = ObjectAnimator.ofFloat(modelImageView,
//		          "rotation",dest);
//		      animation1.setDuration(2000);
//		      animation1.start();
		      
	    //  ObjectAnimator fadeOut = ObjectAnimator.ofFloat(modelImageView, "alpha",0f);
	    //      fadeOut.setDuration(2000);
		
	          ObjectAnimator mover = ObjectAnimator.ofFloat(modelImageView,
	              "translationX", -500f, 0f);
	          mover.setDuration(2000);
	          ObjectAnimator fadeIn = ObjectAnimator.ofFloat(modelImageView, "alpha",
	              0f, 1f);
	          fadeIn.setDuration(2000);
	          AnimatorSet animatorSet = new AnimatorSet();

	          animatorSet.play(mover).with(fadeIn); //.after(fadeOut);
	          animatorSet.start();
		      
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.model_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		if (id == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
