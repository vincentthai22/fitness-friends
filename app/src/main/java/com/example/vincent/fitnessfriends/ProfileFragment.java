package com.example.vincent.fitnessfriends;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Vincent on 11/1/2016.
 */

public class ProfileFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static ProfileFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        final GridLayout gl = (GridLayout) view;
        final LinearLayout ll = (LinearLayout) view.findViewById(R.id.profileLinearLayout);
        Button postButton  = (Button) ll.findViewById(R.id.postButton);
        final EditText status = (EditText) ll.findViewById(R.id.statusUpdate);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView newStatus = new TextView(getContext());
                newStatus.setText(status.getText());
                newStatus.setPadding(10,0,0,0);
                newStatus.setTextSize(18);
                ll.addView(newStatus);
            }
        });
        //TextView textView = (TextView) ll.findViewById(R.id.fragmentText);
       // textView.setText("Fragment # Friends" + mPage);
        return view;
    }

}
