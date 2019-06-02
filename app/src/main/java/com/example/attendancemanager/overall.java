package com.example.attendancemanager;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class overall extends AppCompatActivity {
    DatabaseHandler db;
    List<sdata> subs;
    TextView t1;
    View container;
    String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall);
        t1 = (TextView)findViewById(R.id.overalltext);
        db = new DatabaseHandler(this);
        subs = db.getAllSubjects();
        container = findViewById(R.id.layout1);
        float ov=0.0f;
        ov=db.getOverallAtt();
        t1.setText("Overall Attendance = "+String.format("%.1f",ov)+"%");
        t1.setTextSize(25);
        for(sdata x:subs)
        {
            //x.name
            Button btt = new Button(this);
            btt.setText(x.name);
            btt.setTextSize(30);
            btt.setContentDescription(x.name);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,30,10,30);
            btt.setLayoutParams(params);
            btt.setBackgroundColor(Color.parseColor("#fafc7d"));
            ((LinearLayout)container).addView(btt);

            btt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ii = new Intent(overall.this,overall_stats.class);
                    ii.putExtra("subject",((Button)v).getContentDescription().toString());
                    startActivity(ii);
                }
            });
        }
    }
}

