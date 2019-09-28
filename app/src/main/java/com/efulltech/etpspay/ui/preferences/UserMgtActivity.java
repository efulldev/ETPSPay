package com.efulltech.etpspay.ui.preferences;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.efulltech.etpspay.ui.data.LoginRepository;
import com.efulltech.etpspay.ui.data.model.LoggedInUser;
import com.efulltech.etpspay.utils.Constants;
import com.efulltech.etpspay.utils.UserLevelAdapter;
import com.efulltech.etpspay.utils.UserLevelItem;
import com.efulltech.etpspay.utils.UsersAdapter;
import com.efulltech.etpspay.utils.UsersDBHelper;
import com.efulltech.etpspay.utils.UsersListRecyclerTouchListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.efulltech.etpspay.ui.data.LoginDataSource;

import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.efulltech.etpspay.R;


import java.util.ArrayList;
import java.util.List;


public class UserMgtActivity extends AppCompatActivity {

    private SQLiteDatabase mDatabase;
    private UsersAdapter mAdapter;
    private UsersDBHelper usersDBHelper;
    private RecyclerView recyclerView;
    private TranslateAnimation moveSideways, moveUpwards, moveInWards;

    private ArrayList<UserLevelItem> mUserList;
    private UserLevelAdapter mUserLevelAdapter;
    private int selectedUserLevel;
    private List<LoggedInUser> allUsersList;

    private LoginDataSource dataSource;
    private LoginRepository loginRepository;
    private LoggedInUser user;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_mgt);
        // animate intro transitional elements
        animateElements();

        //get logged in user
        dataSource = new LoginDataSource();
        loginRepository = LoginRepository.getInstance(dataSource);
        user = loginRepository.getLoggedInUser();

        usersDBHelper = new UsersDBHelper(this);
        mDatabase = usersDBHelper.getWritableDatabase();
        // get users list from the database
        allUsersList = usersDBHelper.listAllUsers(mDatabase);
        // initiate the adapter
        mAdapter = new UsersAdapter(this, allUsersList);
        // display all users stored in the database on a list
        recyclerView = findViewById(R.id.userListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        // onTouch listener
        recyclerView.addOnItemTouchListener(new UsersListRecyclerTouchListener(this,
                recyclerView, new UsersListRecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //navigate to user's profile
                Intent profile = new Intent(UserMgtActivity.this, UserProfileActivity.class);

                LoggedInUser user = allUsersList.get(position);
                profile.putExtra("username", user.getUserName());
                profile.putExtra("fullname", user.getDisplayName());
                profile.putExtra("permLevel", user.getPermLevel());
                profile.putExtra("permLevelName", user.getPermLevelName());
                profile.putExtra("userId", user.getUserId());
                profile.putExtra("db_position", position);
                startActivity(profile);
            }

            @Override
            public void onLongClick(View view, int position) {
//                showActionsDialog(position);
            }
        }));

        // initialize list of user levels. This is to be used for the
        // add new user dialog
        initUserList();

        FloatingActionButton fab = findViewById(R.id.addUserFab);
        // disable fab if user is an operator
        if(user.getPermLevel() == 0){
            fab.setActivated(false);
            fab.setVisibility(View.GONE);
        }
        // set onClickListener for fab
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddUserDialog();
            }
        });
    }

    private void showAddUserDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserMgtActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.add_new_user_layout, null);

        // get a reference to the spinner
        Spinner userLevelSpinner = (Spinner) mView.findViewById(R.id.userLevelSpinner);
        // instantiate the userLevelAdapter
        mUserLevelAdapter = new UserLevelAdapter(UserMgtActivity.this, mUserList);
        userLevelSpinner.setAdapter(mUserLevelAdapter);
        // get a reference to add user btn on the dialog
        Button addUserBtn = (Button) mView.findViewById(R.id.addUserBtn);

        builder.setView(mView);
        AlertDialog dialog = builder.create();
        dialog.show();

        // handle click events for the spinner
        userLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                UserLevelItem selLevel = (UserLevelItem) adapterView.getItemAtPosition(i);
//                String selUserLevelName = selLevel.getUserLevel();
                selectedUserLevel = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //handle click events for the addUserBtn
        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText fullName = mView.findViewById(R.id.newUserFullName_EditText);
                EditText username = mView.findViewById(R.id.newUserName_EditText);
                EditText pin = mView.findViewById(R.id.newUserPin);
                //check if all fields are filled
                if(fullName.getText().toString().trim().length() > 0){
                    if(username.getText().toString().trim().length() > 0){
                        //before you proceed, check if the username already exists on the users database
                        String _sql = "SELECT * FROM "+ Constants.UserEntry.TABLE_NAME+" WHERE "
                                +Constants.UserEntry.COLUMN_USERNAME+" == '"+username.getText().toString().trim()+"' ";
                        Cursor cursor = mDatabase.rawQuery(_sql, null);
                        if(cursor.getCount() == 0) {
                            if (pin.getText().toString().trim().length() == 6) {
                                String encPin = null;
                                try {
                                    //encrypt user pin
                                    encPin = LoginDataSource.encrypt(username.getText().toString().trim(), pin.getText().toString().trim());
//                                decPin = LoginDataSource.decrypt(username.getText().toString().trim(),  encPin);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                //create new user account
                                ContentValues contentValues = new ContentValues();
                                contentValues.put(Constants.UserEntry.COLUMN_NAME, fullName.getText().toString().trim());
                                contentValues.put(Constants.UserEntry.COLUMN_USERNAME, username.getText().toString().trim());
                                contentValues.put(Constants.UserEntry.COLUMN_PASSWORD, encPin);
                                contentValues.put(Constants.UserEntry.COLUMN_PERMISSION_LEVEL, selectedUserLevel);
                                //save new user into the database
                                mDatabase.insert(Constants.UserEntry.TABLE_NAME, null, contentValues);
                                //update the list after a new user is added
                                updateUsersList();
                                //clear input fields
                                fullName.getText().clear();
                                username.getText().clear();
                                pin.getText().clear();
                                dialog.hide();
                                Toast.makeText(getApplicationContext(), "A new user has been added to the user list", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "PIN must be 6 digits", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Username field must not be left empty", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Full name field must not be left empty", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }




    private void initUserList() {
        mUserList = new ArrayList<>();
        if (user.getPermLevel() != 0) { // operators are not allowed to add any user
            mUserList.add(new UserLevelItem("Operator", R.drawable.ic_face_black_24dp));
            mUserList.add(new UserLevelItem("Supervisor", R.drawable.ic_supervisor_account_deep_blue_64dp));
            if(user.getPermLevel() == 2 || user.getPermLevel() == 3) { // only an admin & a super admin can add another admin
                mUserList.add(new UserLevelItem("Admin", R.drawable.ic_account_circle_green_24dp));
            }
            if(user.getPermLevel() == 3) { // only a super admin can add another super admin
                mUserList.add(new UserLevelItem("Super Admin", R.drawable.ic_account_box_yellow_24dp));
            }
        }
    }


    // get all users from database
    private Cursor getAllUsers(){
        return mDatabase.query(Constants.UserEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Constants.UserEntry.COLUMN_TIMESTAMP + " DESC");
    }


    private void animateElements() {
        // animate the user management header
        moveSideways = new TranslateAnimation(-800, 0, 0, 0);
        moveSideways.setDuration(800);
        moveSideways.setFillAfter(true);
        findViewById(R.id.userMgtCardViewHeader).startAnimation(moveSideways);
        //animate the recycler view
//        moveInWards = new TranslateAnimation(800, 0, 0, 0);
//        moveInWards.setDuration(2000);
//        moveInWards.setFillAfter(true);
//        findViewById(R.id.userListRecyclerView).startAnimation(moveInWards);
        // animate the fab
        moveUpwards = new TranslateAnimation(0, 0, 500, 0);
        moveUpwards.setDuration(1000);
        moveUpwards.setFillAfter(true);
        findViewById(R.id.addUserFab).startAnimation(moveUpwards);
    }

    private void updateUsersList(){
        // update recycler view
        allUsersList = usersDBHelper.listAllUsers(mDatabase);
        mAdapter = new UsersAdapter(UserMgtActivity.this, allUsersList);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // update recycler view
        updateUsersList();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
