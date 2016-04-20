package com.example.activity.menu;

import com.database.ob.Menu;
import com.database.other.MenuAdapter;
import com.database.tool.DBAdapter_Course;
import com.database.tool.DBAdapter_PersonCourse;
import com.example.activity.courseList.CourseDetailActivity;
import com.example.activity.courseList.CourseListActivity;
import com.example.activity.login.LoginActivity;
import com.example.activity.login.MainActivity;
import com.example.activity.login.R;
import com.example.activity.search.SearchActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 彪 on 2016/4/18.
 */
public class MenuActivity extends Activity implements View.OnClickListener {

    private List<Menu> menuList = new ArrayList<Menu>();
    private ListView menuLv;
    private String account;
    private TextView accountInfo;
    private DBAdapter_PersonCourse database_myCourse;

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_menu);
        DBAdapter_Course database = new DBAdapter_Course(getBaseContext());
        database.open();
        database.init();
        Intent intent;
        intent = getIntent();
        account = intent.getStringExtra("name");
        //init要在获取账户名之后执行
        initMenu();
        //setText需要在初始化之后
        accountInfo.setText(account);


        MenuAdapter adapter = new MenuAdapter(MenuActivity.this, R.layout.muen_item, menuList);
        menuLv = (ListView) findViewById(R.id.my_listView);
        menuLv.setAdapter(adapter);

        menuLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                String msg = "位置：" + String.valueOf(arg2) + ",ID:" + String.valueOf(arg3);
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                switch (1 + Integer.parseInt(String.valueOf(arg2))) {
                    case 1:
                        Intent intent = new Intent(MenuActivity.this, MyCourseActivity.class);
                        intent.putExtra("name", account);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(MenuActivity.this, CourseListActivity.class);
                        intent.putExtra("name", account);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(MenuActivity.this, MyInfoActivity.class);
                        intent.putExtra("name", account);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(MenuActivity.this, RePassActivity.class);
                        intent.putExtra("name",account);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(MenuActivity.this, LoginActivity.class);
                        intent.putExtra("name", account);
                        startActivity(intent);
                        finish();
                        break;
                }
            }
        });
    }

    private void initMenu() {
        accountInfo = (TextView) findViewById(R.id.account_name);
        Menu myCourse = new Menu("我的课程", R.mipmap.mycourse);
        menuList.add(myCourse);
        Menu courseSearch = new Menu("课程查询", R.mipmap.coursesearch);
        menuList.add(courseSearch);
        Menu myInfo = new Menu("个人信息", R.mipmap.personalinfo);
        menuList.add(myInfo);
        Menu rePass = new Menu("修改密码", R.mipmap.resetpassword);
        menuList.add(rePass);
        Menu reLog = new Menu("注销登陆", R.mipmap.resetlogin);
        menuList.add(reLog);
        database_myCourse = new DBAdapter_PersonCourse(getBaseContext());
//        //每次重新启动之前删除所有数据
//        database_myCourse.deleteAllData();
        database_myCourse.open(account);

        //以何身份打开数据库
        //Toast.makeText(getApplicationContext(),"Menu打开数据库"+account,Toast.LENGTH_SHORT).show();

        //database_myCourse.init();
    }

    @Override
    public void onClick(View v) {

    }

}
