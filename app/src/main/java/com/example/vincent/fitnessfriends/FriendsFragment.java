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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vincent on 11/1/2016.
 */

public class FriendsFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String JSON_FRIENDS_LIST = "JSON_FRIENDS_LIST";
    private int mPage;
    private ArrayList<ArrayList<String>> listViewData;

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
        String jsonString = "";
        try {
            jsonString = getActivity().getIntent().getExtras().getString(JSON_FRIENDS_LIST);
        }catch (NullPointerException e) {

        }
        View view = inflater.inflate(R.layout.fragment_friends_tab, container, false);
        LinearLayout ll = (LinearLayout) view;
        ListView list = (ListView) ll.findViewById(R.id.listView);
        List<String> nameList = new ArrayList<>();
        TextView text = new TextView(getContext());

        text.setText("Facebook Friends");
        list.addHeaderView(text);
        nameList.add("Charly Thai");
        nameList.add("Josh Granata");
        nameList.add("Sam Lee");
        nameList.add("Sampath Jayarantha");
        nameList.add("Ricky Bobby");
        nameList.add("Bobby Ricky");
        nameList.add("Morty");
        nameList.add("Tom From MySpace");
        nameList.add("Zuchinni");
        if(jsonString.length() > 0) {
            try {
                parseJSON((ArrayList) nameList, jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(nameList);
        //nameList.add(getActivity().getIntent().getExtras().get(JSON_FRIENDS_LIST) + "");
        MyArrayAdapter adapter = new MyArrayAdapter(this.getContext(),R.layout.list_item, nameList);
        list.setAdapter(adapter);
        return view;
    }

    public void parseJSON(ArrayList<String> list, String temp) throws JSONException {
        JSONArray jArray = new JSONArray(temp);
        for(int i = 0 ; i < jArray.length(); i++){
            JSONObject friends = jArray.getJSONObject(i);
            list.add(friends.get("name")+"");
            Log.d("FACEBOOK2",""+friends.get("name"));
        }
    }

}
class MyArrayAdapter extends ArrayAdapter{

    private List<String> list;
    private int resource, headerResource;

    public MyArrayAdapter(Context context, int resource, int headerResource,  List<String> list){
        super(context,resource, list);
        this.list = (ArrayList) list;
        this.resource = resource;
        this.headerResource = headerResource;
    }
    public MyArrayAdapter(Context context, int resource,  List<String> list){
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
        TextView text, rightText;
        ImageView img;
        if(convertView == null)
            view = mInflater.inflate(resource,parent,false);
        else
            view = convertView;
        switch(resource) {
            case R.layout.list_item:
                text = (TextView) view.findViewById(R.id.nameLabel);
                img = (ImageView) view.findViewById(R.id.profileImage);
                Log.d("setText", "" + getItem(position));
                text.setText(getItem(position));
                img.setImageResource(R.drawable.profile_placeholder);
                return view;
            case R.layout.list_header:
            case R.layout.edit_profile_list_item:
                    text = (TextView) view.findViewById(R.id.leftTextView);
                    rightText = (TextView) view.findViewById(R.id.rightTextView);
                    text.setText(getItem(position));
                    rightText.setText(getItem(position));




        }
        return view;
    }
}
