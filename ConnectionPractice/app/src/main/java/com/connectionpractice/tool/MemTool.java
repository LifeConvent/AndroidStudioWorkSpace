package com.connectionpractice.tool;

import android.content.Context;
import android.content.SharedPreferences;

import com.connectionpractice.config.BasicConstant;


/**
 * Created by nnit on 5/7/16.
 */
public class MemTool {
    public static void saveLocalConfigString(Context ctx, String key, String value) {
        SharedPreferences pre = ctx.getApplicationContext().getSharedPreferences(BasicConstant.SHARE_PERFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pre.edit();
        edt.putString(key, value);
        edt.commit();

    }
    public static String loadLocalConfigString(Context ctx, String key) {
        SharedPreferences pre = ctx.getApplicationContext().getSharedPreferences(BasicConstant.SHARE_PERFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        return pre.getString(key, "");

    }

    public static void saveLocalConfigStringWithAES(Context ctx, String key, String value) {

        String encode = AESTool.encrypt(BasicConstant.SHARE_PERFERENCE_AESKEY, value);
        saveLocalConfigString(ctx,key,encode);
    }
    public static String loadLocalConfigStringWithAES(Context ctx, String key) {
        String encode = loadLocalConfigString(ctx, key);
        if(encode.equals("")) {
            return "";
        }
        return AESTool.decrypt(BasicConstant.SHARE_PERFERENCE_AESKEY,encode);

    }
    public static boolean isListModified(Context ctx, String list, String field) {
        String saved = MemTool.loadLocalConfigStringWithAES(ctx, field);
        saved = Md5Tool.getMD5(saved);
        if(saved.equals(Md5Tool.getMD5(list))){
            return false;
        }else {
            return true;
        }
    }
}
