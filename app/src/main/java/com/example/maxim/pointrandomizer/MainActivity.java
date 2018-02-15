package com.example.maxim.pointrandomizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    final String FILENAME = "data";
    ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readData();
    }

    public void writeData()
    {
        try {
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(openFileOutput(FILENAME, MODE_PRIVATE)));
            int i = 0;
            while(i != data.size())
            {
                wr.write(data.get(i)+"\n");
                i++;
            }
            wr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void readData()
    {
        try
        {
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));
            String str = "";
            int i = 1;
            while((str = rd.readLine()) != null) {
                data.add(str);
            }
            rd.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public final void randomize_click(View view) {
        if(!data.isEmpty()) {
            Random r = new Random();
            int rand = r.nextInt();
            rand = (rand>0)?(rand%(data.size()/2)):((rand*-1)%(data.size()/2));

            if(rand<0) return;
            rand *= 2;
            TextView tv = (TextView) findViewById(R.id.point_name_textview);

            tv.setText(data.get(rand));
            tv = (TextView) findViewById(R.id.point_location_textview);
            tv.setText(data.get(rand+1));
        }
    }

    public void openLocations_click(View view) {
        if(data.size()>0)
        {
            Intent intent = new Intent(this, OpenPointsActivity.class);
            intent.putStringArrayListExtra("list", data);
            startActivityForResult(intent, 2);
        }
        else
        {
            Toast t = Toast.makeText(getApplicationContext(), "Не найдено ни одной локации!", Toast.LENGTH_SHORT);
            t.show();
        }
    }

    public void addPoint_click(View view) {
        Intent intent = new Intent(this, AddPointActivity.class);
        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(resultCode == 1)
        {
            Bundle b = intent.getExtras();
            data.add(b.getString("name"));
            data.add(b.getString("loc"));
            writeData();
        }
        else if(resultCode == 2)
        {
            Bundle b = intent.getExtras();
            data = b.getStringArrayList("list");
            writeData();

        }
        TextView tv = (TextView) findViewById(R.id.point_name_textview);
        tv.setText("");
        tv = (TextView) findViewById(R.id.point_location_textview);
        tv.setText("");
    }

}
