package com.example.promanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class TaskInforActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_infor);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            Log.e("Bundle", bundle.getString("activity_id"));
        }
    }
}