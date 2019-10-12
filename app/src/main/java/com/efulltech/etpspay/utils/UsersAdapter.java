/*
    THIS ADAPTER WILL HELP FETCH USERS FROM THE USERS DATABASE
    AND DISPLAY THEM ON THE RECYCLER VIEW IN THE USER MANAGEMENT ACTIVITY
 */
package com.efulltech.etpspay.utils;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.efulltech.etpspay.R;
import com.efulltech.etpspay.ui.data.LoginDataSource;
import com.efulltech.etpspay.ui.data.LoginRepository;
import com.efulltech.etpspay.ui.data.model.LoggedInUser;

import java.util.List;


public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private Context mContext;
    private Cursor mCursor;
    private List<LoggedInUser> allUsersList;

    public UsersAdapter(Context context, List<LoggedInUser> loggedInUsers){
        this.allUsersList = loggedInUsers;
        this.mContext = context;
    }

    public UsersAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_item_layout, parent, false);

//        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
//        View view = layoutInflater.inflate(R.layout.user_list_item_layout, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        //get logged in user
        LoginDataSource dataSource = new LoginDataSource();
        LoginRepository loginRepository = LoginRepository.getInstance(dataSource);
        LoggedInUser loggedInUser = loginRepository.getLoggedInUser();
        //get current user on the list
       LoggedInUser user = this.allUsersList.get(position);
        String fullName = user.getDisplayName();
        int permission = user.getPermLevel();
                String level;
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
        // only display userTag if current user on the list is the logged in user
        if(loggedInUser.getUserId() != user.getUserId()){
            holder.userTag.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
//        return mCursor.getCount();
        return allUsersList.size();
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


    public class UsersViewHolder extends RecyclerView.ViewHolder{
        public TextView fullNameText;
        public TextView avatarText;
        public TextView permissionText;
        public ImageView userTag;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            fullNameText = itemView.findViewById(R.id.userFullnameTextView);
            avatarText = itemView.findViewById(R.id.avatarText);
            permissionText = itemView.findViewById(R.id.permissionTextView);
            userTag = itemView.findViewById(R.id.userTagImage);
        }
    }
}