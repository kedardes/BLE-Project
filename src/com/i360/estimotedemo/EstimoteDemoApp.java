package com.i360.estimotedemo;

import java.util.ArrayList;
import java.util.List;


import com.i360.estimotedemo.data.DataManager;
import com.i360.estimotedemo.data.DataManagerImpl;
import com.i360.estimotedemo.model.beaconLink;

import android.app.Application;


public class EstimoteDemoApp extends Application {

	private List<beaconLink> beaconlinks;
	private DataManager dataManager;
	
	public DataManager getDataManager() {
		return this.dataManager;
	}
	
	@Override
	public void onCreate() {
		beaconlinks =  new ArrayList<beaconLink>();
		dataManager = new DataManagerImpl(this);
		super.onCreate();
	}
	
	public List<beaconLink> getBeaconLinks(){
		return this.beaconlinks;
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
	}
}



