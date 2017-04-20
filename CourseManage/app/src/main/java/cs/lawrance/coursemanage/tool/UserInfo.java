package cs.lawrance.coursemanage.tool;

/**
 * Created by lawrance on 2017/4/20.
 */
public class UserInfo {
    private String gStrUserAccount = "";
    private String gStrUserToken = "";

    private String gStrUserName = "";
    private String gStrUserAge = "";
    private String gStrUserPhone = "";
    private String gStrUserCity = "";
    private String gStrUserBirth = "";
    private String gStrUserSex = "";
    private String gStrUserImage = "";

    private static class UserInfoHolder {
        private static UserInfo instance = new UserInfo();
    }

    public static UserInfo getInstance() {
        return UserInfoHolder.instance;
    }

    public String getUserImage() {
        return gStrUserImage;
    }

    public void setUserImage(String mStrUserImage) {
        this.gStrUserImage = mStrUserImage;
    }

    public String getUserSex() {
        return gStrUserSex;
    }

    public void setUserSex(String mStrUserSex) {
        this.gStrUserSex = mStrUserSex;
    }

    public String getUserBirth() {
        return gStrUserBirth;
    }

    public void setUserBirth(String mStrUserBirth) {
        this.gStrUserBirth = mStrUserBirth;
    }

    public String getUserCity() {
        return gStrUserCity;
    }

    public void setUserCity(String mStrUserCity) {
        this.gStrUserCity = mStrUserCity;
    }

    public String getUserPhone() {
        return gStrUserPhone;
    }

    public void setUserPhone(String mStrUserPhone) {
        this.gStrUserPhone = mStrUserPhone;
    }

    public String getUserAge() {
        return gStrUserAge;
    }

    public void setUserAge(String mStrUserAge) {
        this.gStrUserAge = mStrUserAge;
    }

    public String getUserName() {
        return gStrUserName;
    }

    public void setUserName(String gStrUserName) {
        this.gStrUserName = gStrUserName;
    }

    public String getUserToken() {
        return gStrUserToken;
    }

    public void setUserToken(String gStrUsertoken) {
        this.gStrUserToken = gStrUsertoken;
    }

    public String getUserAccount() {
        return gStrUserAccount;
    }

    public void setUserAccount(String gStrUserAccount) {
        this.gStrUserAccount = gStrUserAccount;
    }

}
