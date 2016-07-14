package com.connectionpractice.tool;

/**
 * Created by 彪 on 2016/5/30.
 */
public class UserInfo {
    private String mStrUserName;
    private String mStrUserPassword;

    private String mStrUserHospital="";
    private String mStrUserDepartment="";
    private String mStrUserTitle="";
    private String mStrUserAge="";
    private String mStrToken="";
    private String mStrAESkey="";
    private String mStrUserPhone="";
    private String mStrUserCity="";
    private String mStrUserComplication="";
    private int mStrUserDtype;
    private int mIntSex=0;
    private String mStrUserBirth="";
    private String mStrUserFirstDay="";
    private int mIntInfotype=0;
    private String mStrUserThumb="";
    private String mStrUserLevel="";

    private static class UserInfoHolder {
        private static UserInfo instance = new UserInfo();
    }
    public static UserInfo getInstance() {
        return UserInfoHolder.instance;
    }

    public String getmStrUserName() {
        return mStrUserName;
    }

    public void setmStrUserName(String mStrUserName) {
        this.mStrUserName = mStrUserName;
    }

    public String getmStrUserPassword() {
        return mStrUserPassword;
    }

    public void setmStrUserPassword(String mStrUserPassword) {
        this.mStrUserPassword = mStrUserPassword;
    }

    public String getToken() {
        return mStrToken;
    }

    public String getAESkey() {
        return mStrAESkey;
    }
}
