package com.example.promanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddMoreTaskActivity extends AppCompatActivity {
    public Query db;

    ArrayList<String> activityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_more_task);
        db = ((GlobalVar)this.getApplication()).getLocalQuery();
        Bundle bundle = getIntent().getExtras();

        loadInformation(bundle.getString("project_id"));

        Log.e("CHECK ID ADD MORE", bundle.getString("project_id"));
        Toast.makeText(AddMoreTaskActivity.this, bundle.getString("project_id"), Toast.LENGTH_SHORT).show();

        ((TextView)findViewById(R.id.confirm_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabase.createActivity(new MyDatabase.ActivityIdCallback() {
                    @Override
                    public void onActivityIdReceived(String activityID) {
                        Activity_Database act = new Activity_Database();
                        act.setActivityName(((TextInputEditText)findViewById(R.id.activity_name_textInput)).getText().toString().trim());
                        act.setActivityHost(((TextInputEditText)findViewById(R.id.manager_textInput)).getText().toString().trim());
                        act.setActivityDeadline(((TextInputEditText)findViewById(R.id.deadline_textInput)).getText().toString().trim());
                        act.setActivityDescribe(((TextInputEditText)findViewById(R.id.description_textInput)).getText().toString().trim());
                        act.setActivityStatus("Not Finished");
                        act.setActivityAgreement("0%");
                        act.setActivityID(activityID);

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Activity");

                        String pathObject = String.valueOf(act.getActivityID());

                        myRef.child(pathObject).setValue(act, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(AddMoreTaskActivity.this, "Create Activity complete!", Toast.LENGTH_SHORT).show();
                                MyDatabase.addNewTaskToProject(AddMoreTaskActivity.this, ProjectInforActivity.class, act, bundle.getString("project_id"));
//                                backToPreviousPage(bundle.getString("project_id"));
                            }
                        });
//                        Toast.makeText(AddMoreTaskActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        ((TextView)findViewById(R.id.back_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToPreviousPage(bundle.getString("project_id"));
            }
        });
    }

    private void loadInformation(String proId) {
        MyDatabase.getProjectById(proId, new MyDatabase.getCurrentProjectCallback() {
            @Override
            public void onCurrentProjectReceived(Project_Database projectCurrent) {
                Log.d("ProjectID", proId);
                ((TextView) findViewById(R.id.name_textview)).setText(projectCurrent.getProjectName());
                try{
                    ((TextView) findViewById(R.id.total_acts_textview)).setText("Total current activities: " + String.valueOf(projectCurrent.getActivityIdList().size()));
                }
                catch (Exception e){
                    ((TextView) findViewById(R.id.total_acts_textview)).setText("Total current activities: 0");

                }
                Toast.makeText(AddMoreTaskActivity.this, "getProjectById DONE!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void createTask(){
        Activity_Database activity_database = new Activity_Database();
//        activity_database.setActivityID();
    }

    private void backToPreviousPage(String proId){
        Log.e("backToPreviousPage", "DONE");
        Intent intent = new Intent(AddMoreTaskActivity.this, ProjectInforActivity.class);
        Bundle bundleBack = new Bundle();
        bundleBack.putString("project_id", proId);
        intent.putExtras(bundleBack);
        startActivity(intent);
    }
}