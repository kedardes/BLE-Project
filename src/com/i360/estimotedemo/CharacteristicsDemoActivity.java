package com.i360.estimotedemo;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.connection.BeaconConnection;
import com.estimote.sdk.connection.BeaconConnection.BeaconCharacteristics;
import com.estimote.sdk.connection.BeaconConnection.ConnectionCallback;
import com.i360.estimotedemo.model.beaconLink;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CharacteristicsDemoActivity extends Activity {

	private Beacon beacon;
	private BeaconConnection connection;
	private EstimoteDemoApp myapp; 
	private TextView statusView;
	private TextView beaconDetailsView;
	private EditText minorEditView;
	private EditText modelImageEditView;
	private EditText modelNameEditView;
	private EditText modelYearEditView;
	private EditText modelPriceEditView;
	private EditText modelLocationEditView;
	private EditText targetUrlEditView;
	private View afterConnectedView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myapp = (EstimoteDemoApp) getApplication();
		setContentView(R.layout.characteristics_demo);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		statusView = (TextView) findViewById(R.id.status);
		
		beaconDetailsView = (TextView) findViewById(R.id.beacon_details);
		afterConnectedView = findViewById(R.id.after_connected);
		minorEditView = (EditText) findViewById(R.id.minor);
		modelImageEditView = (EditText) findViewById(R.id.modelimage);
		modelNameEditView = (EditText) findViewById(R.id.modelname);
		modelYearEditView = (EditText) findViewById(R.id.modelyear);
		modelPriceEditView = (EditText) findViewById(R.id.modelprice);
		modelLocationEditView = (EditText) findViewById(R.id.location);
		
		targetUrlEditView = (EditText) findViewById(R.id.targeturl);
		
		beacon = getIntent().getParcelableExtra(ListBeaconsActivity.EXTRAS_BEACON);
		connection = new BeaconConnection(this, beacon, createConnectionCallback());
		findViewById(R.id.update).setOnClickListener(createUpdateButtonListener());
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!connection.isConnected()) {
			statusView.setText("Status: Connecting .....");
			connection.authenticate();
		}
	}
	
	@Override
	protected void onDestroy()
	{
		connection.close();
		super.onDestroy();
	}
	
	private OnClickListener createUpdateButtonListener() {
		// TODO Auto-generated method stub
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int minor = parseMinorFromEditView();
				String modelImage = parseFieldFromEditView(modelImageEditView);
				String modelName = parseFieldFromEditView(modelNameEditView);
				String modelYear = parseFieldFromEditView(modelYearEditView);
				String modelPrice = parseFieldFromEditView(modelPriceEditView);
				String modelLocation = parseFieldFromEditView(modelLocationEditView);
				String targeturl = parseFieldFromEditView(targetUrlEditView);
				if (minor == -1) {
					showToast("Minor must be a number");
				}
				else {
					updateMinor(minor);
					updatelocaldb(minor,modelImage,modelName, modelYear, modelPrice, modelLocation, targeturl);
				}
				
			}
		};
	}

	private void updatelocaldb(int minor, String modelImage, String modelName, String modelYear, String modelPrice, String modelLocation, String targetURL) {
		// TODO Auto-generated method stub
		beaconLink beacon = new beaconLink();
		beacon.setMinor(String.valueOf(minor));
		beacon.setModelName(modelName);
		beacon.setModelImage(modelImage);
		beacon.setModelYear(modelYear);
		beacon.setModelPrice(modelPrice);
		beacon.setModelLocation(modelLocation);
		beacon.setTargetUrl(targetURL);
		myapp.getDataManager().SaveBeacon(beacon);
	}
	
	private void updateMinor(int minor) {
		connection.writeMinor(minor, new BeaconConnection.WriteCallback() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						showToast("Minor value updated");
					}
					
				});
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						showToast("Minor not updated");
					}
					
				});
			}
		});
	}
	
	private String parseFieldFromEditView(EditText view) {
		try {
			return String.valueOf(view.getText());
		} catch(Exception e) {
			return "";
		}
		
	}
	
	private int parseMinorFromEditView() {
		try {
			return Integer.parseInt(String.valueOf(minorEditView.getText()));
		}
		catch (NumberFormatException e) {
			return -1;
		}
	}
	
	private ConnectionCallback createConnectionCallback() {
		// TODO Auto-generated method stub
		return new BeaconConnection.ConnectionCallback() {
			
			@Override
			public void onDisconnected() {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						statusView.setText("Status: Disconnected from Beacon");
					}}); 
					
				
				
			}
			
			@Override
			public void onAuthenticationError() {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						statusView.setText("Status: cannot connect to beacon. Authentication problems");
					}
					
				});
			}
			
			@Override
			public void onAuthenticated(final BeaconCharacteristics beaconChars) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						statusView.setText("Status:Connected to beacon");
						StringBuilder sb = new StringBuilder()
							.append("Major: ").append(beacon.getMajor()).append("\n")
							.append("Minor: ").append(beacon.getMinor()).append("\n")
							.append("Advertising interval: ").append(beaconChars.getAdvertisingIntervalMillis()).append("ms\n")
							.append("Broadcasting power: ").append(beaconChars.getBroadcastingPower()).append(" dBm\n")
							.append("Battery: ").append(beaconChars.getBatteryPercent()).append(" %");
						beaconDetailsView.setText(sb.toString());
						minorEditView.setText(String.valueOf(beacon.getMinor()));
						beaconLink beacon_local = myapp.getDataManager().findBeacon(String.valueOf(beacon.getMinor()));
						if (beacon_local != null) {
						    modelImageEditView.setText(beacon_local.getModelImage());
						    modelNameEditView.setText(beacon_local.getModelName());
						    modelYearEditView.setText(beacon_local.getModelYear());
						    modelPriceEditView.setText(beacon_local.getModelPrice());
						    modelLocationEditView.setText(beacon_local.getModelLocation());
							targetUrlEditView.setText(beacon_local.getTargetUrl());
						}
						afterConnectedView.setVisibility(View.VISIBLE);
					}});
			}
		};
	}

	private void showToast(String text) {
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.characteristics_demo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
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
