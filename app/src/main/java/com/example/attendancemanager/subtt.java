package com.example.attendancemanager;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

public class subtt extends AppCompatActivity {
    DatabaseHandler db;
    List<sdata> subs;
    View container;
    String day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtt);
        db = new DatabaseHandler(this);
        subs = db.getAllSubjects();
        container = findViewById(R.id.layout1);
        Intent i=getIntent();
        day=i.getExtras().getString("day");
        for(sdata x:subs)
        {
            //x.name
            Button btt = new Button(this);
            btt.setText(x.name);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,10,10,10);
            btt.setLayoutParams(params);
            btt.setBackgroundColor(Color.parseColor("#fafc7d"));
            ((LinearLayout)container).addView(btt);

            btt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sub = ((Button)v).getText().toString();
                    ttdata tt= new ttdata();
                    tt.day=day;
                    tt.sub=sub;
                    db.addTimeTable(tt);
                    Toast.makeText(getApplicationContext(),sub+" has been inserted into "+day,Toast.LENGTH_SHORT).show();


                }
            });
        }
    }
}
