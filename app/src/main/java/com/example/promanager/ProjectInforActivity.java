package com.example.promanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;

public class ProjectInforActivity extends AppCompatActivity {
    public static Query db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_infor);
        db = ((GlobalVar)this.getApplication()).getLocalQuery();

        ((TextView)findViewById(R.id.back_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProjectInforActivity.this, MainActivity.class));
            }
        });

        Bundle bundle = getIntent().getExtras();
        loadInformation(bundle.getString("project_id"));
    }

    public void loadInformation(String proId){
        Project project = MyDatabase.getProjectById(db, proId);
        ((TextView)findViewById(R.id.name_textview)).setText(project.getProjectName());
        ((TextView)findViewById(R.id.hoster_textview)).setText(project.getProjectOwner());
        ((TextView)findViewById(R.id.deadline_textview)).setText(project.getProjectDeadline());
        ((TextView)findViewById(R.id.description_textview)).setText(project.getProjectDescribe());

        // test data
        project.addActivityList("act20127333");
        project.addActivityList("act20127306");

        for (String actId: project.getActivityIdList()) {
            View activity_tag = SetUp.getActivityTag(MyDatabase.getActivityById(db, actId), this.getApplicationContext());
            activity_tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ProjectInforActivity.this, TaskInforActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("activity_id", actId);
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
}