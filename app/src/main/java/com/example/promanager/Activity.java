package com.example.promanager;

public class Activity {
    private String activityID;
    private String activityName;
    private String activityDescribe;
    private String activityDeadline;
    private String activityHost;
    private String activityFile;
    private String activityStatus;
    private String activityAgreement;

    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityDescribe() {
        return activityDescribe;
    }

    public void setActivityDescribe(String activityDescribe) {
        this.activityDescribe = activityDescribe;
    }

    public String getActivityDeadline() {
        return activityDeadline;
    }

    public void setActivityDeadline(String activityDeadline) {
        this.activityDeadline = activityDeadline;
    }

    public String getActivityHost() {
        return activityHost;
    }

    public void setActivityHost(String activityHost) {
        this.activityHost = activityHost;
    }

    public String getActivityFile() {
        return activityFile;
    }

    public void setActivityFile(String activityFile) {
        this.activityFile = activityFile;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getActivityAgreement() {
        return activityAgreement;
    }

    public void setActivityAgreement(String activityAgreement) {
        this.activityAgreement = activityAgreement;
    }

    public Activity(){
    }

    public Activity(String activityID, String activityName, String activityDescribe, String activityDeadline, String activityHost, String activityFile, String activityStatus, String activityAgreement) {
        this.activityID = activityID;
        this.activityName = activityName;
        this.activityDescribe = activityDescribe;
        this.activityDeadline = activityDeadline;
        this.activityHost = activityHost;
        this.activityFile = activityFile;
        this.activityStatus = activityStatus;
        this.activityAgreement = activityAgreement;
    }
}
