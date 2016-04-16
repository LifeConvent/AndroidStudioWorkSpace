package com.example.activity.courseList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.activity.login.R;

/**
 * Created by 彪 on 2016/4/16.
 */
public class CourseDetailActivity extends Activity implements View.OnClickListener{
    @Override
    public void onClick(View v) {

    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
    }
}
