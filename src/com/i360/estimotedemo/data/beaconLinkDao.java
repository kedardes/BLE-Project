package com.i360.estimotedemo.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;
import android.util.Log;

import com.i360.estimotedemo.Constants;
import com.i360.estimotedemo.model.beaconLink;

public class beaconLinkDao implements Dao<beaconLink> {

	
	private static final String INSERT = 
			"insert into " + beaconLinkTable.TABLE_NAME + "(" + beaconLinkTable.Columns.MINOR + "," + 
					beaconLinkTable.Columns.IMAGE_NAME + "," +
					beaconLinkTable.Columns.MODEL_NAME + "," +
					beaconLinkTable.Columns.MODEL_YEAR + "," +
					beaconLinkTable.Columns.MODEL_PRICE + "," +
					beaconLinkTable.Columns.MODEL_LOCATION + "," +
					beaconLinkTable.Columns.TARGET_URL + ") values (?,?,?,?,?,?,?) ";
	
	private SQLiteDatabase db;
	private SQLiteStatement insertStatement;
	
	public beaconLinkDao(SQLiteDatabase db) {
		this.db = db;
		this.insertStatement = db.compileStatement(beaconLinkDao.INSERT);
	}
	
	@Override
	public long save(beaconLink beaconlink) {
		// TODO Auto-generated method stub
		insertStatement.clearBindings();
		insertStatement.bindString(1, beaconlink.getMinor());
		insertStatement.bindString(2, beaconlink.getModelImage());
		insertStatement.bindString(3, beaconlink.getModelName());
		insertStatement.bindString(4, beaconlink.getModelYear());
		insertStatement.bindString(5, beaconlink.getModelPrice());
		insertStatement.bindString(6, beaconlink.getModelLocation());
		insertStatement.bindString(7, beaconlink.getTargetUrl());
		return insertStatement.executeInsert();
	}

	@Override
	public void update(beaconLink beaconlink) {
		// TODO Auto-generated method stub
		final ContentValues values = new ContentValues();
		values.put(beaconLinkTable.Columns.MINOR, beaconlink.getMinor());
		values.put(beaconLinkTable.Columns.IMAGE_NAME, beaconlink.getModelImage());
		values.put(beaconLinkTable.Columns.MODEL_NAME, beaconlink.getModelName());
		values.put(beaconLinkTable.Columns.MODEL_YEAR, beaconlink.getModelYear());
		values.put(beaconLinkTable.Columns.MODEL_PRICE, beaconlink.getModelPrice());
		values.put(beaconLinkTable.Columns.MODEL_LOCATION, beaconlink.getModelLocation());
		values.put(beaconLinkTable.Columns.TARGET_URL, beaconlink.getTargetUrl());
		int numrows = db.update(beaconLinkTable.TABLE_NAME, values, BaseColumns._ID + " = ?", new String[] {String.valueOf(beaconlink.getId())});
		Log.i(Constants.LOG_TAG, "number of rows affected " + numrows + " while updating beacon");
	}

	@Override
	public void delete(beaconLink beaconlink) {
		// TODO Auto-generated method stub
		if (beaconlink.getId() > 0) {
			db.delete(beaconLinkTable.TABLE_NAME, BaseColumns._ID + " = ?", new String[] { String.valueOf(beaconlink.getId())});
		}
	}

	@Override
	public beaconLink get(long id) {
		// TODO Auto-generated method stub
		beaconLink beaconlink = null;
		Cursor c = db.query(beaconLinkTable.TABLE_NAME,new String[] {BaseColumns._ID,
					beaconLinkTable.Columns.MINOR,
					beaconLinkTable.Columns.IMAGE_NAME,
					beaconLinkTable.Columns.MODEL_NAME,
					beaconLinkTable.Columns.MODEL_YEAR,
					beaconLinkTable.Columns.MODEL_PRICE,
					beaconLinkTable.Columns.MODEL_LOCATION,
					beaconLinkTable.Columns.TARGET_URL
				},BaseColumns._ID + " = ?", new String[] {String.valueOf(id)}, null, null, null, "1");
		
		if (c.moveToFirst()) {
			beaconlink = this.buildBeaconFromCursor(c);
		}
		
		if (!c.isClosed()) {
			c.close();
		}
		return beaconlink;
	}

	private beaconLink buildBeaconFromCursor(Cursor c) {
		beaconLink beaconlink = null;
		if (c != null) {
			beaconlink = new beaconLink();
			beaconlink.setId(c.getLong(0));
			beaconlink.setMinor(c.getString(1));
			beaconlink.setModelImage(c.getString(2));
			beaconlink.setModelName(c.getString(3));
			beaconlink.setModelYear(c.getString(4));
			beaconlink.setModelPrice(c.getString(5));
			beaconlink.setModelLocation(c.getString(6));
			beaconlink.setTargetUrl(c.getString(7));
		}
		
		return beaconlink;
	}
	
	public beaconLink find (String minor) {
		long beaconId = 0L;
		String sql = "select _id from " + beaconLinkTable.TABLE_NAME + " Where " + beaconLinkTable.Columns.MINOR + " = ? limit 1 ";
		Cursor c = db.rawQuery(sql, new String[] {minor});
		if (c.moveToFirst()) {
			beaconId = c.getLong(0);
		}
		
		if (!c.isClosed()) {
			c.close();
		}
		return this.get(beaconId);
	}
	
	@Override
	public List<beaconLink> getAll() {
		List<beaconLink> list = new ArrayList<beaconLink>();
		Cursor c = db.query(beaconLinkTable.TABLE_NAME, new String[] { BaseColumns._ID, 
										beaconLinkTable.Columns.MINOR,
										beaconLinkTable.Columns.IMAGE_NAME,
										beaconLinkTable.Columns.MODEL_NAME,
										beaconLinkTable.Columns.MODEL_YEAR,
										beaconLinkTable.Columns.MODEL_PRICE,
										beaconLinkTable.Columns.MODEL_LOCATION,
										beaconLinkTable.Columns.TARGET_URL}, null,null,null,null,beaconLinkTable.Columns.MODEL_NAME, null);
		
		if (c.moveToFirst()) {
			do {
				beaconLink beacon = this.buildBeaconFromCursor(c);
				if (beacon != null) {
					list.add(beacon);
				}
			} while (c.moveToNext());
			
		}
		
		if (c.isClosed()) {
			c.close();
		}
		// TODO Auto-generated method stub
		return list;
	}

}
