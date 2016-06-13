package com.connectionpractice.base;

import android.os.Bundle;

import com.connectionpractice.R;

public class TempaActivity extends BaseTabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tempa);
//        setRunningActivity(ACTIVITY_ACTA);
        showMessageByString(null,"a");
        super.onCreate(savedInstanceState);
    }
}
