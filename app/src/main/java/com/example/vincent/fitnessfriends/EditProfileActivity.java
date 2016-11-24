package com.example.vincent.fitnessfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import static com.example.vincent.fitnessfriends.FriendsFragment.JSON_FRIENDS_LIST;

/**
 * Created by Vincent on 11/23/2016.
 */

public class EditProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null

        //setup up button
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        Log.d(JSON_FRIENDS_LIST, getIntent().getExtras().getString(JSON_FRIENDS_LIST));
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = NavUtils.getParentActivityIntent(this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        NavUtils.navigateUpTo(this,intent);
        this.finish();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
//            savedInstanceState.putBoolean(USER_TURN, userTurn);
//            savedInstanceState.putInt(USER_POINTS, userScore);
        }else{

        }
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
//        userTurn = savedInstanceState.getBoolean(USER_TURN);
//        userScore = savedInstanceState.getInt(USER_POINTS);

    }
}
