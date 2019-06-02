package com.example.attendancemanager;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ExtraClass extends AppCompatActivity {
    Calendar sCalendar;
    List<String> ttlist;
    LinearLayout llay;
    String day;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_class);
        sCalendar = Calendar.getInstance();
        day = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        llay = (LinearLayout)findViewById(R.id.container);
    }

    protected void onStart() {
        super.onStart();
        llay.removeAllViews();
        db = new DatabaseHandler(this);
        ttlist = db.getSubsNotInDay(day);
        for (String tt : ttlist) {
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            TextView tv = new TextView(this);
            TextView percent = new TextView(this);
            Button pr = new Button(this);
            pr.setText("✔");
            pr.setTextSize(34);
            pr.setBackgroundResource(R.drawable.round_button_green);
            pr.setContentDescription(tt);
            Button ab = new Button(this);
            ab.setText("✖");
            ab.setTextSize(34);
            ab.setBackgroundResource(R.drawable.round_button_red);
            ab.setContentDescription(tt);
            pr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ViewGroup) v.getParent()).setBackgroundColor(Color.parseColor("#b9f442"));
                    db.update_att(((Button) v).getContentDescription().toString(), 1);
                    onStart();
                }
            });
            ab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ViewGroup) v.getParent()).setBackgroundColor(Color.parseColor("#f45341"));
                    db.update_att(((Button) v).getContentDescription().toString(), 0);
                    onStart();
                }
            });
            int p = db.getSubVals(tt, 0);
            int t = db.getSubVals(tt, 1);
            float avg = 100 * (float) p / t;
            String sAvg = String.format("%.1f", avg);


            tv.setText(tt + "\n " + Integer.toString(p) + "/" + Integer.toString(t));
            tv.setTextSize(20);
            percent.setText(sAvg);
            percent.setTextSize(20);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 20);
            ll.setLayoutParams(params);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(500, LinearLayout.LayoutParams.MATCH_PARENT);
            tv.setLayoutParams(params1);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setBackgroundColor(Color.parseColor("#fafc7d"));
            ll.addView(tv);
            ll.addView(percent);
            ll.addView(pr);
            ll.addView(ab);

            llay.addView(ll);
        }
    }
}

