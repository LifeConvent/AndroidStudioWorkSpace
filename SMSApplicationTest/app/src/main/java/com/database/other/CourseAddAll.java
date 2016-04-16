//package com.database.tool;
//
//import android.content.ContentValues;
//
//import com.database.ob.Course;
//
///**
// * Created by 彪 on 2016/4/9.
// */
//public class CourseAddAll {
//    public static void init() {
//        Course[] courseItem = new Course[100];
//        initCourse(courseItem);
//        for (int i = 0; i < courseItem.length; i++) {
//            put(courseItem[i]);
//        }
//    }
//
//    public static void put(Course course) {
//        DBAdapter_Course base = new DBAdapter_Course(getBaseContext());
//        base.open();
//        base.insert(course);
//    }
//    /**
//     * public int NO;
//     * public String NAME;
//     * public String START_TIME;
//     * public String END_TIME;
//     * public String TEACHER;
//     * public float TAKE_TIME;
//     * public String CONTENT;
//     * public String NEEDS;
//     */
//    public static void initCourse(Course[] course) {
//        for (int i = 0; i < course.length; i++) {
//            course[i] = new Course();
//            course[i].setNO(20160100 + i);
//            course[i].setNAME(new String(String.valueOf(i * 3452)));
//            course[i].setSTART_TIME(new String(String.valueOf((6 + i) % 12 + 1)));
//            course[i].setEND_TIME(new String(String.valueOf((8 + i) % 12 + 1)));
//            course[i].setTEACHER(new String(String.valueOf(i * 2345)));
//            course[i].setTAKE_TIME((float) Math.abs(((6 + i) % 12 + 1) - ((8 + i) % 12 + 1)));
//            course[i].setCONTENT("数据库系统原理课程设计是数据库系统原理实践环节的及为重要的一部分，其目的是：\n" +
//                    "      (1)培养学生应用数据库系统原理，在需求分析的基础上，对系统进行概念设计，学会设计局部ER，全局ER图。\n" +
//                    "(2)培养学生应用数据库系统原理，在概念设计的基础上，应用关系规范化理论对系统进行逻辑设计，学会在ER图基础上，设计出易于查询和操作的合理的规范化关系模型\n" +
//                    "(3)培养学生能够应用SQL语言，对所设计的规范化关系模型进行物理设计，并且能够应用事务处理，存储过程，触发器，游标技术以保证数据库系统的数据完整性，安全性，一致性，保证数据共享和防止数据冲突。\n" +
//                    "(4) )培养学生理论与实际相结合能力，培养学生开发创新能力。\n");
//            course[i].setNEEDS("数据库课程设计毕业设计说明书一律采用单面打印。纸张大小为A4复印纸，页边距采用：上2.5cm、下2.0cm、左2.8cm、右1.2cm。无特殊要求的汉字采用小四号宋体字，行间距为1.25倍行距。页眉从正文开始，一律设为“数据库课程设计说明书”，采用宋体五号字居中书写。页码从正文开始按阿拉伯数字（宋体小五号）连续编排，居中书写。");
//        }
//    }
//
//
//}
