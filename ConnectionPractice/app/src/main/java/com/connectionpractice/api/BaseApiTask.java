package com.connectionpractice.api;

import android.os.AsyncTask;

import com.connectionpractice.tool.NetworkUtil;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 彪 on 2016/5/28.
 */
public class BaseApiTask extends AsyncTask<Void, Void, Integer> {

    private OnTaskCompleted listener = null;
    private String result;
    protected List<NameValuePair> nameValuePair;
    private String url;
    private int id;

    public BaseApiTask(OnTaskCompleted listener, int id) {
        this.listener = listener;
        this.id = id;
        this.nameValuePair = new ArrayList<NameValuePair>();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<NameValuePair> getNameValuePair() {

        return this.nameValuePair;
    }

    public int getId() {
        return id;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        try {
            result = NetworkUtil.httpPost(url, nameValuePair);
        } catch (Exception e) {
            return ApiStatus.STATUS_ERROR_CONNECTION_FAILED;
        }
        return ApiStatus.STATUS_SUCCESS;
    }

    protected void onPostExecute(Integer status) {
        if (isCancelled()) {
            return;
        }
        if (listener != null) {
            JSONObject jsonObj = null;
            ApiStatus api_status = new ApiStatus();
            api_status.status = status;
            try {
                jsonObj = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonObj = decodeAES(jsonObj);
            listener.onTaskFinish(api_status, id, jsonObj);
        }
    }

    public JSONObject decodeAES(JSONObject ob) {
        return ob;
    }

    public interface OnTaskCompleted {
        void onTaskFinish(ApiStatus api_status, int id, JSONObject response);
    }
}
