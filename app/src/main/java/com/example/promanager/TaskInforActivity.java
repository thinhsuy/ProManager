package com.example.promanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;


public class TaskInforActivity extends AppCompatActivity {
    public Query db;
    public Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_infor);
        db = ((GlobalVar)this.getApplication()).getLocalQuery();

        bundle = getIntent().getExtras();

        ((TextView)findViewById(R.id.back_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToPreviousPage();
            }
        });

        String actId = bundle.getString("activity_id");
        loadInformation(actId);
        setUpOnClickListener(actId);
    }

    private void backToPreviousPage(){
        if (bundle.getString("source").toString().equals("main"))
            startActivity(new Intent(TaskInforActivity.this, MainActivity.class));
        else {
            Intent intent = new Intent(TaskInforActivity.this, ProjectInforActivity.class);
            Bundle bundleBack = new Bundle();
            bundleBack.putString("projectId", bundle.getString("project_id"));
            intent.putExtras(bundleBack);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void loadInformation(String actId){
        if (actId==null) return;
        Activity_Database act = MyDatabase.getActivityById(db, actId);
        ((TextView)findViewById(R.id.header_textview)).setText(act.getActivityName());
        ((TextView)findViewById(R.id.hoster_textview)).setText(act.getActivityHost());
        ((TextView)findViewById(R.id.deadline_textview)).setText(act.getActivityDeadline());
        ((TextView)findViewById(R.id.description_textview)).setText(act.getActivityDescribe());
        ((TextView)findViewById(R.id.status_textview)).setText(act.getActivityStatus());
        ((TextView)findViewById(R.id.rate_textview)).setText(act.getActivityAgreement());
    }

    public void setUpOnClickListener(String actId){
        ((TextView)findViewById(R.id.status_textview)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet_status();
            }
        });

        ((TextView)findViewById(R.id.rate_textview)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet_agreement();
            }
        });

        ((TextView)findViewById(R.id.submit_btn_textview)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabase.setStatusActivity(actId, ((TextView)findViewById(R.id.status_textview)).getText().toString());
                MyDatabase.setAgreementActivity(actId, ((TextView)findViewById(R.id.rate_textview)).getText().toString());
                MyDatabase.setFileFolderActivity(actId, ((TextInputEditText)findViewById(R.id.folder_text)).getText().toString());
                Toast.makeText(TaskInforActivity.this, "Updating data ...", Toast.LENGTH_SHORT).show();
                backToPreviousPage();
            }
        });
    }

    private void showBottomSheet_status(){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.status_bottom_sheet);
        ((LinearLayout) bottomSheetDialog.findViewById(R.id.finished_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView)findViewById(R.id.status_textview)).setText("Finished");
                bottomSheetDialog.cancel();
            }
        });
        ((LinearLayout) bottomSheetDialog.findViewById(R.id.not_finished_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView)findViewById(R.id.status_textview)).setText("Not Finished");
                bottomSheetDialog.cancel();
            }
        });
        ((LinearLayout) bottomSheetDialog.findViewById(R.id.on_process_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView)findViewById(R.id.status_textview)).setText("On Process");
                bottomSheetDialog.cancel();
            }
        });
        bottomSheetDialog.show();
    }


    private void showBottomSheet_agreement(){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.agreement_bottom_sheet);
        NumberPicker picker = (NumberPicker)bottomSheetDialog.findViewById(R.id.number_picker);
        picker.setMaxValue(100);
        picker.setMinValue(0);
        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                String str = "Rate: " + String.valueOf(picker.getValue()) + "%";
                ((TextView)findViewById(R.id.rate_textview)).setText(str);
            }
        });
        bottomSheetDialog.show();
    }
}