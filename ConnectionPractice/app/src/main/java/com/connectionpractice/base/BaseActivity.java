package com.connectionpractice.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.connectionpractice.R;

/**
 * Created by 彪 on 2016/5/28.
 */
public class BaseActivity extends Activity {

    private ProgressDialog mPdLoadingDialog;

    public ProgressDialog getLoadingDialog() {
        return mPdLoadingDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mPdLoadingDialog = new ProgressDialog(BaseActivity.this);
    }

    public void showMessageByResourceId(View v, int id) {
        String err = getResources().getString(id);
        showMessageByString(v, err);
    }

    public void showMessageByString(View v, String err) {
        Toast.makeText(getApplicationContext(), err,
                Toast.LENGTH_SHORT).show();
    }

    public void showReturnServer(String message) {
        switch (message) {
            case "ACCOUNT_NOT_EXIST":
                showMessageByResourceId(null, R.string.server_err_not_exist);
                getLoadingDialog().dismiss();
                break;
            case "WRONG_PASSWORD":
                showMessageByResourceId(null, R.string.server_err_wrong_password);
                getLoadingDialog().dismiss();
                break;
            case "INPUT_NULL":
                showMessageByResourceId(null, R.string.server_err_nul);
                getLoadingDialog().dismiss();
                break;
            case "INSERT_FAILED":
                showMessageByResourceId(null, R.string.server_err_insert_failed);
                getLoadingDialog().dismiss();
                break;
            case "ACCOUNT_EXIST":
                showMessageByResourceId(null, R.string.server_err_exist);
                getLoadingDialog().dismiss();
                break;
            case "OLD_PASS_WRONG":
                showMessageByResourceId(null, R.string.server_err_old_wrong);
                getLoadingDialog().dismiss();
                break;
        }
    }
}
