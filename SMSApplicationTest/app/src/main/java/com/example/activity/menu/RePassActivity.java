package com.example.activity.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.database.ob.User;
import com.database.tool.DBAdapter_User;
import com.example.activity.login.LoginActivity;
import com.example.activity.login.R;

/**
 * Created by 彪 on 2016/4/18.
 */
public class RePassActivity extends Activity implements View.OnClickListener {

    private EditText originPassEt;
    private EditText resetPassEt;
    private EditText resetPassEt2;
    private Button submitPassSetBt;

    private DBAdapter_User database;

    private String account;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        init();
        database = new DBAdapter_User(getBaseContext());
        database.open();
        Intent intent = getIntent();
        account = intent.getStringExtra("name");
    }

    public void init() {
        originPassEt = (EditText) findViewById(R.id.origin_password);
        resetPassEt = (EditText) findViewById(R.id.reset_pass_1);
        resetPassEt2 = (EditText) findViewById(R.id.reset_pass_2);
        submitPassSetBt = (Button) findViewById(R.id.submit_reset);
        submitPassSetBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_reset:
                //重设密码后进行相应数据库修改操作
                String password = originPassEt.getText().toString();
                String pass1 = resetPassEt.getText().toString();
                String pass2 = resetPassEt2.getText().toString();
                User user = database.queryUser(account);
                if (user == null) {
                    Toast.makeText(getApplicationContext(), "服务器出现异常，请稍后再试！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (user.getAccountPassword().equals(password)) {
                    if (pass1.equals(pass2)){
                        if(judgePass(pass1)) {
                            user.setAccountPassword(pass1);
                            int num = database.updateUser(user);
                            if(num==0) return;
                            Toast.makeText(getApplicationContext(), "密码修改成功!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        }
                        break;
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "两次密码不一致!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "密码输入不正确!", Toast.LENGTH_SHORT).show();
                    break;
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
}
