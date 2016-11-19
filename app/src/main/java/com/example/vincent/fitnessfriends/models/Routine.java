package com.example.vincent.fitnessfriends.models;

import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.os.Bundle;
import android.app.Activity;
import android.view.View.OnClickListener;
import android.widget.Toast;
/**
 * Created by Vincent on 10/25/2016.
 */

public class Routine extends Activity {

        EditText edit;
        Button button;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.generic_textbox);

            edit = (EditText) findViewById(R.id.edittext);
            button = (Button) findViewById(R.id.button);

            button.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    String str = edit.getText().toString();
                    Toast msg = Toast.makeText(getBaseContext(),str,Toast.LENGTH_LONG);
                    msg.show();
                }

            });

        }
}
