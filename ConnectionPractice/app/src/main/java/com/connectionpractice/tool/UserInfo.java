package com.connectionpractice.tool;

/**
 * Created by 彪 on 2016/5/30.
 */
public class UserInfo {
    private String mStrUserName;
    private String mStrUserPassword;

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
}
