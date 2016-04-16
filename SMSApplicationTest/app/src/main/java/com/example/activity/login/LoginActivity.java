package com.example.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.database.ob.User;
import com.database.tool.DBAdapter_User;
import com.example.activity.courseList.CourseListActivity;
import com.example.activity.search.SearchActivity;
import com.test.TestAndroidActivity;

/**
 * Created by 彪 on 2016/3/29.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    //账号信息
    public EditText inputaccountEt;

    private EditText inputaccountsecret;

    private Button logincommitBtn;

    private Button registerBtn;

    private Button searchBtn;

    private Button testListBtn;

    private DBAdapter_User database;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        //getApplicationContext
        database = new DBAdapter_User(getBaseContext());
        database.open();
    }

    @Override
    public void onClick(View v) {
        String  account = inputaccountEt.getText().toString();
        String password = inputaccountsecret.getText().toString();
        switch (v.getId()) {
            case R.id.login_submit:
                Intent intent;
                if (account.equals("") || password.equals("")) {
                    Toast.makeText(getApplicationContext(), "用户名密码不能为空！", Toast.LENGTH_SHORT).show();
                    break;
                }
                //Toast.makeText(getApplicationContext(), account, Toast.LENGTH_SHORT).show();//测试输出账号
                /**数据库查询出现问题*/
                User user = database.queryUser(account);
                //Toast.makeText(getApplicationContext(),user.getAccountPassword(), Toast.LENGTH_SHORT).show();//测试输出取得的密码
                if(user==null){
                    Toast.makeText(getApplicationContext(), "账号不存在！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.equals(user.PASSWORD)) {
                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                } else {
                    Toast.makeText(getApplicationContext(), "用户密码错误！", Toast.LENGTH_SHORT).show();
                    break;
                }
            case R.id.login_register:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
//                if ((account != null && password != null && (judgeAccount(account) && judgePass(password)))||account.equals("admin")) {
//                    database.init(account, password);
//                    intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                    startActivity(intent);
//                } else
//                    Toast.makeText(getApplicationContext(), "用户名密码不能为空！", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.button_search:
                intent = new Intent(LoginActivity.this, CourseListActivity.class);
                startActivity(intent);
                break;
            case R.id.test_list_view:
                intent = new Intent(LoginActivity.this, TestAndroidActivity.class);
                startActivity(intent);
                break;
        }
    }

    public boolean judgePass(String password) {
        String matchPass = "[a-zA-Z][a-zA-Z0-9\\.,-_:;'\"/<>()!~`|]*";
        if ((password.length() <= 16) && password.matches(matchPass)) {
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean judgeAccount(String account) {
        if ((isMatchLength(account, 11) && isPhoneNum(account)) || isMatchEmail(account) || isAccount(account))
            return true;
        else {
            Toast.makeText(getApplicationContext(), "账号信息错误", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static boolean isMatchLength(String phoneNum, int length) {
        if (phoneNum.length() == length) {
            return true;
        } else
            return false;
    }

    public static boolean isPhoneNum(String phoneNum) {
        String tel = "[1][358]\\d{9}";
        if (phoneNum.matches(tel)) {
            return true;
        } else
            return false;
    }

    public static boolean isAccount(String account) {
        String tel = "[a-zA-Z][a-zA-Z0-9]*";
        if (account.matches(tel)) {
            return true;
        } else
            return false;
    }

    public static boolean isMatchEmail(String email) {
        String emailNo = "[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)";
        if (email.matches(emailNo)) {
            return true;
        } else
            return false;
    }


    public void init() {
        inputaccountEt = (EditText) findViewById(R.id.login_account);
        inputaccountsecret = (EditText) findViewById(R.id.login_secret);
        logincommitBtn = (Button) findViewById(R.id.login_submit);
        registerBtn = (Button) findViewById(R.id.login_register);
        searchBtn = (Button) findViewById(R.id.button_search);
        testListBtn = (Button) findViewById(R.id.test_list_view);
        //对Button设置监听事件
        testListBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        logincommitBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }
}
