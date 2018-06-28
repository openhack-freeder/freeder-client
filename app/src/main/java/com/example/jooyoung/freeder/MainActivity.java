package com.example.jooyoung.freeder;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;

import com.example.jooyoung.freeder.Adapter.ListAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Intent _intent;
    ListView d_day;
    CalendarView calender;
    ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        d_day = (ListView)findViewById(R.id.d_day_list);
        calender = (CalendarView)findViewById(R.id.Calendar);
        adapter = new ListAdapter();

        d_day.setAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.ic_launcher_background),"asdf","Asdf");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.ic_launcher_foreground),"Asf","sdf");










    }
}
