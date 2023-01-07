package com.example.promanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddMoreTaskActivity extends AppCompatActivity {
    public Query db;
    public String userId;

    ArrayList<String> activityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_more_task);
        db = ((GlobalVar)this.getApplication()).getLocalQuery();
        userId = ((GlobalVar)this.getApplication()).getUserId();
        Bundle bundle = getIntent().getExtras();

        loadInformation(bundle.getString("project_id"));

        ((TextView)findViewById(R.id.confirm_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabase.createActivity(new MyDatabase.ActivityIdCallback() {
                    @Override
                    public void onActivityIdReceived(String activityID) {
                        Activity_Database act = new Activity_Database();
                        if(((TextInputEditText)findViewById(R.id.activity_name_textInput)).getText().toString().trim().isEmpty()){
                            new AlertDialog.Builder(AddMoreTaskActivity.this)
                                    .setTitle("Create Activity failed!")
                                    .setMessage("Cannot create activity without name")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            ((TextInputEditText)findViewById(R.id.activity_name_textInput)).setText("");
                                        }
                                    })
                                    .show();
                            return;
                        }
                        if(((TextInputEditText)findViewById(R.id.manager_textInput)).getText().toString().trim().isEmpty()){
                            new AlertDialog.Builder(AddMoreTaskActivity.this)
                                    .setTitle("Create Activity failed!")
                                    .setMessage("Cannot create activity without worker")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            ((TextInputEditText)findViewById(R.id.manager_textInput)).setText("");
                                        }
                                    })
                                    .show();
                            return;
                        }

                        String deadline = ((TextInputEditText)findViewById(R.id.deadline_textInput)).getText().toString().trim();
                        String[] separated_check =  deadline.split("/");
                        if (separated_check.length!=3){
                            new AlertDialog.Builder(AddMoreTaskActivity.this)
                                    .setTitle("Create Activity failed!")
                                    .setMessage("Wrong format for deadline, ex: 11/09/2002")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            ((TextInputEditText)findViewById(R.id.deadline_textInput)).setText("");
                                        }
                                    })
                                    .show();
                            return;
                        }

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
                                if (!((TextInputEditText)findViewById(R.id.manager_textInput)).getText().toString().trim().equals(userId)){
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("Invitation");
                                    
                                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Invitation invite = new Invitation();
                                            invite.setFromUser(userId);
                                            invite.setToUser(((TextInputEditText)findViewById(R.id.manager_textInput)).getText().toString().trim());
                                            invite.setProjectId(bundle.getString("project_id"));
                                            invite.setInviteId("invite"+Long.toString(snapshot.getChildrenCount()+1));

                                            String pathObject = String.valueOf(invite.getInviteId());

                                            FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                                            DatabaseReference myRef1 = database1.getReference("Invitation");

                                            myRef1.child(pathObject).setValue(invite, new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
        
                                }
                                else {
                                    backToPreviousPage(bundle.getString("project_id"));
                                }
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
        final String[] total_tasks_str = {""};
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Project").child(proId).child("activityIds");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                total_tasks_str[0] = Long.toString(snapshot.getChildrenCount());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    ((TextView) findViewById(R.id.total_acts_textview)).setText("Total current activities: " + total_tasks_str[0]);
                }
                catch (Exception e){
                    ((TextView) findViewById(R.id.total_acts_textview)).setText("Total current activities: 0");
                }
            }
        }, 500);

        final String[] name = {""};
        MyDatabase.getOwnProject("none", new MyDatabase.getAllProjectsCallback() {
            @Override
            public void onAllProjectsReceived(ArrayList<Project_Database> all_projects) {
                for (Project_Database cur_Project : all_projects) {
                    if (cur_Project.getProjectID().equals(proId)) {
                        name[0] = cur_Project.getProjectName();

                    }
                }
            }
        });
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ((TextView) findViewById(R.id.name_textview)).setText(name[0]);
            }
        }, 500);
    }


    private void backToPreviousPage(String proId){
        Intent intent = new Intent(AddMoreTaskActivity.this, ProjectInforActivity.class);
        Bundle bundleBack = new Bundle();
        bundleBack.putString("project_id", proId);
        intent.putExtras(bundleBack);
        startActivity(intent);
    }
}