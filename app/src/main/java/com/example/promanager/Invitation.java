package com.example.promanager;

import java.util.HashMap;
import java.util.Map;

public class Invitation {
    private String inviteId;
    private String fromUser;
    private String toUser;
    private String projectId;

    public Invitation(String fromUser, String toUser, String projectId) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.projectId = projectId;
    }

    public  Invitation(){}

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getInviteId() {
        return inviteId;
    }

    public void setInviteId(String inviteId) {
        this.inviteId = inviteId;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "inviteId='" + inviteId + '\'' +
                ", fromUser='" + fromUser + '\'' +
                ", toUser='" + toUser + '\'' +
                ", projectId='" + projectId + '\'' +
                '}';
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("fromUser", this.fromUser);
        result.put("toUser", this.toUser);
        result.put("projectId", this.projectId);
        result.put("inviteId", this.inviteId);
        return result;
    }
}
