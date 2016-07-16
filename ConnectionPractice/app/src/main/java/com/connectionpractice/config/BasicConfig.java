package com.connectionpractice.config;

/**
 * Created by 彪 on 2016/5/28.
 */
public class BasicConfig {
    //    public static String HTTP_ROOT = "http://192.168.1.103/";
    public static String HTTP_ROOT = "http://42.122.65.148:9000/";
    public static String PATH_TO_API = "AndroidPHP/";
    public static String ENTRY_NAME = "index.php?";
    public static String NAME_SI = "id=SI001";
    public static String NAME_SU = "id=SU001";
    public static String NAME_RP = "id=RP001";
    public static String NAME_GC = "id=GetCityInfo";
    public static String NAME_SUI = "id=SUI001";
    public static String NETHOD_SUI_SUI = "&m=SUI";
    public static String NETHOD_SUI_SUIM = "&m=SUIM";
    public static String NETHOD_SUI_UUI = "&m=UUI";
    public static String NETHOD_SUI_UUIM = "&m=UUIM";
    public static String NETHOD_SUI_IUI = "&m=IUI";
    public static String NETHOD_SUI_IUIM = "&m=IUIM";

    public static String URL_SIGNIN = HTTP_ROOT + PATH_TO_API + ENTRY_NAME + NAME_SI;
    public static String URL_SIGNUP = HTTP_ROOT + PATH_TO_API + ENTRY_NAME + NAME_SU;
    public static String URL_PESETPASS = HTTP_ROOT + PATH_TO_API + ENTRY_NAME + NAME_RP;
    public static String URL_GETCITYINFO = HTTP_ROOT + PATH_TO_API + ENTRY_NAME + NAME_GC;
    public static String URL_SETUSERINFO = HTTP_ROOT + PATH_TO_API + ENTRY_NAME + NAME_SUI;
    public static String URL_GETUSERINFO = HTTP_ROOT + PATH_TO_API + ENTRY_NAME + NAME_SUI + NETHOD_SUI_SUIM;

    //拷贝来,需要更改
    public static String UPDATE_URL = "http://221.238.232.136/version.php";

    public static final int API_ID_SI001 = 1;
    public static final int API_ID_SU001 = 2;
    public static final int API_ID_RP001 = 3;
    public static final int API_ID_GC000 = 4;
    public static final int API_ID_GC001 = 5;
    public static final int API_ID_GC002 = 6;
    public static final int API_ID_RS001 = 7;
    public static final int API_ID_SUI001 = 8;
    public static final int API_ID_GUIW001 = 9;

}
