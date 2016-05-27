package com.example.activity.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.database.ob.Course;
import com.database.ob.Person;
import com.database.ob.PersonCourse;
import com.database.ob.Teacher;
import com.database.other.CourseAdapter;
import com.database.tool.DBAdapter_Course;
import com.database.tool.DBAdapter_PersonCourse;
import com.database.tool.DBAdapter_Teacher;
import com.example.activity.courseList.CourseNum;
import com.example.activity.courseList.MyCourseDetailActivity;
import com.example.activity.login.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 彪 on 2016/4/18.
 */
public class TeacherManegerActivity extends Activity implements View.OnClickListener {

    private DBAdapter_Teacher database_teacher;
    //private DBAdapter_Course database_course;

    public static TeacherManegerActivity teacherManegerActivity;
    private String account;

    private ListView mycourseList;

    private List<CourseNum> courseNumList = new ArrayList<CourseNum>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course);
        teacherManegerActivity = this;
        init();
        CourseAdapter adapter = new CourseAdapter(TeacherManegerActivity.this, R.layout.course_item, courseNumList);
        mycourseList.setAdapter(adapter);

        mycourseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CourseNum course = courseNumList.get(position);
                String numCourse = course.getCourseNum();
                //Course singleCourse = database_course.querySingleCourse(numCourse, "COURSE_ID");
                Intent intent = new Intent(TeacherManegerActivity.this, MyCourseDetailActivity.class);
                intent.putExtra("name", account);
                intent.putExtra("num", numCourse);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    private void init() {
        database_teacher = new DBAdapter_Teacher(getApplicationContext());
        //database_course = new DBAdapter_Course(getApplicationContext());
        Intent intent = getIntent();
        account = intent.getStringExtra("name");
        mycourseList = (ListView) findViewById(R.id.course_detail_list);
        database_teacher.open();
        database_teacher.init(account,"20135555");

        //以何身份打开数据库
        //Toast.makeText(getApplicationContext(), "MyCourse打开数据库"+account, Toast.LENGTH_SHORT).show();


        Teacher teacher = database_teacher.queryTeacher(account);
        if (teacher != null) {

        } else {
            Toast.makeText(getApplicationContext(), account + "！", Toast.LENGTH_SHORT).show();
        }
    }
}
