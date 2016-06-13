package com.connectionpractice.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 彪 on 2016/6/2.
 */
public class HomeActivity extends BaseTabActivity implements BaseApiTask.OnTaskCompleted {

    private String account;
    private Dialog mMskDialog = null;

    private EditText mEtOriginPass;
    private EditText mEtPass1;
    private EditText mEtPass2;

    private Button mBnSubmit;
    private Button mBnExit;

    private RelativeLayout mRlMyInfoHead;
    private RelativeLayout mRlMyCase;
    private RelativeLayout mRlMyCandy;
    private RelativeLayout mRlResetPass;
    private RelativeLayout mRlMySysInform;

    private TextView mTvTitleName;
    private TextView mTvTitlePhone;
    private TextView mTvTitleEmail;

    private ActvityClickListener mAclLocalclicklistener;

    class ActvityClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bn_edit_submit:
                    String oldpass = mEtOriginPass.getText().toString();
                    String newpass = mEtPass2.getText().toString();
                    oldpass = Md5Tool.getMD5(oldpass);
                    newpass = Md5Tool.getMD5(newpass);
                    RP001ApiTask myTask = new RP001ApiTask(HomeActivity.this, BasicConfig.API_ID_RP001);
                    List<NameValuePair> nameValuePair = myTask.getNameValuePair();
                    nameValuePair.add(new BasicNameValuePair("name", account));
                    nameValuePair.add(new BasicNameValuePair("oldpass", oldpass));
                    nameValuePair.add(new BasicNameValuePair("newpass", newpass));
                    myTask.execute();
                    getLoadingDialog().show();
                    break;
                case R.id.s010801_in_my_info:
                    showMessageByString(null, "个人信息");
                    /**
                     *
                     * 跳转个人信息界面，并进行post获取
                     *
                     * */
                    Intent intent = new Intent();
                    intent.setClass(HomeActivity.this, UserInfoEdit.class);
                    //intent.setClass(HomeActivity.this, MyDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                    );
                    break;
                case R.id.s010801_in_sys_inform:
                    showMessageByString(null, "系统通知");
                    /**
                     *
                     * 跳转系统通知界面，并进行post获取
                     *
                     * */
                    break;
                case R.id.s010801_in_my_case:
                    showMessageByString(null, "我的病历");
                    /**
                     *
                     * 跳转我的病历界面，并进行post获取
                     *
                     * */
                    break;
                case R.id.s010801_in_my_candy:
                    showMessageByString(null, "我的糖豆");
                    /**
                     *
                     * 跳转我的糖豆界面，并进行post获取
                     *
                     * */
                    break;
                case R.id.s010801_in_reset_pass:
                    /**
                     *
                     * 跳转修改密码界面，并进行post获取
                     *
                     * */
                    mMskDialog = new Dialog(HomeActivity.this);
                    mMskDialog.setCanceledOnTouchOutside(false);
                    mMskDialog.setContentView(R.layout.s010801_reset_password);
                    initMakViews(mMskDialog);
                    mMskDialog.show();
                    break;
                case R.id.s010801_in_new_exit:
                    showMessageByString(null,"daozhele");
                    new AlertDialog.Builder(HomeActivity.this).setTitle(getResources().getString(R.string.common_exit_title))
                            .setIcon(R.drawable.ic_launcher)
                            .setPositiveButton(getResources().getString(R.string.common_exit_positive), new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 点击“确认”后的操作
                                    Intent intent = new Intent();
                                    intent.setClass(HomeActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(
                                            android.R.anim.fade_in,
                                            android.R.anim.fade_out
                                    );
                                    finish();
                                }
                            })
                            .setNegativeButton(getResources().getString(R.string.common_exit_negative), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 点击“返回”后的操作,这里不设置没有任何操作
                                }
                            }).show();
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_info);
//        setRunningActivity(ACTIVITY_ACTA);
        super.onCreate(savedInstanceState);
        showMessageByString(null, "wode");
        mAclLocalclicklistener = new ActvityClickListener();

        initViews();
    }

    private void initViews() {
        account = UserInfo.getInstance().getmStrUserName();
        //setText需要在初始化之后


        mRlMyInfoHead = (RelativeLayout) findViewById(R.id.s010801_in_my_info);
        mRlMyInfoHead.setOnClickListener(mAclLocalclicklistener);
        mRlMySysInform = (RelativeLayout) findViewById(R.id.s010801_in_sys_inform);
        mRlMySysInform.setOnClickListener(mAclLocalclicklistener);
        mRlMyCase = (RelativeLayout) findViewById(R.id.s010801_in_my_case);
        mRlMyCase.setOnClickListener(mAclLocalclicklistener);
        mRlMyCandy = (RelativeLayout) findViewById(R.id.s010801_in_my_candy);
        mRlMyCandy.setOnClickListener(mAclLocalclicklistener);
        mRlResetPass = (RelativeLayout) findViewById(R.id.s010801_in_reset_pass);
        mRlResetPass.setOnClickListener(mAclLocalclicklistener);

        mTvTitleName = (TextView) findViewById(R.id.s010801_in_tv_username);
        mTvTitlePhone = (TextView) findViewById(R.id.s010801_in_tv_phone);
        mTvTitleEmail = (TextView) findViewById(R.id.s010801_in_tv_email);

        mTvTitleName.setText(account);

        mBnExit = (Button)findViewById(R.id.s010801_in_new_exit);
        mBnExit.setOnClickListener(mAclLocalclicklistener);
    }

    private void initMakViews(Dialog v) {
        mEtOriginPass = (EditText) v.findViewById(R.id.edit_originpass);
        mBnSubmit = (Button) v.findViewById(R.id.bn_edit_submit);

        mEtPass1 = (EditText) v.findViewById(R.id.edit_pass1);
        mEtPass2 = (EditText) v.findViewById(R.id.edit_pass2);

        mBnSubmit.setOnClickListener(mAclLocalclicklistener);

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

    public boolean judgePassword() {
        if (!mEtPass1.getText().toString().equals(mEtPass2.getText().toString())) {
            showMessageByResourceId(null, R.string.s010302_register_err_password_is_not_same);
            return false;
        } else return true;
    }

    private boolean ifRePasswordNull() {
        if (mEtPass2.getText().toString() != null && !(mEtPass2.getText().toString().equals(""))) {
            return true;
        } else {
            showMessageByResourceId(null, R.string.s010302_register_err_repassword_is_null);
            return false;
        }
    }

    private boolean isPassword() {
        String pass = mEtPass1.getText().toString();
        if (!(pass.equals("")) && pass != null) {
            if (pass.length() < 10) {
                showMessageByResourceId(null, R.string.s010302_register_err_password_is_not_right);
                return false;
            }
        } else {
            showMessageByResourceId(null, R.string.s010302_register_err_password_is_null);
            return false;
        }
        return true;
    }

    private boolean adaptPassComplex() {
        int ls = 0;
        String password = mEtPass1.getText().toString();
        Pattern p = Pattern.compile("[a-z]+?");
        Matcher m = p.matcher(password);
        if (m.find()) {
            ls++;
        }
        p = Pattern.compile("[A-Z]+?");
        m = p.matcher(password);
        if (m.find()) {
            ls++;
        }
        p = Pattern.compile("[0-9]+?");
        m = p.matcher(password);
        if (m.find()) {
            ls++;
        }
        if (ls < 3) {
            showMessageByResourceId(null, R.string.s010302_register_err_repassword_form);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onTaskFinish(ApiStatus api_status, int id, JSONObject response) {
        switch (id) {
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

    protected void onResume() {
        super.onResume();
    }
}