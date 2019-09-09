package com.efulltech.etpspay.ui.preferences;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.efulltech.etpspay.utils.Constants;
import com.efulltech.etpspay.utils.UsersAdapter;
import com.efulltech.etpspay.utils.UsersDBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.efulltech.etpspay.R;

public class UserMgtActivity extends AppCompatActivity {

    private SQLiteDatabase mDatabase;
    private UsersAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_mgt);

        UsersDBHelper usersDBHelper = new UsersDBHelper(this);
        mDatabase = usersDBHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.userListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new UsersAdapter(this, getAllUsers());
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.addUserFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                addUser(view);
                AlertDialog.Builder builder = new AlertDialog.Builder(UserMgtActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.add_new_user_layout, null);
                builder.setView(mView);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void addUser(View view){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.UserEntry.COLUMN_NAME, "Efezino");
        contentValues.put(Constants.UserEntry.COLUMN_USERNAME, "admin");
        contentValues.put(Constants.UserEntry.COLUMN_PASSWORD, "1234");
        contentValues.put(Constants.UserEntry.COLUMN_PERMISSION_LEVEL, 3);
        //save new user into the database
        mDatabase.insert(Constants.UserEntry.TABLE_NAME, null, contentValues);
        mAdapter.swapCursor(getAllUsers());
        Snackbar.make(view, "New User has been added", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

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

}
