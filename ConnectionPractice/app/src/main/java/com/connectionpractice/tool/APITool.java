package com.connectionpractice.tool;


import com.connectionpractice.config.BasicConstant;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nnit on 5/7/16.
 */
public class APITool {
    public static String getResult(JSONObject ob) {
        String result = ob.optString(BasicConstant.API_RESULT_FIELD);
        if(result.isEmpty()){
            return "";
        }else{
            return result;
        }

    }
    public static String getAttachContent(JSONObject ob) {
        String others = ob.optString(BasicConstant.API_OTHERS_FIELD);
        String content = "";
        try {
            if(!others.isEmpty()){
                JSONObject othersJson = new JSONObject(others);
                String attach = othersJson.optString(BasicConstant.API_OTHERS_ATTACH_FIELD);
                JSONObject attachJson = new JSONObject(attach);
                content = attachJson.optString(BasicConstant.API_OTHERS_ATTACH_CONTENT_FIELD);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return content;
        }

    }
    public static String getDetails(JSONObject ob){
        if(ob.isNull(BasicConstant.API_DETAILS_FIELD)){
            return "";
        }
        String temp = ob.optString(BasicConstant.API_DETAILS_FIELD);
        if(temp.equals("[]")) {
            temp = "";
        }
        return temp;
    }
    public static String getDetailsByKey(JSONObject ob,String key) {
        String details = getDetails(ob);
        String content = "";
        try {
            JSONObject jsonDetails = new JSONObject(details);
            if(jsonDetails.isNull(key)) {
                content = "";
            }else {
                content = jsonDetails.optString(key);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return content;
        }

    }
    public static void setDetailsByKey(JSONObject ob,String key,String val) {
        String details = ob.optString(BasicConstant.API_DETAILS_FIELD);
        try {
            JSONObject jsonDetails = new JSONObject(details);
            jsonDetails.put(key, val);
            ob.put(BasicConstant.API_DETAILS_FIELD,jsonDetails);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public static void setDetails(JSONObject ob,String details) {
        //String details = ob.optString(BasicConstant.API_DETAILS_FIELD);
        try {
            //JSONObject jsonDetails = new JSONObject(details);
            //jsonDetails.put(key, val);
            ob.put(BasicConstant.API_DETAILS_FIELD,details);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public static String getMessagesByKey(JSONObject ob,String key) {
        String details = ob.optString(BasicConstant.API_MESSAGES_FIELD);
        String content = "";
        try {
            JSONObject jsonDetails = new JSONObject(details);
            content = jsonDetails.optString(key);

        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return content;
        }

    }
}
