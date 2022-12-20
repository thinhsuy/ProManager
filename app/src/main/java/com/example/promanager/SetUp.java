package com.example.promanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Set;

public class SetUp {
    public static View getActivityFragment(View rootView, String myId){
        SearchView searchBar = (SearchView) rootView.findViewById(R.id.search_bar);
        searchBar.setActivated(true);
        searchBar.setQueryHint("Search");
        searchBar.setIconified(false);
        searchBar.clearFocus();

        LinearLayout content_container = (LinearLayout) rootView.findViewById(R.id.content_container_linearlayout);

        ArrayList<String> all_current_project_id = MyDatabase.getCurrentResponProject(myId);
        for (String proId: all_current_project_id)
            content_container.addView(getProjectSpan(proId));
        
        ArrayList<String> connected_user_id = MyDatabase.getConnectedUserId(myId);
        for (String userId:connected_user_id) {
            ImageView avatar = MyDatabase.getAvatarById(MainActivity.getAppContext(), userId, "small");
            ((LinearLayout)rootView.findViewById(R.id.last_connection_container)).addView(avatar);
        }
        return rootView;
    }

    private static int getValueOfPercent(int value, int percent){
        return percent*value/100;
    }
    private static int getPercentOfValue(int total, int value){
        return value*100/total;
    }

    public static View getInformationFragment(View rootView, String myId){
        RelativeLayout load_bar_out = (RelativeLayout) rootView.findViewById(R.id.load_bar_out);
        load_bar_out.post(new Runnable() {
            @Override
            public void run() {
                int width_loadbarout = load_bar_out.getMeasuredWidth();
                int current_task = MyDatabase.getCurrentTasks(myId);
                int finised_task = MyDatabase.getCurrentFinishedTasks(myId);
                int int_percent = getPercentOfValue(current_task, finised_task);

                RelativeLayout load_bar_in = new RelativeLayout(MainActivity.getAppContext());
                int width_loadbarin = getValueOfPercent(width_loadbarout, int_percent);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width_loadbarin, 40);
                params.setMargins(5,0,0,0);
                load_bar_in.setLayoutParams(new LinearLayout.LayoutParams(params));
                load_bar_in.setBackgroundResource(R.drawable.load_bar_in);
                load_bar_out.addView(load_bar_in);

                String numb_of_task_completed = String.valueOf(finised_task) + " of " + String.valueOf(current_task) + " completed";
                String percent_of_task_completed = String.valueOf(int_percent) + "%";
                ((TextView)rootView.findViewById(R.id.numb_of_task_completed)).setText(numb_of_task_completed);
                ((TextView)rootView.findViewById(R.id.percent_of_task_completed)).setText(percent_of_task_completed);
            }});

        int total_task = MyDatabase.getTotalTasks(myId);
        int total_hour = MyDatabase.getTotalHour(myId);
        ((TextView)rootView.findViewById(R.id.total_task_textview)).setText(String.valueOf(total_task));
        ((TextView)rootView.findViewById(R.id.total_hour_textview)).setText(String.valueOf(total_hour));
        ((TextView)rootView.findViewById(R.id.overview_textview)).setText(MyDatabase.getUserOverview(myId));
        return rootView;
    }

    public static View getOwnFragment(View rootView, String myId){
        SearchView searchBar = (SearchView) rootView.findViewById(R.id.search_bar);
        searchBar.setActivated(true);
        searchBar.setQueryHint("Search");
        searchBar.setIconified(false);
        searchBar.clearFocus();

        LinearLayout content_container = (LinearLayout) rootView.findViewById(R.id.content_container_linearlayout);

        ArrayList<String> all_current_project_id = MyDatabase.getOwnProject(myId);
        for (String proId: all_current_project_id) 
            content_container.addView(getProjectSpan(proId));

        return rootView;
    }

    public static View getSeekFragment(View rootView, String myId){
        SearchView searchBar = (SearchView) rootView.findViewById(R.id.search_bar);
        searchBar.setActivated(true);
        searchBar.setQueryHint("Search");
        searchBar.setIconified(false);
        searchBar.clearFocus();

        LinearLayout content_container = (LinearLayout) rootView.findViewById(R.id.content_container_linearlayout);

        ArrayList<String> all_current_project_id = MyDatabase.getAllProject(myId);
        for (String proId: all_current_project_id)
            content_container.addView(getProjectSpan(proId));

        return rootView;
    }

    public static View getProjectSpan(String proId){
        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.getAppContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View projectView = layoutInflater.inflate(R.layout.project_span, null, false);

        ProjectClass project = MyDatabase.getProjectById(proId);
        ((TextView)projectView.findViewById(R.id.project_header_textview)).setText(project.project_header);
        for (int i=0; i<project.activityIdList.size(); i++){
            ((LinearLayout)projectView.findViewById(R.id.activity_container)).addView(getActivitySpan(project.activityIdList.get(i)));
        }
        return projectView;
    }

    private static View getActivitySpan(String actId){
        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.getAppContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_span, null, false);
        ActivityClass activity = MyDatabase.getActivityById(actId);
        ((TextView)activityView.findViewById(R.id.activity_header_textview)).setText(activity.activity_header);
        ((TextView)activityView.findViewById(R.id.hoster_textview)).setText(activity.hoster);
        ((TextView)activityView.findViewById(R.id.activity_deadline_textview)).setText(activity.deadline);
        ArrayList<String> user_respon_id = MyDatabase.getResponsibilityUserId(actId);
        for (String userId:user_respon_id) {
            ImageView avatar = MyDatabase.getAvatarById(MainActivity.getAppContext(), userId, "tiny");
            ((LinearLayout)activityView.findViewById(R.id.respon_container_layout)).addView(avatar);
        }
        return activityView;
    }
}
