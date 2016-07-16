package com.connectionpractice.api;

import com.connectionpractice.config.BasicConfig;

import org.json.JSONObject;

/**
 * Created by lawrance on 16/7/16.
 */
public class GUIWMApiTask extends BaseApiTask{
    public GUIWMApiTask(OnTaskCompleted listener, int id) {
        super(listener, id);
        setUrl(BasicConfig.URL_GETUSERINFO);
    }
    @Override
    public JSONObject decodeAES(JSONObject ob) {
        return ob;
    }
}