package com.efulltech.etpspay.ui.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;
import android.widget.Toast;

import com.efulltech.etpspay.ui.data.model.LoggedInUser;
import com.efulltech.etpspay.utils.Constants;
import com.efulltech.etpspay.utils.UsersDBHelper;

import java.io.IOException;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private static String AES = "AES";
    private SQLiteDatabase mDatabase;


    public Result<LoggedInUser> login(String username, String password, Context context) {

        UsersDBHelper usersDBHelper = new UsersDBHelper(context);
        mDatabase = usersDBHelper.getWritableDatabase();
        String sql = "SELECT * FROM "+ Constants.UserEntry.TABLE_NAME+" WHERE "
                +Constants.UserEntry.COLUMN_USERNAME+" == '"+username+"' ";
        Cursor cursor = mDatabase.rawQuery(sql, null);
        if(cursor.getCount() > 0){
            if(cursor.moveToFirst()){
                String user_pass = cursor.getString(cursor.getColumnIndex(Constants.UserEntry.COLUMN_PASSWORD));
                // encrypt password and compare
                try {
                    String encPass = encrypt(username, password);
                    if(encPass.equals(user_pass)){
                        // user account verified
                         String user_name = cursor.getString(cursor.getColumnIndex(Constants.UserEntry.COLUMN_USERNAME));
                         String user_full_name = cursor.getString(cursor.getColumnIndex(Constants.UserEntry.COLUMN_NAME));
                         int user_level = cursor.getInt(cursor.getColumnIndex(Constants.UserEntry.COLUMN_PERMISSION_LEVEL));
                         int user_id = cursor.getInt(cursor.getColumnIndex(Constants.UserEntry._ID));
                        try {
                            // TODO: handle loggedInUser authentication
                            LoggedInUser authUser =  new LoggedInUser(user_id, user_full_name, user_name, user_level);
                            cursor.close();
                            mDatabase.close();
                            return new Result.Success<>(authUser);
                        } catch (Exception e) {
                            cursor.close();
                            mDatabase.close();
                            return new Result.Error(new IOException("Error logging in", e));
                        }
                    }else{
                        // invalid password
                        cursor.close();
                        mDatabase.close();
                        Toast.makeText(context, "Invalid login credentials", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return new Result.Error(new IOException("Error logging in", e));
                }
            }
        }else{
            // invalid user credential
            cursor.close();
            mDatabase.close();
            Toast.makeText(context, "Username does not exist", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        mDatabase.close();
        return new Result.Error(new IOException("Error logging in", null));
//        return null;
    }

    public void logout() {
        // TODO: revoke authentication
    }

    public static String encrypt(String _key, String Data) throws Exception{
        // Data holds the text which we wish to encrypt
        // _key holds the key we wish to use to encrypt the data
        SecretKeySpec key = generateKey(_key);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = cipher.doFinal(Data.getBytes());
        return Base64.encodeToString(encVal, Base64.DEFAULT);
    }

    public static String decrypt(String _key, String Data) throws Exception{
        // Data holds the text which we wish to decrypt
        // _key holds the key we wish to use to decrypt the data
        SecretKeySpec key = generateKey(_key);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedVal = Base64.decode(Data, Base64.DEFAULT);
        byte[] decVal = cipher.doFinal(decodedVal);
        return new String(decVal);
    }

    private static SecretKeySpec generateKey(String password) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        return new SecretKeySpec(key, "AES");

    }

}
