package com.example.vincent.fitnessfriends;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 11/16/2016.
 */

public class DrawerMenuAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> values;
    private int resId;

    public DrawerMenuAdapter(Context context, int textViewResourceId, List<String> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = (ArrayList) values;
        this.resId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(this.resId, parent, false);

        TextView elementText = (TextView)rowView.findViewById(R.id.nameLabel);
        ImageView elementImage = (ImageView)rowView.findViewById(R.id.profileImage);
        String textValue = values.get(position);

        elementText.setText(textValue);
        elementImage.setImageResource(R.drawable.profile_placeholder);


        return rowView;
    }
}
