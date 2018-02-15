package com.example.maxim.pointrandomizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddPointActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_point);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    public void create_click(View view)
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        EditText et = (EditText) findViewById(R.id.point_name_ET);
        if(et.getText().toString().length()>30) {
            ErrorMessage(1);
            return;
        }
        intent.putExtra("name", et.getText().toString());
        EditText et2 = (EditText) findViewById(R.id.point_location_ET);
        if(et2.getText().toString().length()>30) {
            ErrorMessage(0);
            return;
        }
        intent.putExtra("loc", et2.getText().toString());
        setResult(1, intent);
        finish();
    }

    public void ErrorMessage(int flag)
    {
        Toast t;
        if(flag == 1) t = Toast.makeText(getApplicationContext(), "Длина названия не должна превышать 30 символов", Toast.LENGTH_SHORT);
        else t = Toast.makeText(getApplicationContext(), "Длина адреса не должна превышать 30 символов", Toast.LENGTH_SHORT);
        t.show();
    }

    public void cancel_click(View view) {
        onBackPressed();
    }
    @Override
    public void onBackPressed()
    {
        setResult(-1, null);
        finish();
    }
}
