package com.example.promanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

public class CreateActivity extends AppCompatActivity {
    TextInputEditText activity_name, activity_manager, activity_deadline, activity_describe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        activity_name = (TextInputEditText) findViewById(R.id.activity_name_tiet);
        activity_manager = (TextInputEditText) findViewById(R.id.activity_manager_tiet);
        activity_deadline = (TextInputEditText) findViewById(R.id.activity_deadline_tiet);
        activity_describe = (TextInputEditText) findViewById(R.id.activity_describe_tiet);

        TextView confirm_btn = (TextView) findViewById(R.id.confirm_button);
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addInformation();
            }
        });

        ((TextView)findViewById(R.id.cancel_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateActivity.this, MainActivity.class));
            }
        });
    }

    private void addInformation(){
        activity_name.getText();
        activity_manager.getText();
        activity_deadline.getText();
        activity_describe.getText();
    }
}