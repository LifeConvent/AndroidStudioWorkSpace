package cs.lawrance.coursemanage.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.List;

import cs.lawrance.coursemanage.R;
import cs.lawrance.coursemanage.api.ApiStatus;
import cs.lawrance.coursemanage.api.BaseApiTask;
import cs.lawrance.coursemanage.api.LoginInApiTask;
import cs.lawrance.coursemanage.config.BasicConfig;
import cs.lawrance.coursemanage.tool.Md5Tool;
import cs.lawrance.coursemanage.tool.SystemStatusManager;
import cs.lawrance.coursemanage.tool.UserInfo;

/**
 * Created by lawrance on 2017/4/18.
 */
public class LoginActivity extends BaseActivity implements BaseApiTask.OnTaskCompleted, View.OnClickListener {

    private EditText gEtAccount;
    private EditText gEtPass;
    private Button gBtLoginin;
    private Button gBtSignup;
    private Button gBtForgetPass;
    private Button gBtRegisterToLogin;
    private Button gBtForgetToLogin;

    private RelativeLayout gRlForgetPass;
    private RelativeLayout gRlSignUp;
    private RelativeLayout gRlLoginIn;

    private String account;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_login);
        initView();
    }

    private void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemStatusManager tintManager = new SystemStatusManager(this);
            tintManager.setStatusBarTintEnabled(true);
            // 设置状态栏的颜色
            tintManager.setStatusBarTintResource(R.color.theme_color);
            getWindow().getDecorView().setFitsSystemWindows(true);
        }
    }

    public void initView() {
        gEtAccount = (EditText) findViewById(R.id.login_account_et);
        gEtPass = (EditText) findViewById(R.id.login_pass_et);
        gBtLoginin = (Button) findViewById(R.id.login_submit_bn);
        gBtSignup = (Button) findViewById(R.id.login_signup_bn);
        gBtForgetPass = (Button) findViewById(R.id.login_forgetpass_bn);
        gBtRegisterToLogin = (Button) findViewById(R.id.register_signin_bn);
        gBtForgetToLogin = (Button) findViewById(R.id.forgetpass_signin_bn);

        gEtAccount.setOnClickListener(this);
        gEtPass.setOnClickListener(this);
        gBtLoginin.setOnClickListener(this);
        gBtSignup.setOnClickListener(this);
        gBtForgetPass.setOnClickListener(this);
        gBtRegisterToLogin.setOnClickListener(this);
        gBtForgetToLogin.setOnClickListener(this);


        gRlForgetPass = (RelativeLayout) findViewById(R.id.login_forgetpass_demo);
        gRlSignUp = (RelativeLayout) findViewById(R.id.login_register_demo);
        gRlLoginIn = (RelativeLayout) findViewById(R.id.login_login_demo);
    }

    @Override
    public void onTaskFinish(ApiStatus status, int id, JSONObject response) {
        getLoadingDialog().dismiss();
        if (response == null) {
            showMessageByResourceId(R.string.login_internet_no);
            return;
        }
        switch (id) {
            case BasicConfig.API_ID_SIGNIN: {
                if (response.optString("status").equals("error")) {
                    //根据id返回错误提示
                    showReturnServerMessage(response.optString("message"));
                } else {
                    token = response.optString("token");
                    //拿到token解析用户名
                    saveUserInfo(account, token);
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                    );
                    finish();
                }
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_submit_bn:
                account = gEtAccount.getText().toString();
                String pass = gEtPass.getText().toString();
                pass = Md5Tool.getMD5(pass);
                LoginInApiTask loginTask = new LoginInApiTask(BasicConfig.API_ID_SIGNIN, LoginActivity.this);
                List<NameValuePair> loginNameValuePair = loginTask.getNameValuePair();
                loginNameValuePair.add(new BasicNameValuePair("account", account));
                loginNameValuePair.add(new BasicNameValuePair("pass", pass));
                //用户名密码组合加密方式
                loginTask.execute();
                //显示加载框
                getLoadingDialog().show();
                break;
            case R.id.login_signup_bn:
                gRlForgetPass.setVisibility(View.GONE);
                gRlLoginIn.setVisibility(View.GONE);
                gRlSignUp.setVisibility(View.VISIBLE);
                break;
            case R.id.login_forgetpass_bn:
                gRlForgetPass.setVisibility(View.VISIBLE);
                gRlLoginIn.setVisibility(View.GONE);
                gRlSignUp.setVisibility(View.GONE);
                break;
            case R.id.forgetpass_signin_bn:
            case R.id.register_signin_bn:
                gRlForgetPass.setVisibility(View.GONE);
                gRlLoginIn.setVisibility(View.VISIBLE);
                gRlSignUp.setVisibility(View.GONE);
                break;
        }
    }

    private void saveUserInfo(String account, String token) {
        UserInfo.getInstance().setUserAccount(account);
        UserInfo.getInstance().setUserToken(token);
    }
}
