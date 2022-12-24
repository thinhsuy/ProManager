package com.example.promanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TaskInfo;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class TaskInforActivity extends AppCompatActivity {
    public Query db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_infor);
        db = ((GlobalVar)this.getApplication()).getLocalQuery();

        Bundle bundle = getIntent().getExtras();

        ((TextView)findViewById(R.id.back_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bundle.getString("source")=="main")
                    startActivity(new Intent(TaskInforActivity.this, MainActivity.class));
                else {
                    Intent intent = new Intent(TaskInforActivity.this, ProjectInforActivity.class);
                    Bundle bundleBack = new Bundle();
                    bundleBack.putString("projectId", bundle.getString("project_id"));
                    intent.putExtras(bundleBack);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });

        loadInformation(bundle.getString("activity_id"));
    }

    public void loadInformation(String actId){
        if (actId==null) return;
        Activity act = MyDatabase.getActivityById(db, actId);
        ((TextView)findViewById(R.id.header_textview)).setText(act.getActivityName());
        ((TextView)findViewById(R.id.hoster_textview)).setText(act.getActivityHost());
        ((TextView)findViewById(R.id.deadline_textview)).setText(act.getActivityDeadline());
        ((TextView)findViewById(R.id.description_textview)).setText(act.getActivityDescribe());
        ((TextView)findViewById(R.id.status_textview)).setText(act.getActivityStatus());
        ((TextView)findViewById(R.id.rate_textview)).setText(act.getActivityAgreement());
    }
}