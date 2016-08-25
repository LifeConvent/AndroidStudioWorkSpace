package com.connectionpractice.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.connectionpractice.R;

/**
 * Created by 彪 on 2016/6/2.
 */
public class BaseTabActivity extends BaseActivity implements View.OnClickListener {

        private View tabview;
        private LinearLayout mLlRecord;
        private LinearLayout mLlMe;
        private LinearLayout mLlActiona;
        private LinearLayout mLlActionb;

        private int mRunningActivity = ACTIVITY_RECORD;

        protected static int ACTIVITY_RECORD = 1;
        protected static int ACTIVITY_ACTA = 2;
        protected static int ACTIVITY_ACTB = 3;
        protected static int ACTIVITY_ME = 4;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            initTabView();
        }

        public void setRunningActivity(int mRunningActivity) {
            this.mRunningActivity = mRunningActivity;
        }

        private void initTabView() {
            tabview = (View)findViewById(R.id.tab_buttons);
            if (tabview != null) {
                mLlRecord = (LinearLayout) findViewById(R.id.tab_record);
                mLlMe = (LinearLayout) findViewById(R.id.tab_me);
                mLlActiona = (LinearLayout) findViewById(R.id.tab_acta);
                mLlActionb = (LinearLayout) findViewById(R.id.tab_actb);

                mLlRecord.setOnClickListener(this);
                mLlMe.setOnClickListener(this);

                mLlActiona.setOnClickListener(this);
                mLlActionb.setOnClickListener(this);
            }
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {
                case R.id.tab_record:
//                    if(mRunningActivity == ACTIVITY_RECORD) {
//                        return;
//                    }
                    intent.setClass(BaseTabActivity.this, TempaActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                    );
                    break;
                case R.id.tab_acta:
//                    if(mRunningActivity == ACTIVITY_ACTA) {
//                        return;
//                    }
//                    mRunningActivity = ACTIVITY_ACTA;
                    intent.setClass(BaseTabActivity.this, TempaActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                    );

                    break;
                case R.id.tab_me:
//                    if(mRunningActivity == ACTIVITY_ME) {
//                        return;
//                    }
//                    mRunningActivity = ACTIVITY_ME;
                    showMessageByString(null,"主页 ");
                    intent.setClass(BaseTabActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                    );
                    break;

                case R.id.tab_actb:
//                    if(mRunningActivity == ACTIVITY_ACTB) {
//                        return;
//                    }
//                    mRunningActivity = ACTIVITY_ACTB;
                    intent.setClass(BaseTabActivity.this, TempaActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                    );
                    break;
                default:
                    break;
            }
            finish();
        }
    }

