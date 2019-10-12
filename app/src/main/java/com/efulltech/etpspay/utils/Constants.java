package com.efulltech.etpspay.utils;


import android.provider.BaseColumns;

public final class Constants {

    //constructor
    private Constants(){}

    public static final String PREFS = "Prefs";
    private static final String PACKAGE_NAME = "com.efulltech.etpspay";
    public static final String ADMIN_PIN = "admin_pin";
    public static final String SUPERVISOR_PIN = "supervisor_pin";
    public static final String OPERATOR_PIN = "operator_pin";
    public static final String PERMISSION = "permission";
    public static final String ADMIN = "admin";
    public static final String SUPERVISOR = "supervisor";
    public static final String OPERATOR = "operator";

    //database constants for user tables
    public static final class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "usersList";
        public static final String COLUMN_NAME = "full_name";
        public static final String COLUMN_USERNAME = "user_name";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_PERMISSION_LEVEL = "permission_level";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

}
