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
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {return false;}
            @Override
            public boolean onQueryTextSubmit(String query) {
                String text = searchBar.getQuery().toString();
                MyDatabase.getOwnProject(myId, new MyDatabase.getAllProjectsCallback() {
                    @Override
                    public void onAllProjectsReceived(ArrayList<Project_Database> all_projects) {
                        for (Project_Database cur_Project : all_projects) {
                            if (cur_Project.getProjectName().equals(query) || cur_Project.getProjectID().equals(query)){
                                Application application = (Application) MainActivity.getAppContext().getApplicationContext();
                                Intent intent = new Intent(application, ProjectInforActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("project_id", cur_Project.getProjectID());
                                intent.putExtras(bundle);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                application.startActivity(intent);
                            }
                        }
                    }
                });
                Toast.makeText(MainActivity.getAppContext(), "Value is not exist!", Toast.LENGTH_SHORT).show();
                return false;
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
        final int[] total_task = {0};
        final int[] total_hour = {0};
        load_bar_out.post(new Runnable() {
            @Override
            public void run() {
                int width_loadbarout = load_bar_out.getMeasuredWidth();
                final int[] current_task = {0};
                final int[] finised_task = {0};

                MyDatabase.getAllProject(myId, new MyDatabase.getAllProjectsCallback() {
                    @Override
                    public void onAllProjectsReceived(ArrayList<Project_Database> all_projects) {
                        for (Project_Database cur_Project : all_projects) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("Project").child(cur_Project.getProjectID()).child("activityIds");
                            myRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot snap : snapshot.getChildren()){
                                        Activity_Database act = snap.getValue(Activity_Database.class);
                                        if (myId.equals(act.getActivityHost()) && act.getActivityStatus().equals("Not Finished"))
                                            current_task[0] += 1;
                                        if (myId.equals(act.getActivityHost()) && act.getActivityStatus().equals("Finished"))
                                            finised_task[0] += 1;
                                        if (myId.equals(act.getActivityHost()))
                                            total_task[0] += 1;
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {}
                            });

                        }
                    }
                });

                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int int_percent = getPercentOfValue(current_task[0], finised_task[0]);
                        RelativeLayout load_bar_in = new RelativeLayout(MainActivity.getAppContext());
                        int width_loadbarin = getValueOfPercent(width_loadbarout, int_percent);
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width_loadbarin, 40);
                        params.setMargins(5,0,0,0);
                        load_bar_in.setLayoutParams(new LinearLayout.LayoutParams(params));
                        load_bar_in.setBackgroundResource(R.drawable.load_bar_in);
                        load_bar_out.addView(load_bar_in);
                        String numb_of_task_completed = String.valueOf(finised_task[0]) + " of " + String.valueOf(current_task[0]) + " completed";
                        String percent_of_task_completed = String.valueOf(int_percent) + "%";
                        ((TextView)rootView.findViewById(R.id.numb_of_task_completed)).setText(numb_of_task_completed);
                        ((TextView)rootView.findViewById(R.id.percent_of_task_completed)).setText(percent_of_task_completed);
                    }
                }, 500);
            }});

        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ((TextView)rootView.findViewById(R.id.total_task_textview)).setText(String.valueOf(total_task[0]));
                ((TextView)rootView.findViewById(R.id.total_hour_textview)).setText(String.valueOf(total_task[0]*60));
            }
        }, 500);

        final String[] overview = {""};
        DatabaseReference userRef = (FirebaseDatabase.getInstance()).getReference("userInfo");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    userInfo_Database user = snap.getValue(userInfo_Database.class);
                    if (user.getUsername().equals(myId)){
                        overview[0] = user.getOverview();
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ((TextView)rootView.findViewById(R.id.overview_textview)).setText(overview[0]);
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
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {return false;}
            @Override
            public boolean onQueryTextSubmit(String query) {
                String text = searchBar.getQuery().toString();
                MyDatabase.getOwnProject(myId, new MyDatabase.getAllProjectsCallback() {
                    @Override
                    public void onAllProjectsReceived(ArrayList<Project_Database> all_projects) {
                        for (Project_Database cur_Project : all_projects) {
                            if (cur_Project.getProjectName().equals(query) || cur_Project.getProjectID().equals(query)){
                                Application application = (Application) MainActivity.getAppContext().getApplicationContext();
                                Intent intent = new Intent(application, ProjectInforActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("project_id", cur_Project.getProjectID());
                                intent.putExtras(bundle);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                application.startActivity(intent);
                            }
                        }
                    }
                });
                Toast.makeText(MainActivity.getAppContext(), "Value is not exist!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
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
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {return false;}
            @Override
            public boolean onQueryTextSubmit(String query) {
                String text = searchBar.getQuery().toString();
                MyDatabase.getOwnProject(myId, new MyDatabase.getAllProjectsCallback() {
                    @Override
                    public void onAllProjectsReceived(ArrayList<Project_Database> all_projects) {
                        for (Project_Database cur_Project : all_projects) {
                            if (cur_Project.getProjectName().equals(query) || cur_Project.getProjectID().equals(query)){
                                Application application = (Application) MainActivity.getAppContext().getApplicationContext();
                                Intent intent = new Intent(application, ProjectInforActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("project_id", cur_Project.getProjectID());
                                intent.putExtras(bundle);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                application.startActivity(intent);
                            }
                        }
                    }
                });
                Toast.makeText(MainActivity.getAppContext(), "Value is not exist!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

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

        MyDatabase.getProjectById(project.getProjectID(), new MyDatabase.getCurrentProjectCallback() {
            @Override
            public void onCurrentProjectReceived(Project_Database project) {
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

        ((TextView)activityView.findViewById(R.id.activity_header_textview)).setText(act.getActivityName());
        ((TextView)activityView.findViewById(R.id.hoster_textview)).setText(act.getActivityHost());
        ((TextView)activityView.findViewById(R.id.activity_deadline_textview)).setText(act.getActivityDeadline());

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

    public static View getRequestTaskSpan(String userId, String manager, String project_name, String desribe, Context context){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View request_view = layoutInflater.inflate(R.layout.task_request_span, null, false);
        ((TextView)request_view.findViewById(R.id.manager_name_textview)).setText("Manager Name: " + manager);
        ((TextView)request_view.findViewById(R.id.project_name_textview)).setText(project_name);
        ((TextView)request_view.findViewById(R.id.description_textview)).setText(desribe);
        ((TextView)request_view.findViewById(R.id.accept_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TaskRequestActivity.getAppContext(), "Accept this request", Toast.LENGTH_SHORT).show();
            }
        });

        ((TextView)request_view.findViewById(R.id.delete_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TaskRequestActivity.getAppContext(), "Remove this request", Toast.LENGTH_SHORT).show();
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
