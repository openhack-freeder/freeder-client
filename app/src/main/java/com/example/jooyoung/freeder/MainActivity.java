package com.example.jooyoung.freeder;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.jooyoung.freeder.Adapter.ListAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    Intent _intent;
    ListView d_day;
    CalendarView calender;
    ListAdapter adapter;
    String event_name,cate;
    User My;
    ArrayList<EventInformation> eventList = new ArrayList<>();
    Spinner category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> spinner_item = new ArrayList<>();
        ArrayAdapter<String> spinner_adapter = new ArrayAdapter<>(this,R.layout.spinner_item,spinner_item);

        String[] temp = {"전체","영화시사회","행사 축제","콘서트,음악회","무용 발레","음악회","뮤지컬","연극,아동극","기타행사"};
        for(int i=0;i<temp.length;i++){
            spinner_item.add(temp[i]);
        }
        spinner_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        category.setAdapter(spinner_adapter);

        d_day = (ListView)findViewById(R.id.d_day_list);
        calender = (CalendarView)findViewById(R.id.Calendar);
        category = (Spinner)findViewById(R.id.categorylist);
        adapter = new ListAdapter();
        My = new User(new EventInformation("asdf","asdf","asdf","asdf","asdf"));
        // 데이터에 저장된 내 정보 ... 에 있는 객체 (반복 돌릴꺼)

        d_day.setAdapter(adapter);

        adapter.addItem("asdf","3");
        adapter.addItem("Asf","5");
        // 이 부분은 반복문으로 데이터 받아올때 넣자

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cate = parent.getItemAtPosition(position).toString();
                if(cate.equals("전체")){

                    for(int i=0;i<eventList.size();i++){
                        adapter.addItem(eventList.get(i).getEvent_name(),eventList.get(i).getEvent_day());
                    }
                }
                else{

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


                _intent.putExtra("event",eventList.get(position));
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
            case R.id.overflow1:
                _intent = new Intent(getApplicationContext(),Mypage.class);

                _intent.putExtra("User",My);
                startActivity(_intent);

                return true;
            case R.id.overflow2:
                return true;
        }
        return false;
    }
}

