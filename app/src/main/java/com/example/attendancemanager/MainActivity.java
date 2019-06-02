package com.example.attendancemanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static  int SPLASH_TIME_OUT = 3000;

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        final boolean cdb = doesDatabaseExist(this,"am");
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          if(cdb) {
                                              Intent homeIntent = new Intent(MainActivity.this, HomeScreen.class);
                                              startActivity(homeIntent);
                                              finish();
                                          }
                                          else{
                                              Intent genIntent = new Intent(MainActivity.this,gender.class);
                                              startActivity(genIntent);
                                              finish();
                                          }
                                      }
                                  },SPLASH_TIME_OUT);
        setContentView(R.layout.activity_main);
    }
}
