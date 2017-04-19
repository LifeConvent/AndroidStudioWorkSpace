package cs.lawrance.coursemanage.tool;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by lawrance on 2017/4/18.
 */
public class NetworkUtil {
    public static String httpPost(String apiUrl, List<NameValuePair> nameValuePair) throws Exception {
        HttpClient httpClient = new DefaultHttpClient();

        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 1000);
        HttpConnectionParams.setSoTimeout(httpParams, 1000);

        HttpPost httpPost = new HttpPost(apiUrl);
        int responseCode = 0;
        String result = "";

        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, HTTP.UTF_8));
        //HTTP post
        HttpResponse response = httpClient.execute(httpPost);
        responseCode = response.getStatusLine().getStatusCode();
        if (responseCode == HttpStatus.SC_OK) {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            response.getEntity().writeTo(byteArrayOutputStream);
            result = byteArrayOutputStream.toString();
        } else if (responseCode == HttpStatus.SC_NOT_FOUND) {

            //cannot find server
            throw new FileNotFoundException();
        } else if (responseCode == HttpStatus.SC_REQUEST_TIMEOUT) {

            //connect timeout
            throw new Exception();
        }
        return result;
    }
}
