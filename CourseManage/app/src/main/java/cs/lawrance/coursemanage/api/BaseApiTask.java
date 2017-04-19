package cs.lawrance.coursemanage.api;

import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cs.lawrance.coursemanage.tool.NetworkUtil;

/**
 * Created by lawrance on 2017/4/18.
 */
public class BaseApiTask extends AsyncTask<Void, Void, Integer> {

    private OnTaskCompleted listener = null;
    private String result;
    private String url;
    protected List<NameValuePair> nameValuePair;
    private int id;

    public BaseApiTask(int id, OnTaskCompleted listener) {
        this.listener = listener;
        this.id = id;
        this.nameValuePair = new ArrayList<NameValuePair>();
    }

    public OnTaskCompleted getListener() {
        return listener;
    }

    public List<NameValuePair> getNameValuePair() {
        return nameValuePair;
    }

    public int getId() {
        return id;
    }

    public void setUrl(String url) {
        this.url = url;
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
            if (result != null) {
                try {
                    jsonObj = new JSONObject(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                jsonObj = null;
            }
            listener.onTaskFinish(api_status, id, jsonObj);
        }
    }

    public interface OnTaskCompleted {
        void onTaskFinish(ApiStatus status, int id, JSONObject response);
    }

}
