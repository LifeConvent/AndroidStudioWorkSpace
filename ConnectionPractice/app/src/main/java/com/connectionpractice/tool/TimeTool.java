package com.connectionpractice.tool;

import java.util.Calendar;

/**
 * Created by nnit on 5/13/16.
 */
public class TimeTool {
    /*
    *
    * */
    public static int TIME_INDEX_YEAR = 0;
    public static int TIME_INDEX_MONTH = 1;
    public static int TIME_INDEX_DAY = 2;
    public static int TIME_INDEX_HOUR = 3;
    public static int TIME_INDEX_MINUTE = 4;
    public static int TIME_INDEX_SECOND = 5;

    public static Integer[] getDateTimeFromLong(long time) {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(time*1000);

        Integer[] result = new Integer[6];
        result[TIME_INDEX_YEAR] = cl.get(Calendar.YEAR);
        result[TIME_INDEX_MONTH] = cl.get(Calendar.MONTH);
        result[TIME_INDEX_DAY] = cl.get(Calendar.DAY_OF_MONTH);
        result[TIME_INDEX_HOUR] = cl.get(Calendar.HOUR_OF_DAY);
        result[TIME_INDEX_MINUTE] = cl.get(Calendar.MINUTE);
        result[TIME_INDEX_SECOND] = cl.get(Calendar.SECOND);
        return result;
    }
}
