package com.connectionpractice.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connectionpractice.R;
import com.connectionpractice.tool.DatePickerUtil;

import java.util.Calendar;


/**
 * Created by SRainbow on 2016/7/16.
 */
public class PInfo_Activity_w extends BaseActivity implements View.OnClickListener{

    private RelativeLayout mRlayoutSex;
    private RelativeLayout mRlayoutAddress;
    private RelativeLayout mRlayoutAge;

    private TextView mTvNikeName;
    private TextView mTvAccount;
    private TextView mTvSex;
    private TextView mTvAge;
    private TextView mTvAddress;
    private TextView mTvAddress_ex;

    private Typeface face_jianxi,face_jianzhongkai,face_jxxingkai,face_jxhei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_personalinfo);
        initViews();
    }

    private void initViews(){
        mRlayoutSex=(RelativeLayout)findViewById(R.id.pinfo_sex_rlayout);
        mRlayoutAddress=(RelativeLayout)findViewById(R.id.pinfo_address_rlayout);
        mRlayoutAge=(RelativeLayout)findViewById(R.id.pinfo_age_rlayout);

        mTvNikeName=(TextView)findViewById(R.id.pinfo_nickname_tv);
        mTvNikeName=(TextView)findViewById(R.id.pinfo_nickname_tv);
        mTvAccount=(TextView)findViewById(R.id.pinfo_account_tv);
        mTvSex=(TextView)findViewById(R.id.pinfo_sex_tv);
        mTvAge=(TextView)findViewById(R.id.pinfo_age_tv);
        mTvAddress=(TextView)findViewById(R.id.pinfo_address_tv);
        mTvAddress_ex=(TextView)findViewById(R.id.pinfo_address_ex_tv);

        face_jianxi=Typeface.createFromAsset(getAssets(), "fonts/jianxi.ttf");
        face_jianzhongkai=Typeface.createFromAsset(getAssets(),"fonts/jianzhongkai.ttf");
        face_jxxingkai=Typeface.createFromAsset(getAssets(),"fonts/jxxingk.ttf");
        face_jxhei=Typeface.createFromAsset(getAssets(),"fonts/jxhei.ttf");

        //设置字体
        mTvAddress.setTypeface(face_jianxi);
        mTvAddress_ex.setTypeface(face_jxhei);

        mRlayoutSex.setOnClickListener(this);
        mRlayoutAddress.setOnClickListener(this);
        mRlayoutAge.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pinfo_sex_rlayout:
                String[] sexArray=new String[2];
                sexArray[0]="男";
                sexArray[1]="女";
                int index=0;
                for(int i=0;i<2;i++){
                    if(mTvSex.getText().toString().equals(sexArray[i])){
                        index=i;
                    }
                }
                showListViewDialog(sexArray,index,mTvSex);
                break;
            case R.id.pinfo_address_rlayout:
                showMessageByString(null, "AddressRlayout");
                break;
            case R.id.pinfo_age_rlayout:
                DatePickerUtil dateTimePicKDialog = new DatePickerUtil(
                        PInfo_Activity_w.this, getCurrentDate());
                dateTimePicKDialog.dateTimePicKDialog(mTvAge);
                break;
        }

    }


    private void showListViewDialog(final String[] items, int index, final TextView title) {
        AlertDialog dg = new AlertDialog.Builder(this, R.style.ListViewExpandDialog)
                .setSingleChoiceItems(items, index, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        title.setText(items[which]);
//                        title.setTag(which);
                        dialog.dismiss();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                    }
                })
                .create();

        ListView view = dg.getListView();

        view.setDivider(new ColorDrawable(Color.GRAY));
        view.setDividerHeight(1);

        dg.show();
        Window window = dg.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);


        View parent = (View) view.getParent();
        ViewGroup.LayoutParams par2 = (ViewGroup.LayoutParams) parent.getLayoutParams();
        par2.width = 500;
        parent.setLayoutParams(par2);

        parent = (View) view.getParent().getParent();
        ViewGroup.LayoutParams par3 = (ViewGroup.LayoutParams) parent.getLayoutParams();
        par3.height = 225;
        parent.setLayoutParams(par3);
    }

    public String getCurrentDate(){
        Calendar c=Calendar.getInstance();
        final int myYear=c.get(Calendar.YEAR);
        final int myMonth=c.get(Calendar.MONTH)+1;
        final int myDay=c.get(Calendar.DAY_OF_MONTH);
        if(myMonth<10){

            if(myDay<10){
                return myYear+"年0"+myMonth+"月0"+myDay+"日";
            }else{
                return myYear+"年0"+myMonth+"月"+myDay+"日";
            }
        }
        else if(myMonth<10){
            if(myDay<10){
                return myYear+"年"+myMonth+"月0"+myDay+"日";
            }else{
                return myYear+"年"+myMonth+"月"+myDay+"日";
            }

        }
        return "";
    }
}
