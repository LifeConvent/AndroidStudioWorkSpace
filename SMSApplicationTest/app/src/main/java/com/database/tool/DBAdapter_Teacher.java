package com.database.tool;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.database.ob.Teacher;

/**
 * Created by 彪 on 2016/4/12.
 */
public class DBAdapter_Teacher {
    private static final String DB_NAME = "teacher.db";
    private static final String DB_TABLE = "TeacherInfo";
    private static final int DB_VERSION = 1;

    public static final String TABLE_ID = "ID";
    public static final String USER_NAME = "NAME";
    public static final String USER_NUM = "NUM";

    private SQLiteDatabase db = null;
    private Context context;
    private DBOpenHelper dbOpenHelper;

    public String NAME;
    public String NUM;


    private static class DBOpenHelper extends SQLiteOpenHelper {
        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        private static final String DB_CREATE = "create table " + DB_TABLE + "(" + TABLE_ID +
                " integer primary key autoincrement," + USER_NAME +
                " text not null," + USER_NUM + " text not null);";

        //   private static final String DB_INSERT = "insert into "+DB_TABLE+"values('admin','admin');";

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
            //         db.execSQL(DB_INSERT);
            ContentValues newValues = new ContentValues();
            newValues.put(USER_NAME, "admin");
            newValues.put(USER_NUM, "admin");
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

    public long insert(Teacher teacher) {
        ContentValues newValues = new ContentValues();
        newValues.put(USER_NAME, teacher.NAME);
        newValues.put(USER_NUM, teacher.NUM);
        //将数据插入表中，无替换数据
        return db.insert(DB_TABLE, null, newValues);
    }

    //数据库第一次建立时被调用
    public DBAdapter_Teacher(Context context) {
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
    public Teacher queryTeacher(String name) {
        Teacher teacher = getOneData(name);
        return teacher;
    }

    public Teacher getOneData(String name) {
        Cursor results = db.query(DB_TABLE, new String[]{TABLE_ID, USER_NAME, USER_NUM},
                USER_NAME + "=" + "'" + name + "'", null, null, null, null);
        return ConvertToPeople(results);
    }

    public int updateTeacher(Teacher teacher) {
        ContentValues updateValues = new ContentValues();
        updateValues.put(USER_NAME, teacher.getAccountName());
        updateValues.put(USER_NUM, teacher.getAccountNum());
        return db.update(DB_TABLE, updateValues, USER_NAME + "=" + "'" + teacher.getAccountName() + "'", null);
    }

    private Teacher ConvertToPeople(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        Teacher teacher = new Teacher();
        for (int i = 0; i < resultCounts; i++) {
            teacher = new Teacher();
            teacher.NAME = cursor.getString(cursor.getColumnIndex(USER_NAME));
            teacher.NUM = cursor.getString(cursor.getColumnIndex(USER_NUM));
            cursor.moveToNext();
        }
        return teacher;
    }

    public void init(String name, String num) {
        Teacher teacher = new Teacher();
        teacher.setAccountName(name);
        teacher.setAccountNum(num);
        put(teacher);
    }

    public void put(Teacher teacher) {
        this.insert(teacher);
    }

}
