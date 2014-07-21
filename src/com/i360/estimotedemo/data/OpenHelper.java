package com.i360.estimotedemo.data;

import com.i360.estimotedemo.Constants;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OpenHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private Context context;
	   
	public OpenHelper(final Context context) {
		super(context, DataConstants.DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	
	@Override 
	public void onOpen(final SQLiteDatabase db) {
		super.onOpen(db);
		if (!db.isReadOnly()) {
			db.execSQL("PRAGMA foreign_keys=ON");
			
			Cursor c = db.rawQuery("PRAGMA foreign_keys", null);
			
			if (c.moveToFirst()) {
				int result = c.getInt(0);
				Log.i(Constants.LOG_TAG, "SQLite foreign key support (1 is on, 0 is off): " + result);
			}
			else {
				Log.i(Constants.LOG_TAG, "SQLite foreign key support NOT AVAILABLE");
         }
			
         if (!c.isClosed()) {
            c.close();
         }
		
		}
		
	}

	@Override
	public void onCreate(final SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.i(Constants.LOG_TAG, "DataHelper.OpenHelper onCreate creating database " + DataConstants.DATABASE_NAME);
		beaconLinkTable.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.i(Constants.LOG_TAG, "SQLiteOpenHelper onUpgrate = oldversion:" + oldVersion  + " newVersion:" + newVersion);
		beaconLinkTable.onUpgrade(db, oldVersion, newVersion);
	}
	
	

}
