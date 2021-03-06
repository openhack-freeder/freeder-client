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

import com.example.jooyoung.freeder.Adapter.ListAdapter2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventListActivity extends AppCompatActivity {
    Intent _intent;
    User My;
    ListView events;
    ListAdapter2 adapter;
    Date mDate;
    long mNow;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd");
    ArrayList<EventInformation> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventlist);
        _intent = getIntent();
        eventList = (ArrayList<EventInformation>)_intent.getSerializableExtra("Event");
        My = (User)_intent.getSerializableExtra("User");


        adapter = new ListAdapter2();
        events = (ListView)findViewById(R.id.event_list);

        for(int i=0;i<eventList.size();i++){
            int temp ,check;
            String current_day;
            current_day = getDate();
            temp = (Integer.parseInt(eventList.get(i).getEvent_day().substring(19))-(Integer.parseInt(current_day.substring(8))));
            check = Integer.parseInt(current_day.substring(5,7));
            if(eventList.get(i).getEvent_day().substring(16,18).equals(current_day.substring(5,7))){
                if(temp<10){
                    if(temp < 0)
                    {}
                    else{
                        adapter.addItem(eventList.get(i).getEvent_name(),String.valueOf(temp),eventList.get(i).isFavorite());
                    }

                }
            }
            else if(eventList.get(i).getEvent_day().substring(16,18).equals("0" + String.valueOf(check+1))){
                if(check == 2){
                    int ftemp = Integer.parseInt(current_day.substring(8));
                    int sftemp = 28 - ftemp;
                    if((temp + ftemp)+sftemp < 10){
                        adapter.addItem(eventList.get(i).getEvent_name(),String.valueOf((temp + ftemp)+sftemp),eventList.get(i).isFavorite());
                    }
                }
                else if(check == 4 || check == 6 || check == 9 || check == 11){
                    int ftemp = Integer.parseInt(current_day.substring(8));
                    int sftemp = 30 - ftemp;
                    if((temp + ftemp)+sftemp < 10){
                        adapter.addItem(eventList.get(i).getEvent_name(),String.valueOf((temp + ftemp)+sftemp),eventList.get(i).isFavorite());
                    }
                }
                else{
                    int ftemp = Integer.parseInt(current_day.substring(8));
                    int sftemp = 31 - ftemp;
                    if((temp + ftemp)+sftemp < 10){
                        adapter.addItem(eventList.get(i).getEvent_name(),String.valueOf((temp + ftemp)+sftemp),eventList.get(i).isFavorite());
                    }
                }
            }
        }
        events.setAdapter(adapter);


        events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _intent = new Intent(getApplicationContext(), EventActivity.class);
                EventInformation temp = new EventInformation();
                String name;
                name = adapter.getItem(position);
                for (int i = 0; i < eventList.size(); i++) {
                    if (name.equals(eventList.get(i).getEvent_name())) {
                        temp = eventList.get(i);
                    }
                }
                _intent.putExtra("event", temp);
                startActivity(_intent);
            }
        });

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
