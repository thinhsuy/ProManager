package com.example.promanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout fullname_til;
    private TextInputLayout username_til;
    private TextInputLayout password_til;
    private TextInputLayout confirm_til;
    private TextInputLayout about_til;
    Button sign_up_btn;
    TextView login_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sign_up_btn = (Button) findViewById(R.id.sign_up_btn);
        login_tv = (TextView) findViewById(R.id.login_textview);
        fullname_til = (TextInputLayout)findViewById(R.id.fullname_textinputlayout);
        username_til = (TextInputLayout)findViewById(R.id.username_textinputlayout);
        password_til = (TextInputLayout)findViewById(R.id.password_textinputlayout);
        confirm_til = (TextInputLayout)findViewById(R.id.confirm_textinputlayout);
        about_til = (TextInputLayout)findViewById(R.id.about_textinputlayout);
        set_event_onclick();
    }

    private void set_event_onclick() {
        login_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!check_infor_textinput()) return;
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private boolean check_infor_textinput(){
        String fullname = fullname_til.getEditText().getText().toString();
        String username = username_til.getEditText().getText().toString();
        String password= password_til.getEditText().getText().toString();
        String confirm = confirm_til.getEditText().getText().toString();
        String about = about_til.getEditText().getText().toString();
        return set_database(fullname, username, password, confirm, about);
    }

    private boolean set_database(String fullname, String username, String password, String confirm, String about){
        //set database here
        if (fullname.equals("") || username.equals("") || password.equals("") || confirm.equals("") || about.equals(""))
            return false;
        else if (!MyDatabase.setDatabaseRegister(fullname, username, password, confirm, about))
            return false;
        else return true;
    }
}