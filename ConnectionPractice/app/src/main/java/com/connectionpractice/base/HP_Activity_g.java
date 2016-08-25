package com.connectionpractice.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.connectionpractice.R;
import com.connectionpractice.api.ApiStatus;
import com.connectionpractice.api.BaseApiTask;

import org.json.JSONObject;

/**
 * Created by lawrance on 16/7/24.
 */
public class HP_Activity_g extends BaseTabActivity implements BaseApiTask.OnTaskCompleted {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_page_g);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tab_me:
                Intent intent = new Intent();
                showMessageByString(null,"主页 ");
                intent.setClass(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                );
                break;
        }
    }

    @Override
    public void onTaskFinish(ApiStatus api_status, int id, JSONObject response) {

    }
}
