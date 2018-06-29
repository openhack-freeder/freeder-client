package com.example.jooyoung.freeder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

// event 정보 출력 ( 내가 응모할 )
public class EventActivity extends AppCompatActivity {
    Intent _intent;
    EventInformation current_event;
    TextView event_name;
    ImageView event_link;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventinformation);
        _intent = getIntent();
        current_event = (EventInformation)_intent.getSerializableExtra("event");


    }
}
