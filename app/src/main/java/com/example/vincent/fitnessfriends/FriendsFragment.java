package com.example.vincent.fitnessfriends;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 11/1/2016.
 */

public class FriendsFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String JSON_FRIENDS_LIST = "JSON_FRIENDS_LIST";
    private int mPage;

    public static FriendsFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FriendsFragment fragment = new FriendsFragment();
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
        View view = inflater.inflate(R.layout.fragment_friends_tab, container, false);
        LinearLayout ll = (LinearLayout) view;
        ListView list = (ListView) ll.findViewById(R.id.listView);

        List<String> nameList = new ArrayList<>();
        TextView text = new TextView(getContext());
        text.setText("Friends");
        list.addHeaderView(text);
        nameList.add("Vincent");
        nameList.add(getActivity().getIntent().getExtras().get(JSON_FRIENDS_LIST) + "");
        //nameList.add(getActivity().getIntent().getExtras().get(JSON_FRIENDS_LIST) + "");
        MyArrayAdapter adapter = new MyArrayAdapter(this.getContext(),R.layout.list_item, nameList);
        list.setAdapter(adapter);
        return view;
    }
}
class MyArrayAdapter extends ArrayAdapter{
    private List<String> list;
    private int resource;
    public MyArrayAdapter(Context context, int resource,  List<String> list ){
        super(context,resource, list);
        this.list = (ArrayList) list;
        this.resource = resource;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        return createViewFromResource(position, convertView, parent, resource);
    }
    public String getItem(int position){
        return list.get(position);
    }
    public View createViewFromResource(int position, View convertView, ViewGroup parent, int resource){
        View view;
        LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView text;
        ImageView img;
        if(convertView == null)
            view = mInflater.inflate(resource,parent,false);
        else
            view = convertView;

        text = (TextView) view.findViewById(R.id.nameLabel);
        img = (ImageView) view.findViewById(R.id.profileImage);
        Log.d("setText", ""+getItem(position));
        text.setText(""+getItem(position));
        img.setImageResource(R.drawable.profile_placeholder);
        return view;
    }
}
