package com.example.vincent.fitnessfriends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.google.firebase.auth.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.widget.LoginButton;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private expanderSkeleton liAdapt;
    private ExpandableListView liView;

    private List<String> liHead;
    private HashMap<String, List<String>> liChild;

    private AccessTokenTracker fbTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        startLoginActivity();
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);
        fbTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) {
                if (accessToken2 == null) {
                    Log.d("FB", "User Logged Out.");
                    finish();
                }
            }
        };
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        liView = (ExpandableListView) findViewById(R.id.lvExp);


        makeLists();

        liAdapt = new expanderSkeleton(this, liHead, liChild);


        liView.setAdapter(liAdapt);


        liView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {

                return false;
            }
        });

        liView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        liHead.get(groupPosition) + " Minimized",
                        Toast.LENGTH_SHORT).show();

            }
        });

        liView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        liHead.get(groupPosition) + " Inflated",
                        Toast.LENGTH_SHORT).show();
            }
        });

        liView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        liHead.get(groupPosition)
                                + " : "
                                + liChild.get(
                                liHead.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
    }
    public void startLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    private void makeLists() {
        liHead = new ArrayList<>();
        liChild = new HashMap<>();

        // Add option data
        liHead.add("profile");
        liHead.add("friends");
        liHead.add("data");

        // Add more sub-options here
        List<String> profileOptions = new ArrayList<>();
        profileOptions.add("op 1");
        profileOptions.add("op 2");

        List<String> friendsOptions = new ArrayList<>();
        friendsOptions.add("op 1");

        List<String> dataOptions = new ArrayList<>();
        dataOptions.add("op 2");


        liChild.put(liHead.get(0), profileOptions);
        liChild.put(liHead.get(1), friendsOptions);
        liChild.put(liHead.get(2), dataOptions);
    }
}
