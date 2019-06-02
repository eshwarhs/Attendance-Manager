package com.example.attendancemanager;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HomeScreen extends AppCompatActivity {
    ImageButton button;
    Button extra;
    Calendar sCalendar;
    TextView heading;
    List<ttdata> ttlist;
    SharedPreferences pref;
    LinearLayout llay;
    String day;
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_home_screen);
        pref = getSharedPreferences("user_details",MODE_PRIVATE);
        String name = pref.getString("username","User");
        Toast.makeText(getApplicationContext(),"Welcome, "+name,Toast.LENGTH_LONG).show();
        sCalendar = Calendar.getInstance();
        day = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        heading = (TextView)findViewById(R.id.header);
        heading.setText(day);
        button = (ImageButton) findViewById(R.id.menu);
        extra = (Button) findViewById(R.id.extra_butt);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(HomeScreen.this, button);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent i1 = new Intent();
                        switch (item.getItemId())
                        {
                            case R.id.one: i1 = new Intent(HomeScreen.this,timetable.class);break;
                            case R.id.two: i1 = new Intent(HomeScreen.this,overall.class);break;
                            case R.id.three: i1 = new Intent(HomeScreen.this,subjects.class);break;
                        }
                        startActivity(i1);
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method
        llay = (LinearLayout)findViewById(R.id.sub_disp);
        final Intent i_extra=new Intent(this,ExtraClass.class);
        extra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i_extra);
            }
        });
    }

    protected void onStart() {
        llay.removeAllViews();
        super.onStart();
        db = new DatabaseHandler(this);
        ttlist=db.getDayTimeTables(day);
        for(ttdata tt:ttlist)
        {
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            TextView tv= new TextView(this);
            TextView percent= new TextView(this);
            Button pr = new Button(this);
            pr.setBackgroundResource(R.drawable.round_button_green);
            pr.setText("✔");pr.setTextSize(34);pr.setContentDescription(tt.sub);
            Button ab = new Button(this);
            ab.setBackgroundResource(R.drawable.round_button_red);
            ab.setText("✖");ab.setTextSize(34);ab.setContentDescription(tt.sub);
            pr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ViewGroup)v.getParent()).setBackgroundColor(Color.parseColor("#b9f442"));
                    db.update_att(((Button)v).getContentDescription().toString(),1);
                    onStart();
                }
            });
            ab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ViewGroup)v.getParent()).setBackgroundColor(Color.parseColor("#f45341"));
                    db.update_att(((Button)v).getContentDescription().toString(),0);
                    onStart();
                }
            });
            int p=db.getSubVals(tt.sub,0);
            int t=db.getSubVals(tt.sub,1);
            float avg=100*(float)p/t;
            String sAvg=String.format("%.1f",avg);


            tv.setText(tt.sub+"\n "+Integer.toString(p)+"/"+Integer.toString(t));
            tv.setTextSize(20);
            percent.setText(sAvg);
            percent.setTextSize(20);
            percent.setWidth(130);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,10,10,20);
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
