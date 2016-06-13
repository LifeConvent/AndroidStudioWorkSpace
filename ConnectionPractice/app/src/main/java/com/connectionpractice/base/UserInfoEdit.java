package com.connectionpractice.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.connectionpractice.R;
import com.connectionpractice.api.ApiStatus;
import com.connectionpractice.api.BaseApiTask;
import com.connectionpractice.api.GetCityInfoApiTask;
import com.connectionpractice.api.SI001ApiTask;
import com.connectionpractice.config.BasicConfig;
import com.connectionpractice.tool.UserInfo;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 彪 on 2016/6/4.
 */
public class UserInfoEdit extends BaseActivity implements View.OnClickListener, BaseApiTask.OnTaskCompleted {

    private String timeYear;
    private String timeMonth;
    private String mStrBirth;
    private String cityProvince;
    private String city;
    private String mStrFistDay;
    private int mIntDtype;
    private int mIntSex;

    private String mStrCityposition = "0";
    private String mStrProvinceposition = "0";
    private String mStrSalt;
    private Dialog mMskDialog;

    private EditText mEtName;
    private EditText mEtPhone;
    private EditText mEtDisease;

    private Button mBtMan;
    private Button mBtFemale;
    private Button mBtSubmit;
    private Button mBtBirthdayYear;
    private Button mBtBirthdayMonth;
    private Button mBtDefiniteYear;
    private Button mBtDefiniteMonth;
    private Button mBtCityProvince;
    private Button mBtCity;
    private Button mBtTwon;

    private LinearLayout mBtBack;

    private ListView mLvListAll;
    private ArrayList<String> mAlArrayAll = new ArrayList<String>();
    private ArrayList<String> mAlProvince = new ArrayList<String>();
    private ArrayList<String> mAlCity = new ArrayList<String>();
    private ArrayList<String> mAlTown = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s010801_edit);
        initView();
    }

    private void initView() {
        mBtMan = (Button) findViewById(R.id.s010801_in_ed_bt_men);
        mBtFemale = (Button) findViewById(R.id.s010801_in_ed_bt_female);
        mBtBack = (LinearLayout) findViewById(R.id.s010801_in_ed_back);
        mBtSubmit = (Button) findViewById(R.id.s010801_in_ed_bt_commit);
        mEtName = (EditText) findViewById(R.id.s010801_in_ed_et_name);
        mEtPhone = (EditText) findViewById(R.id.s010801_in_ed_et_phone);
        mEtDisease = (EditText) findViewById(R.id.s010801_in_ed_et_disease);
        mBtBirthdayYear = (Button) findViewById(R.id.s010801_in_ed_bt_year);
        mBtBirthdayMonth = (Button) findViewById(R.id.s010801_in_ed_bt_month);
        mBtDefiniteYear = (Button) findViewById(R.id.s010801_in_ed_bt_definite_year);
        mBtDefiniteMonth = (Button) findViewById(R.id.s010801_in_ed_bt_definite_month);
        mBtCityProvince = (Button) findViewById(R.id.s010801_in_ed_bt_province);
        mBtCity = (Button) findViewById(R.id.s010801_in_ed_bt_city);
        mBtTwon = (Button) findViewById(R.id.s010801_in_ed_bt_town);

        mBtTwon.setOnClickListener(this);
        mBtCityProvince.setOnClickListener(this);
        mBtCity.setOnClickListener(this);
        mBtDefiniteYear.setOnClickListener(this);
        mBtDefiniteMonth.setOnClickListener(this);
        mBtBirthdayMonth.setOnClickListener(this);
        mBtBirthdayYear.setOnClickListener(this);
        mEtDisease.setOnClickListener(this);
        mEtPhone.setOnClickListener(this);
        mBtBack.setOnClickListener(this);
        mBtMan.setOnClickListener(this);
        mBtFemale.setOnClickListener(this);
        mBtSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.s010801_in_ed_bt_men:
                mBtMan.setTextColor(getResources().getColor(R.color.white));
                mBtMan.setBackground(getResources().getDrawable(R.drawable.s010801_in_edit_submit_newbuttonstyle));

                mIntSex = 1;

                mBtFemale.setTextColor(getResources().getColor(R.color.s010801_in_ed_sextext));
                mBtFemale.setBackground(getResources().getDrawable(R.drawable.s010801_in_edit_sex_newbuttonstyle));
                break;
            case R.id.s010801_in_ed_bt_female:
                mBtFemale.setTextColor(getResources().getColor(R.color.white));
                mBtFemale.setBackground(getResources().getDrawable(R.drawable.s010801_in_edit_submit_newbuttonstyle));

                mIntSex = 2;

                mBtMan.setTextColor(getResources().getColor(R.color.s010801_in_ed_sextext));
                mBtMan.setBackground(getResources().getDrawable(R.drawable.s010801_in_edit_sex_newbuttonstyle));
                break;
            case R.id.s010801_in_ed_back:
                Intent intent = new Intent(UserInfoEdit.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                );
                finish();
                break;
            case R.id.s010801_in_ed_bt_commit:
                setUserValues();
                setUserInfo();
                break;
            case R.id.s010801_in_ed_bt_year:
                //年份list选择
                mMskDialog = new Dialog(UserInfoEdit.this, R.style.MaskDialog);
                mMskDialog.setCanceledOnTouchOutside(false);
                mMskDialog.setContentView(R.layout.s010801_in_ed_year_newlistview);
                initTimeYearMakViews(mMskDialog, mBtBirthdayYear);
                mMskDialog.show();
                break;
            case R.id.s010801_in_ed_bt_month:
                //年份list选择
                mMskDialog = new Dialog(UserInfoEdit.this, R.style.MaskDialog);
                mMskDialog.setCanceledOnTouchOutside(false);
                mMskDialog.setContentView(R.layout.s010801_in_ed_year_newlistview);
                initTimeMonthMakViews(mMskDialog, mBtBirthdayMonth);
                mMskDialog.show();
                break;
            case R.id.s010801_in_ed_bt_definite_year:
                //年份list选择
                mMskDialog = new Dialog(UserInfoEdit.this, R.style.MaskDialog);
                mMskDialog.setCanceledOnTouchOutside(false);
                mMskDialog.setContentView(R.layout.s010801_in_ed_year_newlistview);
                initTimeYearMakViews(mMskDialog, mBtDefiniteYear);
                mMskDialog.show();
                break;
            case R.id.s010801_in_ed_bt_definite_month:
                //年份list选择
                mMskDialog = new Dialog(UserInfoEdit.this, R.style.MaskDialog);
                mMskDialog.setCanceledOnTouchOutside(false);
                mMskDialog.setContentView(R.layout.s010801_in_ed_year_newlistview);
                initTimeMonthMakViews(mMskDialog, mBtDefiniteMonth);
                mMskDialog.show();
                break;
            case R.id.s010801_in_ed_bt_province:
                //省份list选择
                GetCityInfoApiTask myTask = new GetCityInfoApiTask(UserInfoEdit.this, BasicConfig.API_ID_GC000);
                List<NameValuePair> nameValuePair = myTask.getNameValuePair();
                nameValuePair.add(new BasicNameValuePair("type", "province"));
                nameValuePair.add(new BasicNameValuePair("num", "0"));
                myTask.execute();
                getLoadingDialog().show();

                mMskDialog = new Dialog(UserInfoEdit.this, R.style.MaskDialog);
                mMskDialog.setCanceledOnTouchOutside(false);
                mMskDialog.setContentView(R.layout.s010801_in_ed_year_newlistview);
                initProvinceMakViews(mMskDialog, mBtCityProvince);
                mMskDialog.show();
                break;
            case R.id.s010801_in_ed_bt_city:
                //市级list选择
                if (mStrProvinceposition == null || mStrProvinceposition.equals("") || mStrProvinceposition.equals("0")) {
                    return;
                }
                GetCityInfoApiTask myTask1 = new GetCityInfoApiTask(UserInfoEdit.this, BasicConfig.API_ID_GC001);
                List<NameValuePair> nameValuePair1 = myTask1.getNameValuePair();
                nameValuePair1.add(new BasicNameValuePair("type", "city"));
                nameValuePair1.add(new BasicNameValuePair("num", "1"));
                nameValuePair1.add(new BasicNameValuePair("provinceid", mStrProvinceposition));
                myTask1.execute();
                getLoadingDialog().show();
                //cityProvince

                mMskDialog = new Dialog(UserInfoEdit.this, R.style.MaskDialog);
                mMskDialog.setCanceledOnTouchOutside(false);
                mMskDialog.setContentView(R.layout.s010801_in_ed_year_newlistview);
                initCityMakViews(mMskDialog, mBtCity);
                mMskDialog.show();
                break;
            case R.id.s010801_in_ed_bt_town:
                //区级list选择
                if (mStrCityposition == null || mStrCityposition.equals("") || mStrCityposition.equals("0")) {
                    return;
                }
                GetCityInfoApiTask myTask2 = new GetCityInfoApiTask(UserInfoEdit.this, BasicConfig.API_ID_GC002);
                List<NameValuePair> nameValuePair2 = myTask2.getNameValuePair();
                nameValuePair2.add(new BasicNameValuePair("type", "town"));
                nameValuePair2.add(new BasicNameValuePair("num", "2"));
                nameValuePair2.add(new BasicNameValuePair("provinceid", mStrProvinceposition));
                nameValuePair2.add(new BasicNameValuePair("cityid", mStrCityposition));
                myTask2.execute();
                getLoadingDialog().show();
                //cityProvince

                mMskDialog = new Dialog(UserInfoEdit.this, R.style.MaskDialog);
                mMskDialog.setCanceledOnTouchOutside(false);
                mMskDialog.setContentView(R.layout.s010801_in_ed_year_newlistview);
                initTownMakViews(mMskDialog, mBtTwon);
                mMskDialog.show();
                break;
            default:
                break;
        }
    }

    private void setUserValues() {
        /**
         *
         * 缺省空值判断
         * 个别传值格式不确定
         *
         * */
//        UserInfo.getInstance().setUserName(mEtName.getText().toString());
//        UserInfo.getInstance().setUserSex(mIntSex);
//        UserInfo.getInstance().setUserBirth(mStrBirth);
//        UserInfo.getInstance().setUserCity(mBtCityProvince.getText().toString() + " " + mBtCity.getText().toString());
//        UserInfo.getInstance().setUserPhone(mEtPhone.getText().toString());
//        UserInfo.getInstance().setUserFirstDay(mStrFistDay);
//        UserInfo.getInstance().setUserComplication(mEtDisease.getText().toString());
//        UserInfo.getInstance().setUserDtype(mIntDtype);
    }

    private void initCityMakViews(Dialog v, final Button button) {
        mLvListAll = (ListView) v.findViewById(R.id.s010801_in_ed_year_list);
        ListAdapter adapter = new ListAdapter(UserInfoEdit.this, R.layout.s010801_in_ed_list_item, mAlCity);
        mLvListAll.setAdapter(adapter);
        mLvListAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showMessageByString(null, String.valueOf(id));
                mStrCityposition = String.valueOf(id);
                city = mAlCity.get(position);
                mMskDialog.dismiss();
                mAlCity.clear();
                button.setText(city);
            }
        });
    }

    private void initTownMakViews(Dialog v, final Button button) {
        mLvListAll = (ListView) v.findViewById(R.id.s010801_in_ed_year_list);
        ListAdapter adapter = new ListAdapter(UserInfoEdit.this, R.layout.s010801_in_ed_list_item, mAlTown);
        mLvListAll.setAdapter(adapter);
        mLvListAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showMessageByString(null, String.valueOf(id));
                city = mAlTown.get(position);
                mMskDialog.dismiss();
                mAlTown.clear();
                button.setText(city);
            }
        });
    }

    private void initProvinceMakViews(Dialog v, final Button button) {
        mLvListAll = (ListView) v.findViewById(R.id.s010801_in_ed_year_list);
        ListAdapter adapter = new ListAdapter(UserInfoEdit.this, R.layout.s010801_in_ed_list_item, mAlProvince);
        mLvListAll.setAdapter(adapter);
        mLvListAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mStrProvinceposition = String.valueOf(id);
                cityProvince = mAlProvince.get(position);
                showMessageByString(null, String.valueOf(id));
                mMskDialog.dismiss();
                mAlProvince.clear();
                button.setText(cityProvince);
            }
        });
    }

    public class ListAdapter extends ArrayAdapter<String> {

        private int resourceId;

        public ListAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String str = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            TextView year = (TextView) view.findViewById(R.id.s010801_in_ed_year_tv);
            year.setText(str);
            return view;
        }
    }

    private void initTimeYearMakViews(Dialog v, final Button button) {
        mLvListAll = (ListView) v.findViewById(R.id.s010801_in_ed_year_list);
        Time currenttime = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料
        currenttime.setToNow(); // 取得系统时间。
        int year = currenttime.year;
        mAlArrayAll.add("年");
        for (int i = year; i >= 1900; i--) {
            mAlArrayAll.add(String.valueOf(i));
        }
        ListAdapter adapter = new ListAdapter(UserInfoEdit.this, R.layout.s010801_in_ed_list_item, mAlArrayAll);
        mLvListAll.setAdapter(adapter);
        mLvListAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                timeYear = mAlArrayAll.get(position);
                mMskDialog.dismiss();
                mAlArrayAll.clear();
                button.setText(timeYear);
            }
        });
    }

    private void initTimeMonthMakViews(Dialog v, final Button button) {
        mLvListAll = (ListView) v.findViewById(R.id.s010801_in_ed_year_list);
//        Time currenttime = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料
//        currenttime.setToNow(); // 取得系统时间。
//        int month = currenttime.month;
        mAlArrayAll.add("月");
        for (int i = 1; i <= 12; i++) {
            mAlArrayAll.add(String.valueOf(i));
        }
        ListAdapter adapter = new ListAdapter(UserInfoEdit.this, R.layout.s010801_in_ed_list_item, mAlArrayAll);
        mLvListAll.setAdapter(adapter);
        mLvListAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                timeMonth = mAlArrayAll.get(position);
                mMskDialog.dismiss();
                mAlArrayAll.clear();
                button.setText(timeMonth);
            }
        });
    }

    @Override
    public void onTaskFinish(ApiStatus api_status, int id, JSONObject response) {
        if (response==null) {
            getLoadingDialog().dismiss();
            return;
        }
        getLoadingDialog().dismiss();
        switch (id) {
            case BasicConfig.API_ID_RS001: {
                mMskDialog.dismiss();
                mMskDialog = null;
                Intent intent = new Intent(UserInfoEdit.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                );
                finish();
                break;
            }
            case BasicConfig.API_ID_GC000:
                /**
                 *
                 * 需要验证返回是否为空
                 *
                 * */
                if (mAlProvince.size() >= response.length()) {
                    return;
                }
                mAlProvince.add("省");
                for (int i = 1; i <= response.length(); i++) {
                    String temp = response.optString(String.valueOf(i));
                    mAlProvince.add(temp);
                }
                break;
            case BasicConfig.API_ID_GC001:
                /**
                 *
                 * 需要验证返回是否为空
                 *
                 * */
                mAlCity.clear();
                mAlCity.add("市");
                for (int i = 1; i <= response.length(); i++) {
                    String temp = response.optString(String.valueOf(i));
                    mAlCity.add(temp);
                }
                break;
            case BasicConfig.API_ID_GC002:
                /**
                 *
                 * 需要验证返回是否为空
                 *
                 * */
                mAlTown.clear();
                mAlTown.add("区");
                for (int i = 1; i <= response.length(); i++) {
                    String temp = response.optString(String.valueOf(i));
                    mAlTown.add(temp);
                }
                break;
        }
    }

    private void setUserInfo() {
        getLoadingDialog().show();
//        mBATApiConnect =new C0108PIApiTask(S010801_Edit.this, BasicConfig.API_ID_C0108PIAPITASK_SET);
//        mBATApiSalt.execute();
    }

}

