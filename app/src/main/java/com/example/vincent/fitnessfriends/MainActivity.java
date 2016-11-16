package com.example.vincent.fitnessfriends;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
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

import org.json.JSONException;
import org.json.JSONObject;

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
    private static final String FACEBOOK_NAME = "facebookLogin";

    //FireBase database instances
    // Write a message to the database
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference myRef = database.getReference("message");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myRef.setValue("Hello database");
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        Log.d("FACEBOOKJSON", getIntent().getExtras().getString(JSON_FRIENDS_LIST));
        setContentView(R.layout.activity_main);
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
                LoginManager.getInstance().logOut();
                return true;
            case R.id.action_settings:
                return true;
            case R.id.editProfileButton:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void initialize(){
        if(Profile.getCurrentProfile() != null) {
            Toast.makeText(getApplicationContext(), "Logged in as " + Profile.getCurrentProfile().getName(), Toast.LENGTH_LONG).show();
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

        ab.setDisplayHomeAsUpEnabled(true);

       // ArrayList<String> temp = getFriendsList();
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.android_toolbar, menu);
        return true;
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