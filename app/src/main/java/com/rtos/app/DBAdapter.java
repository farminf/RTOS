
package com.rtos.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBAdapter {

	/////////////////////////////////////////////////////////////////////
	//	Constants & Data
	/////////////////////////////////////////////////////////////////////
	// For logging:
	private static final String TAG = "DBAdapter";
	
	// DB Fields
	public static final String KEY_ROWID = "_id";
	public static final int COL_ROWID = 0;
	/*
	 * CHANGE 1:
	 */
	// TODO: Setup your fields here:
	public static final String TASK_NAME = "task";
	public static final String TASK_START = "start";
	public static final String TASK_COMPUTATION = "computation";
    public static final String TASK_PERIOD = "period";
    public static final String TASK_DEADLINE = "deadline";
    public static final String TASK_AbDEADLINE = "Abdeadline";



    // TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
	public static final int COL_NAME = 1;
	public static final int COL_START = 2;
	public static final int COL_COMPUTATION = 3;
    public static final int COL_PERIOD = 4;
    public static final int COL_DEADLINE = 5;
    public static final int COL_AbDEADLINE = 6;




    public static final String[] ALL_KEYS = new String[] {KEY_ROWID, TASK_NAME, TASK_START, TASK_COMPUTATION
            ,TASK_PERIOD ,TASK_DEADLINE ,TASK_AbDEADLINE};
	
	// DB info: it's name, and the table we are using (just one).
	public static final String DATABASE_NAME = "MyDb";
	public static final String DATABASE_TABLE = "mainTable";
	// Track DB version if a new version of your app changes the format.
	public static final int DATABASE_VERSION = 2;
	
	private static final String DATABASE_CREATE_SQL = 
			"create table " + DATABASE_TABLE 
			+ " (" + KEY_ROWID + " integer primary key autoincrement, "
			
			/*
			 * CHANGE 2:
			 */
			// TODO: Place your fields here!
			// + KEY_{...} + " {type} not null"
			//	- Key is the column name you created above.
			//	- {type} is one of: text, integer, real, blob
			//		(http://www.sqlite.org/datatype3.html)
			//  - "not null" means it is a required field (must be given a value).
			// NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
			+ TASK_NAME + " string not null, "
			+ TASK_START + " integer not null, "
			+ TASK_COMPUTATION + " integer not null,"
            + TASK_PERIOD + " integer not null,"
            + TASK_DEADLINE + " integer not null,"
            + TASK_AbDEADLINE + " integer not null"


                    // Rest  of creation:
			+ ");";
	
	// Context of application who uses us.
	private final Context context;
	
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	/////////////////////////////////////////////////////////////////////
	//	Public methods:
	/////////////////////////////////////////////////////////////////////
	
	public DBAdapter(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}
	
	// Open the database connection.
	public DBAdapter open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}
	
	// Close the database connection.
	public void close() {
		myDBHelper.close();
	}
	
	// Add a new set of values to the database.
	public long insertRow(String taskName, int start, int computation , int period, int deadline , int AbDeadline) {
		/*
		 * CHANGE 3:
		 */		
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues initialValues = new ContentValues();
		initialValues.put(TASK_NAME, taskName);
		initialValues.put(TASK_START, start);
		initialValues.put(TASK_COMPUTATION, computation);
        initialValues.put(TASK_PERIOD, period);
        initialValues.put(TASK_DEADLINE, deadline);
        initialValues.put(TASK_AbDEADLINE, AbDeadline);


        // Insert it into the database.
		return db.insert(DATABASE_TABLE, null, initialValues);
	}
//-----------------------------------------------------------------
public int checkDBEmpty(){
    //int value = 6;
    //String statement = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + TASK_PERIOD + " = " + value;
    //String where = "min(" + COL_PERIOD +")" ;
    //Cursor c = db.query(true, DATABASE_TABLE, ALL_KEYS,
    //  null, null, null, null, null, null);
    //String name = cursor.getString(DBAdapter.COL_NAME);
    //c.moveToFirst();  //ADD THIS!
    //int rowID = c.getInt(0);
    int count = 0 ;
    String statement = "SELECT * FROM " + DATABASE_TABLE ;
    Cursor cursor = db.rawQuery(statement, null);
    count = cursor.getCount();
    if (cursor != null) {
        cursor.moveToFirst();
    }
    return count ;
}
    //-----------------------------------------------------------------

    // Delete a row from the database, by rowId (primary key)
	public boolean deleteRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(DATABASE_TABLE, where, null) != 0;
	}
    //-----------------------------------------------------------------
    public boolean deleteRowByName(String name) {
        String where = TASK_NAME + " = " + '"' + name + '"' ;
        return db.delete(DATABASE_TABLE, where, null) != 0;
    }
    //-----------------------------------------------------------------
    public Cursor findMinT(){
        //int value = 6;
        //String statement = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + TASK_PERIOD + " = " + value;
        //String where = "min(" + COL_PERIOD +")" ;
        //Cursor c = db.query(true, DATABASE_TABLE, ALL_KEYS,
              //  null, null, null, null, null, null);
        //String name = cursor.getString(DBAdapter.COL_NAME);
        //c.moveToFirst();  //ADD THIS!
        //int rowID = c.getInt(0);
        String statement = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + TASK_PERIOD +
                " = (SELECT MIN(" + TASK_PERIOD + ") FROM " + DATABASE_TABLE + ")";
        Cursor cursor = db.rawQuery(statement, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor ;
    }
    //-----------------------------------------------------------------
    public int abDeadlineResult (String name){
        String where = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + TASK_NAME + " = " + '"' + name + '"';
        Cursor cursor = db.rawQuery(where, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor.getInt(DBAdapter.COL_AbDEADLINE);

    }
    //-----------------------------------------------------------------
    public int computationResult (String name){
        String where = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + TASK_NAME + " = " + '"' + name + '"';
        Cursor cursor = db.rawQuery(where, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return  cursor.getInt(DBAdapter.COL_COMPUTATION);

    }
    //-----------------------------------------------------------------
    public Cursor findStartT(int time){
        String statement = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + TASK_START + " = " + time;
        Cursor cursor = db.rawQuery(statement, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor ;
    }
    //-----------------------------------------------------------------
    public Boolean computationMinusOne(String name){
        String statement = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + TASK_NAME + " = " + '"' + name + '"';
        String where = TASK_NAME + " = " + '"' + name + '"';
        Cursor cursor = db.rawQuery(statement, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        int computation = cursor.getInt(DBAdapter.COL_COMPUTATION);
        computation -- ;
        ContentValues newValues = new ContentValues();
        newValues.put(TASK_COMPUTATION, computation);
        return db.update(DATABASE_TABLE, newValues, where , null) != 0;
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
        //return cursor ;
    }
    //-----------------------------------------------------------------
    public int findAbDeadline(String name){
        String where = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + TASK_NAME + " = " + '"' + name + '"';
        Cursor cursor = db.rawQuery(where, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor.getInt(DBAdapter.COL_AbDEADLINE);
    }
    //-----------------------------------------------------------------
	public void deleteAll() {
		Cursor c = getAllRows();
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRow(c.getLong((int) rowId));				
			} while (c.moveToNext());
		}
		c.close();
	}
    //-----------------------------------------------------------------
	// Return all data in the database.
	public Cursor getAllRows() {
		String where = null;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
							where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
    //-----------------------------------------------------------------
	// Get a specific row (by rowId)
	public Cursor getRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
						where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
    //-----------------------------------------------------------------
	// Change an existing row to be equal to new data.
	public boolean updateRow(long rowId, String taskName, int start, int computation , int period , int deadline , int AbDeadline) {
		String where = KEY_ROWID + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues newValues = new ContentValues();
		newValues.put(TASK_NAME, taskName);
		newValues.put(TASK_START, start);
		newValues.put(TASK_COMPUTATION, computation);
        newValues.put(TASK_PERIOD, period);
        newValues.put(TASK_DEADLINE, deadline);
        newValues.put(TASK_AbDEADLINE, AbDeadline);


        // Insert it into the database.
		return db.update(DATABASE_TABLE, newValues, where, null) != 0;
	}

    //-----------------------------------------------------------------
    public boolean updateComputation(String taskName , int computation) {
        String where = TASK_NAME + " = " + '"' + taskName + '"';
        ContentValues newValues = new ContentValues();
        newValues.put(TASK_COMPUTATION, computation);

        // Insert it into the database.
        return db.update(DATABASE_TABLE, newValues, where, null) != 0;
    }







	/////////////////////////////////////////////////////////////////////
	//	Private Helper Classes:
	/////////////////////////////////////////////////////////////////////
	
	/**
	 * Private class which handles database creation and upgrading.
	 * Used to handle low-level database access.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE_SQL);			
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data!");
			
			// Destroy old database:
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			
			// Recreate new database:
			onCreate(_db);
		}
	}
}
