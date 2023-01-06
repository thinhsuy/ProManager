package com.example.promanager;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.flexbox.FlexboxLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class SetUp {
    public final Handler handler = new Handler();
    public static View getActivityFragment(Query db, View rootView, String myId){
        SearchView searchBar = (SearchView) rootView.findViewById(R.id.search_bar);
        searchBar.setActivated(true);
        searchBar.setQueryHint("Search");
        searchBar.setIconified(false);
        searchBar.clearFocus();

        LinearLayout content_container = (LinearLayout) rootView.findViewById(R.id.content_container_linearlayout);

        final ArrayList<Project_Database>[] all_my_project = new ArrayList[]{new ArrayList<Project_Database>()};
        MyDatabase.getOwnProject(myId, new MyDatabase.getAllProjectsCallback() {
            @Override
            public void onAllProjectsReceived(ArrayList<Project_Database> all_projects) {
                all_my_project[0] = all_projects;
                boolean isEmpty = true;
                for (Project_Database cur_Project : all_projects) {
                    if (!myId.equals(cur_Project.getProjectOwner())) continue;
                    content_container.addView(getProjectSpan(db, cur_Project, "Manage"));
                    isEmpty=!isEmpty;
                } if (isEmpty)
                    content_container.addView(getEmptyProjectSpan());
            }
        });

        ArrayList<String> connected_user_id = MyDatabase.getConnectedUserId(db, myId);
        for (String userId:connected_user_id) {
            ImageView avatar = MyDatabase.getAvatarById(db, MainActivity.getAppContext(), userId, "small");
            ((LinearLayout)rootView.findViewById(R.id.last_connection_container)).addView(avatar);
        }
        return rootView;
    }

    private static int getValueOfPercent(int value, int percent){
        return percent*value/100;
    }
    private static int getPercentOfValue(int total, int value){
        if (total==0) return total;
        return value*100/total;
    }

    public static View getInformationFragment(Query db, View rootView, String myId){
        RelativeLayout load_bar_out = (RelativeLayout) rootView.findViewById(R.id.load_bar_out);
        load_bar_out.post(new Runnable() {
            @Override
            public void run() {
                int width_loadbarout = load_bar_out.getMeasuredWidth();
                final int[] current_task = {0};
                MyDatabase.getAllProject(myId, new MyDatabase.getAllProjectsCallback() {
                    @Override
                    public void onAllProjectsReceived(ArrayList<Project_Database> all_projects) {
                        for (Project_Database cur_Project : all_projects) {
                            if (myId.equals(cur_Project.getProjectOwner()))
                                current_task[0] += 1;
                        }
                    }
                });

                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int finised_task = MyDatabase.getCurrentFinishedTasks(db, myId);
                        int int_percent = getPercentOfValue(current_task[0], finised_task);
                        RelativeLayout load_bar_in = new RelativeLayout(MainActivity.getAppContext());
                        int width_loadbarin = getValueOfPercent(width_loadbarout, int_percent);
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width_loadbarin, 40);
                        params.setMargins(5,0,0,0);
                        load_bar_in.setLayoutParams(new LinearLayout.LayoutParams(params));
                        load_bar_in.setBackgroundResource(R.drawable.load_bar_in);
                        load_bar_out.addView(load_bar_in);
                        String numb_of_task_completed = String.valueOf(finised_task) + " of " + String.valueOf(current_task[0]) + " completed";
                        String percent_of_task_completed = String.valueOf(int_percent) + "%";
                        ((TextView)rootView.findViewById(R.id.numb_of_task_completed)).setText(numb_of_task_completed);
                        ((TextView)rootView.findViewById(R.id.percent_of_task_completed)).setText(percent_of_task_completed);
                    }
                }, 500);
            }});

        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                int total_task = MyDatabase.getTotalTasks(db, myId);
                int total_hour = MyDatabase.getTotalHour(db, myId);
                ((TextView)rootView.findViewById(R.id.total_task_textview)).setText(String.valueOf(total_task));
                ((TextView)rootView.findViewById(R.id.total_hour_textview)).setText(String.valueOf(total_hour));
                ((TextView)rootView.findViewById(R.id.overview_textview)).setText(MyDatabase.getUserOverview(db, myId));
            }
        }, 500);
        return rootView;
    }

    public static View getOwnFragment(Query db, View rootView, String myId) throws InterruptedException {
        SearchView searchBar = (SearchView) rootView.findViewById(R.id.search_bar);
        searchBar.setActivated(true);
        searchBar.setQueryHint("Search");
        searchBar.setIconified(false);
        searchBar.clearFocus();
        ((TextView) rootView.findViewById(R.id.generate_new_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Application application = (Application) MainActivity.getAppContext().getApplicationContext();
                Intent intent = new Intent(application, CreateActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                application.startActivity(intent);
            }
        });
        LinearLayout content_container = (LinearLayout) rootView.findViewById(R.id.content_container_linearlayout);

        final ArrayList<Project_Database>[] all_my_project = new ArrayList[]{new ArrayList<Project_Database>()};
        MyDatabase.getOwnProject(myId, new MyDatabase.getAllProjectsCallback() {
            @Override
            public void onAllProjectsReceived(ArrayList<Project_Database> all_projects) {
                all_my_project[0] = all_projects;
                boolean isEmpty = true;
                for (Project_Database cur_Project : all_projects) {
                    if (!myId.equals(cur_Project.getProjectOwner())) continue;
                    content_container.addView(getProjectSpan(db, cur_Project, "Own"));
                    isEmpty=!isEmpty;
                } if (isEmpty)
                    content_container.addView(getEmptyProjectSpan());
            }
        });
        return rootView;
    }

    public static View getSeekFragment(Query db, View rootView, String myId){
        SearchView searchBar = (SearchView) rootView.findViewById(R.id.search_bar);
        searchBar.setActivated(true);
        searchBar.setQueryHint("Search");
        searchBar.setIconified(false);
        searchBar.clearFocus();

        LinearLayout content_container = (LinearLayout) rootView.findViewById(R.id.content_container_linearlayout);

        final ArrayList<Project_Database>[] all_own_project = new ArrayList[1];
        all_own_project[0] = new ArrayList<Project_Database>();

        MyDatabase.getAllProject(myId, new MyDatabase.getAllProjectsCallback() {
            @Override
            public void onAllProjectsReceived(ArrayList<Project_Database> all_projects) {
                all_own_project[0] = all_projects;
                for (Project_Database cur_Project : all_projects)
                    content_container.addView(getProjectSpan(db, cur_Project, "Seek"));
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (all_own_project[0].size()==0) {
                    content_container.addView(getEmptyProjectSpan());
                }
            }
        }, 500);

        return rootView;
    }

    public static View getEmptyProjectSpan(){
        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.getAppContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View projectView = layoutInflater.inflate(R.layout.project_span, null, false);
        ((TextView)projectView.findViewById(R.id.project_header_textview)).setText("This is sample project");
        for (int i=0; i<2; i++){
            ((LinearLayout)projectView.findViewById(R.id.activity_container)).addView(getEmptyActivitySpan());
        }
        return projectView;
    }

    public static View getEmptyActivitySpan(){
        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.getAppContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_span, null, false);
        ((TextView)activityView.findViewById(R.id.activity_header_textview)).setText("You have no activity yet");
        ((TextView)activityView.findViewById(R.id.hoster_textview)).setText("Let's find your manager");
        ((TextView)activityView.findViewById(R.id.activity_deadline_textview)).setText("Your deadline would be here");
        return activityView;
    }

    public static View getProjectSpan(Query db, Project_Database project, String page){
        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.getAppContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View projectView = layoutInflater.inflate(R.layout.project_span, null, false);

//        Project_Database project;
        MyDatabase.getProjectById(project.getProjectID(), new MyDatabase.getCurrentProjectCallback() {
            @Override
            public void onCurrentProjectReceived(Project_Database project) {
                //chưa xử lí xong
                TextView header = (TextView)projectView.findViewById(R.id.project_header_textview);
                header.setText(project.getProjectName());

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Project").child(project.getProjectID()).child("activityIds");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Activity_Database act = dataSnapshot.getValue(Activity_Database.class);
                            ((LinearLayout)projectView.findViewById(R.id.activity_container)).addView(getActivitySpan(project.getProjectID(), act));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//                for (int i=0; i<project.getActivityIdList().size(); i++){
//                    ((LinearLayout)projectView.findViewById(R.id.activity_container)).addView(getActivitySpan(db, project.getActivityIdList().get(i)));
//                }
                header.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Application application = (Application) MainActivity.getAppContext().getApplicationContext();
                        Intent intent = new Intent(application, ProjectInforActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("project_id", project.getProjectID());
                        intent.putExtras(bundle);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        application.startActivity(intent);
                    }
                });
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
            }
        }, 500);
        return projectView;
    }

    private static View getActivitySpan(String proId, Activity_Database act){
        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.getAppContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_span, null, false);
//        Activity_Database activity = MyDatabase.getActivityById(db, actId);
        ((TextView)activityView.findViewById(R.id.activity_header_textview)).setText(act.getActivityName());
        ((TextView)activityView.findViewById(R.id.hoster_textview)).setText(act.getActivityHost());
        ((TextView)activityView.findViewById(R.id.activity_deadline_textview)).setText(act.getActivityDeadline());
//        ArrayList<String> user_respon_id = MyDatabase.getResponsibilityUserId(db, actId);
//        for (String userId:user_respon_id) {
//            ImageView avatar = MyDatabase.getAvatarById(db, MainActivity.getAppContext(), userId, "tiny");
//            ((LinearLayout)activityView.findViewById(R.id.respon_container_layout)).addView(avatar);
//        }
        ((FlexboxLayout)activityView.findViewById(R.id.activity_container)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Application application = (Application) MainActivity.getAppContext().getApplicationContext();
                Intent intent = new Intent(application, TaskInforActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("activity_id", act.getActivityID());
                bundle.putString("project_id", proId);
                bundle.putString("source", "main");
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                application.startActivity(intent);
            }
        });
        return activityView;
    }

    public static View getNotificaitonSpan(String content, Context context){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View notification_view = layoutInflater.inflate(R.layout.notification_span, null, false);
        ((TextView) notification_view.findViewById(R.id.notification_content)).setText(content);
        return notification_view;
    }

    public static View getRequestTaskSpan(Query db, Activity_Database act, String userId, Context context){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View request_view = layoutInflater.inflate(R.layout.task_request_span, null, false);
        ((TextView)request_view.findViewById(R.id.name_textview)).setText(act.getActivityName());
        ((TextView)request_view.findViewById(R.id.accept_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TaskRequestActivity.getAppContext(), "Accept this request", Toast.LENGTH_SHORT).show();
                MyDatabase.acceptRequest(db, userId, act.getActivityID());
            }
        });

        ((TextView)request_view.findViewById(R.id.delete_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TaskRequestActivity.getAppContext(), "Remove this request", Toast.LENGTH_SHORT).show();
                MyDatabase.removeRequest(db, userId, act.getActivityID());
            }
        });

        return request_view;
    }

    public static View getActivityTag(Activity_Database act, Context context){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View tag_view = layoutInflater.inflate(R.layout.activity_tag, null, false);
        ((TextView)tag_view.findViewById(R.id.name_textview)).setText(act.getActivityName());
        return tag_view;
    }
}
