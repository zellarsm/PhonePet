package com.example.utils;

import java.util.ArrayList;
import java.util.List;

import com.example.vos.Poop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "PhonePet";
 
    // table name
    private static final String POOP_TABLE = "poop";
 
    //Table Columns names
    private static final String POOP_Y = "y";
    private static final String POOP_X = "x";
    private static final String KEY_ID = "id";
   
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		String CREATE_POOP_TABLE = "CREATE TABLE " + POOP_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + POOP_X + " TEXT," + POOP_Y + " TEXT" + ")";
        database.execSQL(CREATE_POOP_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	   
	    db.execSQL("DROP TABLE IF EXISTS " + POOP_TABLE);
	    onCreate(db);
	  }
	
	public void addPoop(Poop poop) {
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(POOP_X, poop.getX()); 
	    values.put(POOP_Y, poop.getY());
	 
	    // Inserting Row
	    db.insert(POOP_TABLE, null, values);
	    db.close(); // Closing database connection
	}
	
	public Poop getPoop(int id) {
		
		SQLiteDatabase db = this.getReadableDatabase();
		 
	    Cursor cursor = db.query(POOP_TABLE, new String[] { KEY_ID,
	           POOP_X, POOP_Y }, KEY_ID + "=?",
	            new String[] { String.valueOf(id) }, null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    Poop poop = new Poop(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)),
	            Integer.parseInt(cursor.getString(2)));
	   
	    return poop;
	}
	
	
	public List<Poop> getAllPoop() {
	    List<Poop> poopList = new ArrayList<Poop>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + POOP_TABLE;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	            Poop poop = new Poop();
	            poop.setID(Integer.parseInt(cursor.getString(0)));
	            poop.setX(Integer.parseInt(cursor.getString(1)));
	            poop.setY(Integer.parseInt(cursor.getString(2)));
	            // Adding contact to list
	            poopList.add(poop);
	        } while (cursor.moveToNext());
	    }
	 
	    // return list
	    return poopList;
	}
	
	// Getting poop Count
    public int getPoopCount() {
        String countQuery = "SELECT  * FROM " + POOP_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
    
    public int updateContact(Poop poop) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(POOP_X, poop.getX());
        values.put(POOP_Y, poop.getY());
     
        // updating row
        return db.update(POOP_TABLE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(poop.getID()) });
    }
    
    public void deletePoop(Poop poop) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(POOP_TABLE, KEY_ID + " = ?",
                new String[] { String.valueOf(poop.getID()) });
        db.close();
    }
    
    public void deleteDatabse() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(POOP_TABLE, "1", new String[] {});
        Log.d("Database stuff", "Database table succesfully deleted");
        db.close();
    }
}
