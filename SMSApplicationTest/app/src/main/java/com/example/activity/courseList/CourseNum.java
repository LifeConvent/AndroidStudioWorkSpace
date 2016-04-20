package com.example.activity.courseList;

/**
 * Created by 彪 on 2016/4/19.
 */
public class CourseNum {
    private String descript;
    private String courseNum;

    public CourseNum(String courseNum, String descript) {
        this.courseNum = courseNum;
        this.descript = descript;
    }

    public String getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(String courseNum) {
        this.courseNum = courseNum;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }
}
