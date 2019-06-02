package com.example.attendancemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class subjects extends AppCompatActivity {

    Button plus;
    Button tick_sub;
    LinearLayout container1;
    DatabaseHandler db;
    int c=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        db = new DatabaseHandler(this);
        container1 = (LinearLayout)findViewById(R.id.container);
        plus = (Button)findViewById(R.id.add_sub);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c++;
                addView(c);
            }
        });

        tick_sub = (Button)findViewById(R.id.tick_sub);
        tick_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
                Intent homeIntent = new Intent(subjects.this,timetable.class);
                startActivity(homeIntent);
                finish();
            }
        });
    }

    public void addView(int eid)
    {
        LinearLayout ll = new LinearLayout(this);
        EditText sub = new EditText(this);
        //sub.setText(Integer.toString(eid));
        sub.setId(eid);
        sub.setHint("Enter Subject");
        final Button del = new Button(this);
        del.setText("del");
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewGroup)del.getParent().getParent()).removeView((ViewGroup)del.getParent());
            }
        });
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setPadding(0,20,0,20);
        sub.setLayoutParams(new LinearLayout.LayoutParams(600,LinearLayout.LayoutParams.WRAP_CONTENT));
        del.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        ((LinearLayout)ll).addView(sub);
        ((LinearLayout)ll).addView(del);
        ll.setLayoutParams(new LinearLayout.LayoutParams(800,LinearLayout.LayoutParams.WRAP_CONTENT));
        ((LinearLayout)container1).addView(ll);
    }

    public void submit()
    {
        int count = container1.getChildCount();
        for(int i=0;i<count;i++)
        {
            View view = container1.getChildAt(i);
            if(view instanceof LinearLayout)
            {
                EditText text = (EditText)((LinearLayout) view).getChildAt(0);
                String val= text.getText().toString().trim();
                if(val.length()!=0)
                {
                    db.addSubject(val);
                }
            }
        }
    }
}
