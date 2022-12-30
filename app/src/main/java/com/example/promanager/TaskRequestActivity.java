package com.example.promanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskRequestActivity extends AppCompatActivity {
    public static Query db;
    public static String userId;
    private static Context context;
    public static Context getAppContext() {
        return TaskRequestActivity.context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TaskRequestActivity.context = getApplicationContext();
        setContentView(R.layout.activity_task_request);
        db = ((GlobalVar)this.getApplication()).getLocalQuery();
        userId = ((GlobalVar)this.getApplication()).getUserId();
        ((TextView)findViewById(R.id.back_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TaskRequestActivity.this, NotificationActivity.class));
            }
        });
        getRequestSpan();
    }

    public void getRequestSpan(){
        LinearLayout container = ((LinearLayout)findViewById(R.id.content_container));
        ArrayList<String> activity_request_list_id = MyDatabase.getActivityRequestListId(db, userId);
        for (String actId:activity_request_list_id) {
            Activity_Database act = MyDatabase.getActivityById(db, actId);
            container.addView(SetUp.getRequestTaskSpan(db, act, userId, this.getApplicationContext()));
        }
    }
}