package com.example.promanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

public class AddMoreTaskActivity extends AppCompatActivity {
    public Query db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_more_task);
        db = ((GlobalVar)this.getApplication()).getLocalQuery();
        Bundle bundle = getIntent().getExtras();
        loadInformation(bundle.getString("project_id"));
    }

    private void loadInformation(String proId){
        Project project = MyDatabase.getProjectById(db, proId);
        ((TextView)findViewById(R.id.name_textview)).setText(project.getProjectName());
        ((TextView)findViewById(R.id.total_acts_textview)).setText("Total current activities: " + String.valueOf(project.getActivityIdList().size()));
        Activity act = new Activity();
        act.setActivityName(((TextInputEditText)findViewById(R.id.activity_name_textInput)).getText().toString());
        act.setActivityHost(((TextInputEditText)findViewById(R.id.manager_textInput)).getText().toString());
        act.setActivityDeadline(((TextInputEditText)findViewById(R.id.deadline_textInput)).getText().toString());
        act.setActivityDescribe(((TextInputEditText)findViewById(R.id.description_textInput)).getText().toString());
        ((TextView)findViewById(R.id.confirm_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabase.addNewTaskToProject(act, proId);
                backToPreviousPage(proId);
            }
        });
        ((TextView)findViewById(R.id.back_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToPreviousPage(proId);
            }
        });
    }

    private void backToPreviousPage(String proId){
        Intent intent = new Intent(AddMoreTaskActivity.this, ProjectInforActivity.class);
        Bundle bundleBack = new Bundle();
        bundleBack.putString("project_id", proId);
        intent.putExtras(bundleBack);
        startActivity(intent);
    }
}