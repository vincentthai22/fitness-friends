package com.example.vincent.fitnessfriends.models;

import java.util.ArrayList;

/**
 * Created by Vincent on 10/25/2016.
 */

public class Profile {
    private String first, last, city, state;
    private ArrayList<Group> myGroups;
    private Routine currentRoutine;
    private ArrayList<Profile> friends;
    private float myWeight, myBmi;
    private int myHeight;

    public Profile(String first, String last){
        this.first = first;
        this.last = last;
        city = state = "";
        myWeight = myBmi = myHeight = 0;
        friends = new ArrayList<>();
        currentRoutine = new Routine();
        myGroups = new ArrayList<>();
    }

    public Profile(String first, String last, String city, String state) {
        this.first = first;
        this.last = last;
        this.city = city;
        this.state = state;
        myWeight = myBmi = myHeight = 0;
        friends = new ArrayList<>();
        currentRoutine = new Routine();
        myGroups = new ArrayList<>();
    }

    public int getMyHeight() {
        return myHeight;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public void setMyHeight(int myHeight) {
        this.myHeight = myHeight;
    }

    public void setMyWeight(float myWeight) {
        this.myWeight = myWeight;
    }

    public void setMyBmi(float myBmi) {
        this.myBmi = myBmi;
    }

    public float getMyWeight() {

        return myWeight;
    }

    public float getMyBmi() {
        return myBmi;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }
    public String getName(){
        return last + ", " + first;
    }

}
