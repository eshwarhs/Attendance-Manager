package com.example.attendancemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class name extends AppCompatActivity {

    TextView tv;
    SeekBar seekBar;
    ImageView im;
    EditText namet;
    SharedPreferences pref;
    Button tick_name;
    int prval = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_name);
        pref = getSharedPreferences("user_details",MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        String g = pref.getString("gender","Error!");
        im = findViewById(R.id.imageView);
        if(g.equals("male"))
        {
            im.setImageResource(R.drawable.boysquare);
        }
        else if(g.equals("female"))
        {
            im.setImageResource(R.drawable.girlsquare);
        }
        namet = (EditText)findViewById(R.id.editTextName);
        String namett = namet.getText().toString().trim();
        editor.putString("username",namett);
        editor.commit();
        tick_name = findViewById(R.id.tick_name);
        tv= findViewById(R.id.attendancePer);
        seekBar=findViewById(R.id.seekBar2);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv.setText(" "+progress+"%");
                prval = progress;
                //editor.putInt("criteria",prval);
                //editor.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        tick_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namett = namet.getText().toString().trim();
                editor.putString("username",namett);
                editor.putInt("criteria",prval);
                editor.commit();
                Intent subIntent = new Intent(name.this,subjects.class);
                startActivity(subIntent);
                finish();
            }
        });
    }
}
