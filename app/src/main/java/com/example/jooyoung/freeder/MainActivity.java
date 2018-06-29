package com.example.jooyoung.freeder;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

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
    User My = new User();
    ArrayList<EventInformation> eventList = new ArrayList<>();
    ArrayList<EventInformation> selectList = new ArrayList<>();
    Spinner category;
    CheckBox favorite;
    JSONTask connection;
    JSONObject jsonObject;
    JSONArray jsonArray;
    JSONObject ttemp;
    String current_day,select_day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        category = (Spinner)findViewById(R.id.categorylist);
        List<String> _spinner_item = new ArrayList<>();
        String[] temp = {"전체","영화","축제,행사","무용,발레","뮤지컬,연극","기타행사"};
        _spinner_item.addAll(Arrays.asList(temp));
        ArrayAdapter spinner_adapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,_spinner_item);
        final DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext(),"User.db",null,1);

        current_day = getDate();
        select_day = getDate();
        category.setAdapter(spinner_adapter);

        d_day = (ListView)findViewById(R.id.d_day_list);
        calender = (CalendarView)findViewById(R.id.Calendar);
        favorite = (CheckBox)findViewById(R.id.event_favorite);
        connection = new JSONTask();

        if(dbHelper.select().equals("")){
            Toast.makeText(getApplicationContext(),"디비가 비었습니다.",Toast.LENGTH_SHORT).show();

        }else {
            String sp1[] = dbHelper.select().split("&");
            for (int i = 0; i < sp1.length; i++) {
                String sp2[] = sp1[i].split("@");
                for (int j = 0; j < sp2.length; j++) {
                    My.setMyevent(new EventInformation(sp2[0], sp2[1], sp2[2], sp2[3], sp2[4], sp2[5]));
                }
            }
        }
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

        select_list();


        d_day.setAdapter(adapter);

        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                if((month+1)>=10)
                    select_day = year + "." + (month+1) + "." + dayOfMonth;
                else
                    select_day = year + "." + "0" + (month+1) + "." + dayOfMonth;

                adapter = new ListAdapter();
                for(int i=0;i<eventList.size();i++){
                    int temp ,check;
                    temp = (Integer.parseInt(eventList.get(i).getEvent_day().substring(19))-(Integer.parseInt(select_day.substring(8))));
                    check = Integer.parseInt(select_day.substring(5,7));
                    if(eventList.get(i).getEvent_day().substring(16,18).equals(select_day.substring(5,7))){
                        if(temp<10){
                            if(temp < 0)
                            {}
                            else{
                                selectList.add(eventList.get(i));
                                adapter.addItem(eventList.get(i).getEvent_name(),String.valueOf(temp));
                            }

                        }
                    }
                    else if(eventList.get(i).getEvent_day().substring(16,18).equals("0" + String.valueOf(check+1))){
                        if(check % 2 == 0){
                            if(check == 2){
                                int ftemp = Integer.parseInt(select_day.substring(8));
                                int sftemp = 28 - ftemp;
                                if((temp + ftemp)+sftemp < 10){
                                    selectList.add(eventList.get(i));
                                    adapter.addItem(eventList.get(i).getEvent_name(),String.valueOf((temp + ftemp)+sftemp));
                                }
                            }
                            else{
                                int ftemp = Integer.parseInt(select_day.substring(8));
                                int sftemp = 30 - ftemp;
                                if((temp + ftemp)+sftemp < 10){
                                    selectList.add(eventList.get(i));
                                    adapter.addItem(eventList.get(i).getEvent_name(),String.valueOf((temp + ftemp)+sftemp));
                                }
                            }
                        }
                        else{
                            int ftemp = Integer.parseInt(select_day.substring(8));
                            int sftemp = 31 - ftemp;
                            if((temp + ftemp)+sftemp < 10){
                                selectList.add(eventList.get(i));
                                adapter.addItem(eventList.get(i).getEvent_name(),String.valueOf((temp + ftemp)+sftemp));
                            }
                        }
                    }
                }
                d_day.setVisibility(View.VISIBLE);
                d_day.setAdapter(adapter);



                category.setSelection(0);
            }
        });



        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cate = parent.getItemAtPosition(position).toString();
                if(cate.equals("전체")){
                    adapter = new ListAdapter();
                    for(int i=0;i<eventList.size();i++){
                        int temp ,check;
                        temp = (Integer.parseInt(eventList.get(i).getEvent_day().substring(19))-(Integer.parseInt(select_day.substring(8))));
                        check = Integer.parseInt(select_day.substring(5,7));
                        if(eventList.get(i).getEvent_day().substring(16,18).equals(select_day.substring(5,7))){
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
                                    int ftemp = Integer.parseInt(select_day.substring(8));
                                    int sftemp = 28 - ftemp;
                                    if((temp + ftemp)+sftemp < 10){
                                        adapter.addItem(eventList.get(i).getEvent_name(),String.valueOf((temp + ftemp)+sftemp));
                                    }
                                }
                                else{
                                    int ftemp = Integer.parseInt(select_day.substring(8));
                                    int sftemp = 30 - ftemp;
                                    if((temp + ftemp)+sftemp < 10){
                                        adapter.addItem(eventList.get(i).getEvent_name(),String.valueOf((temp + ftemp)+sftemp));
                                    }
                                }
                            }
                            else{
                                int ftemp = Integer.parseInt(select_day.substring(8));
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
                            temp = (Integer.parseInt(eventList.get(i).getEvent_day().substring(19)) - (Integer.parseInt(select_day.substring(8))));
                            check = Integer.parseInt(select_day.substring(5, 7));
                            if (eventList.get(i).getEvent_day().substring(16, 18).equals(select_day.substring(5, 7))) {
                                if (temp < 10) {
                                    if (temp < 0) {
                                    } else {
                                        adapter.addItem(eventList.get(i).getEvent_name(), String.valueOf(temp));
                                    }

                                }
                            } else if (eventList.get(i).getEvent_day().substring(16, 18).equals("0" + String.valueOf(check + 1))) {
                                if (check % 2 == 0) {
                                    if (check == 2) {
                                        int ftemp = Integer.parseInt(select_day.substring(8));
                                        int sftemp = 28 - ftemp;
                                        if ((temp + ftemp) + sftemp < 10) {
                                            adapter.addItem(eventList.get(i).getEvent_name(), String.valueOf((temp + ftemp) + sftemp));
                                        }
                                    } else {
                                        int ftemp = Integer.parseInt(select_day.substring(8));
                                        int sftemp = 30 - ftemp;
                                        if ((temp + ftemp) + sftemp < 10) {
                                            adapter.addItem(eventList.get(i).getEvent_name(), String.valueOf((temp + ftemp) + sftemp));
                                        }
                                    }
                                } else {
                                    int ftemp = Integer.parseInt(select_day.substring(8));
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

       /* favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

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
                _intent.putExtra("Event",eventList);

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

    public void select_list(){
        for(int i=0;i<eventList.size();i++){
            int temp ,check;
            temp = (Integer.parseInt(eventList.get(i).getEvent_day().substring(19))-(Integer.parseInt(current_day.substring(8))));
            check = Integer.parseInt(current_day.substring(5,7));
            if(eventList.get(i).getEvent_day().substring(16,18).equals(current_day.substring(5,7))){
                if(temp<10){
                    if(temp < 0)
                    {}
                    else{
                        selectList.add(eventList.get(i));
                    }

                }
            }
            else if(eventList.get(i).getEvent_day().substring(16,18).equals("0" + String.valueOf(check+1))){
                if(check % 2 == 0){
                    if(check == 2){
                        int ftemp = Integer.parseInt(current_day.substring(8));
                        int sftemp = 28 - ftemp;
                        if((temp + ftemp)+sftemp < 10){
                            selectList.add(eventList.get(i));
                        }
                    }
                    else{
                        int ftemp = Integer.parseInt(current_day.substring(8));
                        int sftemp = 30 - ftemp;
                        if((temp + ftemp)+sftemp < 10){
                            selectList.add(eventList.get(i));
                        }
                    }
                }
                else{
                    int ftemp = Integer.parseInt(current_day.substring(8));
                    int sftemp = 31 - ftemp;
                    if((temp + ftemp)+sftemp < 10){
                        selectList.add(eventList.get(i));
                    }
                }
            }
        }
    }
}

