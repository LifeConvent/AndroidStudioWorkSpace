package com.database.tool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.database.ob.Course;
import com.database.ob.Person;
import com.database.ob.PersonCourse;
import com.example.activity.search.SearchActivity;

/**
 * Created by 彪 on 2016/4/19.
 */
public class DBAdapter_PersonCourse {

    private static String account;

    private static final String DB_NAME = "mycourse.db";
    private static final String DB_TABLE = "MyCourseInfo";
    private static final int DB_VERSION = 1;

    public static final String TAG = "个人课程表数据库";

    public static final String TABLE_ID = "ID";
    public static final String COURSE_ID = "NO";
    public static final String USER_NAME = "NAME";

    private SQLiteDatabase db = null;
    private Context context;
    private DBOpenHelper dbOpenHelper;

    private static class DBOpenHelper extends SQLiteOpenHelper {
        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        private static String DB_CREATE = "create table " + DB_TABLE + "(" + TABLE_ID +
                " integer primary key autoincrement," + USER_NAME +" text not null,"+ COURSE_ID + " text not null);";

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
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

    //数据库第一次建立时被调用
    public DBAdapter_PersonCourse(Context context) {
        this.context = context;
    }

    public void open(String name) throws SQLiteException {
        account = name;
        dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
        try {
            //建立或打开可读写的数据库实例，成功后实例被缓存
            db = dbOpenHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbOpenHelper.getReadableDatabase();
        }
    }

    public void init() {
        PersonCourse course = new PersonCourse(account, "20160100");
        this.insert(course);
    }

    public long insert(PersonCourse course) {
        ContentValues newValues = new ContentValues();
        newValues.put(USER_NAME, account);
        newValues.put(COURSE_ID, course.getCourse());
        //将数据插入表中，无替换数据
        return db.insert(DB_TABLE, null, newValues);
    }

    public long insert(String account,String course) {
        ContentValues newValues = new ContentValues();
        newValues.put(USER_NAME, account);
        newValues.put(COURSE_ID, course);
        //将数据插入表中，无替换数据
        return db.insert(DB_TABLE, null, newValues);
    }

    public long deleteAllData() {
        return db.delete(DB_TABLE, null, null);
    }

    public long deleteOneData(String num) {
        return db.delete(DB_TABLE, Course.COURSE_ID + "=" + "'"+num+"'", null);
    }

    public void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    public PersonCourse[] queryCourse(String name) {
        PersonCourse[] course = getOneData(name);
        Log.i(TAG, "多组查询" + course);
        return course;
    }

    public PersonCourse[] getOneData(String name) {
        Cursor results = db.query(DB_TABLE, new String[]{TABLE_ID, USER_NAME,COURSE_ID},
                USER_NAME + "=" + "'"+name+"'", null, null, null, null);
        return ConvertToPeople(results);
    }

    private PersonCourse[] ConvertToPeople(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        PersonCourse[] courses = new PersonCourse[resultCounts];
        for (int i = 0; i < resultCounts; i++) {
            courses[i] = new PersonCourse();
            courses[i].setAccount(account);
            courses[i].setCourse(cursor.getString(cursor.getColumnIndex(COURSE_ID)));
            cursor.moveToNext();
        }
        return courses;
    }


}

