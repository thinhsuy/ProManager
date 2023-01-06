package com.example.promanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {
    public Query db;
    public String userId;
    private static Context context;
    public static Context getAppContext() {
        return NotificationActivity.context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        NotificationActivity.context = getApplicationContext();
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
        final ArrayList<String>[] listNotifications = new ArrayList[]{new ArrayList<String>()};

        MyDatabase.getAllProject("none", new MyDatabase.getAllProjectsCallback() {
            @Override
            public void onAllProjectsReceived(ArrayList<Project_Database> all_projects) {
                for (Project_Database cur_Project : all_projects) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Project").child(cur_Project.getProjectID()).child("activityIds");
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snap : snapshot.getChildren()){
                                Activity_Database act = snap.getValue(Activity_Database.class);
                                if (act.getActivityHost().equals(userId) && act.getActivityStatus().equals("Not Finished"))
                                    listNotifications[0].add("\"" + act.getActivityName() + "\" hạn là " + act.getActivityDeadline());
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });

                }
            }
        });

        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                for (String notification: listNotifications[0]) {
                    View view = SetUp.getNotificaitonSpan(notification, NotificationActivity.getAppContext());
                    container.addView(view);
                }
            }
        }, 500);


    }

}