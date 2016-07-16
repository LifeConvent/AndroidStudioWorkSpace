package com.connectionpractice.tool;

/**
 * Created by 彪 on 2016/5/30.
 */
public class UserInfo {
    private String mStrUserName="";
    private String mStrUserPassword="";
    private String mStrToken="";
    private String mStrAESkey="";
    private String mStrUserAccount;

    private String mStrUserAge="";
    private String mStrUserPhone="";
    private String mStrUserCity="";
    private String mStrUserBirth="";
    private String mStrUserSex="";
    private String mStrUserImage="";

    private String mStrUserLevel="";

    public String getUserPhone() {
        return mStrUserPhone;
    }

    public void setUserPhone(String mStrUserPhone) {
        this.mStrUserPhone = mStrUserPhone;
    }

    public String getUserAge() {
        return mStrUserAge;
    }

    public void setUserAge(String mStrUserAge) {
        this.mStrUserAge = mStrUserAge;
    }

    public String getUserAccount() {
        return mStrUserAccount;
    }

    public void setUserAccount(String mStrUserAccount) {
        this.mStrUserAccount = mStrUserAccount;
    }

    public String getUserCity() {
        return mStrUserCity;
    }

    public void setUserCity(String mStrUserCity) {
        this.mStrUserCity = mStrUserCity;
    }

    public String getUserBirth() {
        return mStrUserBirth;
    }

    public void setUserBirth(String mStrUserBirth) {
        this.mStrUserBirth = mStrUserBirth;
    }

    public String getUserSex() {
        return mStrUserSex;
    }

    public void setUserSex(String mStrUserSex) {
        this.mStrUserSex = mStrUserSex;
    }

    public String getUserImage() {
        return mStrUserImage;
    }

    public void setUserImage(String mStrUserImage) {
        this.mStrUserImage = mStrUserImage;
    }


    private static class UserInfoHolder {
        
        private static UserInfo instance = new UserInfo();
    }
    public static UserInfo getInstance() {
        return UserInfoHolder.instance;
    }

    public String getUserName() {
        return mStrUserName;
    }

    public void setUserName(String mStrUserName) {
        this.mStrUserName = mStrUserName;
    }

    public String getUserPassword() {
        return mStrUserPassword;
    }

    public void setUserPassword(String mStrUserPassword) {
        this.mStrUserPassword = mStrUserPassword;
    }

    public String getToken() {
        return mStrToken;
    }

    public String getAESkey() {
        return mStrAESkey;
    }
}
