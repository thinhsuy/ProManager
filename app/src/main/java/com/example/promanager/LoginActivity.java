package com.example.promanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "ResetPasswordByEmail";
    private Button sign_in_btn;
    private Button sign_up_btn;
    private ProgressDialog progressDialog;
    private TextView forget_password_tv;
    private TextInputLayout username_til;
    private TextInputLayout password_til;
    public Query db;
    String username;
    String password;
    Boolean isCheck = false;
    private static Context context;
    public static Context getAppContext() {
        return LoginActivity.context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginActivity.context = getApplicationContext();
        sign_in_btn = (Button) findViewById(R.id.sign_in_btn);
        sign_up_btn = (Button) findViewById(R.id.sign_up_btn);
        forget_password_tv = (TextView) findViewById(R.id.forget_password_textview);
        set_event_onclick();

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("Invitation");
//
//
//
//        Invitation invite = new Invitation();
//        invite.setFromUser("username1");
//        invite.setToUser("username2");
//        invite.setProjectId("project1");
//
//        myRef.setValue(invite, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//
//            }
//        });
    }
    private void ValidateToLogin(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("userInfo");

        if (findViewById(R.id.username_textInput) != null && findViewById(R.id.password_textInput) != null) {

            TextInputEditText username_til = (TextInputEditText) findViewById(R.id.username_textInput);
            TextInputEditText password_til = (TextInputEditText) findViewById(R.id.password_textInput);

            if (username_til != null && password_til != null) {
                if(username_til.getText().toString().trim().isEmpty() || password_til.getText().toString().trim().isEmpty())
                {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Login failed!")
                            .setMessage("Vui lòng nhập đầy đủ thông tin")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    password_til.setText("");
                                    username_til.setText("");
                                }
                            })
                            .show();
                    return;
                }
            }

            username = username_til.getText().toString().trim();
            password = password_til.getText().toString().trim();

//            username = "username1";
//            password = "123456";

            try {
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        Toast.makeText(LoginActivity.this, Long.toString(snapshot.getChildrenCount()), Toast.LENGTH_SHORT).show();
                        if(snapshot.child(username).exists()){
                            userInfo_Database user = snapshot.child(username).getValue(userInfo_Database.class);
                            if(user.getUsername().equals(username) && user.getPass().equals(password)){
//                                MyDatabase.getCurrentUserId(username);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("username", ((TextInputEditText)findViewById(R.id.username_textInput)).getText().toString());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                            else{
                                new AlertDialog.Builder(LoginActivity.this)
                                        .setTitle("Login failed!")
                                        .setMessage("Password không đúng")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                password_til.setText("");
                                            }
                                        })
                                        .show();
                            }
                        }
                        else{
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("Login failed!")
                                    .setMessage("Username không tồn tại!")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            username_til.setText("");
                                            password_til.setText("");
                                        }
                                    })
                                    .show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
            catch (Exception e){

            }
        }
    }

    private void set_event_onclick(){
        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Kiểm tra account, hợp lệ thì vào màn hình chính
                ValidateToLogin();
            }
        });
        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        forget_password_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
            }
        });
    }

    //Cai ham nay nen dem qua ben ForgetPasswordActivity.java

}