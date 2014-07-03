package com.rtos.app;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by farminfarzin on 6/28/14.
 */
public class rm extends Activity {


    TextView rm;
    DBAdapter myDb;
    int time = 0;
    boolean feasible;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rm);


        rm = (TextView) findViewById(R.id.rmViewer);
        myDb = new DBAdapter(this);
        myDb.open();

        //Cursor c = myDb.findMin();
        String message = "";
        feasible = true ;
        for (int i=0 ; i != 4 ; i++){
            Cursor c = myDb.findMinT();
            if (c.moveToFirst()) {
                do {
                    String name = c.getString(DBAdapter.COL_NAME);
                    int computation = c.getInt(DBAdapter.COL_COMPUTATION);
                    int period = c.getInt(DBAdapter.COL_PERIOD);
                    message += "Task" + name + " has started in time " + time + "\n";
                    time = time + computation;
                    message += "Task" + name + " has finished in time " + time +"\n";
                    if ( period < time ){
                        feasible = false ;
                    }
                    myDb.deleteRowByName(name);

                }while(c.moveToNext());
            }
            c.close();
        }
//        if (c.moveToFirst()) {
//            do {
//                // Process the data:
//                int id = c.getInt(DBAdapter.COL_ROWID);
//                String name = c.getString(DBAdapter.COL_NAME);
//                //int start = cursor.getInt(DBAdapter.COL_START);
//                //int computation = cursor.getInt(DBAdapter.COL_COMPUTATION);
//                //int period = cursor.getInt(DBAdapter.COL_PERIOD);
//                //int deadline = cursor.getInt(DBAdapter.COL_DEADLINE);
//                //int AbDeadline = cursor.getInt(DBAdapter.COL_AbDEADLINE);
//                //String minTaskName = myDb.findMin();
//
//
//
//                // Append data to the message:
//                message += "id=" + id
//                        +", name=" + name
//                        //+", start =" + start
//                        //+", Compute =" + computation
//                        //+", Period =" + period
//                        //+", deadline =" + deadline
//                        //+", AbDeadline =" + AbDeadline
//                        //+", task with minimum period time =" + minTaskName
//                        +"\n";
//
//            } while(c.moveToNext());
//
//        }
        //c.close();
        if (feasible == true ) {
            message += "Task set is Feasible \n";
        }
        else {
            message += "Task set is NOT Feasible \n";
        }
        rm.setText(message);


    }
}
