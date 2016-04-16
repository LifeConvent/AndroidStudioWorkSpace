package com.database.ob;

import android.net.Uri;


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
public class Course {

//    public static final String MIME_DIR_PREFIX = "vnd.android.cursor.dir";
//    public static final String MIME_ITEM_PREFIX = "vnd.android.cursor.dir";
//    public static final String MINE_ITEM = "vnd.database.course";
//
//    public static final String MINE_TYPE_SINGLE = MIME_ITEM_PREFIX + "/" + MINE_ITEM;
//    public static final String MINE_TYPE_MULTIPLE = MIME_DIR_PREFIX + "/" + MINE_ITEM;
//
//    public static final String AUTHORITY = "com.database.tool.provider";
//    public static final String PATH_SINGLE = "course/#";
//    public static final String PATH_MULTIPLE = "course";
//    public static final String CONTENT_URI_STRING = "content://" + AUTHORITY + "/" + PATH_MULTIPLE;
//    public static final Uri CONTENT_URI= Uri.parse(CONTENT_URI_STRING);
//
    public static final String TABLE_ID = "ID";//自增编号
    public static final String COURSE_ID = "NO";
    public static final String COURSE_NAME = "NAME";
    public static final String COURSE_TIME_START = "START_TIME";
    public static final String COURSE_TIME_END = "END_TIME";
    public static final String COURSE_TEACHER = "TEACHER";
    public static final String COURSE_TIME_TAKE = "TAKE_TIME";
    public static final String COURSE_CONTENT = "CONTENT";
    public static final String COURSE_NEEDS = "NEEDS";

    public int ID = -1;

    public long NO;
    public String NAME;
    public String START_TIME;
    public String END_TIME;
    public String TEACHER;
    public float TAKE_TIME;
    public String CONTENT;
    public String NEEDS;

    public Course(){

    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNO(long NO) {
        this.NO = NO;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public void setSTART_TIME(String START_TIME) {
        this.START_TIME = START_TIME;
    }

    public void setEND_TIME(String END_TIME) {
        this.END_TIME = END_TIME;
    }

    public void setTEACHER(String TEACHER) {
        this.TEACHER = TEACHER;
    }

    public void setTAKE_TIME(float TAKE_TIME) {
        this.TAKE_TIME = TAKE_TIME;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public void setNEEDS(String NEEDS) {
        this.NEEDS = NEEDS;
    }





    public String toString() {
        String result = "";
        result += "ID:" + this.ID + "\n";
        result += "课程号:" + this.NO + "\n";
        result += "课程名称:" + this.NAME + "\n";
        result += "开始时间:" + this.START_TIME + "\n";
        result += "结束时间:" + this.END_TIME + "\n";
        result += "讲课人:" + this.TEACHER + "\n";
        result += "用时:" + this.TAKE_TIME + "\n";
        result += "课程内容:" + this.CONTENT + "\n";
        result += "课程须知:" + this.NEEDS + "\n";
        return result;
    }
}
