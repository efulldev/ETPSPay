package com.efulltech.etpspay.ui.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private int userId;
    private String displayName;
    private String userName;
    private int permLevel;

    public LoggedInUser(int userId, String displayName, String userName, int permLevel) {
        this.userId = userId;
        this.displayName = displayName;
        this.userName = userName;
        this.permLevel = permLevel;
    }

    public int getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUserName(){ return userName; }

    public int getPermLevel(){ return permLevel; }

    public String getPermLevelName(){
        String level;
        switch(permLevel){
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
        return level;
    }
}
