package cs.lawrance.coursemanage.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by lawrance on 2017/4/18.
 */
public class BaseActivity extends Activity {

    private ProgressDialog gPdLoadingDialog;

    public ProgressDialog getLoadingDialog() {
        return gPdLoadingDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gPdLoadingDialog = new ProgressDialog(BaseActivity.this);
    }

    public void showMessageByResourceId(int id) {
        String err = getResources().getString(id);
        showMessageByString(err);
    }

    public void showMessageByString(String err) {
        Toast.makeText(getApplicationContext(), err, Toast.LENGTH_SHORT).show();
    }

    public void showReturnServerMessage(String message) {
        switch (message) {
            case "":
                break;

        }
    }

}
