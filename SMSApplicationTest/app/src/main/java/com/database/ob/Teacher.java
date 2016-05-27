package com.database.ob;

/**
 * Created by 彪 on 2016/4/12.
 */
public class Teacher {

    public String NAME;
    public String NUM;

    public Teacher(String account, String num) {
        this.setAccountName(account);
        this.setAccountNum(num);
    }

    public Teacher() {
    }

    public void setAccountNum(String accountPassword) {
        this.NUM = accountPassword;
    }

    public void setAccountName(String accountName) {
        this.NAME = accountName;
    }

    public String getAccountName() {
        return NAME;
    }

    public String getAccountNum() {
        return NUM;
    }

}
