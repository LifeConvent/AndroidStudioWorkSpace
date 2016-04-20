package com.database.ob;

/**
 * Created by 彪 on 2016/4/19.
 */
public class PersonCourse {

    private String account;
    private String course;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public PersonCourse(String account, String course) {
        this.account = account;
        this.course = course;
    }

    public PersonCourse() {
    }
}
