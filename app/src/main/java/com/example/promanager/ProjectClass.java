package com.example.promanager;

import java.util.ArrayList;

public class ProjectClass {
    public String project_header;
    public String manager;
    public ArrayList<String> activityIdList;
    public ArrayList<ActivityClass> activityList = new ArrayList<ActivityClass>();
    public void ProjectClass(){}
    public void addActivityList(ActivityClass act){this.activityList.add(act);}
    public void removeActivityList(ActivityClass act) {this.activityList.remove(act);}
}

