package com.example.jooyoung.freeder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.jooyoung.freeder.Adapter.ListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventListActivity extends AppCompatActivity {
    Intent _intent;
    User My;
    ListView events;
    ListAdapter adapter;
    Date mDate;
    long mNow;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.mm.dd");
    ArrayList<EventInformation> eventList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventlist);
        _intent = getIntent();

        adapter = new ListAdapter();
        events = (ListView)findViewById(R.id.event_list);

        for(int i=0;i<eventList.size();i++){
            int temp;
            String current_day;
            current_day = getDate();
            temp = (Integer.parseInt(eventList.get(i).getEvent_day().substring(19))-(Integer.parseInt(current_day.substring(19))));
            if(eventList.get(i).getEvent_day().substring(16,18).equals(current_day.substring(16,18))) {
                if (temp < 10) {
                    if (temp < 0) {
                    } else {
                        adapter.addItem(eventList.get(i).getEvent_name(), String.valueOf(temp));
                    }
                }
            }
        }
        events.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.main_overflow, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.overflow01:
                _intent = new Intent(getApplicationContext(),Mypage.class);

                _intent.putExtra("User",My);
                startActivity(_intent);

                return true;
            case R.id.overflow02:
                _intent = new Intent(getApplicationContext(),MainActivity.class);

                startActivity(_intent);
                return true;
        }
        return false;
    }

    private String getDate(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
}
