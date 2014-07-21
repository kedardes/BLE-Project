package com.i360.estimotedemo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.i360.estimotedemo.model.beaconLink;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ListModels extends Activity {

	private static final String TAG = ListBeaconsActivity.class.getSimpleName();
	
	public static final String EXTRAS_TARGET_ACTIVITY = "extrasTargetActivity";
	public static final String EXTRAS_BEACON_LINK = "extrasBeaconLink";
	
	private static final int REQUEST_ENABLE_BT = 1234;
	private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid",null,null,null);
	
	private BeaconManager beaconManager;
	private modelListAdapter adapter;
	private EstimoteDemoApp myapp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_models);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		adapter = new modelListAdapter(this);
		myapp = (EstimoteDemoApp) getApplication();
		
		ListView list = (ListView) findViewById(R.id.model_list);
		list.setAdapter(adapter);
		list.setOnItemClickListener(createOnClickListener());
		
		beaconManager = new BeaconManager(this);
		beaconManager.setBackgroundScanPeriod(TimeUnit.SECONDS.toMillis(2000), 0);
		beaconManager.setRangingListener(new BeaconManager.RangingListener() {
			@Override
			public void onBeaconsDiscovered(Region region, final List<Beacon> rangedBeacons) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
					 List<beaconLink> beaconLinks = new ArrayList<beaconLink>();
					 ArrayList<beaconLink> beaconListFromDB = (ArrayList<beaconLink>) myapp.getDataManager().getBeacons();
					 for (Beacon rangedBeacon : rangedBeacons) {
						for(beaconLink db_beacon: beaconListFromDB) {
							if (String.valueOf(rangedBeacon.getMinor()).equals(db_beacon.getMinor())) {
								beaconLinks.add(db_beacon);
							}
						}
					 }
					 getActionBar().setSubtitle("Number of Models found: " + beaconLinks.size());
					 adapter.replaceWith(beaconLinks);
					}
				});
			}
		});
		
	}

	private OnItemClickListener createOnClickListener() {
		// TODO Auto-generated method stub
		return new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
				if (getIntent().getStringExtra(EXTRAS_TARGET_ACTIVITY) != null)
				{
					try {
						Class<?> clazz = Class.forName(getIntent().getStringExtra(EXTRAS_TARGET_ACTIVITY));
						Intent intent = new Intent(ListModels.this, clazz);
						intent.putExtra(EXTRAS_BEACON_LINK, adapter.getItem(position));
						startActivity(intent);
					}
					catch (ClassNotFoundException e)
					{
						Log.e(TAG, "Finding class by name failed",e);
					}
				}
			}
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scan_menu, menu);
		MenuItem refreshItem = menu.findItem(R.id.refresh);
		refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		
	    if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
	        Toast.makeText(this, "BLE NOT SUPPORTED ON THIS DEVICE", Toast.LENGTH_SHORT).show();
	        finish();
	    }
	    
		if (!beaconManager.hasBluetooth()) {
			Toast.makeText(this,"Device does not have Bluetooth Low Energy", Toast.LENGTH_LONG).show();
			return;
		}
		
		if (!beaconManager.isBluetoothEnabled()) {
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent,REQUEST_ENABLE_BT);
		}
		else {
			connectToService();
		}	
	}
	
	@Override
	protected void onStop()
	{
		try {
			beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS_REGION);
		}
		catch (RemoteException e)
		{
			Log.d(TAG,"Error while stopping ranging", e);
		}
		
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		beaconManager.disconnect();
		super.onDestroy();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_ENABLE_BT) {
			if (resultCode == Activity.RESULT_OK) {
				connectToService();
			}
			else {
				Toast.makeText(this,"BlueTooth not Enabled",Toast.LENGTH_LONG).show();
				getActionBar().setSubtitle("BlueTooth not enabled");
			}
		}
	}
	
	private void connectToService() {
		getActionBar().setSubtitle("Scanning ......");
		adapter.replaceWith(Collections.<beaconLink>emptyList());
		beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
			
			@Override
			public void onServiceReady() {
				// TODO Auto-generated method stub
				try {
				beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
				}
				catch  (RemoteException e){
					Toast.makeText(ListModels.this,"Cannot start ranging, something terrible happened", Toast.LENGTH_LONG).show();
					Log.e(TAG, "Cannot start ranging", e);
				}
				}
			
		});
	}
	
}
