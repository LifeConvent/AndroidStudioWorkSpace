package com.example.activity.courseList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.database.ob.Course;
import com.database.tool.DBAdapter_Course;
import com.example.activity.login.R;
import com.example.activity.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 彪 on 2016/4/9.
 */
public class CourseListActivity extends Activity implements View.OnClickListener {

    private EditText searchCourseBtn;
    private ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        searchCourseBtn = (EditText) findViewById(R.id.list_search_course);
        listView = (ListView) findViewById(R.id.list_course_LV);
        List<String> list = new ArrayList<String>();
        DBAdapter_Course database = new DBAdapter_Course(getApplicationContext());
        database.open();
        //database.init();
        //id字段从1开始查询
        for (int i = 1; i < 100; i++) {
            Course course = database.querySingleCourse(i);
            list.add("课程号:" + course.NO + "\n" + "课程名：" + course.NAME + "\n" + "讲课人:" + course.TEACHER);

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String msg = "位置：" + String.valueOf(arg2) + ",ID:" + String.valueOf(arg3);
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CourseListActivity.this,CourseDetailActivity.class);
                startActivity(intent);
            }
        });
        searchCourseBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.list_search_course:
                Intent intent = new Intent(CourseListActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
        }
    }


}
