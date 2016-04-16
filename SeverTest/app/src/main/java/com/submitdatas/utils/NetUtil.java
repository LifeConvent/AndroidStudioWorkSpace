package com.submitdatas.utils;

/**
 * Created by 彪 on 2016/4/15.
 */

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetUtil {

    /**
     * 使用GET访问去访问网络
     *
     * @param username
     * @param password
     * @return 服务器返回的结果
     */
    public static String loginOfGet(String username, String password) {
        HttpURLConnection conn = null;
        try {
            String data = "username=" + username + "&password=" + password;
            URL url = new URL("http://192.168.1.4:8080/AndroidServer/LoginServlet?" + data);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(5000);
            conn.connect();
            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream is = conn.getInputStream();
                String state = getStringFromInputStream(is);
                return state;
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }


        return null;
    }

    /**
     * 根据输入流返回一个字符串
     *
     * @param is
     * @return
     * @throws Exception
     */
    private static String getStringFromInputStream(InputStream is) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len = -1;
        while ((len = is.read(buff)) != -1) {
            baos.write(buff, 0, len);
        }
        is.close();
        String html = baos.toString();
        baos.close();
        return html;
    }


    public static String SendRequest(String adress_Http, String strJson) {

        String returnLine = "";
        try {

            System.out.println("**************开始http通讯**************");
            System.out.println("**************调用的接口地址为**************" + adress_Http);
            System.out.println("**************请求发送的数据为**************" + strJson);
            URL my_url = new URL(adress_Http);
            HttpURLConnection connection = (HttpURLConnection) my_url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            byte[] content = strJson.getBytes("utf-8");
            out.write(content, 0, content.length);
            out.flush();
            out.close(); // flush and close
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            //StringBuilder builder = new StringBuilder();
            String line = "";
            System.out.println("Contents of post request start");
            while ((line = reader.readLine()) != null) {
                // line = new String(line.getBytes(), "utf-8");
                returnLine += line;
                System.out.println(line);
            }
            System.out.println("Contents of post request ends");
            reader.close();
            connection.disconnect();
            System.out.println("========返回的结果的为========" + returnLine);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnLine;
    }
}