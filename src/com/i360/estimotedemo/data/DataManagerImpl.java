package com.i360.estimotedemo.data;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;
import android.util.Log;

import com.i360.estimotedemo.Constants;
import com.i360.estimotedemo.model.beaconLink;

public class DataManagerImpl implements DataManager {

	private Context context;
	private SQLiteDatabase db;
	private beaconLinkDao beaconDao;
	
	public DataManagerImpl(Context context) {
		this.context = context;
		
		SQLiteOpenHelper openHelper = new OpenHelper(this.context);
		db = openHelper.getWritableDatabase();
		
		beaconDao = new beaconLinkDao(db);		
	}
	
	public SQLiteDatabase getDb() {
		return db;
	}
	
	private void openDb() {
		if (!db.isOpen()) {
			db=SQLiteDatabase.openDatabase(DataConstants.DATABASE_PATH,null, SQLiteDatabase.OPEN_READWRITE);
			beaconDao = new beaconLinkDao(db);		
		}
	}
	
	private void closeDb() {
		if (db.isOpen()) {
			db.close();
		}
	}
	
	private void resetDb() {
		Log.i(Constants.LOG_TAG, "Resetting database connection (close and re-open).");
		closeDb();
		SystemClock.sleep(500);
		openDb();
	}
	
	@Override
	public beaconLink getBeacon(long beaconid) {
		// TODO Auto-generated method stub
		return beaconDao.get(beaconid);
	}

	@Override
	public List<beaconLink> getBeacons() {
		// TODO Auto-generated method stub
		return beaconDao.getAll();
	}

	@Override
	public beaconLink findBeacon(String minor) {
		// TODO Auto-generated method stub
		return beaconDao.find(minor);
		
	}

	@Override
	public long SaveBeacon(beaconLink beacon) {
		// TODO Auto-generated method stub
		beaconLink beaconToUpdate = findBeacon(beacon.getMinor());
		if (beaconToUpdate != null) 
		{
			Log.i(Constants.LOG_TAG, "updating the beacon now");
			beaconToUpdate.setModelImage(beacon.getModelImage());
			beaconToUpdate.setModelName(beacon.getModelName());
			beaconToUpdate.setModelYear(beacon.getModelYear());
			beaconToUpdate.setModelPrice(beacon.getModelPrice());
			beaconToUpdate.setModelLocation(beacon.getModelLocation());
			beaconToUpdate.setTargetUrl(beacon.getTargetUrl());
			beaconToUpdate.setMinor(beacon.getMinor());
			beaconDao.update(beaconToUpdate);
			return beacon.getId();
		}
		Log.i(Constants.LOG_TAG, "saving a new beacon now");
		return beaconDao.save(beacon);
	}

	@Override
	public void DeleteBeacon(beaconLink beacon) {
		// TODO Auto-generated method stub
		beaconDao.delete(beacon);
	}

}
