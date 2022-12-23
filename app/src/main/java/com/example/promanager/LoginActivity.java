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
    public Query db;

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
        ((GlobalVar)this.getApplication()).setLocalQuery(new Query(this, "ProManager1.sqlite", null, 1));
        db = ((GlobalVar)this.getApplication()).getLocalQuery();
        try {MyDatabase.Creation(db);}
        catch (Exception ex) {Log.e("SQLException", ex.toString());}
    }

    private void set_event_onclick(){
        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //after checking, if not vaild -> do nothing, else -> move to next activity
//                if (!check_infor_textfield()) {
//                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
//                    return;
//                }
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
        if (username.equals("") || password.equals("") || !MyDatabase.checkLogin(db, username, password))
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