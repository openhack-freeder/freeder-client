package com.example.jooyoung.freeder;

import android.content.Intent;
import android.database.Cursor;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.jooyoung.freeder.Adapter.ListAdapter;
import com.example.jooyoung.freeder.Adapter.SpinnerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {
    Intent _intent;
    ListView d_day;
    CalendarView calender;
    ListAdapter adapter;
    String event_name, cate, receivemsg;
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
    String current_day, select_day;
    TextView select;
    View.OnClickListener mOnClickListner;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        category = (Spinner) findViewById(R.id.categorylist);
        List<String> _spinner_item = new ArrayList<>();
        String[] temp = {"전체", "영화", "축제,행사", "무용,발레", "뮤지컬,연극", "기타행사"};
        _spinner_item.addAll(Arrays.asList(temp));
        ArrayList<spinner_item> spin = new ArrayList<spinner_item>();

        spin.add(new spinner_item(temp[0], R.drawable.all));
        spin.add(new spinner_item(temp[1], R.drawable.movie));
        spin.add(new spinner_item(temp[2], R.drawable.festival));
        spin.add(new spinner_item(temp[3], R.drawable.dance));
        spin.add(new spinner_item(temp[4], R.drawable.musical));
        spin.add(new spinner_item(temp[5], R.drawable.etc));

        SpinnerAdapter spinner_adapter = new SpinnerAdapter(getApplicationContext(),spin);
        dbHelper = new DatabaseHelper(getApplicationContext(), "User10.db", null, 1);


        current_day = getDate();
        select_day = getDate();
        category.setAdapter(spinner_adapter);

        d_day = (ListView) findViewById(R.id.d_day_list);
        calender = (CalendarView) findViewById(R.id.Calendar);
        favorite = (CheckBox) findViewById(R.id.event_favorite);
        select = (TextView) findViewById(R.id.select_day);
        connection = new JSONTask();
        select.setText(select_day);// 입력 날짜 출력

        if (dbHelper.select().equals("")) {
            Toast.makeText(getApplicationContext(), "디비가 비었습니다.", Toast.LENGTH_SHORT).show();
            Log.i("디비 : ", "비었음");

        } else {
            String sp1[] = dbHelper.select().split("$");
            for (int i = 0; i < sp1.length; i++) {
                String sp2[] = sp1[i].split("@");
                My.setMyevent(new EventInformation(sp2[0], sp2[1], sp2[2], sp2[3], sp2[4], sp2[5]));
            }
        }
        try {
            receivemsg = connection.execute("check").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            jsonArray = new JSONArray(receivemsg);
            for (int i = 0; i < jsonArray.length(); i++) {
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

        set_dday();
        Collections.sort(eventList, new Comparator<EventInformation>() {
            @Override
            public int compare(EventInformation o1, EventInformation o2) {
                if (o1.getDday() > o2.getDday()) {
                    return 1;
                } else if (o1.getDday() < o2.getDday()) {
                    return -1;
                }
                return 0;
            }
        });

        select_list();

        for (int i = 0; i < My.getMyevent().size(); i++) {
            for (int j = 0; j < eventList.size(); j++) {
                if (My.getMyevent().get(i).getEvent_name().equals(eventList.get(j).getEvent_name())) {
                    eventList.get(j).setFavorite(true);
                }
            }
        }


        d_day.setAdapter(adapter);

        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                if ((month + 1) >= 10)
                    select_day = year + "." + (month + 1) + "." + dayOfMonth;
                else
                    select_day = year + "." + "0" + (month + 1) + "." + dayOfMonth;

                select.setText(select_day);

                adapter = new ListAdapter(mOnClickListner);
                for (int i = 0; i < eventList.size(); i++) {
                    int temp, check;
                    temp = (Integer.parseInt(eventList.get(i).getEvent_day().substring(19)) - (Integer.parseInt(select_day.substring(8))));
                    check = Integer.parseInt(select_day.substring(5, 7));
                    if (eventList.get(i).getEvent_day().substring(16, 18).equals(select_day.substring(5, 7))) {
                        if (temp < 10) {
                            if (temp < 0) {
                            } else {
                                selectList.add(eventList.get(i));
                                adapter.addItem(eventList.get(i).getEvent_name(), String.valueOf(temp), eventList.get(i).isFavorite());
                            }

                        }
                    } else if (eventList.get(i).getEvent_day().substring(16, 18).equals("0" + String.valueOf(check + 1))) {
                        if (check % 2 == 0) {
                            if (check == 2) {
                                int ftemp = Integer.parseInt(select_day.substring(8));
                                int sftemp = 28 - ftemp;
                                if ((temp + ftemp) + sftemp < 10) {
                                    selectList.add(eventList.get(i));
                                    adapter.addItem(eventList.get(i).getEvent_name(), String.valueOf((temp + ftemp) + sftemp), eventList.get(i).isFavorite());
                                }
                            } else {
                                int ftemp = Integer.parseInt(select_day.substring(8));
                                int sftemp = 30 - ftemp;
                                if ((temp + ftemp) + sftemp < 10) {
                                    selectList.add(eventList.get(i));
                                    adapter.addItem(eventList.get(i).getEvent_name(), String.valueOf((temp + ftemp) + sftemp), eventList.get(i).isFavorite());
                                }
                            }
                        } else {
                            int ftemp = Integer.parseInt(select_day.substring(8));
                            int sftemp = 31 - ftemp;
                            if ((temp + ftemp) + sftemp < 10) {
                                selectList.add(eventList.get(i));
                                adapter.addItem(eventList.get(i).getEvent_name(), String.valueOf((temp + ftemp) + sftemp), eventList.get(i).isFavorite());
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
                if (cate.equals("전체")) {
                    adapter = new ListAdapter(mOnClickListner);
                    for (int i = 0; i < eventList.size(); i++) {
                        int temp, check;
                        temp = (Integer.parseInt(eventList.get(i).getEvent_day().substring(19)) - (Integer.parseInt(select_day.substring(8))));
                        check = Integer.parseInt(select_day.substring(5, 7));
                        if (eventList.get(i).getEvent_day().substring(16, 18).equals(select_day.substring(5, 7))) {
                            if (temp < 10) {
                                if (temp < 0) {
                                } else {
                                    adapter.addItem(eventList.get(i).getEvent_name(), String.valueOf(temp), eventList.get(i).isFavorite());
                                }

                            }
                        } else if (eventList.get(i).getEvent_day().substring(16, 18).equals("0" + String.valueOf(check + 1))) {
                            if (check % 2 == 0) {
                                if (check == 2) {
                                    int ftemp = Integer.parseInt(select_day.substring(8));
                                    int sftemp = 28 - ftemp;
                                    if ((temp + ftemp) + sftemp < 10) {
                                        adapter.addItem(eventList.get(i).getEvent_name(), String.valueOf((temp + ftemp) + sftemp), eventList.get(i).isFavorite());
                                    }
                                } else {
                                    int ftemp = Integer.parseInt(select_day.substring(8));
                                    int sftemp = 30 - ftemp;
                                    if ((temp + ftemp) + sftemp < 10) {
                                        adapter.addItem(eventList.get(i).getEvent_name(), String.valueOf((temp + ftemp) + sftemp), eventList.get(i).isFavorite());
                                    }
                                }
                            } else {
                                int ftemp = Integer.parseInt(select_day.substring(8));
                                int sftemp = 31 - ftemp;
                                if ((temp + ftemp) + sftemp < 10) {
                                    adapter.addItem(eventList.get(i).getEvent_name(), String.valueOf((temp + ftemp) + sftemp), eventList.get(i).isFavorite());
                                }
                            }
                        }
                    }
                    d_day.setVisibility(View.VISIBLE);
                    d_day.setAdapter(adapter);
                } else {
                    adapter = new ListAdapter(mOnClickListner);
                    for (int i = 0; i < eventList.size(); i++) {
                        if (eventList.get(i).getEvent_genre().equals(cate)) {
                            int temp, check;
                            temp = (Integer.parseInt(eventList.get(i).getEvent_day().substring(19)) - (Integer.parseInt(select_day.substring(8))));
                            check = Integer.parseInt(select_day.substring(5, 7));
                            if (eventList.get(i).getEvent_day().substring(16, 18).equals(select_day.substring(5, 7))) {
                                if (temp < 10) {
                                    if (temp < 0) {
                                    } else {
                                        adapter.addItem(eventList.get(i).getEvent_name(), String.valueOf(temp), eventList.get(i).isFavorite());
                                    }

                                }
                            } else if (eventList.get(i).getEvent_day().substring(16, 18).equals("0" + String.valueOf(check + 1))) {
                                if (check % 2 == 0) {
                                    if (check == 2) {
                                        int ftemp = Integer.parseInt(select_day.substring(8));
                                        int sftemp = 28 - ftemp;
                                        if ((temp + ftemp) + sftemp < 10) {
                                            adapter.addItem(eventList.get(i).getEvent_name(), String.valueOf((temp + ftemp) + sftemp), eventList.get(i).isFavorite());
                                        }
                                    } else {
                                        int ftemp = Integer.parseInt(select_day.substring(8));
                                        int sftemp = 30 - ftemp;
                                        if ((temp + ftemp) + sftemp < 10) {
                                            adapter.addItem(eventList.get(i).getEvent_name(), String.valueOf((temp + ftemp) + sftemp), eventList.get(i).isFavorite());
                                        }
                                    }
                                } else {
                                    int ftemp = Integer.parseInt(select_day.substring(8));
                                    int sftemp = 31 - ftemp;
                                    if ((temp + ftemp) + sftemp < 10) {
                                        adapter.addItem(eventList.get(i).getEvent_name(), String.valueOf((temp + ftemp) + sftemp), eventList.get(i).isFavorite());
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

        mOnClickListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.event_favorite:
                        String name = (String) v.getTag();
                        for (int i = 0; i < eventList.size(); i++) {
                            if (eventList.get(i).getEvent_name().equals(name)) {
                                if (eventList.get(i).isFavorite()) {
                                    eventList.get(i).setFavorite(false);
                                    for (int j = 0; j < My.getMyevent().size(); j++) {
                                        if (My.getMyevent().get(j).getEvent_name().equals(name)) {
                                            dbHelper.delete(My.getMyevent().get(j).getEvent_name());
                                            if (dbHelper.select().equals("")) {
                                                My = new User();
                                            } else {
                                                My = new User();
                                                String sp1[] = dbHelper.select().split("%");
                                                for (int m = 0; m < sp1.length; m++) {
                                                    String sp2[] = sp1[m].split("@");
                                                    My.setMyevent(new EventInformation(sp2[0], sp2[1], sp2[2], sp2[3], sp2[4], sp2[5]));
                                                }
                                                break;
                                            }

                                        }
                                    }
                                } else {
                                    My = new User();
                                    eventList.get(i).setFavorite(true);
                                    dbHelper.insert(eventList.get(i).getEvent_name(), eventList.get(i).getEvent_day(), eventList.get(i).getEvent_time(),
                                            eventList.get(i).getEvent_location(), eventList.get(i).getURL(), eventList.get(i).getEvent_genre());

                                    String sp1[] = dbHelper.select().split("%");
                                    Log.i("디비 크기", String.valueOf(sp1.length));
                                    for (int m = 0; m < sp1.length; m++) {
                                        String sp2[] = sp1[m].split("@");
                                        My.setMyevent(new EventInformation(sp2[0], sp2[1], sp2[2], sp2[3], sp2[4], sp2[5]));
                                    }
                                    Log.i("디비 내용", My.getMyevent().get(0).getEvent_name());
                                    break;
                                }
                            }
                        }
                }
            }
        };
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.main_overflow2, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.overflow03:
                _intent = new Intent(getApplicationContext(), Mypage.class);

                _intent.putExtra("User", My);
                _intent.putExtra("Event", eventList);
                _intent.putExtra("current_day",current_day);
                startActivity(_intent);

                return true;
            case R.id.overflow04:
                _intent = new Intent(getApplicationContext(), EventListActivity.class);
                _intent.putExtra("Event", eventList);
                _intent.putExtra("User", My);

                startActivity(_intent);
                return true;
        }
        return false;
    }

    private String getDate() {
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    public void select_list() {
        for (int i = 0; i < eventList.size(); i++) {
            int temp, check;
            temp = (Integer.parseInt(eventList.get(i).getEvent_day().substring(19)) - (Integer.parseInt(current_day.substring(8))));
            check = Integer.parseInt(current_day.substring(5, 7));
            if (eventList.get(i).getEvent_day().substring(16, 18).equals(current_day.substring(5, 7))) {
                if (temp < 10) {
                    if (temp < 0) {
                    } else {
                        selectList.add(eventList.get(i));
                    }

                }
            } else if (eventList.get(i).getEvent_day().substring(16, 18).equals("0" + String.valueOf(check + 1))) {
                if (check % 2 == 0) {
                    if (check == 2) {
                        int ftemp = Integer.parseInt(current_day.substring(8));
                        int sftemp = 28 - ftemp;
                        if ((temp + ftemp) + sftemp < 10) {
                            selectList.add(eventList.get(i));
                        }
                    } else {
                        int ftemp = Integer.parseInt(current_day.substring(8));
                        int sftemp = 30 - ftemp;
                        if ((temp + ftemp) + sftemp < 10) {
                            selectList.add(eventList.get(i));
                        }
                    }
                } else {
                    int ftemp = Integer.parseInt(current_day.substring(8));
                    int sftemp = 31 - ftemp;
                    if ((temp + ftemp) + sftemp < 10) {
                        selectList.add(eventList.get(i));
                    }
                }
            }
        }
    }

    public void set_dday() {
        for (int i = 0; i < eventList.size(); i++) {
            int timetemp, check, dday;
            dday = 100;
            timetemp = (Integer.parseInt(eventList.get(i).getEvent_day().substring(19)) - (Integer.parseInt(select_day.substring(8))));
            check = Integer.parseInt(select_day.substring(5, 7));
            if (eventList.get(i).getEvent_day().substring(16, 18).equals(select_day.substring(5, 7))) {
                dday = timetemp;
            } else if (eventList.get(i).getEvent_day().substring(16, 18).equals("0" + String.valueOf(check + 1))) {
                if (check % 2 == 0) {
                    if (check == 2) {
                        int ftemp = Integer.parseInt(select_day.substring(8));
                        int sftemp = 28 - ftemp;
                        dday = (timetemp + ftemp) + sftemp;
                    } else {
                        int ftemp = Integer.parseInt(select_day.substring(8));
                        int sftemp = 30 - ftemp;
                        dday = (timetemp + ftemp) + sftemp;
                    }
                } else {
                    int ftemp = Integer.parseInt(select_day.substring(8));
                    int sftemp = 31 - ftemp;
                    dday = (timetemp + ftemp) + sftemp;
                }
            } else if (eventList.get(i).getEvent_day().substring(16, 18).equals("0" + String.valueOf(check + 2))) {
                if (check % 2 == 0) {
                    if (check == 2) {
                        int ftemp = Integer.parseInt(select_day.substring(8));
                        int sftemp = 28 - ftemp;
                        dday = (timetemp + ftemp) + sftemp;
                    } else {
                        int ftemp = Integer.parseInt(select_day.substring(8));
                        int sftemp = 30 - ftemp;
                        dday = (timetemp + ftemp) + sftemp;
                    }
                } else {
                    int ftemp = Integer.parseInt(select_day.substring(8));
                    int sftemp = 31 - ftemp;
                    dday = (timetemp + ftemp) + sftemp;
                }
            } else if (eventList.get(i).getEvent_day().substring(16, 18).equals("0" + String.valueOf(check + 3))) {
                if (check % 2 == 0) {
                    if (check == 2) {
                        int ftemp = Integer.parseInt(select_day.substring(8));
                        int sftemp = 28 - ftemp;
                        dday = (timetemp + ftemp) + sftemp;
                    } else {
                        int ftemp = Integer.parseInt(select_day.substring(8));
                        int sftemp = 30 - ftemp;
                        dday = (timetemp + ftemp) + sftemp;
                    }
                } else {
                    int ftemp = Integer.parseInt(select_day.substring(8));
                    int sftemp = 31 - ftemp;
                    dday = (timetemp + ftemp) + sftemp;
                }
            } else if (eventList.get(i).getEvent_day().substring(16, 18).equals("0" + String.valueOf(check + 4))) {
                if (check % 2 == 0) {
                    if (check == 2) {
                        int ftemp = Integer.parseInt(select_day.substring(8));
                        int sftemp = 28 - ftemp;
                        dday = (timetemp + ftemp) + sftemp;
                    } else {
                        int ftemp = Integer.parseInt(select_day.substring(8));
                        int sftemp = 30 - ftemp;
                        dday = (timetemp + ftemp) + sftemp;
                    }
                } else {
                    int ftemp = Integer.parseInt(select_day.substring(8));
                    int sftemp = 31 - ftemp;
                    dday = (timetemp + ftemp) + sftemp;
                }
            } else if (eventList.get(i).getEvent_day().substring(16, 18).equals("0" + String.valueOf(check + 5))) {
                if (check % 2 == 0) {
                    if (check == 2) {
                        int ftemp = Integer.parseInt(select_day.substring(8));
                        int sftemp = 28 - ftemp;
                        dday = (timetemp + ftemp) + sftemp;
                    } else {
                        int ftemp = Integer.parseInt(select_day.substring(8));
                        int sftemp = 30 - ftemp;
                        dday = (timetemp + ftemp) + sftemp;
                    }
                } else {
                    int ftemp = Integer.parseInt(select_day.substring(8));
                    int sftemp = 31 - ftemp;
                    dday = (timetemp + ftemp) + sftemp;
                }
            } else if (eventList.get(i).getEvent_day().substring(16, 18).equals("0" + String.valueOf(check + 6))) {
                if (check % 2 == 0) {
                    if (check == 2) {
                        int ftemp = Integer.parseInt(select_day.substring(8));
                        int sftemp = 28 - ftemp;
                        dday = (timetemp + ftemp) + sftemp;
                    } else {
                        int ftemp = Integer.parseInt(select_day.substring(8));
                        int sftemp = 30 - ftemp;
                        dday = (timetemp + ftemp) + sftemp;
                    }
                } else {
                    int ftemp = Integer.parseInt(select_day.substring(8));
                    int sftemp = 31 - ftemp;
                    dday = (timetemp + ftemp) + sftemp;
                }
            }
            eventList.get(i).setDday(dday);
        }

    }
}
