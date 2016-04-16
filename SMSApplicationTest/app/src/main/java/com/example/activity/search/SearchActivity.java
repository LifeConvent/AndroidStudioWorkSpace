package com.example.activity.search;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.database.ob.Course;
import com.database.tool.DBAdapter_Course;
import com.example.activity.login.R;

public class SearchActivity extends Activity implements View.OnClickListener {

    //id输入框
    public static EditText idEt;
    //文本显示框
    public static TextView hintTv;
    //查询确认按钮
    private Button closeBaseBtn;

    private Button searchBtn;

    public DBAdapter_Course database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        idEt = (EditText) findViewById(R.id.id_get);
        hintTv = (TextView) findViewById(R.id.hint_view);
        searchBtn = (Button) findViewById(R.id.search_commit);
        closeBaseBtn = (Button) findViewById(R.id.close_database_btn);
        searchBtn.setOnClickListener(this);
        idEt.setOnClickListener(this);
        hintTv.setOnClickListener(this);
        database = new DBAdapter_Course(getBaseContext());
        database.open();
        database.init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_commit:
                Course[] course = database.queryCourse();
                Toast.makeText(getApplicationContext(),
                        idEt.getText().toString(), Toast.LENGTH_LONG).show();
                if (course != null)
                    for (int i = 0; i < course.length; i++)
                        hintTv.setText(course[i].toString());
                else
                    hintTv.setText("查询内容不存在！");
                //database.close();
                break;
            case R.id.close_database_btn:
                database.close();
                Toast.makeText(getApplicationContext(),
                        "数据库已关闭！", Toast.LENGTH_LONG).show();
        }
    }
}
