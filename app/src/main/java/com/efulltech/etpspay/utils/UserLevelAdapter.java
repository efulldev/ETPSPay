package com.efulltech.etpspay.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.efulltech.etpspay.R;

import java.util.ArrayList;

public class UserLevelAdapter extends ArrayAdapter<UserLevelItem> {

    public UserLevelAdapter(Context context, ArrayList<UserLevelItem> userLevelList){
        super(context, 0, userLevelList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

     private View initView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_level_layout, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.user_level_logo);
        TextView textView = convertView.findViewById(R.id.user_level_name);

        UserLevelItem userLevelItem = getItem(position);

        if(userLevelItem != null){
            imageView.setImageResource(userLevelItem.getUserLogo());
            textView.setText(userLevelItem.getUserLevel());
        }

        return convertView;
     }
}
