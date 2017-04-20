package cs.lawrance.coursemanage.config;

/**
 * Created by lawrance on 2017/4/18.
 */
public class BasicConfig {

    public static String PASS_KEY = "";

    //    URL TEMP
//    public static String HTTP_ROOT = "http://localhost/";
    public static String HTTP_ROOT = "http://iq8k2a.natappfree.cc/";
    public static String PATH_TO_API = "CMBack/";
    public static String ENTRY_NAME = "index.php/";
    public static String MODEL_HOME = "Home/";
    public static String CONTROLLER_HOME = "Home/";
    public static String CONTROLLER_SIGN = "Sign/";
    public static String CONTROLLER_UPDATE = "Update/";
    public static String METHOD_ = "";
    public static String METHOD_SIGNIN = "signIn";
    public static String METHOD_UPDATE = "update";

    //    URL
    public static String URL_SIGNIN = HTTP_ROOT + PATH_TO_API + ENTRY_NAME + MODEL_HOME + CONTROLLER_SIGN + METHOD_SIGNIN;

    //    版本更新地址
    public static String UPDATE_URL = HTTP_ROOT + PATH_TO_API + ENTRY_NAME + MODEL_HOME + CONTROLLER_UPDATE + METHOD_UPDATE;

    //    API
    public static final int API = 0;
    public static final int API_ID_SIGNIN = 1;
}

