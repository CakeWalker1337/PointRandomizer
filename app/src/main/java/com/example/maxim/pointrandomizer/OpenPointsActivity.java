package com.example.maxim.pointrandomizer;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;

public class OpenPointsActivity extends AppCompatActivity {

    ArrayList<String> locs = new ArrayList<>();
    ArrayList<String> groups = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_points);

        final ExpandableListView lw = (ExpandableListView) findViewById(R.id.points_listview);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        ArrayList<String> data = extras.getStringArrayList("list");
        for(int i = 0; i<data.size(); i++)
        {
            if(i%2==0) groups.add(data.get(i));
            else locs.add(data.get(i));
        }

        lw.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                int groupPosition = ExpandableListView.getPackedPositionGroup(id);

                groups.remove(groupPosition);
                locs.remove(groupPosition);
                ExpListAdapter adapter = new ExpListAdapter(getApplicationContext(), groups, locs);
                lw.setAdapter(adapter);

                return false;
           }
        });

        ExpListAdapter adapter = new ExpListAdapter(getApplicationContext(), groups, locs);
        lw.setAdapter(adapter);

        Toast t = Toast.makeText(this, "Нажмите и держите, чтобы удалить локацию", Toast.LENGTH_LONG);
        t.show();
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed()
    {
        ArrayList<String> data = new ArrayList<>();
        for(int i = 0; i<groups.size(); i++)
        {
            data.add(groups.get(i));
            data.add(locs.get(i));
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putStringArrayListExtra("list", data);
        setResult(2, intent);
        finish();

    }
}


