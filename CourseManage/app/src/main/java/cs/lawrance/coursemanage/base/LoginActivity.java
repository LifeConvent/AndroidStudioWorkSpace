package cs.lawrance.coursemanage.base;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import org.json.JSONObject;

import cs.lawrance.coursemanage.R;
import cs.lawrance.coursemanage.api.ApiStatus;
import cs.lawrance.coursemanage.api.BaseApiTask;
import cs.lawrance.coursemanage.tool.SystemStatusManager;

/**
 * Created by lawrance on 2017/4/18.
 */
public class LoginActivity extends BaseActivity implements BaseApiTask.OnTaskCompleted, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_login);
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

    }

    @Override
    public void onTaskFinish(ApiStatus status, int id, JSONObject response) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_submit_bn:
                break;
        }
    }
}
