/*
    DATABASE HELPER CLASS FOR MANAGING USER ACCOUNTS
    ON THE DEVICE. THIS TABLE WILL HOLD VALUES FOR
    USERS WHO HAVE ACCESS TO USE THE DEVICE AND WILL
    STORE THEIR CORRESPONDING PERMISSION LEVELS

    PERMISSION LEVELS:
    0 -> OPERATOR (POS OPERATOR)
    1 -> SUPERVISOR (BUSINESS OWNER / MANAGER)
    2 -> ADMIN (BANKS OR PTSP)
    3 ->SUPER ADMIN (EFULL TECH)
*/

package com.efulltech.etpspay.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.efulltech.etpspay.ui.data.LoginDataSource;
import com.efulltech.etpspay.utils.Constants.*;
import androidx.annotation.Nullable;

public class UsersDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "usersList_db";
    public static final int DATABASE_VERSION = 1;

    public UsersDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_USERS_TABLE = "CREATE TABLE "+ UserEntry.TABLE_NAME
                +" ("+ UserEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
        UserEntry.COLUMN_NAME +" TEXT NOT NULL, "+
        UserEntry.COLUMN_USERNAME +" TEXT NOT NULL, "+
        UserEntry.COLUMN_PASSWORD +" TEXT NOT NULL, "+
        UserEntry.COLUMN_PERMISSION_LEVEL +" INTEGER NOT NULL, "+
        UserEntry.COLUMN_TIMESTAMP +" TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";

        sqLiteDatabase.execSQL(SQL_CREATE_USERS_TABLE);
        initRootUserAcc(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+UserEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    private void initRootUserAcc(SQLiteDatabase sqLiteDatabase) {
        // initialize the app with default super admin user account
        String username = "root";
        String password = "260089";
        try {
            String encPin = LoginDataSource.encrypt(username.trim(),  password.trim());
            String SQL_INSERT_ROOT_USER = "INSERT INTO "+UserEntry.TABLE_NAME
                    +" ("+UserEntry.COLUMN_NAME+","+UserEntry.COLUMN_USERNAME+","
                    +" "+UserEntry.COLUMN_PASSWORD
                    +", "+UserEntry.COLUMN_PERMISSION_LEVEL+") " +
                    "VALUES ('ROOT ADMIN', '"+username+"', '"+encPin+"', "+3+")";
            //save new user into the database
            sqLiteDatabase.execSQL(SQL_INSERT_ROOT_USER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
