//package com.database.tool;
//
//import android.content.ContentResolver;
//import android.database.Cursor;
//import android.net.Uri;
//import android.widget.EditText;
//
//import com.database.ob.Course;
//import com.example.test.login.MainActivity;
//
///**
// * Created by 彪 on 2016/4/9.
// */
//public class CourseResolver {
//
//    //    private EditText idEntry;
//    private static ContentResolver resolver;
//
//    /**
//     * public int NO;
//     * public String NAME;
//     * public String START_TIME;
//     * public String END_TIME;
//     * public String TEACHER;
//     * public float TAKE_TIME;
//     * public String CONTENT;
//     * public String NEEDS;
//     */
//    public static void queryCourse() {
//        Uri uri = Uri.parse(Course.CONTENT_URI_STRING + "/" + MainActivity.idEntry.getText().toString());
//        Cursor cursor = resolver.query(uri, new String[]{Course.COURSE_ID, Course.COURSE_NAME,
//                Course.COURSE_TIME_START, Course.COURSE_TIME_END, Course.COURSE_TEACHER,
//                Course.COURSE_TIME_TAKE, Course.COURSE_CONTENT, Course.COURSE_NEEDS}, null, null, null);
//        if (cursor == null) {
//            MainActivity.welcome.setText("数据库中没有数据！");
//            return;
//        }
//        String msg = "";
//        //指针移动到第一条数据上
//        if (cursor.moveToFirst()) {
//            msg += "ID:" + cursor.getInt(cursor.getColumnIndex(Course.COURSE_ID)) + ",";
//            msg += "课程名："+cursor.getString(cursor.getColumnIndex(Course.COURSE_NAME))+",";
//        }
//        MainActivity.welcome.setText("数据库：\n"+msg);
//    }
//}
