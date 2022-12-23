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
    private TextInputLayout email_til; //firebase -> Chờ code layout để ánh xạ
    private TextInputLayout phonenumber_til; //firebase -> Chờ code layout để ánh xạ
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    public Query db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = ((GlobalVar)this.getApplication()).getLocalQuery();
        mAuth = FirebaseAuth.getInstance(); //Firebase
        progressDialog = new ProgressDialog(this);
        set_event_onclick();
    }

    private void set_event_onclick() {
        ((Button)findViewById(R.id.back_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        ((Button)findViewById(R.id.sign_up_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!check_infor_textinput()) {
                    Toast.makeText(RegisterActivity.this, "Sign Up Failed!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(RegisterActivity.this, "Sign Up Successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private boolean check_infor_textinput(){
        String email = ((TextView)findViewById(R.id.email_textinputlayout)).getText().toString();
        String username = ((TextView)findViewById(R.id.username_textinputlayout)).getText().toString();
        String password = ((TextView)findViewById(R.id.password_textinputlayout)).getText().toString();
        String confirm = ((TextView)findViewById(R.id.confirm_textinputlayout)).getText().toString();
        String phone = ((TextView)findViewById(R.id.phone_textinputlayout)).getText().toString();
        String about = ((TextView)findViewById(R.id.about_textinputlayout)).getText().toString();
        String image = ((TextView)findViewById(R.id.imageLink_textinputlayout)).getText().toString();

        return set_database(username, password, email, phone, confirm, about, image);
    }

    private boolean set_database(String username, String password, String email, String phone, String confirm, String about, String image){
        //set database here
        if (username.equals("") || password.equals("") || email.equals("") || phone.equals("") || confirm.equals("") || about.equals("")){
            Toast.makeText(RegisterActivity.this, "Please fill all information!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!MyDatabase.setDatabaseRegister(db, username, password, email, phone,confirm, about, image)){
            Toast.makeText(RegisterActivity.this, "Create data failed!", Toast.LENGTH_SHORT).show();
            return false;
        }
        onClickSignUp(email, password);
        return true;
    }

    //Hàm này để khi bấm đăng kí sẽ gửi thông tin lên firebase
    private void onClickSignUp(String strEmail, String strPassword) {
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