package com.example.activity.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.database.ob.Person;
import com.database.tool.DBAdapter_Person;
import com.example.activity.login.R;

/**
 * Created by 彪 on 2016/4/18.
 */
public class MyInfoActivity extends Activity implements View.OnClickListener {

    DBAdapter_Person database_person;
    private String account;

    private TextView myInfo_show;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        init();
    }

    private void init(){
        database_person = new DBAdapter_Person(getApplicationContext());
        database_person.open();
        Intent intent = getIntent();
        account = intent.getStringExtra("name");
        database_person.init(account);
        myInfo_show = (TextView) findViewById(R.id.myInfo_show);
        myInfo_show.setText(account);
        Person person = database_person.querySinglePerson(account, "PERSON_ACCOUNT");
        if(person!=null)
            //跳转个人信息查看界面
            myInfo_show.setText(person.toString());
        else
            //跳转个人信息输入界面
            myInfo_show.setText("系统错误！");
    }

    @Override
    public void onClick(View v) {

    }
}
