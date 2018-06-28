package com.example.jooyoung.freeder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class Mypage extends AppCompatActivity {
    Intent _intent;
    User My;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);
        _intent = getIntent();

        My = (User)_intent.getSerializableExtra("User");
    }
}
