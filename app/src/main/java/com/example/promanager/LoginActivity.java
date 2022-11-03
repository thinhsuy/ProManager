package com.example.promanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private Button sign_in_btn;
    private Button sign_up_btn;
    private TextView forget_password_tv;
    private TextInputLayout username_til;
    private TextInputLayout password_til;

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
    }

    private void set_event_onclick(){
        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //after checking, if not vaild -> do nothing, else -> move to next activity
                if (!check_infor_textfield()) return;
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
        if (username.equals("") || password.equals(""))
            return false;
        else return true;
    }
}