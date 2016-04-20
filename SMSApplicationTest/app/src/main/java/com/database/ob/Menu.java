package com.database.ob;

/**
 * Created by 彪 on 2016/4/16.
 */
public class Menu {
    private String name;
    private int imageId;

    public Menu(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
