package com.example.vincent.fitnessfriends;

import android.support.v4.app.Fragment;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vincent.fitnessfriends.models.Routine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 11/30/2016.
 */

public class RoutineFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static RoutineFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        RoutineFragment fragment = new RoutineFragment();
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
        View view = inflater.inflate(R.layout.fragment_routine_tab, container, false);
        ListView list = (ListView) view.findViewById(R.id.routineListView);
        List<String> optionList = new ArrayList<>();
        buildList(optionList);
        MyArrayAdapter adapter = new MyArrayAdapter(getActivity().getApplicationContext(),R.layout.edit_profile_list_item, optionList);

        list.setAdapter(adapter);
        return view;
    }
    public void buildList(List<String> optionList){
        AssetManager assetManager = getActivity().getAssets();
        try {
            InputStream optionListStream = assetManager.open("routineList.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(optionListStream));
            String line;
            while((line = in.readLine()) != null) {
                optionList.add(line);
            }
        } catch (IOException e) {
            Toast toast = Toast.makeText(getActivity(), "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
