package com.rtos.app;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by farminfarzin on 6/28/14.
 */
public class edf extends Activity {

    TextView edf;
    DBAdapter myDb;
    int time = 0;
    String CPU = null;
    String blockedTask = null;
    boolean feasible = true;
    String message = "";
    int taskNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edf);

        edf = (TextView) findViewById(R.id.edfViewer);
        myDb = new DBAdapter(this);
        myDb.open();
       // Cursor cursor = myDb.getAllRows();

        while(time < 30){

            Cursor c = myDb.findStartT(time);
            taskNumber = c.getCount();
            if (taskNumber != 0){
                //Log.v("", "This is #"+ c.getCount()  );

                String newTaskName = c.getString(DBAdapter.COL_NAME);
                int AbDlnew = c.getInt(DBAdapter.COL_AbDEADLINE);
                if(CPU == null){
                    message += "Task " + newTaskName + " has started in time " + time + "\n";
                    CPU = newTaskName ;
                    time ++;
                    Log.v("", "here");
                    myDb.computationMinusOne(newTaskName);
                    checkFinish(newTaskName);
                }
                else { //CPU != null

                    int AbDlold = myDb.findAbDeadline(CPU);
                    if (AbDlold <= AbDlnew){
                        myDb.computationMinusOne(CPU);
                        blockedTask = newTaskName;
                        message += "Task " + blockedTask + " has Blocked in time " + time + "\n";
                        checkFinish(CPU);
                        time++;
                    }
                    else {
                        blockedTask = CPU ;
                        CPU = newTaskName;
                        message += "Task " + blockedTask + " has Blocked in time " + time + "\n";
                        message += "Task " + newTaskName + " has started in time " + time + "\n";
                        myDb.computationMinusOne(newTaskName);
                        time ++;
                        checkFinish(newTaskName);
                    }


                }

            }
            else { // c = null it means, there is not a new task to start at the time
                if (CPU != null){
                    time++;
                    myDb.computationMinusOne(CPU);
                    checkFinish(CPU);
                }
                else {  // CPU = null
                    if (blockedTask == null){
                        time++;
                    }
                    else { //blockedTask is not null
                        CPU = blockedTask;
                        blockedTask = null ;
                        message += "Task " + CPU + " has Started AGAIN in time " + time + "\n";
                        myDb.computationMinusOne(CPU);
                        time++;
                        checkFinish(CPU);
                    }
                }
            }



        }
        if (feasible == true ) {
            message += "Task set is Feasible \n";
        }
        else {
            message += "Task set is NOT Feasible \n";
        }
        edf.setText(message);


    }

    private void checkFinish(String name) {
        int com = myDb.computationResult(name);
        int abDeadline = myDb.abDeadlineResult(name);
        if (com == 0 ){
            message += "Task " + name + " has finished in time " + time + "\n";
            CPU = null ;
            myDb.deleteRowByName(name);
            if (abDeadline < time){
                feasible = false ;
            }
        }
    }
}
