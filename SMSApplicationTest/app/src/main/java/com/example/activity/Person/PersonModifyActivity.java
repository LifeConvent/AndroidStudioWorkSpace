package com.example.activity.Person;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.database.ob.Person;
import com.database.tool.DBAdapter_Person;
import com.example.activity.login.R;
import com.example.activity.menu.MenuActivity;

/**
 * Created by 彪 on 2016/4/20.
 */
public class PersonModifyActivity extends Activity implements View.OnClickListener {

    private String account;
    private DBAdapter_Person database_person;

    private Button modifySumbit;
    private TextView show_account;
    private EditText et1;
    private EditText et2;
    private EditText et3;
    private EditText et4;
    private EditText et5;
    private EditText et6;
    private EditText et7;
    private EditText et8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_modify);
        Intent intent = getIntent();
        account = intent.getStringExtra("name");
        init();
        show_account.setText("账号：" + account);

        /**向编辑框中输出已有数据*/
        Person person = database_person.querySinglePerson(account, "PERSON_ACCOUNT");
        if (person.getName()!=null)
            et1.setText(person.getName());
        if (person.getSex()!=null)
            et2.setText(person.getSex());
        if (person.getAge()!=0)
            et3.setText(String.valueOf(person.getAge()));
        if (person.getDepartment()!=null)
            et4.setText(person.getDepartment());
        if (person.getPosition()!=null)
            et5.setText(person.getPosition());
        if (person.getInitial()!=null)
            et6.setText(person.getInitial());
        if (person.getPhone()!=null)
            et7.setText(person.getPhone());
        if (person.getEmail()!=null)
            et8.setText(person.getEmail());

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_button:
                String name = et1.getText().toString();
                String sex = et2.getText().toString();
                int age = Integer.parseInt(et3.getText().toString());
                String dep = et4.getText().toString();
                String pos = et5.getText().toString();
                String ini = et6.getText().toString();
                String tel = et7.getText().toString();
                String email = et8.getText().toString();

                /**  输完所有信息才能修改  */
                if (!(name.equals("") || sex.equals("") || String.valueOf(age).equals("") || dep.equals("") || pos.equals("") || ini.equals("") || tel.equals("") || email.equals(""))) {
                    Person person = new Person(account, name, sex, age, dep, pos, ini, tel, email);
                    database_person.updatePerson(person);
                    person = database_person.querySinglePerson(account, "PERSON_ACCOUNT");
                    Toast.makeText(getApplicationContext(), "修改成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PersonModifyActivity.this, MenuActivity.class);
                    intent.putExtra("name", account);
                    startActivity(intent);
                    finish();
                    break;
                } else {
                    Toast.makeText(getApplicationContext(), "请输入信息后再确认修改！", Toast.LENGTH_SHORT).show();
                    return;
                }
        }
    }

    private void init() {
        database_person = new DBAdapter_Person(getApplicationContext());
        database_person.open();
        show_account = (TextView) findViewById(R.id.account);
        modifySumbit = (Button) findViewById(R.id.submit_button);
        modifySumbit.setOnClickListener(this);
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        et4 = (EditText) findViewById(R.id.et4);
        et5 = (EditText) findViewById(R.id.et5);
        et6 = (EditText) findViewById(R.id.et6);
        et7 = (EditText) findViewById(R.id.et7);
        et8 = (EditText) findViewById(R.id.et8);
        /** 点击事件之前获取值，数字可能会出现错误  */
    }
}