package com.example.jooyoung.freeder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jooyoung.freeder.Adapter.ListAdapter;
import com.example.jooyoung.freeder.Adapter.ListAdapter_my;

import java.util.ArrayList;

public class Mypage extends AppCompatActivity {
    Intent _intent;
    User Mylist = new User();
    ListView my_events;
    ListAdapter_my adapter;
    Button all_erase;
    DatabaseHelper dbHelper;
    String current_day;
    ArrayList<EventInformation> _eventlist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);
        dbHelper = new DatabaseHelper(getApplicationContext(), "User9.db", null, 1);

        _intent = getIntent();

        Mylist = (User)_intent.getSerializableExtra("User");
        current_day = _intent.getStringExtra("current_day");
        _eventlist = (ArrayList<EventInformation>)_intent.getSerializableExtra("Event");

        // 리스트뷰에 데이터 불러오기
        my_events = (ListView)findViewById(R.id.my_list);
        all_erase = (Button)findViewById(R.id.all_erase);
        adapter = new ListAdapter_my();
        my_events.setAdapter(adapter);

        for(int i=0;i<Mylist.getMyevent().size();i++){
            for(int j=0;j<_eventlist.size();j++){
                if(_eventlist.get(j).getEvent_name().equals(Mylist.getMyevent().get(i).getEvent_name())){
                    Mylist.getMyevent().get(i).setDday(_eventlist.get(j).getDday());
                }
            }
            adapter.addItem(Mylist.getMyevent().get(i));
        }

        all_erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<Mylist.getMyevent().size();i++){
                    if(Mylist.getMyevent().get(i).getDday()==0){
                        dbHelper.delete(Mylist.getMyevent().get(i).getEvent_name());
                        if(dbHelper.select().equals("")){
                            Mylist = new User();
                        }
                        else{
                            Mylist = new User();
                            String sp1[] = dbHelper.select().split("%");
                            for (int m = 0; m < sp1.length; m++) {
                                String sp2[] = sp1[m].split("@");
                                Mylist.setMyevent(new EventInformation(sp2[0], sp2[1], sp2[2], sp2[3], sp2[4], sp2[5]));
                            }
                        }
                    }
                }


            }
        });


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.main_overflow3, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.overflow05:
                _intent = new Intent(getApplicationContext(), MainActivity.class);

                _intent.putExtra("User", Mylist);
                startActivity(_intent);
                return true;
        }
        return false;
    }

}
