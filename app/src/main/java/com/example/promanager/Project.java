package com.example.promanager;

public class Project {
    private String projectID;
    private String projectName;
    private String projectOwner;
    private String projectDeadline;
    private String projectDescribe;
    private String projectPrivacy;

    public Project(String projectID, String projectName, String projectOwner, String projectDeadline, String projectDescribe, String projectPrivacy) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.projectOwner = projectOwner;
        this.projectDeadline = projectDeadline;
        this.projectDescribe = projectDescribe;
        this.projectPrivacy = projectPrivacy;
    }

    public Project() {
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(String projectOwner) {
        this.projectOwner = projectOwner;
    }

    public String getProjectDeadline() {
        return projectDeadline;
    }

    public void setProjectDeadline(String projectDeadline) {
        this.projectDeadline = projectDeadline;
    }

    public String getProjectDescribe() {
        return projectDescribe;
    }

    public void setProjectDescribe(String projectDescribe) {
        this.projectDescribe = projectDescribe;
    }

    public String getProjectPrivacy() {
        return projectPrivacy;
    }

    public void setProjectPrivacy(String projectPrivacy) {
        this.projectPrivacy = projectPrivacy;
    }
}
