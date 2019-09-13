package com.efulltech.etpspay.ui.auth.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private String permLevelName;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName, String permLevName) {
        this.displayName = displayName;
        this.permLevelName = permLevName;
    }

    String getDisplayName() {
        return displayName;
    }

    String getPermLevelName() {
        return permLevelName;
    }
}
