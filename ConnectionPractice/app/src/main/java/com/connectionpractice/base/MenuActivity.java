package com.connectionpractice.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.connectionpractice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 彪 on 2016/4/18.
 */
public class MenuActivity extends BaseActivity implements View.OnClickListener {

    private List<Menu> menuList = new ArrayList<Menu>();
    private ListView menuLv;
    private String account;
    private TextView accountInfo;

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_menu);
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
                switch (1 + Integer.parseInt(String.valueOf(arg2))) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:

                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                }
            }
        });
    }

    private void initMenu() {
        accountInfo = (TextView) findViewById(R.id.account_name);
        Menu myCourse = new Menu("我的课程", R.drawable.mycourse);
        menuList.add(myCourse);
        Menu courseSearch = new Menu("课程查询", R.drawable.coursesearch);
        menuList.add(courseSearch);
        Menu myInfo = new Menu("个人信息", R.drawable.personalinfo);
        menuList.add(myInfo);
        Menu rePass = new Menu("修改密码", R.drawable.resetpassword);
        menuList.add(rePass);
        Menu teacherInfo = new Menu("管理教师", R.drawable.personalinfo);
        menuList.add(teacherInfo);
        Menu reLog = new Menu("注销登陆", R.drawable.resetlogin);
        menuList.add(reLog);
    }

    @Override
    public void onClick(View v) {

    }

}
