package com.example.promanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

public class CreateActivity extends AppCompatActivity {
    public Query db;
    public String project_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        db = ((GlobalVar)this.getApplication()).getLocalQuery();
        setOnclickListener();
    }

    private void setOnclickListener(){
        ((TextView)findViewById(R.id.back_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ((TextView)findViewById(R.id.confirm_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCreationProject();
                Intent intent = new Intent(CreateActivity.this, AddMoreTaskActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("project_id", project_id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void setCreationProject(){
        Project_Database project = new Project_Database();
        project.setProjectName(((TextInputEditText)findViewById(R.id.project_name_textInput)).getText().toString());
        project.setProjectDeadline(((TextInputEditText)findViewById(R.id.project_deadline_textInput)).getText().toString());
        project.setProjectDescribe(((TextInputEditText)findViewById(R.id.project_describe_textInput)).getText().toString());
        project_id = MyDatabase.createNewProject(db, project);
    }


}