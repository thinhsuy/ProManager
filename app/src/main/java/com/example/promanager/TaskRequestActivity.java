package com.example.promanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

public class TaskRequestActivity extends AppCompatActivity {
    public static Query db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_request);
        db = ((GlobalVar)this.getApplication()).getLocalQuery();
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
        ArrayList<String> activity_request_list_id = MyDatabase.getActivityRequestListId(db, "20127333");
        for (String actId:activity_request_list_id) {
            Activity act = MyDatabase.getActivityById(db, actId);
            container.addView(SetUp.getRequestTaskSpan(act, this.getApplicationContext()));
        }
    }
}