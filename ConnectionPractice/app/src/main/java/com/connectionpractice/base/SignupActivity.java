package com.connectionpractice.base;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.connectionpractice.R;
import com.connectionpractice.api.ApiStatus;
import com.connectionpractice.api.BaseApiTask;
import com.connectionpractice.api.SU001ApiTask;
import com.connectionpractice.config.BasicConfig;
import com.connectionpractice.tool.Md5Tool;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 彪 on 2016/5/28.
 */
public class SignupActivity extends BaseActivity implements View.OnClickListener, BaseApiTask.OnTaskCompleted {

    private EditText mEtaccount;
    private EditText mEtpassword1;
    private EditText mEtpassword2;

    private Button mBnregister;

    private String account;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initView();
    }

    public void initView() {
        mEtaccount = (EditText) findViewById(R.id.singup_username_et);
        mEtpassword1 = (EditText) findViewById(R.id.password1_et);
        mEtpassword2 = (EditText) findViewById(R.id.password2_et);
        mBnregister = (Button) findViewById(R.id.signup_register_bn);

        mBnregister.setOnClickListener(this);

        mEtpassword1.setOnFocusChangeListener(new View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    isAccount(mEtaccount.getText().toString());
                    //isPassword();
                } else {
                    // 此处为失去焦点时的处理内容
                }
            }
        });
        mEtpassword2.setOnFocusChangeListener(new View.
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup_register_bn:
                account = mEtaccount.getText().toString();
                if (isAccount(account)) {
                    if (isPassword()) {
//                        if (adaptPassComplex() == false) {
//                            return;
//                        }
                        if (ifRePasswordNull() == false) {
                            return;
                        }
                        if (judgePassword() == false) {
                            return;
                        }
                        password = mEtpassword2.getText().toString();
                        password = Md5Tool.getMD5(password);
                        SU001ApiTask myTask = new SU001ApiTask(SignupActivity.this, BasicConfig.API_ID_SU001);
                        List<NameValuePair> nameValuePair = myTask.getNameValuePair();
                        nameValuePair.add(new BasicNameValuePair("name", account));
                        nameValuePair.add(new BasicNameValuePair("pass", password));
                        myTask.execute();
                        getLoadingDialog().show();
                    }
                }
                break;
        }
    }

    @Override
    public void onTaskFinish(ApiStatus api_status, int id, JSONObject response) {
        switch (id) {
            case BasicConfig.API_ID_SU001:
                if ((response.optString("result").equals("success"))) {
                    getLoadingDialog().dismiss();
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                } else {
                    showReturnServer(response.optString("message"));
                    break;
                }
        }
    }

    /******************
     * 逻辑、正则判断
     *******************/

    public boolean judgePassword() {
        if (!(mEtpassword1.getText().toString().equals(mEtpassword2.getText().toString()))) {
            showMessageByResourceId(null, R.string.register_err_password_is_not_same);
            return false;
        } else return true;
    }

    private boolean ifRePasswordNull() {
        if (mEtpassword2.getText().toString() != null && !(mEtpassword2.getText().toString().equals(""))) {
            return true;
        } else {
            showMessageByResourceId(null, R.string.register_err_repassword_is_null);
            return false;
        }
    }

    private boolean isPassword() {
        String pass = mEtpassword1.getText().toString();
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

    private boolean adaptPassComplex() {
        int ls = 0;
        String password = mEtpassword1.getText().toString();
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
            //showMessageByResourceId(null, R.string.s010302_register_err_repassword_form);
            return false;
        } else {
            return true;
        }
    }

    private boolean judgePhoneNums(String phoneNums) {
        //符合手机号码要求
        if (!phoneNums.equals("")) {
            if ((isMatchLength(phoneNums, 11))) {
                if (isMobileNo(phoneNums)) {

                    return true;
                } else {
                    //showMessageByResourceId(null, R.string.s010302_register_err_phone_1);
                    return false;
                }
            } else {
                //showMessageByResourceId(null, R.string.s010302_register_err_phone_2);
                return false;
            }
        } else {
            //showMessageByResourceId(null, R.string.s010302_register_err_phone_3);
            return false;
        }
    }

    private boolean isAccount(String account) {
        if (account.equals("")) {
            showMessageByResourceId(null, R.string.register_err_account_1);
            return false;
        }
        if (judgeAccount(account)) {
            //符合1-20位要求
            if (account.length() <= 20 && account.length() >= 1) {
                return true;
            } else {
                showMessageByResourceId(null, R.string.register_err_password_length_is_not_right);
                return false;
            }
        } else {
            showMessageByResourceId(null, R.string.register_err_account_2);
            return false;
        }
    }

    public static boolean judgeAccount(String account) {
        String telRegx = "[A-Za-z0-9_]*";
        if (TextUtils.isEmpty(account))
            return false;
        else
            return account.matches(telRegx);
    }

    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }

    public static boolean isMobileNo(String mobileNums) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        // "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，
        // "\\d{9}"代表后面是可以是0～9的数字，有9位
        String telRegx = "[1][358]\\d{9}";
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegx);
    }

    /************************/
}
