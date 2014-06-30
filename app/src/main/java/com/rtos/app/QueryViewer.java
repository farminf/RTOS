package com.rtos.app;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by farminfarzin on 6/27/14.
 */
public class QueryViewer extends Activity {


    TextView query;
    DBAdapter myDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_viewer);

        myDb = new DBAdapter(this);
        myDb.open();

        query = (TextView) findViewById(R.id.queryViewer);
        Cursor cursor = myDb.getAllRows();

        String message = "";
        // populate the message from the cursor

        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                int id = cursor.getInt(DBAdapter.COL_ROWID);
                String name = cursor.getString(DBAdapter.COL_NAME);
                int start = cursor.getInt(DBAdapter.COL_START);
                int computation = cursor.getInt(DBAdapter.COL_COMPUTATION);
                int period = cursor.getInt(DBAdapter.COL_PERIOD);
                int deadline = cursor.getInt(DBAdapter.COL_DEADLINE);
                int AbDeadline = cursor.getInt(DBAdapter.COL_AbDEADLINE);



                // Append data to the message:
                message += "id=" + id
                        +", name=" + name
                        +", start =" + start
                        +", Compute =" + computation
                        +", Period =" + period
                        +", deadline =" + deadline
                        +", AbDeadline =" + AbDeadline
                        +"\n";
            } while(cursor.moveToNext());
        }
        cursor.close();
        query.setText(message);





//        int id = cursor.getInt(DBAdapter.COL_ROWID);
//        String name = cursor.getString(DBAdapter.COL_NAME);
//        Log.v("", name);
//        int cmp = cursor.getInt(DBAdapter.COL_COMPUTATION);
//        Log.d("",String.format("value = %d", cmp));



    }
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }
    private void closeDB() {
        myDb.close();

    }
}
