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
import com.test.TestAndroidActivity;

/**
 * Created by 彪 on 2016/4/16.
 */
public class RegisterAccountActivity extends Activity implements View.OnClickListener {

    private String account;

    /**手机号注册后手机号的保留问题*/
    private String phone;

    public EditText inputaccountEt;

    private EditText inputaccountsecret;

    private EditText inputaccountsecret2;

    private Button logincommitBtn;

    private DBAdapter_User database;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_2);
        init();
        //getApplicationContext
        database = new DBAdapter_User(getBaseContext());
        database.open();
    }

    @Override
    public void onClick(View v) {
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        account = inputaccountEt.getText().toString();
        String password = inputaccountsecret.getText().toString();
        String password2 = inputaccountsecret2.getText().toString();
        switch (v.getId()) {
            case R.id.registerin_request_btn:
                if (account.equals("") || password .equals("") || password2 .equals("")) {
                    Toast.makeText(getApplicationContext(), "用户名密码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Toast.makeText(getApplicationContext(), account, Toast.LENGTH_SHORT).show();//测试输出账号
                User user = database.queryUser(account);
                //Toast.makeText(getApplicationContext(),user.getAccountPassword(), Toast.LENGTH_SHORT).show();//测试输出取得的密码
                //Intent intent;
                if (user!=null) {
                    Toast.makeText(getApplicationContext(), "用户名已存在！", Toast.LENGTH_SHORT).show();
                    break;
                } else if (!(password2.equals(password))) {
                    Toast.makeText(getApplicationContext(), "两次输入的密码不一致！", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    if (judgeAccount(account) && judgePass(password)) {
                        user=new User(account,password);
                        database.put(user);
                        Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT).show();
                        intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                        //关闭用户注册界面
                        finish();
                        break;
                    }
                }
        }
    }

    public boolean judgePass(String password) {
        String matchPass = "[a-zA-Z0-9\\.,-_:;'\"/<>()!~`|]*";
        if ((password.length() <= 16) && password.matches(matchPass)) {
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "密码格式错误", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean judgeAccount(String account) {
        if ((isMatchLength(account, 11) && isPhoneNum(account)) || isMatchEmail(account) || isAccount(account))
            return true;
        else {
            Toast.makeText(getApplicationContext(), "账号格式错误", Toast.LENGTH_SHORT).show();
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
        String tel = "[a-zA-Z0-9]*";
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
        inputaccountEt = (EditText) findViewById(R.id.login_account_register);
        inputaccountsecret = (EditText) findViewById(R.id.login_secret_register);
        inputaccountsecret2 = (EditText) findViewById(R.id.login_secret_register_2);
        logincommitBtn = (Button) findViewById(R.id.registerin_request_btn);
        //对Button设置监听事件
        logincommitBtn.setOnClickListener(this);
    }

}
