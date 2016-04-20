package com.example.activity.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.database.ob.Course;
import com.database.tool.DBAdapter_Course;
import com.database.tool.DBAdapter_PersonCourse;
import com.example.activity.courseList.CourseDetailActivity;
import com.example.activity.login.R;

public class SearchActivity extends Activity implements View.OnClickListener {

    //id输入框
    public static EditText idEt;
    //文本显示框
    public static TextView hintTv;
    //查询确认按钮
    private Button searchBtn;
    //添加课程确定按钮
    private Button addSubBtn;

    private ListView detailListLv;

    private String account;

    public DBAdapter_Course database;
    private DBAdapter_PersonCourse database_myCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        idEt = (EditText) findViewById(R.id.id_get);
        //hintTv = (TextView) findViewById(R.id.hint_view);
        searchBtn = (Button) findViewById(R.id.search_commit);
        addSubBtn = (Button) findViewById(R.id.add_button_search);
        addSubBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        //hintTv.setOnClickListener(this);
        database = new DBAdapter_Course(getBaseContext());
        database_myCourse = new DBAdapter_PersonCourse(getApplicationContext());
        Intent intent = getIntent();
        account = intent.getStringExtra("name");
        database_myCourse.open(account);
        database.open();
        detailListLv = (ListView) findViewById(R.id.course_detail_list_search);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_commit:
                Course course;
                if (Integer.parseInt(idEt.getText().toString()) < 1000)
                    course = database.querySingleCourse(Integer.parseInt(idEt.getText().toString()));
                else if (Integer.parseInt(idEt.getText().toString()) < 10000)
                    course = database.querySingleCourse(idEt.getText().toString(), "COURSE_NAME");
                else course = database.querySingleCourse(idEt.getText().toString(), "COURSE_NUM");
                if (course == null) {
                    Toast.makeText(getApplicationContext(), "查询内容不存在！", Toast.LENGTH_LONG).show();
                    return;
                }
                String[] data = {"课程号：      " + String.valueOf(course.NO), "课程名称：       " + course.NAME, "开始时间：      " + course.START_TIME, "结束时间：      " + course.END_TIME, "总耗时：      " + String.valueOf(course.TAKE_TIME), "讲师：        " + course.TEACHER, "课程大致内容:" + course.CONTENT, "课程须知:" + course.NEEDS + "\n\n\n"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, data);
                detailListLv.setAdapter(adapter);
                break;
            case R.id.add_button_search:
                if (Integer.parseInt(idEt.getText().toString()) < 1000)
                    course = database.querySingleCourse(Integer.parseInt(idEt.getText().toString()));
                else if (Integer.parseInt(idEt.getText().toString()) < 10000)
                    course = database.querySingleCourse(idEt.getText().toString(), "COURSE_NAME");
                else course = database.querySingleCourse(idEt.getText().toString(), "COURSE_NUM");
                database_myCourse.insert(account, String.valueOf(course.NO));
                Toast.makeText(getApplicationContext(),"成功添加课程！",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}
