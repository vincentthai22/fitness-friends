package com.example.vincent.fitnessfriends;

import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.view.View;
import android.content.Context;
import android.view.ViewGroup;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import java.util.HashMap;
import java.util.List;

public class expanderSkeleton extends BaseExpandableListAdapter {

    private Context _con;
    private List<String> _head;

    private HashMap<String, List<String>> _listchild;

    public expanderSkeleton(Context con, List<String> head,
                            HashMap<String, List<String>> child) {
        this._con = con;
        this._head = head;
        this._listchild = child;
    }

    @Override
    public Object getChild(int groupPlacement, int childPlacement) {
        return this._listchild.get(this._head.get(groupPlacement))
                .get(childPlacement);
    }

    @Override
    public long getChildId(int placeGroup, int placeChild) {
        return placeChild;
    }

    @Override
    public View getChildView(int positionGroup, final int positionChild,
                             boolean isEndNode, View changeView, ViewGroup parent) {

        final String childText = (String) getChild(positionGroup, positionChild);

        if (changeView == null) {
            LayoutInflater inflate = (LayoutInflater) this._con
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            changeView = inflate.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) changeView
                .findViewById(R.id.nameLabel);

        txtListChild.setText(childText);
        return changeView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listchild.get(this._head.get(groupPosition))
                .size();
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._head.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._head.size();
    }

    @Override
    public View getGroupView(int posGroup, boolean expand,
                             View viewChanger, ViewGroup parent) {
        String headerTitle = (String) getGroup(posGroup);
        if (viewChanger == null) {
            LayoutInflater secondInflate = (LayoutInflater) this._con
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewChanger = secondInflate.inflate(R.layout.list_group, null);
        }

        TextView textHead = (TextView) viewChanger
                .findViewById(R.id.lblListHeader);
        textHead.setTypeface(null, Typeface.BOLD);
        textHead.setText(headerTitle);

        return viewChanger;
    }


}