package com.example.promanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private static final String TAG = "ForgetPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        ((TextView)findViewById(R.id.back_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
            }
        });

        ((TextView)findViewById(R.id.send_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send email for resetting password?
                Toast.makeText(ForgetPasswordActivity.this, "New password just sent to your mail!", Toast.LENGTH_SHORT).show();
                MyDatabase.sendEmailResetPassword(((TextInputEditText)findViewById(R.id.email_textInput)).getText().toString());
                startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
            }
        });
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
                            Toast.makeText(ForgetPasswordActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }
}