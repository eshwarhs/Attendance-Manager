package com.example.attendancemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class gender extends AppCompatActivity {

    ImageButton male;
    ImageButton female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_gender);
        SharedPreferences pref = getSharedPreferences("user_details",MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        male = (ImageButton) findViewById(R.id.male);
        female = (ImageButton) findViewById(R.id.female);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("gender","male");
                editor.commit();
                Intent homeIntent = new Intent(gender.this,name.class);
                startActivity(homeIntent);
                finish();
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("gender","female");
                editor.commit();
                Intent homeIntent = new Intent(gender.this,name.class);
                startActivity(homeIntent);
                finish();
            }
        });
    }
}
