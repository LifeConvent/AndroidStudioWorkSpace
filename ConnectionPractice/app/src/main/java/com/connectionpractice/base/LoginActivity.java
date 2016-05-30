package com.connectionpractice.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.connectionpractice.R;
import com.connectionpractice.api.ApiStatus;
import com.connectionpractice.api.BaseApiTask;
import com.connectionpractice.api.SI001ApiTask;
import com.connectionpractice.config.BasicConfig;
import com.connectionpractice.tool.Md5Tool;
import com.connectionpractice.tool.UserInfo;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by 彪 on 2016/5/28.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, BaseApiTask.OnTaskCompleted {

    private EditText mEtAccount;
    private EditText mEtPassword;
    private Button mBnLogin;
    private Button mBnRgesiter;

    private String account;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    public void init(){
        mBnLogin = (Button) findViewById(R.id.submit_bn);
        mBnRgesiter = (Button) findViewById(R.id.register_bn);
        mEtAccount = (EditText) findViewById(R.id.username_et);
        mEtPassword = (EditText) findViewById(R.id.password_et);
        mBnLogin.setOnClickListener(this);
        mBnRgesiter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_bn:
                String account = mEtAccount.getText().toString();
                String password = mEtPassword.getText().toString();
                password = Md5Tool.getMD5(password);
                SI001ApiTask myTask = new SI001ApiTask(LoginActivity.this, BasicConfig.API_ID_SI001);

                //List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
                List<NameValuePair> nameValuePair = myTask.getNameValuePair();
                nameValuePair.add(new BasicNameValuePair("name", account));
                nameValuePair.add(new BasicNameValuePair("pass", password));
                myTask.execute();
                getLoadingDialog().show();
                break;
            case R.id.register_bn:
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onTaskFinish(ApiStatus api_status, int id, JSONObject response) {
        if (response==null) {
            getLoadingDialog().dismiss();
            return;
        }
        getLoadingDialog().dismiss();
        switch (id) {
            case BasicConfig.API_ID_SI001: {
                if (response.optString("result").equals("error")) {
                    showReturnServer(response.optString("message"));
                }else {
                    account = response.optString("name");
                    password = response.optString("pass");
                    saveUserInfo(response);
                    Intent intent = new Intent(LoginActivity.this,MenuActivity.class);
                    startActivity(intent);
                }
                break;
            }
            default:
                break;
        }
    }

    private void saveUserInfo(JSONObject ob) {
        UserInfo.getInstance().setmStrUserName(account);
        UserInfo.getInstance().setmStrUserPassword(password);
    }
}
