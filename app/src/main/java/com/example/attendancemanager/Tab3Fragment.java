package com.example.attendancemanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class Tab3Fragment extends Fragment{
    private static final String TAG="Tab3Fragment";
    DatabaseHandler db;
    Button addsubtt;
    List<ttdata> ttlist;
    LinearLayout llay;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.tab3_fragment,container,false);
        addsubtt = (Button)view.findViewById(R.id.add_subtt3);
        addsubtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addsubb = new Intent(getActivity().getApplication(),subtt.class);
                addsubb.putExtra("day","Wednesday");
                startActivity(addsubb);
            }
        });
        llay = view.findViewById(R.id.sub_disp_3);
        return view;
    }
    public void onStart() {
        super.onStart();
        db = new DatabaseHandler(getActivity());
        llay.removeAllViews();
        ttlist=db.getDayTimeTables("Wednesday");
        for(ttdata tt:ttlist)
        {
            LinearLayout ll = new LinearLayout(getContext());
            ll.setOrientation(LinearLayout.HORIZONTAL);
            TextView tv= new TextView(getContext());
            ImageButton dele = new ImageButton(getContext());
            dele.setImageResource(R.drawable.trash);
            dele.setBackgroundColor(Color.parseColor("#ff0800"));
            dele.setId(tt.id);
            dele.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ViewGroup)v.getParent().getParent()).removeView((ViewGroup)v.getParent());
                    db.removeTT(((ImageButton)v).getId());
                }
            });
            tv.setText(tt.sub);
            tv.setTextSize(38);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,10,10,20);
            ll.setLayoutParams(params);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(850, LinearLayout.LayoutParams.WRAP_CONTENT);
            tv.setLayoutParams(params1);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setBackgroundColor(Color.parseColor("#fafc7d"));
            ll.setHorizontalGravity(8);
            ll.addView(tv);
            ll.addView(dele);
            llay.addView(ll);
        }
    }
}