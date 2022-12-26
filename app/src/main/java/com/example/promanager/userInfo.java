package com.example.promanager;

import java.util.HashMap;
import java.util.Map;

public class userInfo {
    private String username;
    private String pass;
    private String overview;
    private String phonenumber;
    private String email;
    private String avatarLink;
    private int totalTasks;
    private int totalHours;
    private int currentTasks;
    private int currentFinisheds;

    public userInfo(String username, String password, String phonenumber, String email, String overview, String avatarLink) {
        this.username = username;
        this.email = email;
        this.pass = password;
        this.phonenumber = phonenumber;
        this.overview = overview;
        this.avatarLink = avatarLink;
    }

    public userInfo() {
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(int totalTasks) {
        this.totalTasks = totalTasks;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public int getCurrentTasks() {
        return currentTasks;
    }

    public void setCurrentTasks(int currentTasks) {
        this.currentTasks = currentTasks;
    }

    public int getCurrentFinisheds() {
        return currentFinisheds;
    }

    public void setCurrentFinisheds(int currentFinisheds) {
        this.currentFinisheds = currentFinisheds;
    }

    @Override
    public String toString() {
        return "userInfo{" +
                "username='" + username + '\'' +
                ", pass='" + pass + '\'' +
                ", overview='" + overview + '\'' +
                ", email='" + email + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", totalTasks=" + totalTasks +
                ", totalHours=" + totalHours +
                ", currentTasks=" + currentTasks +
                ", currentFinisheds=" + currentFinisheds +
                ", avatarLink=" + avatarLink +
                '}';
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", this.username);
        result.put("pass", this.pass);
        result.put("phonenumber", this.phonenumber);
        result.put("email", this.email);
        result.put("overview", this.overview);
        result.put("totalTasks", this.totalTasks);
        result.put("totalHours", this.totalHours);
        result.put("currentTasks", this.currentTasks);
        result.put("currentFinisheds", this.currentFinisheds);
        result.put("avatarLink", this.avatarLink);
        return result;
    }

}
