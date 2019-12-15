package com.minh.getdata;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UserListAdapter extends BaseAdapter {
    final ArrayList<User> listUser;

    public UserListAdapter(ArrayList<User> listUser) {
        this.listUser = listUser;
    }

    @Override
    public int getCount() {
        return listUser.size();
    }

    @Override
    public Object getItem(int position) {
        return listUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listUser.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewUser;
        if (convertView == null) {
            viewUser = View.inflate(parent.getContext(), R.layout.list_item, null);

        } else viewUser = convertView;
        User user = (User) getItem(position);
        ((TextView) viewUser.findViewById(R.id.nameText)).setText(user.name);
        ((TextView) viewUser.findViewById(R.id.addressText)).setText(user.address);
        ((TextView) viewUser.findViewById(R.id.phoneNumber)).setText(user.phone);
        ((TextView) viewUser.findViewById(R.id.websiteText)).setText(user.website);
        ((TextView) viewUser.findViewById(R.id.emailText)).setText(user.email);
        ((TextView) viewUser.findViewById(R.id.companyText)).setText(user.company);
        return viewUser;
    }
}
