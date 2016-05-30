package com.connectionpractice.config;

/**
 * Created by 彪 on 2016/5/28.
 */
public class BasicConfig {
    public static String HTTP_ROOT = "http://192.168.1.102:8081/";
    public static String PATH_TO_API = "AndroidPHP/";
    public static String ENTRY_NAME = "index.php?";
    public static String NAME_SI = "id=SI001";
    public static String NAME_SU = "id=SU001";
    public static String NAME_RP = "id=RP001";
    public static String URL_SIGNIN = HTTP_ROOT + PATH_TO_API + ENTRY_NAME + NAME_SI ;
    public static String URL_SIGNUP = HTTP_ROOT + PATH_TO_API + ENTRY_NAME + NAME_SU ;
    public static String URL_PESETPASS = HTTP_ROOT + PATH_TO_API + ENTRY_NAME + NAME_RP ;

    public static final int API_ID_SI001 = 1;
    public static final int API_ID_SU001 = 2;
    public static final int API_ID_RP001 = 2;
}
