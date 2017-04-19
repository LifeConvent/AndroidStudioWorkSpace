package cs.lawrance.coursemanage.base;

import android.os.Bundle;
import android.view.View;

import org.json.JSONObject;

import cs.lawrance.coursemanage.R;
import cs.lawrance.coursemanage.api.ApiStatus;
import cs.lawrance.coursemanage.api.BaseApiTask;

/**
 * Created by lawrance on 2017/4/18.
 */
public class LoginActivity extends BaseActivity implements BaseApiTask.OnTaskCompleted, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
