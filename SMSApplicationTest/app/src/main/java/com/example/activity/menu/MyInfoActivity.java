package com.example.activity.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.database.ob.Person;
import com.database.ob.PersonTemp;
import com.database.other.PersonAdapter;
import com.database.tool.DBAdapter_Person;
import com.example.activity.Person.PersonModifyActivity;
import com.example.activity.courseList.CourseDetailActivity;
import com.example.activity.login.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 彪 on 2016/4/18.
 */
public class MyInfoActivity extends Activity implements View.OnClickListener {

    DBAdapter_Person database_person;
    private String account;

    private ListView my_info_list;
    private Button re_button;

    private List<PersonTemp> personTempList = new ArrayList<PersonTemp>();
    private String[] content = new String[10];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        init();
        initPersonTemp();
        PersonAdapter adapter = new PersonAdapter(MyInfoActivity.this, R.layout.person_item, personTempList);
        my_info_list.setAdapter(adapter);
    }

    private void init() {
        database_person = new DBAdapter_Person(getApplicationContext());
        database_person.open();
        Intent intent = getIntent();
        account = intent.getStringExtra("name");
        Person person = database_person.querySinglePerson(account, "PERSON_ACCOUNT");
        if (person == null)
            database_person.init(account);
        my_info_list = (ListView) findViewById(R.id.my_info_list);
        re_button = (Button) findViewById(R.id.re_button);
        re_button.setOnClickListener(this);
        person = database_person.querySinglePerson(account, "PERSON_ACCOUNT");
        if (person == null)
            Toast.makeText(getApplicationContext(), "系统错误！稍后再试！", Toast.LENGTH_SHORT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.re_button:
                //Toast.makeText(getApplicationContext(), "系统错误！", Toast.LENGTH_SHORT);
                Intent intent = new Intent(MyInfoActivity.this, PersonModifyActivity.class);
                intent.putExtra("name", account);
                startActivity(intent);
                finish();
                break;
        }
    }

//    public static final String TABLE_ID = "ID";//自增编号
//    public static final String PERSON_ACCOUNT = "ACCOUNT";
//    public static final String PERSON_NAME = "NAME";
//    public static final String PERSON_SEX = "SEX";
//    public static final String PERSON_AGE = "AGE";
//    public static final String PERSON_DEPARTMENT = "DEPARTMENT";
//    public static final String PERSON_POSITION = "POSITION";
//    public static final String PERSON_INITIAL = "INITIAL";
//    public static final String PERSON_PHONE = "PHONE";
//    public static final String PERSON_EMAIL = "EMAIL";

    public void initPersonTemp() {
        Person person = database_person.querySinglePerson(account, "PERSON_ACCOUNT");
        PersonTemp[] personTemp = new PersonTemp[11];
        personTemp[1] = new PersonTemp("账号：" + account);
        personTempList.add(personTemp[1]);
        personTemp[2] = new PersonTemp("姓名：" + person.getName());
        personTempList.add(personTemp[2]);
        personTemp[3] = new PersonTemp("性别：" + person.getSex());
        personTempList.add(personTemp[3]);
        personTemp[4] = new PersonTemp("年龄：" + person.getAge());
        personTempList.add(personTemp[4]);
        personTemp[5] = new PersonTemp("部门：" + person.getDepartment());
        personTempList.add(personTemp[5]);
        personTemp[6] = new PersonTemp("职位：" + person.getPosition());
        personTempList.add(personTemp[6]);
        personTemp[7] = new PersonTemp("工号：" + person.getInitial());
        personTempList.add(personTemp[7]);
        personTemp[8] = new PersonTemp("电话：" + person.getPhone());
        personTempList.add(personTemp[8]);
        personTemp[9] = new PersonTemp("邮箱：" + person.getEmail());
        personTempList.add(personTemp[9]);
        personTemp[10] = new PersonTemp("");
        personTempList.add(personTemp[10]);
        //Toast.makeText(getApplicationContext(), person.toString(), Toast.LENGTH_SHORT).show();
    }
}
