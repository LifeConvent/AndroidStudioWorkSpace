package com.database.ob;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;

import com.example.activity.login.LoginActivity;
import com.example.activity.login.R;
import com.example.activity.search.SearchActivity;

/**
 * Created by 彪 on 2016/4/12.
 */
public class User {
    private static final String DB_NAME = "user.db";
    private static final String DB_TABLE = "UserInfo";
    private static final int DB_VERSION = 1;

    public static final String TABLE_ID = "ID";
    public static final String USER_NAME = "NAME";
    public static final String USER_PASSWORD = "PASSWORD";

    public String NAME;
    public String PASSWORD;

    public User(String account,String password){
        this.setAccountName(account);
        this.setAccountPassword(password);
    }

    public User(){}

    public String getAccountPassword() {
        return PASSWORD;
    }

    public void setAccountPassword(String accountPassword) {
        this.PASSWORD = accountPassword;
    }

    public void setAccountName(String accountName) {
        this.NAME = accountName;
    }

    public String getAccountName() {
        return NAME;
    }

}
