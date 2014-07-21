package com.i360.estimotedemo.data;

import android.os.Environment;

public class DataConstants {

	private static final String APP_PACKAGE_NAME = "com.i360.estimotedemo";
	private static final String EXTERNAL_DATA_DIR_NAME = "beaconsdata";
	public static final String EXTERNAL_DATA_PATH = Environment.getExternalStorageDirectory() + "/" + DataConstants.EXTERNAL_DATA_DIR_NAME;
	
	public static final String DATABASE_NAME = "mybeacons.db";
	public static final String DATABASE_PATH = Environment.getDataDirectory() + "/data/" + DataConstants.APP_PACKAGE_NAME + "/databases/" + DataConstants.DATABASE_NAME; 
 	
	public DataConstants() {
		
	}
}
