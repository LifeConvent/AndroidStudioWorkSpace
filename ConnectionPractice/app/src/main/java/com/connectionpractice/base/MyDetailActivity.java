package com.connectionpractice.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connectionpractice.R;
import com.connectionpractice.api.ApiStatus;
import com.connectionpractice.api.BaseApiTask;
import com.connectionpractice.api.GUIWMApiTask;
import com.connectionpractice.api.RP001ApiTask;
import com.connectionpractice.api.SI001ApiTask;
import com.connectionpractice.api.SU001ApiTask;
import com.connectionpractice.config.BasicConfig;
import com.connectionpractice.config.BasicConstant;
import com.connectionpractice.imageloader.ImageLoader;
import com.connectionpractice.tool.APITool;
import com.connectionpractice.tool.Md5Tool;
import com.connectionpractice.tool.MemTool;
import com.connectionpractice.tool.PICTOOL;
import com.connectionpractice.tool.UserInfo;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by 彪 on 2016/6/2.
 */
public class MyDetailActivity extends BaseActivity implements View.OnClickListener, BaseApiTask.OnTaskCompleted {

    private ImageView mIbAvatar;
    private boolean mBlnGotPicture;
    private String mStrPictureBase64;
    private String mStrPictureName;
    private String account;
    private Button mBnExit;

    private RelativeLayout mRlMyInfoHead;
    private RelativeLayout mRlMyCase;
    private RelativeLayout mRlMyCandy;
    private RelativeLayout mRlResetPass;
    private RelativeLayout mRlMySysInform;

    private TextView mTvTitleName;

    private ActvityClickListener mAclLocalclicklistener;


    private ImageLoader mIlImageLoader;

    class ActvityClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bn_edit_submit:
                    break;
                case R.id.s010801_in_my_info:
                    showMessageByString(null, "个人信息");
                    /**
                     *
                     * 跳转个人信息界面，并进行post获取
                     *
                     * */
                    Intent intent = new Intent();
                    intent.setClass(MyDetailActivity.this, UserInfoEditActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                    );
                    break;
                case R.id.s010801_in_sys_inform:
                    showMessageByString(null, "照片头像上传");
                    /**
                     *
                     * 跳转系统通知界面，并进行post获取
                     *
                     * */
                    showPictureSourceDialog();
                    break;
                case R.id.s010801_in_my_case:
                    showMessageByString(null, "更新test数据");
                    /**
                     *
                     * 跳转我的病历界面，并进行post获取
                     *
                     * */
                    SU001ApiTask myTask = new SU001ApiTask(MyDetailActivity.this, BasicConfig.API_ID_SUI001);
                    myTask.setUrl(BasicConfig.URL_SETUSERINFO + BasicConfig.NETHOD_SUI_UUIM);
                    List<NameValuePair> nameValuePair = myTask.getNameValuePair();
                    nameValuePair.add(new BasicNameValuePair("account", "test"));
                    nameValuePair.add(new BasicNameValuePair("name", "测试"));
                    nameValuePair.add(new BasicNameValuePair("sex", "测试"));
                    nameValuePair.add(new BasicNameValuePair("age", "测试"));
                    nameValuePair.add(new BasicNameValuePair("phone", "测试"));
                    nameValuePair.add(new BasicNameValuePair("city", "测试"));
                    nameValuePair.add(new BasicNameValuePair("birthday", "测试"));
                    if (mStrPictureBase64.equals(""))
                        nameValuePair.add(new BasicNameValuePair("image", null));
                    else
                        nameValuePair.add(new BasicNameValuePair("image", "data:image/png;base64," + mStrPictureBase64));
                    myTask.execute();
                    break;
                case R.id.s010801_in_my_candy:
                    showMessageByString(null, "设置图片");
                    /**
                     *
                     * 跳转我的糖豆界面，并进行post获取
                     *
                     * */
                    Bitmap bp = PICTOOL.base64ToBitmap(mStrPictureBase64);
                    mIbAvatar.setImageBitmap(bp);
                    break;
                case R.id.s010801_in_reset_pass:
                    /**
                     *
                     * 下载图片，并进行post获取
                     *
                     * */

                    GUIWMApiTask myTask_GUI = new GUIWMApiTask(MyDetailActivity.this, BasicConfig.API_ID_GUIW001);
                    List<NameValuePair> nameValuePair_GUI = myTask_GUI.getNameValuePair();
                    nameValuePair_GUI.add(new BasicNameValuePair("account", "test"));
                    myTask_GUI.execute();
                    break;
                case R.id.s010801_in_new_exit:
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_info);
        super.onCreate(savedInstanceState);
        mAclLocalclicklistener = new ActvityClickListener();

        initViews();
        initVars();
    }

    private void initViews() {
        account = UserInfo.getInstance().getUserName();
        //setText需要在初始化之后

        mRlMyInfoHead = (RelativeLayout) findViewById(R.id.s010801_in_my_info);
        mRlMyInfoHead.setOnClickListener(mAclLocalclicklistener);
        mRlMySysInform = (RelativeLayout) findViewById(R.id.s010801_in_sys_inform);
        mRlMySysInform.setOnClickListener(mAclLocalclicklistener);
        mRlMyCase = (RelativeLayout) findViewById(R.id.s010801_in_my_case);
        mRlMyCase.setOnClickListener(mAclLocalclicklistener);
        mRlMyCandy = (RelativeLayout) findViewById(R.id.s010801_in_my_candy);
        mRlMyCandy.setOnClickListener(mAclLocalclicklistener);
        mRlResetPass = (RelativeLayout) findViewById(R.id.s010801_in_reset_pass);
        mRlResetPass.setOnClickListener(mAclLocalclicklistener);


        mTvTitleName = (TextView) findViewById(R.id.s010801_in_tv_username);

        mIbAvatar = (ImageView) findViewById(R.id.s010801_newmyheadiamge);

        mTvTitleName.setText(account);

        mBnExit = (Button) findViewById(R.id.s010801_in_new_exit);
        mBnExit.setOnClickListener(mAclLocalclicklistener);
    }

    private void delPicture(ImageButton ib, boolean gotpic) {
        if (!gotpic) {
            return;
        }
        if (ib == null) {
            return;
        }
        Drawable drawable = ib.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        ib.setImageResource(R.drawable.s010801_in_newdefaultimage);
    }

    //相机拍照
    private String refreshPictureCapture(ImageView v, Intent data) {
        String base64 = "";
        Bitmap bp = null;
        bp = (Bitmap) data.getExtras().get("data");
        v.setImageBitmap(bp);
        v.setVisibility(View.VISIBLE);
        base64 = PICTOOL.bitmapToBase64(bp, 64.00);
        return base64;
    }

    //选取相册照片
    private String refreshPicture(ImageView v, Intent data) {
        String base64 = "";
        if (data != null) {
            //取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
            Uri mImageCaptureUri = data.getData();
            //返回的Uri不为空时，那么图片信息数据都会在Uri中获得。如果为空，那么我们就进行下面的方式获取
            if (mImageCaptureUri != null) {
                Bitmap image;
                try {
                    //这个方法是根据Uri获取Bitmap图片的静态方法
                    image = PICTOOL.getSmallBitmap(mImageCaptureUri, this);
                    if (image != null) {
                        v.setImageBitmap(image);
                        v.setVisibility(View.VISIBLE);
                        //mStrPictureBase64
                        base64 = PICTOOL.bitmapToBase64(image, 64.00);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    //这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片
                    Bitmap image = extras.getParcelable("data");
                    if (image != null) {
                        v.setImageBitmap(image);
                        v.setVisibility(View.VISIBLE);
                        base64 = PICTOOL.bitmapToBase64(image, 64.00);
                    }
                }
            }

        }
        return base64;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0: {
                    mStrPictureBase64 = refreshPictureCapture(mIbAvatar, data);
                    mBlnGotPicture = true;
                    break;
                }
                case 1: {
                    mStrPictureBase64 = refreshPicture(mIbAvatar, data);
                    mBlnGotPicture = true;
                    break;
                }
                default:
                    break;
            }
        }
    }

    private String getPicture(boolean local) {
        String name = "";
        String status = Environment.getExternalStorageState();
        if (!local) {
            if (status.equals(Environment.MEDIA_MOUNTED)) {
                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 0);

                } catch (ActivityNotFoundException e) {
                    showMessageByString(null, getResources().getString(R.string.common_error_no_folder));
                }
            } else {
                showMessageByString(null, getResources().getString(R.string.common_error_no_card));
            }
        } else {
            if (status.equals(Environment.MEDIA_MOUNTED)) {
                try {
                    //选择照片的时候也一样，我们用Action为Intent.ACTION_GET_CONTENT，
                    //有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 1);
                } catch (ActivityNotFoundException e) {
                    showMessageByString(null, getResources().getString(R.string.common_error_no_card));
                }
            } else {
                showMessageByString(null, getResources().getString(R.string.common_error_no_card));
            }
        }
        return name;

    }

    private void showPictureSourceDialog() {
        ContextThemeWrapper ctx = new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light_Dialog);
        AlertDialog dg = new AlertDialog.Builder(ctx)
                .setTitle(getResources().getString(R.string.s010401_record_newpicturefromwhere))
                .setPositiveButton(
                        getResources().getString(R.string.s010401_record_newpicturefromlocal),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getPicture(true);//TASK_ID_GET_PICTURE_FOR_MEDICINEUSED_FROM_LOCAL
                            }
                        })
                .setNeutralButton(
                        getResources().getString(R.string.s010401_record_newpicturefromcamera),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mStrPictureName = getPicture(false);
                            }
                        }
                )
                .setNegativeButton(
                        getResources().getString(R.string.s010401_record_newpicturecancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }
                )
                .create();
        dg.show();
    }

    @Override
    public void onClick(View v) {

    }

    private void initVars() {
        mAclLocalclicklistener = new ActvityClickListener();
        mIlImageLoader = new ImageLoader(this, -1);
        mIlImageLoader = new ImageLoader(this, -1);
        mIlImageLoader.setOnLoadFinishListener(new ImageLoader.OnLoadCompleted() {
            @Override
            public void onLoadFinish(Bitmap bm) {
                String base64 = PICTOOL.bitmapToBase64(bm, 300);
                MemTool.saveLocalConfigStringWithAES(MyDetailActivity.this,
                        BasicConstant.SHARE_PERFERENCE_THUMB_FIELD, base64);

                UserInfo.getInstance().setUserImage(base64);
            }

            //            @Override
            public Bitmap onResizeBitmap(Bitmap bm) {
                return bm;
            }
        });
    }

    @Override
    public void onTaskFinish(ApiStatus api_status, int id, JSONObject response) {
        if (response == null) {
            getLoadingDialog().dismiss();
            return;
        }
        getLoadingDialog().dismiss();
        switch (id) {
            case BasicConfig.API_ID_SUI001:
                break;
            case BasicConfig.API_ID_GUIW001:
                try {
                    String strTemp;
                    strTemp = response.getString("account");
                    UserInfo.getInstance().setUserAccount(strTemp);

                    strTemp = response.getString("name");
                    UserInfo.getInstance().setUserName(strTemp);

                    strTemp = response.getString("sex");
                    UserInfo.getInstance().setUserSex(strTemp);

                    strTemp = response.getString("phone");
                    UserInfo.getInstance().setUserPhone(strTemp);

                    strTemp = response.getString("birthday");
                    UserInfo.getInstance().setUserBirth(strTemp);

                    strTemp = response.getString("city");
                    UserInfo.getInstance().setUserCity(strTemp);

                    strTemp = response.getString("age");
                    UserInfo.getInstance().setUserAge(strTemp);

                    strTemp = BasicConfig.HTTP_ROOT + BasicConfig.PATH_TO_API + response.getString("image").substring(3);
                    mIlImageLoader.DisplayImage(strTemp, mIbAvatar, false);
                    //mIvAvatar.setImageResource(R.drawable.s010801_png_patientavatar);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getLoadingDialog().dismiss();
                initUserView();
                break;
            default:
                break;
        }
    }

    private void initUserView() {
        String base = UserInfo.getInstance().getUserImage();
        if (!base.equals("")) {
            mStrPictureBase64 = base;
            Bitmap bm = PICTOOL.base64ToBitmap(base);
            mIbAvatar.setImageBitmap(bm);
            mIbAvatar.setVisibility(View.VISIBLE);
            mBlnGotPicture = true;
        }
    }
}
