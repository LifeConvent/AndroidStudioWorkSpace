package com.example.activity.courseList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.database.ob.Course;
import com.database.tool.DBAdapter_Course;
import com.database.tool.DBAdapter_PersonCourse;
import com.example.activity.login.R;

/**
 * Created by 彪 on 2016/4/16.
 */
public class CourseDetailActivity extends Activity implements View.OnClickListener {

    private DBAdapter_Course database;
    private DBAdapter_PersonCourse database_myCourse;
    private long id;

    private String account;

    private ListView detaillistView;
    private Button addCourseBtn;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_button:
                Course course = database.querySingleCourse(id + 1);
                database_myCourse.insert(account, String.valueOf(course.NO));
                /**
                 *
                 * 创建个人课程表,如何实现隐式自动跳转
                 *
                 *
                 * */
                Toast.makeText(getApplicationContext(),"成功添加课程！",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Intent intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra("id"));
        account = intent.getStringExtra("name");
        database_myCourse = new DBAdapter_PersonCourse(getApplicationContext());
        database_myCourse.open(account);
        database = new DBAdapter_Course(getApplicationContext());
        database.open();
        init();
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

    private void init() {
        addCourseBtn = (Button) findViewById(R.id.add_button);
        detaillistView = (ListView) findViewById(R.id.course_detail_list);
        Course course = database.querySingleCourse(id + 1);
        String[] data = {"课程号：      " + String.valueOf(course.NO), "课程名称：       " + course.NAME, "开始时间：      " + course.START_TIME, "结束时间：      " + course.END_TIME, "总耗时：      " + String.valueOf(course.TAKE_TIME), "讲师：        " + course.TEACHER, "课程大致内容:" + course.CONTENT, "课程须知:" + course.NEEDS + "\n\n\n"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CourseDetailActivity.this, android.R.layout.simple_list_item_1, data);
        detaillistView.setAdapter(adapter);
        addCourseBtn.setOnClickListener(this);
    }
}
