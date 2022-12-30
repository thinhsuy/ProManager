package com.example.promanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

public class ProjectInforActivity extends AppCompatActivity {
    public static Query db;
    public String proId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_infor);
        db = ((GlobalVar)this.getApplication()).getLocalQuery();
        Bundle bundle = getIntent().getExtras();
        proId = bundle.getString("project_id");

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
        Project_Database project = MyDatabase.getProjectById(db, proId);
        ((TextView)findViewById(R.id.name_textview)).setText(project.getProjectName());
        ((TextView)findViewById(R.id.hoster_textview)).setText(project.getProjectOwner());
        ((TextView)findViewById(R.id.deadline_textview)).setText(project.getProjectDeadline());
        ((TextView)findViewById(R.id.description_textview)).setText(project.getProjectDescribe());

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