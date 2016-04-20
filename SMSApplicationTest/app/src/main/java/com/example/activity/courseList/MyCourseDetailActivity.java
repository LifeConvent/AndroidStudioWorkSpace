package com.example.activity.courseList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.database.ob.Course;
import com.database.tool.DBAdapter_Course;
import com.database.tool.DBAdapter_PersonCourse;
import com.example.activity.login.R;
import com.example.activity.menu.MenuActivity;

/**
 * Created by 彪 on 2016/4/19.
 */
public class MyCourseDetailActivity extends Activity implements View.OnClickListener {

    private DBAdapter_Course database;
    private DBAdapter_PersonCourse database_mycourse;

    private String account;

    private String numCourse;

    private ListView detaillistView;

    private Button cancelBtn;


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cacel_button:
                database_mycourse.deleteOneData(numCourse);
                Toast.makeText(getApplicationContext(),"取消成功！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MenuActivity.class);
                intent.putExtra("name",account);
                startActivity(intent);
                /**
                 *
                 * 再次关闭取消之前打开的我的课程界面，防止重复
                 *
                 * */
                finish();
                break;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycourse_detail);
        Intent intent = getIntent();
        numCourse = intent.getStringExtra("num");
        account = intent.getStringExtra("name");
        database = new DBAdapter_Course(getBaseContext());
        database.open();
        database_mycourse = new DBAdapter_PersonCourse(getApplicationContext());
        database_mycourse.open(account);
        //Toast.makeText(getApplicationContext(),"MyCourseD！"+account,Toast.LENGTH_SHORT).show();
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
        detaillistView = (ListView) findViewById(R.id.course_detail_list);
        Course course = database.querySingleCourse(numCourse,"COURSE_ID");
        String[] data = {"课程号：      " + String.valueOf(course.NO), "课程名称：       " + course.NAME, "开始时间：      " + course.START_TIME, "结束时间：      " + course.END_TIME, "总耗时：      " + String.valueOf(course.TAKE_TIME), "讲师：        " + course.TEACHER, "课程大致内容:" + course.CONTENT, "课程须知:" + course.NEEDS + "\n\n\n"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MyCourseDetailActivity.this, android.R.layout.simple_list_item_1, data);
        detaillistView.setAdapter(adapter);
        cancelBtn = (Button) findViewById(R.id.cacel_button);
        cancelBtn.setOnClickListener(this);
    }
}

