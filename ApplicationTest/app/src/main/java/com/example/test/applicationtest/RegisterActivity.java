package com.example.test.applicationtest;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by 彪 on 2016/3/29.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {

    //账号信息
    private EditText inputaccountEt;

    private EditText inputaccountsecret;

    private Button logincommitBtn;

    private Button registerBtn;

    @Override
    public void onClick(View v) {
        String account = inputaccountEt.getText().toString();
        String password = inputaccountsecret.getText().toString();
        switch (v.getId()) {
            case R.id.login_submit:
                if (!(judgeAccount(account)&&judgePass(password))) {
                    return;
                }
                Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_register:
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    public boolean judgePass(String password){
        String matchPass = "[a-zA-Z][a-zA-Z0-9\\.,-_:;'\"/<>()!~`|]*";
        if((password.length()<=16)&&password.matches(matchPass)){
            return true;
        }else {
            Toast.makeText(getApplicationContext(),"密码错误",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean judgeAccount(String account) {
        if ((isMatchLength(account, 11) && isPhoneNum(account)) || isMatchEmail(account))
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

    public static boolean isMatchEmail(String email) {
        String emailNo = "[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)";
        if (email.matches(emailNo)) {
            return true;
        } else
            return false;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    public void init() {
        inputaccountEt = (EditText) findViewById(R.id.login_account);
        inputaccountsecret = (EditText) findViewById(R.id.login_secret);
        logincommitBtn = (Button) findViewById(R.id.login_submit);
        registerBtn = (Button) findViewById(R.id.login_register);
        //对Button设置监听事件
        logincommitBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }
}
