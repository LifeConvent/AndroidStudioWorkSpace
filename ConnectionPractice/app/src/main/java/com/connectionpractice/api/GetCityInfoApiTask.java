package com.connectionpractice.api;

import com.connectionpractice.config.BasicConfig;

import org.json.JSONObject;

/**
 * Created by 彪 on 2016/6/4.
 */
public class GetCityInfoApiTask extends BaseApiTask{
    public GetCityInfoApiTask(BaseApiTask.OnTaskCompleted listener, int id) {
        super(listener, id);
        setUrl(BasicConfig.URL_GETCITYINFO);
    }
    @Override
    public JSONObject decodeAES(JSONObject ob) {
        return ob;
    }
}
