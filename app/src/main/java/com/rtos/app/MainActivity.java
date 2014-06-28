package com.rtos.app;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class MainActivity extends Activity {

    DBAdapter myDb;
    Button run;
    Button query;
    Button saveButton;
    Button clear;
    RadioGroup algorithms;
    int startA;
    int computeA;
    int periodA;
    int deadlineA;
    int startB;
    int computeB;
    int periodB;
    int deadlineB;
    int startC;
    int computeC;
    int periodC;
    int deadlineC;
    int startD;
    int computeD;
    int periodD;
    int deadlineD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        saveButton =  (Button) findViewById(R.id.save);
        run =  (Button) findViewById(R.id.Run);
        clear =  (Button) findViewById(R.id.clear);
        algorithms = (RadioGroup) findViewById(R.id.algorithms);
        query = (Button) findViewById(R.id.query);


        openDB();
        saveData();
        deleteAll();
        queryAll();
        run();


    }

    private void queryAll() {
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Query = new Intent(MainActivity.this,QueryViewer.class);
                startActivity(Query);
            }
        });
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    private void closeDB() {
        myDb.close();

    }

    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }

   private void saveData() {

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText Astart = (EditText) findViewById(R.id.AStart);
                EditText Acomputation = (EditText) findViewById(R.id.ACompute);
                EditText Aperiod = (EditText) findViewById(R.id.APeriod);
                EditText Adeadline = (EditText) findViewById(R.id.ADeadline);

                EditText Bstart = (EditText) findViewById(R.id.BStart);
                EditText Bcomputation = (EditText) findViewById(R.id.BCompute);
                EditText Bperiod = (EditText) findViewById(R.id.BPeriod);
                EditText Bdeadline = (EditText) findViewById(R.id.BDeadline);

                EditText Cstart = (EditText) findViewById(R.id.CStart);
                EditText Ccomputation = (EditText) findViewById(R.id.CCompute);
                EditText Cperiod = (EditText) findViewById(R.id.CPeriod);
                EditText Cdeadline = (EditText) findViewById(R.id.CDeadline);

                EditText Dstart = (EditText) findViewById(R.id.DStart);
                EditText Dcomputation = (EditText) findViewById(R.id.DCompute);
                EditText Dperiod = (EditText) findViewById(R.id.DPeriod);
                EditText Ddeadline = (EditText) findViewById(R.id.DDeadline);

                startA = Integer.parseInt(Astart.getText().toString());
                computeA = Integer.parseInt(Acomputation.getText().toString());
                periodA= Integer.parseInt(Aperiod.getText().toString());
                deadlineA = Integer.parseInt(Adeadline.getText().toString());

                startB = Integer.parseInt(Bstart.getText().toString());
                computeB = Integer.parseInt(Bcomputation.getText().toString());
                periodB = Integer.parseInt(Bperiod.getText().toString());
                deadlineB = Integer.parseInt(Bdeadline.getText().toString());

                startC = Integer.parseInt(Cstart.getText().toString());
                computeC = Integer.parseInt(Ccomputation.getText().toString());
                periodC = Integer.parseInt(Cperiod.getText().toString());
                deadlineC = Integer.parseInt(Cdeadline.getText().toString());

                startD = Integer.parseInt(Dstart.getText().toString());
                computeD = Integer.parseInt(Dcomputation.getText().toString());
                periodD = Integer.parseInt(Dperiod.getText().toString());
                deadlineD = Integer.parseInt(Ddeadline.getText().toString());


                long newId1 = myDb.insertRow ("A", startA, computeA, periodA, deadlineA);
                long newId2 = myDb.insertRow ("B", startB, computeB, periodB, deadlineB);
                long newId3 = myDb.insertRow ("C", startC, computeC, periodC, deadlineC);
                long newId4 = myDb.insertRow ("D", startD, computeD, periodD, deadlineD);


            }
        });
    }

    private void deleteAll() {
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.deleteAll();
            }
        });
    }

    private void run() {

        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = algorithms.getCheckedRadioButtonId();
                RadioButton algorithm = (RadioButton) findViewById(selectedId);


                if (algorithm.getText().equals("RM")){
                    Toast.makeText(getApplicationContext(),algorithm.getText(),Toast.LENGTH_SHORT).show();
                    Intent rm = new Intent(MainActivity.this,rm.class);
                    startActivity(rm);
                }
                else if (algorithm.getText().equals("EDF")){
                    Toast.makeText(getApplicationContext(),algorithm.getText(),Toast.LENGTH_SHORT).show();
                    Intent edf = new Intent(MainActivity.this,edf.class);
                    startActivity(edf);
                }
                else if (algorithm.getText().equals("PIP")){
                    Toast.makeText(getApplicationContext(),algorithm.getText(),Toast.LENGTH_SHORT).show();
                    Intent pip = new Intent(MainActivity.this,pip.class);
                    startActivity(pip);

                }
                else {
                    Toast.makeText(getApplicationContext(),"please select an algorithm",Toast.LENGTH_SHORT).show();
//
                }
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
