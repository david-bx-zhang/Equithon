package com.example.david.equithon.Feature;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        InputStream inputStream = null;
        try {
            inputStream = openFileInput("storagecsv.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<String[]> featureList;
        if (inputStream == null) {
            inputStream = getResources().openRawResource(R.raw.test);
            String str = "";
            CSVReader csv = new CSVReader(inputStream);
            featureList = csv.read();
            for (int i = 0; i < featureList.size(); i++) {
                for (int j = 0; j < 12;++j) {
                    str = str + featureList.get(i)[j] + ",";
                }
                str  = str + "\n";
            }
            OutputStream outputStream = null;
            try {
                outputStream = openFileOutput("storagecsv.csv", Context.MODE_PRIVATE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                outputStream.write(str.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            CSVReader csv = new CSVReader(inputStream);
            featureList = csv.read();
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        OutputStream outputStream = null;
        try {
            outputStream = openFileOutput("storagecsv.csv", Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String str = "";
        for (int i = 0; i < featureList.size(); i++) {
            for (int j = 0; j < 12;++j) {
                str = str + featureList.get(i)[j] + ",";
            }
            str  = str + "\n";
        }
        String s = str + "Person,2,Hobby2,Mental Health\n";
        try {
            outputStream.write(s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //        // Example of a call to a native method

        try {
            inputStream = openFileInput("storagecsv.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        CSVReader csv = new CSVReader(inputStream);
        featureList = csv.read();

        String compare1 = new String("David Zhang");
        String compare2 = new String("Test");
        String[] compare1_arr = new String[12];
        String[] compare2_arr = new String[12];


        for (int i = 0; i < featureList.size(); ++i) {
            String name = featureList.get(i)[0];
            if (compare1.equals(name)) {
                for (int j = 0; j < 12; ++j) {
                    compare1_arr[j] = featureList.get(i)[j];
                }
            }
            if (compare2.equals(name)) {
                for (int j = 0; j < 12; ++j) {
                    compare2_arr[j] = featureList.get(i)[j];
                }
            }
        }

        int acc = 0;
        String finalstr = "";

        for (int i = 0; i < 12; ++i) {
            if (i == 2 ||
                    i == 4 ||
                    i == 6  ||
                    i == 8 ||
                    i == 10) {
                if (compare1_arr[i].equals(compare2_arr[i])) {
                    if (acc == 0) {
                        finalstr = "Congratulations, you've matched on: " + compare1_arr[i];
                    } else {
                        finalstr = finalstr + ", " + compare1_arr[i];
                    }

                    ++acc;
                }
            }
        }
        if (acc == 0) {
            finalstr = "No matches are found";
        }


        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(finalstr);
        //tv.setText(featureList.get(1)[0]);
        //tv.setText(stringFromJNI());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
