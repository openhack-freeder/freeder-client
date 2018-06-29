package com.example.jooyoung.freeder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.jooyoung.freeder.Adapter.ListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {
    Intent _intent;
    ListView d_day;
    CalendarView calender;
    ListAdapter adapter;
    String event_name,cate,receivemsg;
    Date mDate;
    long mNow;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd");
    User My;
    ArrayList<EventInformation> eventList = new ArrayList<>();
    Spinner category;
    CheckBox favorite;
    JSONTask connection;
    JSONObject jsonObject;
    JSONArray jsonArray;
    JSONObject ttemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        category = (Spinner)findViewById(R.id.categorylist);
        List<String> _spinner_item = new ArrayList<>();
        String[] temp = {"전체","영화,시사회","축제,행사(방청,콘서트,음악회","무용,발레","뮤지컬,연극","기타행사"};
        _spinner_item.addAll(Arrays.asList(temp));
        ArrayAdapter spinner_adapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,_spinner_item);

        category.setAdapter(spinner_adapter);

        d_day = (ListView)findViewById(R.id.d_day_list);
        calender = (CalendarView)findViewById(R.id.Calendar);
        favorite = (CheckBox)findViewById(R.id.event_favorite);
        connection = new JSONTask();
        My = new User(new EventInformation("asdf","asdf","asdf","asdf","asdf"));
        // 데이터에 저장된 내 정보 ... 에 있는 객체 (반복 돌릴꺼)

        try {
            receivemsg = connection.execute("check").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Log.i("asd",receivemsg);
        try {
            jsonArray= new JSONArray(receivemsg);
            for(int i=0;i<jsonArray.length();i++){
                ttemp = jsonArray.getJSONObject(i);
                EventInformation _temp = new EventInformation();
                String title = ttemp.getString("title");
                String date = ttemp.getString("date");
                String time = ttemp.getString("time");
                String where = ttemp.getString("where");
                String category = ttemp.getString("category");
                String url = ttemp.getString("url");
                _temp.setEvent_name(title);
                _temp.setEvent_day(date);
                _temp.setEvent_time(time);
                _temp.setEvent_location(where);
                _temp.setEvent_genre(category);
                _temp.setURL(url);
                eventList.add(_temp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }




        d_day.setAdapter(adapter);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cate = parent.getItemAtPosition(position).toString();
                if(cate.equals("전체")){
                    adapter = new ListAdapter();
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
                                    adapter.addItem(eventList.get(i).getEvent_name(),String.valueOf(temp));
                                }

                            }
                        }
                        else if(eventList.get(i).getEvent_day().substring(16,18).equals("0" + String.valueOf(check+1))){
                            if(check % 2 == 0){
                                if(check == 2){
                                    int ftemp = Integer.parseInt(current_day.substring(8));
                                    int sftemp = 28 - ftemp;
                                    if((temp + ftemp)+sftemp < 10){
                                        adapter.addItem(eventList.get(i).getEvent_name(),String.valueOf((temp + ftemp)+sftemp));
                                    }
                                }
                                else{
                                    int ftemp = Integer.parseInt(current_day.substring(8));
                                    int sftemp = 30 - ftemp;
                                    if((temp + ftemp)+sftemp < 10){
                                        adapter.addItem(eventList.get(i).getEvent_name(),String.valueOf((temp + ftemp)+sftemp));
                                    }
                                }
                            }
                            else{
                                int ftemp = Integer.parseInt(current_day.substring(8));
                                int sftemp = 31 - ftemp;
                                if((temp + ftemp)+sftemp < 10){
                                    adapter.addItem(eventList.get(i).getEvent_name(),String.valueOf((temp + ftemp)+sftemp));
                                }
                            }
                        }
                    }
                    d_day.setVisibility(View.VISIBLE);
                    d_day.setAdapter(adapter);
                }
                else{
                    adapter = new ListAdapter();
                    for(int i=0;i<eventList.size();i++) {
                        if (eventList.get(i).getEvent_genre().equals(cate)) {
                            int temp, check;
                            String current_day;
                            current_day = getDate();
                            temp = (Integer.parseInt(eventList.get(i).getEvent_day().substring(19)) - (Integer.parseInt(current_day.substring(8))));
                            check = Integer.parseInt(current_day.substring(5, 7));
                            if (eventList.get(i).getEvent_day().substring(16, 18).equals(current_day.substring(5, 7))) {
                                if (temp < 10) {
                                    if (temp < 0) {
                                    } else {
                                        adapter.addItem(eventList.get(i).getEvent_name(), String.valueOf(temp));
                                    }

                                }
                            } else if (eventList.get(i).getEvent_day().substring(16, 18).equals("0" + String.valueOf(check + 1))) {
                                if (check % 2 == 0) {
                                    if (check == 2) {
                                        int ftemp = Integer.parseInt(current_day.substring(8));
                                        int sftemp = 28 - ftemp;
                                        if ((temp + ftemp) + sftemp < 10) {
                                            adapter.addItem(eventList.get(i).getEvent_name(), String.valueOf((temp + ftemp) + sftemp));
                                        }
                                    } else {
                                        int ftemp = Integer.parseInt(current_day.substring(8));
                                        int sftemp = 30 - ftemp;
                                        if ((temp + ftemp) + sftemp < 10) {
                                            adapter.addItem(eventList.get(i).getEvent_name(), String.valueOf((temp + ftemp) + sftemp));
                                        }
                                    }
                                } else {
                                    int ftemp = Integer.parseInt(current_day.substring(8));
                                    int sftemp = 31 - ftemp;
                                    if ((temp + ftemp) + sftemp < 10) {
                                        adapter.addItem(eventList.get(i).getEvent_name(), String.valueOf((temp + ftemp) + sftemp));
                                    }
                                }
                            }
                        }
                        d_day.setVisibility(View.VISIBLE);
                        d_day.setAdapter(adapter);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        d_day.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _intent = new Intent(getApplicationContext(),EventActivity.class);
                EventInformation temp = new EventInformation();
                for(int i=0;i<eventList.size();i++){

                }

                _intent.putExtra("event",eventList.get(position));
                startActivity(_intent);
            }
        });

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }



    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.main_overflow2, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.overflow03:
                _intent = new Intent(getApplicationContext(),Mypage.class);

                _intent.putExtra("User",My);
                startActivity(_intent);

                return true;
            case R.id.overflow04:
                _intent = new Intent(getApplicationContext(),EventListActivity.class);

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

