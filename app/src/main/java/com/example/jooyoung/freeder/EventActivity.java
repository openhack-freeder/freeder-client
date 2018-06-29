package com.example.jooyoung.freeder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

// event 정보 출력
public class EventActivity extends AppCompatActivity {
    Intent _intent;
    EventInformation current_event;
    TextView event_name,event_dday,event_category,event_time,event_applyperiod,event_link;
    CheckBox event_favorite;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventinformation);
        _intent = getIntent();
        current_event = (EventInformation)_intent.getSerializableExtra("event");
        event_name = (TextView)findViewById(R.id.event_name);
        event_dday = (TextView)findViewById(R.id.event_day);
        event_category = (TextView)findViewById(R.id.event_category);
        event_applyperiod = (TextView)findViewById(R.id.event_applyperiod);
        event_link = (TextView) findViewById(R.id.event_apply);
        event_favorite = (CheckBox)findViewById(R.id.event_favorite);



        event_name.setText(current_event.getEvent_name());
        event_dday.setText("D-" + String.valueOf(current_event.getDday()));
        event_category.setText(current_event.getEvent_genre());
        event_applyperiod.setText(current_event.getEvent_day());
        event_favorite.setChecked(current_event.isFavorite());


        event_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _intent = new Intent(Intent.ACTION_VIEW, Uri.parse(current_event.getURL()));
                startActivity(_intent);

            }
        });



    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.main_overflow4, menu);

        return super.onCreateOptionsMenu(menu);
    }
}
