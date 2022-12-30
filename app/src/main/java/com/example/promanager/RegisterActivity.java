package com.example.promanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword_Firebase";
    private TextInputLayout email_til; //firebase -> Chờ code layout để ánh xạ
    private TextInputLayout phonenumber_til; //firebase -> Chờ code layout để ánh xạ
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    public Query db;
    String email;
    String username;
    String password;
    String confirm;
    String phone;
    String about;
    String image;

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
        ((TextView)findViewById(R.id.back_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        ((TextView)findViewById(R.id.sign_up_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!check_infor_textinput()) {
                    Toast.makeText(RegisterActivity.this, "Sign Up Failed!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Sign Up Successfully!", Toast.LENGTH_SHORT).show();
                    //onClickSignUp();
                }
            }
        });
    }

    private boolean check_infor_textinput(){
        email = ((TextInputLayout)findViewById(R.id.email_textinputlayout)).getEditText().getText().toString().trim();
        username = ((TextInputLayout)findViewById(R.id.username_textinputlayout)).getEditText().getText().toString().trim();
        password = ((TextInputLayout)findViewById(R.id.password_textinputlayout)).getEditText().getText().toString().trim();
        confirm = ((TextInputLayout)findViewById(R.id.confirm_textinputlayout)).getEditText().getText().toString().trim();
        phone = ((TextInputLayout)findViewById(R.id.phone_textinputlayout)).getEditText().getText().toString().trim();
        about = ((TextInputLayout)findViewById(R.id.about_textinputlayout)).getEditText().getText().toString().trim();
        image = ((TextInputLayout)findViewById(R.id.imageLink_textinputlayout)).getEditText().getText().toString().trim();

        return set_database(username, password, email, phone, confirm, about, image);
    }

    private boolean set_database(String username, String password, String email, String phone, String confirm, String about, String image){
//        check database here
        if (username.equals("") || password.equals("") || email.equals("") || phone.equals("") || confirm.equals("") || about.equals("")){
            Toast.makeText(RegisterActivity.this, "Please fill all information!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!password.equals(confirm)){
            Toast.makeText(RegisterActivity.this, "Confirm password is different from password!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

//    private void onClickSignUp(){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("userInfo");
//
//        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.child(username).exists()){
//                    new AlertDialog.Builder(RegisterActivity.this)
//                            .setTitle("Register failed!")
//                            .setMessage("Username đã tồn tại")
//                            .setPositiveButton("OK", null)
//                            .show();
//                }
//                else{
//                    userInfo_Database user = new userInfo_Database(username, password, phone, email, about, image);
//
//                    String pathObject = String.valueOf(user.getUsername());
//                    FirebaseDatabase database1 = FirebaseDatabase.getInstance();
//                    DatabaseReference myRef1 = database1.getReference("userInfo");
//                    myRef1.child(pathObject).setValue(user, new DatabaseReference.CompletionListener() {
//                        @Override
//                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                            Toast.makeText(RegisterActivity.this, "Add complete!", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(RegisterActivity.this, Verify_Phone_Number_Firebase.class));
//                        }
//                    });
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    //Hàm này để khi bấm đăng kí sẽ gửi thông tin lên firebase
    private void onClickSignUp1(String strEmail, String strPassword) {
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