package com.i360.estimotedemo.data;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class beaconLinkTable {

	public static final String TABLE_NAME = "beaconlink";
	
	public static class Columns implements BaseColumns {
		public static final String MINOR = "minor";
		public static final String IMAGE_NAME = "imagename";
		public static final String MODEL_NAME = "modelname";
		public static final String MODEL_YEAR = "modelyear";
		public static final String MODEL_PRICE = "modelprice";
		public static final String MODEL_LOCATION = "location";
		public static final String TARGET_URL = "targeturl";
	}
	
	public static void onCreate(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + beaconLinkTable.TABLE_NAME + " (");
		sb.append(BaseColumns._ID + " INTEGER PRIMARY KEY, ");
		sb.append(Columns.MINOR + " TEXT, " );
		sb.append(Columns.IMAGE_NAME + " TEXT, " );
		sb.append(Columns.MODEL_NAME + " TEXT, " );
		sb.append(Columns.MODEL_YEAR + " TEXT, " );
		sb.append(Columns.MODEL_PRICE + " TEXT, " );
		sb.append(Columns.MODEL_LOCATION + " TEXT, " );
		sb.append(Columns.TARGET_URL + " TEXT " );
		sb.append(");");
		db.execSQL(sb.toString());
		
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + beaconLinkTable.TABLE_NAME);
		beaconLinkTable.onCreate(db);
	}
}
