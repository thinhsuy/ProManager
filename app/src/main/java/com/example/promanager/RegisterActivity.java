package com.example.promanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword_Firebase";
    private TextInputLayout fullname_til;
    private TextInputLayout username_til;
    private TextInputLayout password_til;
    private TextInputLayout email_til; //firebase -> Chờ code layout để ánh xạ
    private TextInputLayout phonenumber_til; //firebase -> Chờ code layout để ánh xạ
    private TextInputLayout confirm_til;
    private TextInputLayout about_til;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    Button sign_up_btn;
    TextView login_tv;
    public Query db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = ((GlobalVar)this.getApplication()).getLocalQuery();
        mAuth = FirebaseAuth.getInstance(); //Firebase
        progressDialog = new ProgressDialog(this);
        sign_up_btn = (Button) findViewById(R.id.sign_up_btn);
        login_tv = (TextView) findViewById(R.id.login_textview);
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
        String fullname = fullname_til.getEditText().getText().toString().trim();
        String username = username_til.getEditText().getText().toString().trim();
        String password= password_til.getEditText().getText().toString().trim();
        String email = email_til.getEditText().getText().toString().trim().trim();
        String phonenumber = phonenumber_til.getEditText().getText().toString().trim();
        String confirm = confirm_til.getEditText().getText().toString().trim();
        String about = about_til.getEditText().getText().toString().trim();
        return set_database(fullname, username, password, email, phonenumber, confirm, about);
    }

    private boolean set_database(String fullname, String username, String password, String email, String phonenumber, String confirm, String about){
        //set database here
        if (fullname.equals("") || username.equals("") || password.equals("") || email.equals("") || phonenumber.equals("") || confirm.equals("") || about.equals(""))
            return false;
        else if (!MyDatabase.setDatabaseRegister(db, fullname, username, password, email, phonenumber,confirm, about))
            return false;
        else return true;
    }

    //Hàm này để khi bấm đăng kí sẽ gửi thông tin lên firebase
    private void onClickSignUp() {
        String strEmail = email_til.getEditText().toString().trim();
        String strPassword = password_til.getEditText().toString().trim();
        progressDialog.setTitle("Sign up. Please wait...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(strEmail, strPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.cancel();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity(); //Dong tat ca activity truoc do
                        } else {
                            try {
                                throw task.getException();
                            } catch(Exception e) {
                                Log.e(TAG, e.getMessage());
                            }
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Authentication failed, try again.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}