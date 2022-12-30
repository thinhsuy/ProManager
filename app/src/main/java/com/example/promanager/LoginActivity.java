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
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

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
        username_til = (TextInputLayout) findViewById(R.id.username_textinputlayout);
        password_til = (TextInputLayout) findViewById(R.id.password_textinputlayout);
        set_event_onclick();
        ((GlobalVar)this.getApplication()).setLocalQuery(new Query(this, "ProManager1.sqlite", null, 1));
        db = ((GlobalVar)this.getApplication()).getLocalQuery();
        try {MyDatabase.Creation(db);}
        catch (Exception ex) {Log.e("SQLException", ex.toString());}

//        Project_Database project = new Project_Database(username, password, phone, email, about, image);
//
//        String pathObject = String.valueOf(user.getUsername());
//        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
//        DatabaseReference myRef1 = database1.getReference("userInfo");
//        myRef1.child(pathObject).setValue(user, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                Toast.makeText(LoginActivity.this, "Add Project complete!", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(LoginActivity.this, Verify_Phone_Number_Firebase.class));
//            }
//        });

    }

//    private void testFirebase() {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("userInfo");
//
//        String username = "username1";
//        String password = "123456";
//
//        final Boolean[] isCheck = {false};
//
////        Toast.makeText(LoginActivity.this, i, Toast.LENGTH_SHORT).show();
//
//        myRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                userInfo_Database user = snapshot.getValue(userInfo_Database.class);
//                if(user.getUsername().equals(username) && user.getPass().equals(password)){
//                    isCheck[0] = true;
//                    Toast.makeText(LoginActivity.this, isCheck[0].toString(), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                else{
//                    Toast.makeText(LoginActivity.this, user.getUsername(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(LoginActivity.this, isCheck[0].toString(), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//
//        });
//
//        Toast.makeText(LoginActivity.this, isCheck[0].toString() + "end", Toast.LENGTH_SHORT).show();
//
////        myRef.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                userInfo user = snapshot.getValue(userInfo.class);
////                if(user.getUsername().equals("username1")) {
////                    Toast.makeText(LoginActivity.this, user.toString(), Toast.LENGTH_SHORT).show();
////                }
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////
////            }
////        });
//
////        Toast.makeText(LoginActivity.this, myRef.child("username1").getKey(), Toast.LENGTH_SHORT).show();
//    }

//    private void ValidateToLogin(){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("userInfo");
//        if(username_til.getEditText().getText().toString().trim() == null || password_til.getEditText().getText().toString().trim() == null)
//        {
//            return;
//        }
//
//        username = username_til.getEditText().getText().toString().trim();
//        password = password_til.getEditText().getText().toString().trim();
//
//        try {
//            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    Toast.makeText(LoginActivity.this, Long.toString(snapshot.getChildrenCount()), Toast.LENGTH_SHORT).show();
//                    if(snapshot.child(username).exists()){
//                        userInfo_Database user = snapshot.child(username).getValue(userInfo_Database.class);
//                        if(user.getUsername().equals(username) && user.getPass().equals(password)){
//                            Toast.makeText(LoginActivity.this, user.toString(), Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                        }
//                        else{
//                            new AlertDialog.Builder(LoginActivity.this)
//                                    .setTitle("Login failed!")
//                                    .setMessage("Password không đúng")
//                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            password_til.getEditText().setText("");
//                                        }
//                                    })
//                                    .show();
//                        }
//                    }
//                    else{
//                        new AlertDialog.Builder(LoginActivity.this)
//                                .setTitle("Login failed!")
//                                .setMessage("Username không tồn tại!")
//                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        username_til.getEditText().setText("");
//                                        password_til.getEditText().setText("");
//                                    }
//                                })
//                                .show();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                }
//            });
//        }
//        catch (Exception e){
//
//        }
//    }

    private void set_event_onclick(){
        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //after checking, if not vaild -> do nothing, else -> move to next activity
//                if (!check_infor_textfield()) {
//                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                //ValidateToLogin();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", ((TextInputEditText)findViewById(R.id.username_textInput)).getText().toString());
                bundle.putString("username", ((TextInputEditText)findViewById(R.id.password_textInput)).getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
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

    private boolean check_infor_textfield(){
        username = username_til.getEditText().getText().toString().trim();
        password = password_til.getEditText().getText().toString().trim();
        return check_database(username, password);
    }

    private boolean check_database(String username, String password){
        Log.e("Username", username);
        Log.e("Password", password);

        //check database here
        if (username.equals("") || password.equals("")) {
            Toast.makeText(LoginActivity.this, "Please fill all field", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
//            testFirebaseP2();
            return isCheck;
        }
    }

    //Cai ham nay nen dem qua ben ForgetPasswordActivity.java
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