package cs.lawrance.coursemanage.api;

import cs.lawrance.coursemanage.config.BasicConfig;

/**
 * Created by lawrance on 2017/4/20.
 */
public class LoginInApiTask extends BaseApiTask {
    public LoginInApiTask(int id, OnTaskCompleted listener) {
        super(id, listener);
        setUrl(BasicConfig.URL_SIGNIN);
    }
}
