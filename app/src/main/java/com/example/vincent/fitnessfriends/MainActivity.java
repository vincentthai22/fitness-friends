package com.example.vincent.fitnessfriends;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;
import static com.example.vincent.fitnessfriends.FriendsFragment.JSON_FRIENDS_LIST;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;                                     //fire base classes
    private FirebaseAuth.AuthStateListener mAuthListener;           //i haven't gotten around to looking at
                                                                    //how to implement it  yet
    private expanderSkeleton liAdapt;
    private ExpandableListView liView;

    private List<String> liHead;
    private HashMap<String, List<String>> liChild;

    private AccessTokenTracker fbTracker;//fb tracker used to detect changes log in/out

    //Keys to get String info from facebook
    public static final String FACEBOOK_NAME = "facebookLogin";
    private String userName;

    //FireBase database instances
    // Write a message to the database
    public  FirebaseDatabase database = FirebaseDatabase.getInstance();
    public  DatabaseReference myRef = database.getReference("message");

    AlertDialog.Builder builder;
    AlertDialog dialog;

    //Fragment Manager and Transaction
    FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction transaction;

    @Override
    public FragmentManager getSupportFragmentManager() {
        return super.getSupportFragmentManager();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        myRef.setValue("Hello database");
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);


        fbTracker = new AccessTokenTracker() {                      //uses token to check if user has logged out.
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) {
                if (accessToken2 == null) {
                    Log.d("FB", "User Logged Out.");
                    finish();
                    startLoginActivity();
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
        if(savedInstanceState!= null) {
            getIntent().putExtra(JSON_FRIENDS_LIST, (String) savedInstanceState.get(JSON_FRIENDS_LIST));
            Log.d("FACEBOOKJSON", getIntent().getExtras().getString(JSON_FRIENDS_LIST));
        }
       initialize();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if(NavUtils.getParentActivityIntent(this) != null)
                    NavUtils.navigateUpFromSameTask(this);
                else
                    LoginManager.getInstance().logOut();
                return true;
            case R.id.logoutButton:
                dialog.show();
                return true;
            case R.id.action_settings:
                return true;
            case R.id.editProfileButton:
                startEditProfileActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void initialize(){
        if(Profile.getCurrentProfile() != null) {
            userName = Profile.getCurrentProfile().getName();
            Toast.makeText(getApplicationContext(), "Logged in as " + userName, Toast.LENGTH_LONG).show();
        }

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentPageAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null

        //setup up button
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar ab = getSupportActionBar();

        //ab.setDisplayHomeAsUpEnabled(true);

        // setup alertbox
       setAlertBox();



    }
    public void startEditProfileActivity(){
        Intent intent = new Intent(this, EditProfileActivity.class);
        Bundle args = new Bundle();
        if(Profile.getCurrentProfile() != null)
            args.putString(FACEBOOK_NAME, Profile.getCurrentProfile().getFirstName());
        intent.putExtras(args);
        intent.putExtras(getIntent().getExtras());
        startActivity(intent);
    }
    public void setAlertBox(){
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Log out as " + Profile.getCurrentProfile().getName()+"?");
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LoginManager.getInstance().logOut();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog = builder.create();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.android_toolbar, menu);
        return true;
    }

    public void startLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        finish();
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
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        //NavUtils.navigateUpFromSameTask(this);
        //NavUtils.navigateUpTo(this.getParent(),this.getParentActivityIntent());
        this.finish();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
      //  super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            savedInstanceState.putString(JSON_FRIENDS_LIST, getIntent().getExtras().getString(JSON_FRIENDS_LIST));
        }else{

        }
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        getIntent().putExtra(JSON_FRIENDS_LIST, (String)savedInstanceState.get(JSON_FRIENDS_LIST));
//        userScore = savedInstanceState.getInt(USER_POINTS);

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

//        Josh's old code
//        liView = (ExpandableListView) findViewById(R.id.lvExp);
//
//
//        makeLists();
//
//        liAdapt = new expanderSkeleton(this, liHead, liChild);
//
//
//        liView.setAdapter(liAdapt);
//
//
//        liView.setOnGroupClickListener(new OnGroupClickListener() {
//
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v,
//                                        int groupPosition, long id) {
//
//                return false;
//            }
//        });
//
//        liView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
//
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        liHead.get(groupPosition) + " Minimized",
//                        Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        liView.setOnGroupExpandListener(new OnGroupExpandListener() {
//
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        liHead.get(groupPosition) + " Inflated",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        liView.setOnChildClickListener(new OnChildClickListener() {
//
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v,
//                                        int groupPosition, int childPosition, long id) {
//                Toast.makeText(
//                        getApplicationContext(),
//                        liHead.get(groupPosition)
//                                + " : "
//                                + liChild.get(
//                                liHead.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT)
//                        .show();
//                return false;
//            }
//        });