package com.demo;

/**
 * Created by 彪 on 2016/4/15.
 */
public class Person {
    private int id;
    private String name;
    private String address;

    public Person() {
        // TODO Auto-generated constructor stub
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", address=" + address
                + "]";
    }

    public Person(int id, String name, String address) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
    }

}
