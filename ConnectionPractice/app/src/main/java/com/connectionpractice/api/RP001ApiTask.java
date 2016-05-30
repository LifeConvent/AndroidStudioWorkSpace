package com.connectionpractice.api;

import com.connectionpractice.config.BasicConfig;

import org.json.JSONObject;

/**
 * Created by 彪 on 2016/5/30.
 */
public class RP001ApiTask extends BaseApiTask{
    public RP001ApiTask(OnTaskCompleted listener, int id) {
        super(listener, id);
        setUrl(BasicConfig.URL_PESETPASS);
    }
    @Override
    public JSONObject decodeAES(JSONObject ob) {
        return ob;
    }
}