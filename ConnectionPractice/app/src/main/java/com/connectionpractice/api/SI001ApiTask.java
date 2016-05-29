package com.connectionpractice.api;

import com.connectionpractice.config.BasicConfig;

import org.json.JSONObject;

/**
 * Created by 彪 on 2016/5/28.
 */
public class SI001ApiTask extends BaseApiTask{
    public SI001ApiTask(OnTaskCompleted listener, int id) {
        super(listener, id);
        setUrl(BasicConfig.URL_SIGNIN);
    }
    @Override
    public JSONObject decodeAES(JSONObject ob) {
        return ob;
    }
}
