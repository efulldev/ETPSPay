/*
    THIS ADAPTER WILL HELP FETCH USERS FROM THE USERS DATABASE
    AND DISPLAY THEM ON THE RECYCLER VIEW IN THE USER MANAGEMENT ACTIVITY

 */
package com.efulltech.etpspay.utils;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.efulltech.etpspay.R;

import java.util.zip.Inflater;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public UsersAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder{
        public TextView fullNameText;
        public TextView avatarText;
        public TextView permissionText;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            fullNameText = itemView.findViewById(R.id.userFullnameTextView);
            avatarText = itemView.findViewById(R.id.avatarText);
            permissionText = itemView.findViewById(R.id.permissionTextView);

        }
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.user_list_item_layout, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)){
            return;
        }
        String fullName = mCursor.getString(mCursor.getColumnIndex(Constants.UserEntry.COLUMN_NAME));
        int permission = mCursor.getInt(mCursor.getColumnIndex(Constants.UserEntry.COLUMN_PERMISSION_LEVEL));
        String level = "";
        // determine permission level
        switch(permission){
            case 3:
                level = "Super Admin";
                break;
            case 2:
                level = "Admin";
                break;
            case 1:
                level = "Supervisor";
                break;
            default:
                level = "Operator";
                break;
        }
        holder.fullNameText.setText(fullName);
        holder.permissionText.setText(level);
        holder.avatarText.setText(fullName.trim().substring(0,1));

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if(mCursor != null){
            mCursor.close();
        }

        mCursor = newCursor;

        if(newCursor != null){
            notifyDataSetChanged();
        }
    }

}
