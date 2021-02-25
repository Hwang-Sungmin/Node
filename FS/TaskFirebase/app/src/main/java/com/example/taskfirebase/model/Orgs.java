package com.example.taskfirebase.model;

import java.util.HashMap;
import java.util.UUID;

public class Orgs {
    private String key = UUID.randomUUID().toString();

    String orgName;
    String owner;
    HashMap<String , Orgs > usr;


    public Orgs(){
    }

    public Orgs(String orgName, String owner){
        this.orgName = orgName;
        this.owner = owner;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String getOrgName() {
        return orgName;
    }
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public HashMap<String, Orgs> getUsr() {
        return usr;
    }

    public void setUsr(HashMap<String, Orgs> usr) {
        this.usr = usr;
    }
}
