package com.connectionpractice.base;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ImageButton;

import com.connectionpractice.R;
import com.connectionpractice.api.ApiStatus;
import com.connectionpractice.api.BaseApiTask;
import com.connectionpractice.config.BasicConfig;
import com.connectionpractice.tool.PICTOOL;

import org.json.JSONObject;

import java.io.File;

/**
 * Created by 彪 on 2016/6/2.
 */
public class MyDetailActivity extends BaseActivity implements View.OnClickListener, BaseApiTask.OnTaskCompleted{

    private String mStrPictureBase64;
    private Boolean mBlnGotPicture;
    private String mStrPictureName;

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
//        ib.setImageResource(R.drawable.s010401_in_maskpicturebackground);
    }

    private String refreshPicture(ImageButton v, String name) {
        String base64 = "";
        Bitmap bp = null;
        String filePath = Environment.getExternalStorageDirectory()
                + "/" + BasicConfig.PICTURE_TEMP_DIR + "/" + name;//mStrPictureName;
        bp = PICTOOL.getSmallBitmap(filePath);
        v.setImageBitmap(bp);
        v.setVisibility(View.VISIBLE);
        //mStrPictureBase64
        base64 = PICTOOL.bitmapToBase64(bp, 64.00);
        return base64;
    }

    private String refreshPicture(ImageButton v, Intent data) {
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
                        //mStrPictureBase64 =
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

//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case 0: {
//                    mStrPictureBase64 = refreshPicture(mIbAvatar, mStrPictureName);
//                    mBlnGotPicture = true;
//                    break;
//                }
//                case 1: {
//                    mStrPictureBase64 = refreshPicture(mIbAvatar, data);
//                    mBlnGotPicture = true;
//                    break;
//                }
//                default:
//                    break;
//            }
//        }
    }

    private String getPicture(boolean local) {
        String name = "";
        String status = Environment.getExternalStorageState();
        if (!local) {
            if (status.equals(Environment.MEDIA_MOUNTED)) {
                try {
                    File dir = new File(Environment
                            .getExternalStorageDirectory()
                            + "/"
                            + BasicConfig.PICTURE_TEMP_DIR);
                    if (!dir.exists())
                        dir.mkdirs();
                    name = System.currentTimeMillis() + ".jpg";
                    Intent intent = new Intent(
                            android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(dir, name);// localTempImgDir和localTempImageFileName是自己定义的名字
                    Uri u = Uri.fromFile(f);
                    intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
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

    @Override
    public void onTaskFinish(ApiStatus api_status, int id, JSONObject response) {

    }
}
