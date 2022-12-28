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

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {
    public Query db;
    public String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        db = ((GlobalVar)this.getApplication()).getLocalQuery();
        userId = ((GlobalVar)this.getApplication()).getUserId();
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
        ArrayList<String> listNotifications = MyDatabase.getUserNotifications(db, userId);
        for (String notification: listNotifications) {
            View view = SetUp.getNotificaitonSpan(notification, this.getApplicationContext());
            container.addView(view);
        }
    }

}