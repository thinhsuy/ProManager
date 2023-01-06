package com.example.promanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        final ArrayList<String>[] manager = new ArrayList[]{new ArrayList<String>()};
        final ArrayList<String>[] project_name = new ArrayList[]{new ArrayList<String>()};
        final ArrayList<String>[] describe = new ArrayList[]{new ArrayList<String>()};
        DatabaseReference inviteRef = (FirebaseDatabase.getInstance()).getReference("Invitaion");
        inviteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    Invitation invite = snap.getValue(Invitation.class);
                    if (invite.getToUser().equals(userId)){
                        manager[0].add(invite.getFromUser());
                        MyDatabase.getAllProject("none", new MyDatabase.getAllProjectsCallback() {
                            @Override
                            public void onAllProjectsReceived(ArrayList<Project_Database> all_projects) {
                                for (Project_Database cur_Project : all_projects)
                                    if (cur_Project.getProjectID().equals(invite.getProjectId())){
                                        describe[0].add(cur_Project.getProjectDescribe());
                                        project_name[0].add(cur_Project.getProjectName());
                                        break;
                                    }

                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<manager[0].size(); i++){
                    container.addView(SetUp.getRequestTaskSpan(userId, manager[0].get(i), project_name[0].get(i), describe[0].get(i), TaskRequestActivity.getAppContext()));
                }
            }
        }, 500);
//        for (String actId:activity_request_list_id) {
//            Activity_Database act = MyDatabase.getActivityById(db, actId);
//            container.addView(SetUp.getRequestTaskSpan(db, act, userId, this.getApplicationContext()));
//        }
    }
}