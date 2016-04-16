package com.submitdatas.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 彪 on 2016/4/15.
 */
//http访问，获取json字符串
public class HttpUtils {

    public static String getJsonContent(String path) {
        try {
            URL url = new URL(path);
            //得到HttpURLConnection对象，通过调用URL.openConnection()方法得到该对象
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置时间延迟
            connection.setConnectTimeout(3000);
            //设置GET方法获取
            connection.setRequestMethod("GET");

            connection.setDoInput(true);
            int code = connection.getResponseCode();
            if (code == 200) {
                return changeInputString(connection.getInputStream());
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "";
    }

    private static String changeInputString(InputStream inputStream) {

        String jsonString = "";
        ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(data)) != -1) {
                outPutStream.write(data, 0, len);
            }
            inputStream.close();
            outPutStream.close();
            jsonString = new String(outPutStream.toByteArray());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonString;
    }

    /**----------------------   Post方式去的服务器返回的Json字符串  ------------------------------*/
    public static String doPost(String username, String password, String path) {
        try {

            /**
             * Post方法步骤
             * 1. 初始化连接地址，并建立连接对象
             * 2. 设置读取参数和相应的请求方式
             * 3. 取消缓存和重定向，设置直接获取参数-----/
             * 4. 建立连接
             * 5. 初始化输出流，写入输出数据后关闭输出流(向服务器写入数据)
             * 6. 建立输入流对象和缓冲区，读入数据(从服务器读出数据)
             * 7. 关闭流，返回取得的数据
             * */

            /**-----------------------1. 初始化连接地址，并建立连接对象---------------------------*/
            //初始化路径
            URL url = new URL(path);
            //建立连接
            HttpURLConnection uRLConnection = (HttpURLConnection) url.openConnection();

            /**------------------------2. 设置读取参数和相应的请求方式----------------------------*/
            //设置可读可写，POST必须
            uRLConnection.setDoInput(true);
            uRLConnection.setDoOutput(true);
            //设置请求的方式
            uRLConnection.setRequestMethod("POST");

            /**-------------------3. 取消缓存和重定向，设置直接获取参数---------------------------*/
            //不使用缓冲
            uRLConnection.setUseCaches(false);
            //不链接重定向
            uRLConnection.setInstanceFollowRedirects(false);
            //只有设置contentType为application/x-www-form-urlencoded，
            //servlet就可以直接使用request.getParameter("username");直接得到所需要信息
            uRLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            /**-------------------------------4. 建立连接-----------------------------------------*/
            //进行连接
            uRLConnection.connect();

            /**-------------5. 初始化输出流，写入输出数据后关闭输出流(向服务器写入数据)-----------*/
            //数据输出流，将java基本数据类型写入数据输出流中，并可以通过数据输入流DataInputStream将数据读入
            DataOutputStream out = new DataOutputStream(uRLConnection.getOutputStream());
            //username+password等要写入服务器的数据
            String content = "username=" + username + "&password=" + password;
            //向服务器写入数据
            out.writeBytes(content);
            //刷新数据输出流
            out.flush();
            //关闭输出流
            out.close();


            /**--------------6. 建立输入流对象和缓冲区，读入数据(从服务器读出数据)-----------------*/
            //获得相应输入对象
            InputStream is = uRLConnection.getInputStream();
            //缓存区读取，缓存区数据满了才会对目的地进行一次写出
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String response = "";
            //br.readline 按行读入缓冲区中的数据
            String readLine = null;
            while ((readLine = br.readLine()) != null) {
                //response = br.readLine();
                response = response + readLine;
            }

            /**-------------------------7. 关闭流，返回取得的数据---------------------------------*/
            //关闭输入流缓冲流
            is.close();
            br.close();
            //关闭连接 即设置 http.keepAlive = false;
            uRLConnection.disconnect();
            return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
