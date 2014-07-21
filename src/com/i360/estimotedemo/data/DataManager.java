package com.i360.estimotedemo.data;

import java.util.List;

import com.i360.estimotedemo.model.beaconLink;

public interface DataManager {
	
	public beaconLink getBeacon(long beaconid);
	
	public List<beaconLink> getBeacons();
	
	public beaconLink findBeacon(String minor);
	
	public long SaveBeacon(beaconLink beacon);
	
	public void DeleteBeacon(beaconLink beacon);

}
