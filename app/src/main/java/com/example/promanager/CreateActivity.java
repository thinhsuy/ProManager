package com.example.promanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class CreateActivity extends AppCompatActivity {
    public Query db;
    public String project_id = "";

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
                startActivity(new Intent(CreateActivity.this, MainActivity.class));
            }
        });

        ((TextView)findViewById(R.id.confirm_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCreationProject();

            }
        });
    }

    private void setCreationProject(){

        Project_Database project = new Project_Database();
        project.setProjectOwner(((GlobalVar)this.getApplication()).getUserId());
        project.setProjectName(((TextInputEditText)findViewById(R.id.project_name_textInput)).getText().toString().trim());
        project.setProjectDeadline(((TextInputEditText)findViewById(R.id.project_deadline_textInput)).getText().toString().trim());
        project.setProjectDescribe(((TextInputEditText)findViewById(R.id.project_describe_textInput)).getText().toString().trim());

        MyDatabase.createNewProject(project, new MyDatabase.ProjectIdCallback() {
            @Override
            public void onProjectIdReceived(String projectId) {
//                Toast.makeText(CreateActivity.this, "ProjectID: " + projectId, Toast.LENGTH_SHORT).show();
                project_id = projectId;
                project.setProjectID(project_id);

                FirebaseDatabase database = FirebaseDatabase.getInstance();

                DatabaseReference myRef = database.getReference("Project");

                String pathObject = String.valueOf(project.getProjectID());

                myRef.child(pathObject).setValue(project, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(CreateActivity.this, "Create Project complete!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CreateActivity.this, AddMoreTaskActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("project_id", project_id);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}