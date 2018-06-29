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
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.jooyoung.freeder.Adapter.ListAdapter;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Intent _intent;
    ListView d_day;
    CalendarView calender;
    ListAdapter adapter;
    String event_name;
    User My;
    ArrayList<EventInformation> eventList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        d_day = (ListView)findViewById(R.id.d_day_list);
        calender = (CalendarView)findViewById(R.id.Calendar);
        adapter = new ListAdapter();
        My = new User(new EventInformation("asdf","asdf","asdf","asdf","asdf"));
        // 데이터에 저장된 내 정보 ... 에 있는 객체 (반복 돌릴꺼)

        d_day.setAdapter(adapter);

        adapter.addItem("asdf","3");
        adapter.addItem("Asf","5");
        // 이 부분은 반복문으로 데이터 받아올때 넣자

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

