package com.database.ob;

/**
 * Created by 彪 on 2016/4/19.
 */
public class Person {

    private String name;
    private String sex;
    private long age;
    private String department;
    private String position;
    //工号
    private String initial;
    private String phone;
    private String email;
    private String account;

    //表格表头
    public static final String TABLE_ID = "ID";//自增编号
    public static final String PERSON_ACCOUNT = "ACCOUNT";
    public static final String PERSON_NAME = "NAME";
    public static final String PERSON_SEX = "SEX";
    public static final String PERSON_AGE = "AGE";
    public static final String PERSON_DEPARTMENT = "DEPARTMENT";
    public static final String PERSON_POSITION = "POSITION";
    public static final String PERSON_INITIAL = "INITIAL";
    public static final String PERSON_PHONE = "PHONE";
    public static final String PERSON_EMAIL = "EMAIL";

    public Person(String account,  String name,String sex,  long age, String department, String position, String initial, String phone,String email) {
        this.account = account;
        this.age = age;
        this.department = department;
        this.email = email;
        this.initial = initial;
        this.phone = phone;
        this.name = name;
        this.sex = sex;
        this.position = position;
    }

    public Person(){
//        this.account = " ";
//        this.age = (long)0;
//        this.department = " ";
//        this.email = " ";
//        this.initial = " ";
//        this.phone = " ";
//        this.name = " ";
//        this.sex = " ";
//        this.position = " ";
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Person{" +
                "account='" + account + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", initial='" + initial + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
