package com.database.tool;

/**
 * Created by 彪 on 2016/4/15.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;

import com.database.ob.Course;
import com.database.ob.User;
import com.example.activity.login.LoginActivity;
import com.example.activity.login.R;
import com.example.activity.search.SearchActivity;

/**
 * Created by 彪 on 2016/4/12.
 */
public class DBAdapter_User {
    private static final String DB_NAME = "user.db";
    private static final String DB_TABLE = "UserInfo";
    private static final int DB_VERSION = 1;

    public static final String TABLE_ID = "ID";
    public static final String USER_NAME = "NAME";
    public static final String USER_PASSWORD = "PASSWORD";

    private SQLiteDatabase db = null;
    private Context context;
    private DBOpenHelper dbOpenHelper;

    public String NAME;
    public String PASSWORD;


    private static class DBOpenHelper extends SQLiteOpenHelper {
        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        private static final String DB_CREATE = "create table " + DB_TABLE + "(" + TABLE_ID +
                " integer primary key autoincrement," + USER_NAME +
                " text not null," + USER_PASSWORD + " text not null);";

        //   private static final String DB_INSERT = "insert into "+DB_TABLE+"values('admin','admin');";

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
            //         db.execSQL(DB_INSERT);
            ContentValues newValues = new ContentValues();
            newValues.put(USER_NAME, "1");
            newValues.put(USER_PASSWORD, "1");
            //将数据插入表中，无替换数据
            db.insert(DB_TABLE, null, newValues);
        }


        @Override
        //当表需要进行更新时调用
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //当创建的表存在时进行销毁
            db.execSQL("DROP TABLE IF EXISTS" + DB_TABLE);
            onCreate(db);
            //一般用做数据删除或数据转移
        }
    }

    public void open() throws SQLiteException {
        dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
        try {
            //建立或打开可读写的数据库实例，成功后实例被缓存
            db = dbOpenHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbOpenHelper.getReadableDatabase();
        }
    }

    public long insert(User user) {
        ContentValues newValues = new ContentValues();
        newValues.put(USER_NAME, user.NAME);
        newValues.put(USER_PASSWORD, user.PASSWORD);
        //将数据插入表中，无替换数据
        return db.insert(DB_TABLE, null, newValues);
    }

    //数据库第一次建立时被调用
    public DBAdapter_User(Context context) {
        this.context = context;
    }

    public long deleteAllData() {
        return db.delete(DB_TABLE, null, null);
    }

//    public long deleteOneData(long id) {
//        return db.delete(DB_TABLE, Course.COURSE_ID + "=" + id, null);
//    }


    public void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    //从login传值过来
    public User queryUser(String name) {
        User user = getOneData(name);
        return user;
    }


    public User getOneData(String name) {
        Cursor results = db.query(DB_TABLE, new String[]{TABLE_ID, USER_NAME, USER_PASSWORD},
                USER_NAME + "=" + "'"+name+"'", null, null, null, null);
        return ConvertToPeople(results);
    }

    private User ConvertToPeople(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        User user = new User();
        for (int i = 0; i < resultCounts; i++) {
            user = new User();
            user.NAME = cursor.getString(cursor.getColumnIndex(USER_NAME));
            user.PASSWORD = cursor.getString(cursor.getColumnIndex(USER_PASSWORD));
            cursor.moveToNext();
        }
        return user;
    }

    public String getAccountPassword() {
        return PASSWORD;
    }

    public void setAccountPassword(String accountPassword) {
        this.PASSWORD = accountPassword;
    }

    public String getAccountName() {
        return NAME;
    }

    public void setAccountName(String accountName) {
        this.NAME = accountName;
    }

    public void init(String name, String password) {
        User user = new User();
        user.setAccountName(name);
        user.setAccountPassword(password);
        put(user);
    }

    public void put(User user) {
        this.insert(user);
    }

}