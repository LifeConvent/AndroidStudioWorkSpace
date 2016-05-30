package com.connectionpractice.base;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.connectionpractice.R;
import com.connectionpractice.api.ApiStatus;
import com.connectionpractice.api.BaseApiTask;
import com.connectionpractice.api.RP001ApiTask;
import com.connectionpractice.config.BasicConfig;
import com.connectionpractice.tool.Md5Tool;
import com.connectionpractice.tool.UserInfo;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 彪 on 2016/4/18.
 */
public class MenuActivity extends BaseActivity implements View.OnClickListener,BaseApiTask.OnTaskCompleted {

    private List<Menu> menuList = new ArrayList<Menu>();
    private ListView menuLv;
    private String account;
    private TextView accountInfo;
    private Dialog mMskDialog = null;

    private EditText mEtOriginPass;
    private EditText mEtPass1;
    private EditText mEtPass2;

    private Button mBnSubmit;

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_menu);

        account = UserInfo.getInstance().getmStrUserName();

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
                        mMskDialog = new Dialog(MenuActivity.this);
                        mMskDialog.setCanceledOnTouchOutside(false);
                        mMskDialog.setContentView(R.layout.s010801_reset_password);
                        initMakViews(mMskDialog);
                        mMskDialog.show();
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                }
            }
        });
    }

    private void initMakViews(Dialog v) {
        mEtOriginPass = (EditText) v.findViewById(R.id.edit_originpass);
        mBnSubmit = (Button) v.findViewById(R.id.bn_edit_submit);
        mEtPass1 = (EditText) v.findViewById(R.id.edit_pass1);
        mEtPass2 = (EditText) v.findViewById(R.id.edit_pass2);
        mBnSubmit.setOnClickListener(this);
        mEtPass2.setOnFocusChangeListener(new View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    if (isPassword()) {
                        //adaptPassComplex();
                    }
                } else {
                    // 此处为失去焦点时的处理内容
                }
            }
        });
    }

    private boolean isPassword() {
        String pass = mEtPass1.getText().toString();
        if (!(pass.equals("")) && pass != null) {
            if (pass.length() <= 4) {
                showMessageByResourceId(null, R.string.register_err_password_length_is_not_right);
                return false;
            } else return true;
        } else {
            showMessageByResourceId(null, R.string.register_err_repassword_is_null);
            return false;
        }
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
        switch (v.getId()){
            case R.id.bn_edit_submit:
                String oldpass = mEtOriginPass.getText().toString();
                String newpass = mEtPass2.getText().toString();
                oldpass = Md5Tool.getMD5(oldpass);
                newpass = Md5Tool.getMD5(newpass);
                RP001ApiTask myTask = new RP001ApiTask(MenuActivity.this, BasicConfig.API_ID_RP001);
                List<NameValuePair> nameValuePair = myTask.getNameValuePair();
                nameValuePair.add(new BasicNameValuePair("name", account));
                nameValuePair.add(new BasicNameValuePair("oldpass", oldpass));
                nameValuePair.add(new BasicNameValuePair("newpass", newpass));
                myTask.execute();
                getLoadingDialog().show();
                break;
        }
    }

    @Override
    public void onTaskFinish(ApiStatus api_status, int id, JSONObject response) {
        switch (id){
            case BasicConfig.API_ID_RP001:
                if ((response.optString("result").equals("success"))) {
                    getLoadingDialog().dismiss();
                    mMskDialog.dismiss();
                    return;
                } else {
                    showReturnServer(response.optString("message"));
                    break;
                }
        }
    }
}
