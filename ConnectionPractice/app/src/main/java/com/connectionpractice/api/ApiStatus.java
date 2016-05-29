package com.connectionpractice.api;

/**
 * Created by nnit on 5/5/16.
 */
public class ApiStatus {
    public static final int STATUS_SUCCESS = 0;
    public static final int STATUS_ERROR_NO_CONNECTION = 1;
    public static final int STATUS_ERROR_CONNECTION_FAILED = 2;
    public static final int STATUS_ERROR_FILE_RECEVED = 3;
    public static final int STATUS_ERROR_FILE_RECEVED2 = 4;

    public int status;
}