package com.efulltech.etpspay.ui.preferences;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.efulltech.etpspay.R;
import com.efulltech.etpspay.ui.SplashActivity;
import com.efulltech.etpspay.ui.data.LoginDataSource;
import com.efulltech.etpspay.ui.data.LoginRepository;
import com.efulltech.etpspay.ui.data.model.LoggedInUser;
import com.efulltech.etpspay.utils.UsersDBHelper;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class UserProfileActivity extends AppCompatActivity {

    private static final String TAG = "UserProfileActivity";
    private int userId, permLevel, db_position;
    private String userName, fullName, permLevelName;

    TextView fullNameText, userNameText, permLevText, avatarText;
    private LoginDataSource dataSource;
    private LoginRepository loginRepository;
    private LoggedInUser user;
    private UsersDBHelper usersDBHelper;
    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        dataSource = new LoginDataSource();
        loginRepository = LoginRepository.getInstance(dataSource);
        user = loginRepository.getLoggedInUser();

        usersDBHelper = new UsersDBHelper(this);
        mDatabase = usersDBHelper.getWritableDatabase();

        Intent intent = getIntent();
        this.userId = intent.getIntExtra("userId", 0);
        this.permLevel = intent.getIntExtra("permLevel", 0);
        this.db_position = intent.getIntExtra("db_position", 0);
        this.userName = intent.getStringExtra("username");
        this.fullName = intent.getStringExtra("fullname");
        this.permLevelName = intent.getStringExtra("permLevelName");

        fullNameText = findViewById(R.id.userProfileFullName);
        userNameText = findViewById(R.id.userProfileUserNameText);
        permLevText = findViewById(R.id.userProfilePermLevelText);
        avatarText = findViewById(R.id.userProfileAvatarText);

        fullNameText.setText(this.fullName);
        userNameText.setText("@"+this.userName);
        permLevText.setText(this.permLevelName);
        avatarText.setText(this.fullName.substring(0, 1));
    }

    public void showUpdatePasswordDialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.password_update_item_dialog_layout, null);
//
        ImageView cancelBtn =  mView.findViewById(R.id.cancel);
        Button changeBtn =  mView.findViewById(R.id.changePassword);
        changeBtn.setEnabled(false);

        EditText oldPassword =  mView.findViewById(R.id.oldPassword);
        EditText newPassword =  mView.findViewById(R.id.newPassword);
        EditText confirmPassword =  mView.findViewById(R.id.confirmPassword);


//        title.setText("Update User Password");
        oldPassword.getText();
        newPassword.getText();
        confirmPassword.getText();

        //        disable change button
        if (oldPassword.getText().toString().isEmpty() && newPassword.getText().toString().isEmpty() &&
                confirmPassword.getText().toString().isEmpty()){
            changeBtn.setEnabled(false);
        }else {
            changeBtn.setEnabled(true);
        }
        builder.setView(mView);
        AlertDialog dialog = builder.create();
        dialog.show();

        // onClick Listener to handle cancel btn click
        cancelBtn.setOnClickListener(view12 -> dialog.hide());

        // onClick Listener to handle proceed btn click
        changeBtn.setOnClickListener(view1 -> {
            // delete user from database
//            usersDBHelper.deleteUser(mDatabase, userId);
//            // hide dialog
//            dialog.hide();
//            // display toast
//            Toast.makeText(UserProfileActivity.this, "User account has been deleted", Toast.LENGTH_SHORT).show();
//            //if current user deleted his account
//            if(user.getUserId() == userId){
//                // sign out
//                loginRepository.logout();
//                finish();
//                // open slash screen activity
//                Intent splashIntent = new Intent(UserProfileActivity.this, SplashActivity.class);
//                startActivity(splashIntent);
//            }else {
//                // end activity
//                finish();
//            }
        });

    }

//    On delete user dialog
    public void showDelUserDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.delete_item_dialog_layout, null);

        // get a reference to add user btn on the dialog
        Button cancelBtn = (Button) mView.findViewById(R.id.cancelBtn);
        Button proceedBtn = (Button) mView.findViewById(R.id.proceedBtn);
        TextView title =  mView.findViewById(R.id.deleteItemTitle);
        TextView message =  mView.findViewById(R.id.deleteItemMessageText);

        title.setText("Delete User Account");
        message.setText("Proceeding with this operation will revoke all access rights from the user. \nDo you wish to proceed?");

        builder.setView(mView);
        AlertDialog dialog = builder.create();
        dialog.show();

        // onClick Listener to handle cancel btn click
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });

        // onClick Listener to handle proceed btn click
        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // delete user from database
                usersDBHelper.deleteUser(mDatabase, userId);
                // hide dialog
                dialog.hide();
                // display toast
                Toast.makeText(UserProfileActivity.this, "User account has been deleted", Toast.LENGTH_SHORT).show();
                //if current user deleted his account
                if(user.getUserId() == userId){
                    // sign out
                    loginRepository.logout();
                    finish();
                    // open slash screen activity
                    Intent splashIntent = new Intent(UserProfileActivity.this, SplashActivity.class);
                    startActivity(splashIntent);
                }else {
                    // end activity
                    finish();
                }
            }
        });

    }

    public void showUpdateProfileDialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.profile_update_item_dialog_layout, null);
//
        ImageView cancelBtn =  mView.findViewById(R.id.cancel);
        Button changeBtn =  mView.findViewById(R.id.updateProfileBtn);
//        changeBtn.setEnabled(false);

        EditText mUsername =  mView.findViewById(R.id.userNameUpdate);
        EditText mFullname =  mView.findViewById(R.id.fullnameUpdate);
        EditText mPermLevel =  mView.findViewById(R.id.permLevUpdate);

//        title.setText("Update User Profile");
        mUsername.setText(this.userName);
        mFullname.setText(this.fullName);
        mPermLevel.setText(this.permLevelName);

        builder.setView(mView);
        AlertDialog dialog = builder.create();
        dialog.show();

        // onClick Listener to handle cancel btn click
        cancelBtn.setOnClickListener(view12 -> dialog.hide());

        // onClick Listener to handle proceed btn click
        changeBtn.setOnClickListener(view1 -> {
            // delete user from database
            usersDBHelper.updateUserProfile(mDatabase, this.userId, this.userName, this.fullName, this.permLevelName);
//            // hide dialog

            dialog.hide();
//            // display toast
            Toast.makeText(UserProfileActivity.this, "User account has been updated successfully", Toast.LENGTH_SHORT).show();
//            //if current user deleted his account
//            if(user.getUserId() == userId){
//                // sign out
//                loginRepository.logout();
//                finish();
//                // open slash screen activity
//                Intent splashIntent = new Intent(UserProfileActivity.this, SplashActivity.class);
//                startActivity(splashIntent);
//            }else {
//                // end activity
//                finish();
//            }
        });

    }
}
