package com.database.tool;

import com.database.ob.Course;
import com.example.activity.search.SearchActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 彪 on 2016/4/9.
 * Course 表ID字段
 * COURSE_ID 课程号 2016+id自增
 * COURSE_NAME 课程名
 * COURSE_TIME_START 课程开始时间
 * COURSE_TIME_END 课程结束时间
 * COURSE_TEACHER 课程教师
 * COURSE_TIME_TAKE 单次上课时长
 * COURSE_CONTENT 课程简介
 * COURSE_NEEDS 课程须知
 * course.db 文件名
 * courseinfo 表名
 */


public class DBAdapter_Course {

    private static final String DB_NAME = "course.db";
    private static final String DB_TABLE = "CourseInfo";
    private static final int DB_VERSION = 1;

    public static final String TAG = "课程表数据库";

    public static final String TABLE_ID = "ID";
    public static final String COURSE_ID = "NO";
    public static final String COURSE_NAME = "NAME";
    public static final String COURSE_TIME_START = "START_TIME";
    public static final String COURSE_TIME_END = "END_TIME";
    public static final String COURSE_TEACHER = "TEACHER";
    public static final String COURSE_TIME_TAKE = "TAKE_TIME";
    public static final String COURSE_CONTENT = "CONTENT";
    public static final String COURSE_NEEDS = "NEEDS";

    private SQLiteDatabase db = null;
    private Context context;
    private DBOpenHelper dbOpenHelper;

    private static class DBOpenHelper extends SQLiteOpenHelper {
        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        private static final String DB_CREATE = "create table " + DB_TABLE + "(" + TABLE_ID +
                " integer primary key autoincrement," + COURSE_ID + " integer," + COURSE_NAME +
                " text not null," + COURSE_TIME_START + " text not null," + COURSE_TIME_END +
                " text not null," + COURSE_TEACHER + " text not null," + COURSE_TIME_TAKE +
                " float," + COURSE_CONTENT + " text not null," + COURSE_NEEDS + " text not null);";

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
    public DBAdapter_Course(Context context) {
        this.context = context;
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


    public long insert(Course course) {
        ContentValues newValues = new ContentValues();
        newValues.put(COURSE_ID, course.NO);
        newValues.put(COURSE_NAME, course.NAME);
        newValues.put(COURSE_TIME_START, course.START_TIME);
        newValues.put(COURSE_TIME_END, course.END_TIME);
        newValues.put(COURSE_TEACHER, course.TEACHER);
        newValues.put(COURSE_CONTENT, course.CONTENT);
        newValues.put(COURSE_TIME_TAKE, course.TAKE_TIME);
        newValues.put(COURSE_NEEDS, course.NEEDS);
        //将数据插入表中，无替换数据
        return db.insert(DB_TABLE, null, newValues);
    }

    public long deleteAllData() {
        return db.delete(DB_TABLE, null, null);
    }

    public long deleteOneData(long id) {
        return db.delete(DB_TABLE, Course.COURSE_ID + "=" + id, null);
    }


    public void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    public Course[] queryCourse() {
        Course[] course = getOneData(Integer.parseInt(SearchActivity.idEt.getText().toString()));
        Log.i(TAG, "多组查询" + course);
        return course;
    }

    public Course querySingleCourse(long id) {
        Course course = getSingleOneData(id);
        Log.i(TAG, "单一查询" + course);
        return course;
    }

    public Course querySingleCourse(String num,String type) {
        Course course = getSingleOneData(num,type);
        Log.i(TAG, "单一查询" + course);
        return course;
    }


    public Course[] getOneData(long id) {
        Cursor results = db.query(DB_TABLE, new String[]{TABLE_ID, COURSE_ID, COURSE_NAME, COURSE_TIME_START,
                        COURSE_TIME_END, COURSE_TEACHER, COURSE_TIME_TAKE, COURSE_CONTENT, COURSE_NEEDS},
                TABLE_ID + "=" + id, null, null, null, null);
        return ConvertToPeople(results);
    }

    public Course getSingleOneData(long id) {
        Cursor results = db.query(DB_TABLE, new String[]{TABLE_ID, COURSE_ID, COURSE_NAME, COURSE_TIME_START,
                        COURSE_TIME_END, COURSE_TEACHER, COURSE_TIME_TAKE, COURSE_CONTENT, COURSE_NEEDS},
                TABLE_ID + "=" + id, null, null, null, null);
        return ConvertToSinglePeople(results);
    }

    public Course getSingleOneData(String num,String type) {
        Cursor results;
        if(type.equals("COURSE_NAME"))
        results = db.query(DB_TABLE, new String[]{TABLE_ID, COURSE_ID, COURSE_NAME, COURSE_TIME_START,
                        COURSE_TIME_END, COURSE_TEACHER, COURSE_TIME_TAKE, COURSE_CONTENT, COURSE_NEEDS},
                COURSE_NAME + "=" + "'" + num + "'", null, null, null, null);
        else
            results = db.query(DB_TABLE, new String[]{TABLE_ID, COURSE_ID, COURSE_NAME, COURSE_TIME_START,
                            COURSE_TIME_END, COURSE_TEACHER, COURSE_TIME_TAKE, COURSE_CONTENT, COURSE_NEEDS},
                    COURSE_ID + "=" + "'" + num + "'", null, null, null, null);
        return ConvertToSinglePeople(results);
    }

    /**
     * TABLE_ID = "ID";
     * COURSE_ID = "NO";
     * COURSE_NAME = "NAME";
     * COURSE_TIME_START = "START_TIME";
     * COURSE_TIME_END = "END_TIME";
     * COURSE_TEACHER = "TEACHER";
     * COURSE_TIME_TAKE = "TAKE_TIME";
     * COURSE_CONTENT = "CONTENT";
     * COURSE_NEEDS = "NEEDS";
     */
    private Course[] ConvertToPeople(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        Course[] courses = new Course[resultCounts];
        for (int i = 0; i < resultCounts; i++) {
            courses[i] = new Course();
            courses[i].ID = cursor.getInt(cursor.getColumnIndex(TABLE_ID));
            courses[i].NO = cursor.getLong(cursor.getColumnIndex(COURSE_ID));
            courses[i].NAME = cursor.getString(cursor.getColumnIndex(COURSE_NAME));
            courses[i].START_TIME = cursor.getString(cursor.getColumnIndex(COURSE_TIME_START));
            courses[i].END_TIME = cursor.getString(cursor.getColumnIndex(COURSE_TIME_END));
            courses[i].TEACHER = cursor.getString(cursor.getColumnIndex(COURSE_TEACHER));
            courses[i].TAKE_TIME = cursor.getInt(cursor.getColumnIndex(COURSE_TIME_TAKE));
            courses[i].CONTENT = cursor.getString(cursor.getColumnIndex(COURSE_CONTENT));
            courses[i].NEEDS = cursor.getString(cursor.getColumnIndex(COURSE_NEEDS));
            cursor.moveToNext();
        }
        return courses;
    }

    private Course ConvertToSinglePeople(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        Course courses = new Course();
        for (int i = 0; i < resultCounts; i++) {
            courses = new Course();
            courses.ID = cursor.getInt(cursor.getColumnIndex(TABLE_ID));
            courses.NO = cursor.getLong(cursor.getColumnIndex(COURSE_ID));
            courses.NAME = cursor.getString(cursor.getColumnIndex(COURSE_NAME));
            courses.START_TIME = cursor.getString(cursor.getColumnIndex(COURSE_TIME_START));
            courses.END_TIME = cursor.getString(cursor.getColumnIndex(COURSE_TIME_END));
            courses.TEACHER = cursor.getString(cursor.getColumnIndex(COURSE_TEACHER));
            courses.TAKE_TIME = cursor.getInt(cursor.getColumnIndex(COURSE_TIME_TAKE));
            courses.CONTENT = cursor.getString(cursor.getColumnIndex(COURSE_CONTENT));
            courses.NEEDS = cursor.getString(cursor.getColumnIndex(COURSE_NEEDS));
            cursor.moveToNext();
        }
        return courses;
    }

    public void init() {
        Course[] courseItem = new Course[100];
        initCourse(courseItem);
        for (int i = 0; i < courseItem.length; i++) {
            put(courseItem[i]);
        }
    }

    public void put(Course course) {
        this.insert(course);
    }

    /**
     * public int NO;
     * public String NAME;
     * public String START_TIME;
     * public String END_TIME;
     * public String TEACHER;
     * public float TAKE_TIME;
     * public String CONTENT;
     * public String NEEDS;
     */
    public static void initCourse(Course[] course) {
        for (int i = 0; i < course.length; i++) {
            course[i] = new Course();
            course[i].setNO(20160100 + i);
            course[i].setNAME(String.valueOf(i * 3452));
            course[i].setSTART_TIME("  " + String.valueOf((6 + i) % 12 + 1) + ":00 点");
            course[i].setEND_TIME("  " + String.valueOf((8 + i) % 12 + 1) + ":00 点");
            course[i].setTEACHER("  教师：" + String.valueOf(i * 2345) + "号");
            course[i].setTAKE_TIME(Math.abs(((6 + i) % 12 + 1) - ((8 + i) % 12 + 1)));
            course[i].setCONTENT("\n\n" + "    数据库系统原理课程设计是数据库系统原理实践环节的及为重要的一部分，其目的是：\n" +
                    "      (1)培养学生应用数据库系统原理，在需求分析的基础上，对系统进行概念设计，学会设计局部ER，全局ER图。\n" +
                    "(2)培养学生应用数据库系统原理，在概念设计的基础上，应用关系规范化理论对系统进行逻辑设计，学会在ER图基础上，设计出易于查询和操作的合理的规范化关系模型\n" +
                    "(3)培养学生能够应用SQL语言，对所设计的规范化关系模型进行物理设计，并且能够应用事务处理，存储过程，触发器，游标技术以保证数据库系统的数据完整性，安全性，一致性，保证数据共享和防止数据冲突。\n" +
                    "(4) )培养学生理论与实际相结合能力，培养学生开发创新能力。\n");
            course[i].setNEEDS("\n\n" + "    数据库课程设计毕业设计说明书一律采用单面打印。纸张大小为A4复印纸，页边距采用：上2.5cm、下2.0cm、左2.8cm、右1.2cm。无特殊要求的汉字采用小四号宋体字，行间距为1.25倍行距。页眉从正文开始，一律设为“数据库课程设计说明书”，采用宋体五号字居中书写。页码从正文开始按阿拉伯数字（宋体小五号）连续编排，居中书写。");
        }
    }
}
