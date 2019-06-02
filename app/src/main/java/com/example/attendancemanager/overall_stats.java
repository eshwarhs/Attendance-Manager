package com.example.attendancemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class overall_stats extends AppCompatActivity {
    DatabaseHandler db;
    TextView t1,t2,t3,t4;
    String sub;
    Float cri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_stats);

        db = new DatabaseHandler(this);

        Intent i =getIntent();
        sub = i.getExtras().getString("subject");
        this.setTitle(sub);

        t1=findViewById(R.id.textView1);
        t2=findViewById(R.id.textView2);
        t3=findViewById(R.id.textView3);
        t4=findViewById(R.id.textView4);
        SharedPreferences pref=getSharedPreferences("user_details",MODE_PRIVATE);
        cri = (float)pref.getInt("criteria",0)/100;
    }

    protected void onResume() {

        super.onResume();
        int p = db.getSubVals(sub, 0);
        String sp = Integer.toString(p);
        int t = db.getSubVals(sub, 1);
        String st = Integer.toString(t);
        float avg = 100 * (float) p / t;
        String savg = String.format("%.1f", avg);
        double aa = (t * cri - p) / (1 - cri);
        int a = (int)Math.ceil(aa);
        String sa = Integer.toString(a);
        t1.setText("Attendance : "+savg+"%");
        t2.setText("Classes Attended : "+sp);
        t3.setText("Total Classes : "+st);
        if(avg>=(cri*100)){
            t4.setText("You are on track!");
            t4.setBackgroundColor(Color.parseColor("#95ed89"));
        }
        else {
            t4.setText("You need to attend the\n next "+sa+" classes.");
            t4.setBackgroundColor(Color.parseColor("#f75e54"));
        }


    }
}
