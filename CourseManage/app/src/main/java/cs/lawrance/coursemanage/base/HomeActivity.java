package cs.lawrance.coursemanage.base;

import android.view.View;

import org.json.JSONObject;

import cs.lawrance.coursemanage.api.ApiStatus;
import cs.lawrance.coursemanage.api.BaseApiTask;

/**
 * Created by lawrance on 2017/4/20.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener,BaseApiTask.OnTaskCompleted{
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onTaskFinish(ApiStatus status, int id, JSONObject response) {

    }
}
