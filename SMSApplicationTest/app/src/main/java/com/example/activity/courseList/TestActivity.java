package com.example.activity.courseList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.database.ob.Course;
import com.database.tool.DBAdapter_Course;
import com.example.activity.login.R;

/**
 * Created by 彪 on 2016/4/23.
 */
public class TestActivity extends Activity implements View.OnClickListener {

    private TextView test;
    private DBAdapter_Course database_course;
    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        test = (TextView) findViewById(R.id.test);
        database_course = new DBAdapter_Course(getApplicationContext());
        database_course.open();
        database_course.init();
        Intent intent = getIntent();
        account = intent.getStringExtra("name");
        Course course = database_course.querySingleCourse("20160111","COURSE_ID");
        if(course!=null) {
            test.setText(course.NAME+course.CONTENT);
        }
        Toast.makeText(getApplicationContext(),course.NAME+course.CONTENT,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

    }
}
