package com.example.vincent.fitnessfriends;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.example.vincent.fitnessfriends.FriendsFragment.JSON_FRIENDS_LIST;
import static com.example.vincent.fitnessfriends.MainActivity.FACEBOOK_NAME;

/**
 * Created by Vincent on 11/23/2016.
 */

public class EditProfileActivity extends AppCompatActivity {
    private static final String PROFILE_PICTURE = "profilePicture";
    private final int REQUEST_IMAGE_CAPTURE= 1;
    ImageButton profileImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        setSupportActionBar(toolbar);
        ab.setDisplayHomeAsUpEnabled(true);

        //setupProfileImage();
        ListView list = (ListView) findViewById(R.id.editListView);
        List<String> optionList = new ArrayList<>();
        buildList(optionList);
        MyArrayAdapter adapter = new MyArrayAdapter(this.getApplicationContext(),R.layout.edit_profile_list_item, optionList);

        list.setAdapter(adapter);
        setupProfileImage();
       // TextView aboutLabel = (TextView) findViewById(R.id.aboutLabel);
      //  aboutLabel.setText(aboutLabel.getText() + " " + getIntent().getExtras().getString(FACEBOOK_NAME));
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
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void buildList(List<String> optionList){
        AssetManager assetManager = getAssets();
        try {
            InputStream optionListStream = assetManager.open("optionList.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(optionListStream));
            String line;
            while((line = in.readLine()) != null) {
                optionList.add(line);
            }
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
    }
    //Sets up profile image onclick listener
    public void setupProfileImage(){
        profileImage = (ImageButton) findViewById(R.id.profileImage);
        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            profileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dispatchTakePictureIntent(view);
                }
            });
        }
    }
    public void dispatchTakePictureIntent(View view) {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if( takePicture.resolveActivity(getPackageManager()) != null)
            startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profileImage.setImageBitmap(imageBitmap);
            this.getParentActivityIntent().putExtra(PROFILE_PICTURE, imageBitmap);
        }
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
