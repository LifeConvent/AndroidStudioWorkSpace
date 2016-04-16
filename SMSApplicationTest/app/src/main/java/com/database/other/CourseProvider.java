//package com.database.tool;
//
//import com.database.ob.Course;
//
//import android.content.ContentProvider;
//import android.content.ContentUris;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.UriMatcher;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.database.sqlite.SQLiteQueryBuilder;
//import android.net.Uri;
//import android.support.annotation.Nullable;
//
//
///**
// * Created by 彪 on 2016/4/9.
// */
//public class CourseProvider extends ContentProvider {
//
//    private static final String DB_NAME = "course.db";
//    private static final String DB_TABLE = "courseinfo";
//    private static final int DB_VERSION = 1;
//
//    private SQLiteDatabase db;
//    private DBOpenHelper dbOpenHelper;
//
//    private static final int MULTIPLE_COURSE = 1;
//    private static final int SINGLE_COURSE = 2;
//    private static final UriMatcher uriMatcher;
//
//    static {
//        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//        uriMatcher.addURI(Course.AUTHORITY, Course.PATH_MULTIPLE, MULTIPLE_COURSE);
//        uriMatcher.addURI(Course.AUTHORITY, Course.PATH_SINGLE, SINGLE_COURSE);
//    }
//
//    @Override
//    public boolean onCreate() {
//        Context context = getContext();
//        dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
//        db = dbOpenHelper.getWritableDatabase();
//        if (db == null)
//            return false;
//        else
//            return true;
//    }
//
//    @Nullable
//    @Override
//    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
//        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//        qb.setTables(DB_TABLE);
//        switch (uriMatcher.match(uri)) {
//            case SINGLE_COURSE:
//                qb.appendWhere(Course.TABLE_ID + "=" + uri.getPathSegments().get(1));
//                break;
//            default:
//                break;
//        }
//        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
//        cursor.setNotificationUri(getContext().getContentResolver(), uri);
//        return cursor;
//    }
//
//    @Nullable
//    @Override
//    public String getType(Uri uri) {
//        switch (uriMatcher.match(uri)) {
//            case MULTIPLE_COURSE:
//                return Course.MINE_TYPE_MULTIPLE;
//            case SINGLE_COURSE:
//                return Course.MINE_TYPE_SINGLE;
//            default:
//                throw new IllegalArgumentException("Unknow uri:" + uri);
//        }
//    }
//
//    @Nullable
//    @Override
//    public Uri insert(Uri uri, ContentValues values) {
//        long id = db.insert(DB_TABLE, null, values);
//        if (id > 0) {
//            Uri newUri = ContentUris.withAppendedId(Course.CONTENT_URI, id);
//            getContext().getContentResolver().notifyChange(newUri, null);
//            return newUri;
//        }
//        //throw new SQLException("Failed to insert row into"+uri);
//        return null;
//    }
//
//    @Override
//    public int delete(Uri uri, String selection, String[] selectionArgs) {
//        int count = 0;
//        switch (uriMatcher.match(uri)) {
//            case MULTIPLE_COURSE:
//                count = db.delete(DB_TABLE, selection, selectionArgs);
//                break;
//            case SINGLE_COURSE:
//                String segment = uri.getPathSegments().get(1);
//                count = db.delete(DB_TABLE, Course.TABLE_ID + "=" + segment, selectionArgs);
//                break;
//            default:
//                throw new IllegalArgumentException("Unsupported URI:" + uri);
//        }
//        getContext().getContentResolver().notifyChange(uri, null);
//        return count;
//    }
//
//    @Override
//    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
//        int count;
//        switch (uriMatcher.match(uri)) {
//            case MULTIPLE_COURSE:
//                count = db.update(DB_TABLE, values, selection, selectionArgs);
//                break;
//            case SINGLE_COURSE:
//                String segment = uri.getPathSegments().get(1);
//                count = db.update(DB_TABLE, values, Course.TABLE_ID + "=" + segment, selectionArgs);
//                break;
//            default:
//                throw new IllegalArgumentException("Unkonw uri:" + uri);
//        }
//        getContext().getContentResolver().notifyChange(uri, null);
//        return count;
//    }
//
//    private static class DBOpenHelper extends SQLiteOpenHelper {
//        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//            super(context, name, factory, version);
//        }
//
//        private static final String DB_CREATE = "create table " + DB_TABLE + "(" + Course.TABLE_ID +
//                " integer primary key autoincrement," + Course.COURSE_ID + " integer," + Course.COURSE_NAME +
//                " text not null," + Course.COURSE_TIME_START + " text not null," + Course.COURSE_TIME_END +
//                " text not null," + Course.COURSE_TEACHER + " text not null," + Course.COURSE_TIME_TAKE +
//                " float," + Course.COURSE_CONTENT + " text not null," + Course.COURSE_NEEDS + " text not null);";
//
//        @Override
//        public void onCreate(SQLiteDatabase db) {
//            db.execSQL(DB_CREATE);
//            CourseAddAll.init();
//        }
//
//        @Override
//        //当表需要进行更新时调用
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            //当创建的表存在时进行销毁
//            db.execSQL("DROP TABLE IF EXISTS" + DB_TABLE);
//            onCreate(db);
//            //一般用做数据删除或数据转移
//        }
//    }
//}
