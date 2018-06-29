package com.example.jooyoung.freeder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    Button all_erase,selete_erase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        _intent = getIntent();

        Mylist = (User)_intent.getSerializableExtra("User");

        // 리스트뷰에 데이터 불러오기
        my_events = (ListView)findViewById(R.id.my_list);
        adapter = new ListAdapter_my();
        my_events.setAdapter(adapter);
        Log.i("마이 크기",String.valueOf(Mylist.getMyevent().size()));
        for(int i=0;i<Mylist.getMyevent().size();i++){
            adapter.addItem(Mylist.getMyevent().get(i));
        }

    }
}
