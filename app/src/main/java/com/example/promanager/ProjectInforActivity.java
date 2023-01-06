package com.example.promanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProjectInforActivity extends AppCompatActivity {
    public static Query db;
    public String proId;
    private Context mContext;
    public ArrayList<Activity_Database> activityIdList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_infor);
        db = ((GlobalVar)this.getApplication()).getLocalQuery();
        Bundle bundle = getIntent().getExtras();
        proId = bundle.getString("project_id");
        mContext = getApplicationContext();

        ((TextView)findViewById(R.id.back_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProjectInforActivity.this, MainActivity.class));
            }
        });

        ((TextView)findViewById(R.id.add_more_task_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProjectInforActivity.this, AddMoreTaskActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("project_id", proId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        loadInformation(proId);
    }

    public void loadInformation(String proId){
        MyDatabase.getProjectById(proId, new MyDatabase.getCurrentProjectCallback() {
            @Override
            public void onCurrentProjectReceived(Project_Database project) {
                ((TextView)findViewById(R.id.name_textview)).setText(project.getProjectName());
                ((TextView)findViewById(R.id.hoster_textview)).setText(project.getProjectOwner());
                ((TextView)findViewById(R.id.deadline_textview)).setText(project.getProjectDeadline());
                ((TextView)findViewById(R.id.description_textview)).setText(project.getProjectDescribe());

//                getListActivity();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Project").child(proId).child("activityIds");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot post : snapshot.getChildren()){
                            Activity_Database acti = post.getValue(Activity_Database.class);
                            Log.e("Test activityIdList", "DONE!");
                            View activity_tag = SetUp.getActivityTag(MyDatabase.getActivityById(db, acti.getActivityID()), mContext);
                            activity_tag.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(ProjectInforActivity.this, TaskInforActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("activity_id", acti.getActivityID());
                                    bundle.putString("source", "tag");
                                    bundle.putString("project_id", proId);
                                    intent.putExtras(bundle);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            });
                            ((FlexboxLayout)findViewById(R.id.activity_tag_container)).addView(activity_tag);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("Test getListActivity function", "Get failed");
                    }
                });

//                if(activityIdList.size() != 0){
//                    for (Activity_Database act: activityIdList) {
//                        Log.e("Test activityIdList", "DONE!");
//                        View activity_tag = SetUp.getActivityTag(MyDatabase.getActivityById(db, act.getActivityID()), mContext);
//                        activity_tag.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Intent intent = new Intent(ProjectInforActivity.this, TaskInforActivity.class);
//                                Bundle bundle = new Bundle();
//                                bundle.putString("activity_id", act.getActivityID());
//                                bundle.putString("source", "tag");
//                                bundle.putString("project_id", proId);
//                                intent.putExtras(bundle);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                startActivity(intent);
//                            }
//                        });
//                        ((FlexboxLayout)findViewById(R.id.activity_tag_container)).addView(activity_tag);
//                    }
//                }
//                else{
//                    Log.e("Size of activityIdList", String.valueOf(activityIdList.size()));
//                }
            }
        });
    }

    public void getListActivity(){
        Log.e("Test getListActivity function", "DONE!");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Project").child(proId).child("activityIds");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot post : snapshot.getChildren()){
                    Activity_Database acti = post.getValue(Activity_Database.class);
                    activityIdList.add(acti);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Test getListActivity function", "Get failed");
            }
        });
    }
}