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
import com.efulltech.etpspay.ui.data.LoginRepository;
import com.efulltech.etpspay.ui.data.model.LoggedInUser;
import com.efulltech.etpspay.utils.Constants.*;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

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

    public void deleteUser(SQLiteDatabase sqLiteDatabase, int id){
        String sql = "DELETE FROM "+UserEntry.TABLE_NAME+" WHERE _ID = "+id;
        sqLiteDatabase.execSQL(sql);
    }

    public void updateUserProfile(SQLiteDatabase sqLiteDatabase, int id, String username, String fullname, String permLevelName){
//        String sql = "UPDATE FROM "+UserEntry.TABLE_NAME+" WHERE _ID = "+id;
//        sqLiteDatabase.execSQL(sql);
    ContentValues data = new ContentValues();
    data.put(UserEntry.COLUMN_NAME, fullname);
    data.put(UserEntry.COLUMN_USERNAME, username);
    data.put(UserEntry.COLUMN_PERMISSION_LEVEL, permLevelName);
        sqLiteDatabase.update(UserEntry.TABLE_NAME, data, "_id =" + id, null );
    }

    public static List<LoggedInUser> listAllUsers(SQLiteDatabase sqLiteDatabase){
        // get current logged in user
        LoginDataSource dataSource = new LoginDataSource();
        LoginRepository loginRepository = LoginRepository.getInstance(dataSource);
        LoggedInUser user = loginRepository.getLoggedInUser();
        String sql = "";
        switch (user.getPermLevel()){
            case 3:
                // super admin: fetch all
                sql = "SELECT * FROM "+UserEntry.TABLE_NAME+" ORDER BY "+UserEntry.COLUMN_TIMESTAMP+" DESC";
                break;
            case 2:
                // admin: get all operators, supervisors and admins
                sql = "SELECT * FROM "+UserEntry.TABLE_NAME+" WHERE "+UserEntry.COLUMN_PERMISSION_LEVEL+" != "+3+" ORDER BY "+UserEntry.COLUMN_TIMESTAMP+" DESC";
                break;
            case 1:
                // supervisor: fetch all operators and supervisors
                sql = "SELECT * FROM "+UserEntry.TABLE_NAME+" WHERE "+UserEntry.COLUMN_PERMISSION_LEVEL+" = 1 OR "+UserEntry.COLUMN_PERMISSION_LEVEL+" = 0 ORDER BY "+UserEntry.COLUMN_TIMESTAMP+" DESC";
                break;
            default:
                //operator: fetch only this user
                sql = "SELECT * FROM "+UserEntry.TABLE_NAME+" WHERE "+UserEntry._ID+" = "+user.getUserId()+" ORDER BY "+UserEntry.COLUMN_TIMESTAMP+" DESC";
                break;
        }

        List<LoggedInUser> allUsers = new ArrayList();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int userId = cursor.getInt(cursor.getColumnIndex(UserEntry._ID));
                String fullname = cursor.getString(cursor.getColumnIndex(Constants.UserEntry.COLUMN_NAME));
                String username = cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_USERNAME));
                int permLevel = cursor.getInt(cursor.getColumnIndex(UserEntry.COLUMN_PERMISSION_LEVEL));
                allUsers.add(new LoggedInUser(userId, fullname, username, permLevel));
            }  while(cursor.moveToNext());
        }
        cursor.close();

        return allUsers;
    }


    private void initRootUserAcc(SQLiteDatabase sqLiteDatabase) {
        // initialize the app with default super admin user account
        String username = "root";
        String password = "86337898";
        try {
            // create root user
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
        initAdminUserAcc(sqLiteDatabase);
    }


    private void initAdminUserAcc(SQLiteDatabase sqLiteDatabase) {
        // initialize the app with default super admin user account
        String username = "admin";
        String password = "260089";
        try {
            // create root user
            String encPin = LoginDataSource.encrypt(username.trim(),  password.trim());
            String SQL_INSERT_ROOT_USER = "INSERT INTO "+UserEntry.TABLE_NAME
                    +" ("+UserEntry.COLUMN_NAME+","+UserEntry.COLUMN_USERNAME+","
                    +" "+UserEntry.COLUMN_PASSWORD
                    +", "+UserEntry.COLUMN_PERMISSION_LEVEL+") " +
                    "VALUES ('DEFAULT ADMIN', '"+username+"', '"+encPin+"', "+2+")";
            //save new user into the database
            sqLiteDatabase.execSQL(SQL_INSERT_ROOT_USER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
