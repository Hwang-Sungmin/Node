package com.example.taskfirebase.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {
    private String key = UUID.randomUUID().toString();
    String userName;
    String userId;
    HashMap<String , Orgs > orgs;

    public User(){

    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public User(String userName, String userId){
        this.userName = userName;
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public HashMap<String, Orgs> getOrgs() {
        return orgs;
    }

    public void setOrgs(HashMap<String, Orgs> orgs) {
        this.orgs = orgs;
    }
}
