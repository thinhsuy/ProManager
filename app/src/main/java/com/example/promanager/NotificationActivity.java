package com.example.promanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        getNotificationSpans();
        ((TextView)findViewById(R.id.back_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotificationActivity.this, MainActivity.class));
            }
        });

        ((TextView)findViewById(R.id.checkRequest_textview)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotificationActivity.this, TaskRequestActivity.class));
            }
        });
    }

    public void getNotificationSpans(){
        LinearLayout container = (LinearLayout) findViewById(R.id.content_container);
        for (int i=0;i<3;i++){
            View view = SetUp.getNotificaitonSpan("Architecture Definition hạn là 14 tháng 12 năm 2022 lúc 20:00", this.getApplicationContext());
            container.addView(view);
        }
    }

}