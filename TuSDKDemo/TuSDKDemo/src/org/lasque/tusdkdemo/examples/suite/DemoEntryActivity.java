/**
 * TuSdkDemo
 * DemoEntryActivity.java
 *
 * @author Clear
 * @Date 2014-11-15 下午4:30:52
 * @Copyright (c) 2014 tusdk.com. All rights reserved.
 * @link 开发文档:http://tusdk.com/docs/android/api/
 */
package org.lasque.tusdkdemo.examples.suite;

import org.lasque.tusdkdemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author Clear
 */
public class DemoEntryActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_entry_activity);
        Button mComponentListButtonView = (Button) findViewById(R.id.lsq_entry_list);
        mComponentListButtonView.setOnClickListener(this);
    }

    /** 按钮点击事件处理 */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lsq_entry_list:
                showComponentList();
                break;
        }
    }

    /** 显示组件列表页面 */
    private void showComponentList() {
        CameraComponentSample a = new CameraComponentSample();
        a.showSample(DemoEntryActivity.this);
    }
}