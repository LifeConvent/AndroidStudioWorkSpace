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
import com.database.other.CourseAdapter;
import com.database.tool.DBAdapter_Course;
import com.database.tool.DBAdapter_PersonCourse;
import com.example.activity.courseList.CourseNum;
import com.example.activity.courseList.MyCourseDetailActivity;
import com.example.activity.login.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 彪 on 2016/4/18.
 */
public class MyCourseActivity extends Activity implements View.OnClickListener {

    private DBAdapter_PersonCourse database_mycourse;
    //private DBAdapter_Course database_course;

    public static MyCourseActivity myCourseActivity;
    private String account;

    private ListView mycourseList;

    private List<CourseNum> courseNumList = new ArrayList<CourseNum>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course);
        myCourseActivity = this;
        init();
        CourseAdapter adapter = new CourseAdapter(MyCourseActivity.this, R.layout.course_item, courseNumList);
        mycourseList.setAdapter(adapter);

        mycourseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CourseNum course = courseNumList.get(position);
                String numCourse = course.getCourseNum();
                //Course singleCourse = database_course.querySingleCourse(numCourse, "COURSE_ID");
                Intent intent = new Intent(MyCourseActivity.this, MyCourseDetailActivity.class);
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
        database_mycourse = new DBAdapter_PersonCourse(getApplicationContext());
        //database_course = new DBAdapter_Course(getApplicationContext());
        Intent intent = getIntent();
        account = intent.getStringExtra("name");
        mycourseList = (ListView) findViewById(R.id.course_detail_list);
        database_mycourse.open(account);
        //database_course.open();

        //以何身份打开数据库
        //Toast.makeText(getApplicationContext(), "MyCourse打开数据库"+account, Toast.LENGTH_SHORT).show();


        PersonCourse[] course = database_mycourse.queryCourse(account);
        if (course != null) {
            for (int i = 0; i < course.length; i++) {
                CourseNum temp = new CourseNum(String.valueOf(course[i].getCourse()), "课程号：");
                courseNumList.add(temp);
            }
        } else {
            Toast.makeText(getApplicationContext(), account + ",您目前没有选中课程哦！", Toast.LENGTH_SHORT).show();
        }
    }
}
