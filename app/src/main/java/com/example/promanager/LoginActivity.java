package com.example.promanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "ResetPasswordByEmail";
    private Button sign_in_btn;
    private Button sign_up_btn;
    private ProgressDialog progressDialog;
    private TextView forget_password_tv;
    private TextInputLayout username_til;
    private TextInputLayout password_til;
    Query db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        sign_in_btn = (Button) findViewById(R.id.sign_in_btn);
        sign_up_btn = (Button) findViewById(R.id.sign_up_btn);
        forget_password_tv = (TextView) findViewById(R.id.forget_password_textview);
        username_til = (TextInputLayout) findViewById(R.id.username_textinputlayout);
        password_til = (TextInputLayout) findViewById(R.id.password_textinputlayout);
        set_event_onclick();

        db = new Query(this, "ProManager1.sqlite", null, 1);

        // Create table
//        String createUserInfo = "CREATE TABLE IF NOT EXISTS UserInfo(username VARCHAR(100) PRIMARY KEY," +
//                "pass VARCHAR(100)," +
//                "email VARCHAR(100)," +
//                "phonenumber VARCHAR(10)," +
//                "fullname NVARCHAR(100)," +
//                "overview NVARCHAR(1000)," +
//                "totalTasks smallint," +
//                "totalHours smallint," +
//                "currentTasks smallint," +
//                "currentFinished smallint)";
//        db.queryData(createUserInfo);
////
////        db.queryData("DROP TABLE UserInfo");
//
//        String createProject = "CREATE TABLE IF NOT EXISTS Project(projectID VARCHAR(10) PRIMARY KEY," +
//                "projectName NVARCHAR(100)," +
//                "projectOwner VARCHAR(100)," +
//                "projectDeadline date," +
//                "projectDescribe NVARCHAR(1000)," +
//                "projectPrivacy SMALLINT)";
//        db.queryData(createProject);
//
//        String createActivity = "CREATE TABLE IF NOT EXISTS Activity(activityID VARCHAR(10) PRIMARY KEY," +
//                "activityName NVARCHAR(100)," +
//                "activityDescribe NVARCHAR(1000)," +
//                "activityDeadline date," +
//                "activityHost VARCHAR(10)," +
//                "activityFile VARCHAR(1000)," +
//                "activityStatus VARCHAR(1000)," +
//                "activityAgreement NVARCHAR(100))";
//        db.queryData(createActivity);
//
//        String createUserConnection = "CREATE TABLE IF NOT EXISTS UserConnection(" +
//                "usernameA varchar(100)," +
//                "usernameB varchar(100)," +
//                "PRIMARY KEY (usernameA, usernameB))";
//        db.queryData(createUserConnection);
//
//        String createActivityInProject = "CREATE TABLE IF NOT EXISTS ActivityInProject(" +
//                "projectID varchar(10)," +
//                "activityID varchar(10)," +
//                "PRIMARY KEY (projectID, activityID))";
//        db.queryData(createActivityInProject);
//
//        String createUserResponActivity = "CREATE TABLE IF NOT EXISTS UserResponActivity(" +
//                "activityID varchar(10)," +
//                "username varchar(100)," +
//                "PRIMARY KEY (activityID, username))";
//        db.queryData(createUserResponActivity);
//
//        String createUserResponProject = "CREATE TABLE IF NOT EXISTS UserResponProject(" +
//                "username varchar(100)," +
//                "projectID varchar(10)," +
//                "PRIMARY KEY (username, projectID))";
//        db.queryData(createUserResponProject);
//
//        String createAchieveActivity = "CREATE TABLE IF NOT EXISTS AchieveActivity(" +
//                "username varchar(100)," +
//                "activityID varchar(10)," +
//                "PRIMARY KEY (username, activityID))";
//        db.queryData(createAchieveActivity);
//
//        // ADD DATA
//
//        // UserInfo TABLE
//        db.queryData("INSERT INTO UserInfo " +
//                "VALUES ('username1', '123456', 'abc123@gmail.com', '0893483493', 'US1', 'ABC', 10, 30, 3, 7)");
//        db.queryData("INSERT INTO UserInfo " +
//                "VALUES ('username2', '123456', 'def456@gmail.com', '0399274829', 'US2', 'DEF', 5, 24, 1, 4)");
//        db.queryData("INSERT INTO UserInfo " +
//                "VALUES ('username3', '123456', 'xyz278@gmail.com', '0773827283', 'US3', 'CCC', 8, 20, 5, 3)");
//
//        //Project TABLE
//        db.queryData("INSERT INTO Project VALUES ('project1', 'PA1', 'username1', '2022-11-30', NULL, NULL)");
//        db.queryData("INSERT INTO Project VALUES ('project2', 'PA2', 'username2', '2023-05-05', NULL, NULL)");
//
//        //Activity TABLE
//        db.queryData("INSERT INTO Activity VALUES ('activity1', 'CNPM_Task1', 'dosth', '2023-10-01', 'username1', NULL, NULL, NULL)");
//        db.queryData("INSERT INTO Activity VALUES ('activity2', 'CNPM_Task2', 'dosth2', '2022-12-12', 'username3', NULL, NULL, NULL)");
//        db.queryData("INSERT INTO Activity VALUES ('activity3', 'WEB_Task1', 'dosth3', '2022-12-15', 'username3', NULL, NULL, NULL)");
//
//        //UserConnection TABLE
//        db.queryData("INSERT INTO UserConnection VALUES ('username1', 'username2')");
//        db.queryData("INSERT INTO UserConnection VALUES ('username1', 'username3')");
//        db.queryData("INSERT INTO UserConnection VALUES ('username2', 'username3')");
//
//        //ActivityInProject TABLE
//        db.queryData("INSERT INTO ActivityInProject VALUES ('project1', 'activity2')");
//        db.queryData("INSERT INTO ActivityInProject VALUES ('project1', 'activity3')");
//
//        //UserResponActivity TABLE
//        db.queryData("INSERT INTO UserResponActivity VALUES ('activity1', 'username1')");
//        db.queryData("INSERT INTO UserResponActivity VALUES ('activity2', 'username1')");
//        db.queryData("INSERT INTO UserResponActivity VALUES ('activity2', 'username3')");
//
//        //UserResponProject TABLE
//        db.queryData("INSERT INTO UserResponProject VALUES ('username3', 'project1')");
//        db.queryData("INSERT INTO UserResponProject VALUES ('username3', 'project2')");
//
//        //AchieveActivity TABLE
//        db.queryData("INSERT INTO AchieveActivity VALUES ('username1', 'activity3')");
//        db.queryData("INSERT INTO AchieveActivity VALUES ('username3', 'activity3')");
    }

    private void set_event_onclick(){
        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //after checking, if not vaild -> do nothing, else -> move to next activity
//                if (!check_infor_textfield()) return;
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
//        forget_password_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    private boolean check_infor_textfield(){
        String username = username_til.getEditText().getText().toString();
        String password = password_til.getEditText().getText().toString();
        return check_database(username, password);
    }

    private boolean check_database(String username, String password){
        Log.e("Username", username);
        Log.e("Password", password);
        //check database here
        if (username.equals("") || password.equals("") || !MyDatabase.checkLogin(username, password))
            return false;
        else return true;
    }

    private void onClickForgotPassword(){
        progressDialog.show();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = "user@example.com"; //Truyền email mà người dùng đã đăng kí trước đó

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.cancel();
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }
}