package com.example.vincent.fitnessfriends.models;

import java.util.ArrayList;

/**
 * Created by Vincent on 10/25/2016.
 */

public class Group {
    String owner;
    ArrayList<String> members;

    public Group(String owner){
        this.owner = owner;
    }

    public Group(String owner, ArrayList<String> members){
        this.owner = owner;

        this.members = members;
    }

    public String getOwner() {
        return owner;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void addMember(ArrayList<String> members){
        this.members = members;
    }

    public void addMember(String id){
        members.add(id);
    }
}
