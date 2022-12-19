package com.example.promanager;

public class userInfo {
    private String username;
    private String password;
    private String fullname;
    private String overview;
    private int totalTask;
    private int totalTasks;
    private int currentTasks;
    private int currentFinished;

    public int getTotalTask() {
        return totalTask;
    }

    public void setTotalTask(int totalTask) {
        this.totalTask = totalTask;
    }

    public float getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(int totalTasks) {
        this.totalTasks = totalTasks;
    }

    public int getCurrentTasks() {
        return currentTasks;
    }

    public void setCurrentTasks(int currentTasks) {
        this.currentTasks = currentTasks;
    }

    public int getCurrentFinished() {
        return currentFinished;
    }

    public void setCurrentFinished(int currentFinished) {
        this.currentFinished = currentFinished;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public userInfo(String username, String password, String fullname, String overview) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.overview = overview;
    }

    public userInfo() {
    }
}
