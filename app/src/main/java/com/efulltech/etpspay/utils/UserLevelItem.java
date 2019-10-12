package com.efulltech.etpspay.utils;

public class UserLevelItem {

    private int mUserLogo;
    private String mUserLevel;

    public UserLevelItem(String userLevel, int userLogo){
        mUserLevel = userLevel;
        mUserLogo = userLogo;
    }

    public String getUserLevel(){
        return mUserLevel;
    }

    public int getUserLogo(){
        return mUserLogo;
    }
}
