package com.ex.severtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demo.Person;
import com.submitdatas.utils.HttpUtils;
import com.submitdatas.utils.JsonTools;
import com.submitdatas.utils.NetUtil;

import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends Activity {

    private static final String TAG = "Json Get Test";
    private EditText etUsername;
    private EditText etPassword;
    private Button btCommit;
    private TextView showTv;
    public static final String path = "http://172.16.5.253/DavidTest/index.php?c=C013&m=Signin";
    //public static final String path="http://www.jsonlint.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btCommit = (Button) findViewById(R.id.get_submit);
        showTv = (TextView) findViewById(R.id.show_back);
    }

//    public void doGet(View v) {
//        final String username = etUsername.getText().toString();
//        final String password = etPassword.getText().toString();
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                //访问网络要在子线程中实现，使用get取数据
//                final String state = NetUtil.loginOfGet(username, password);
//
//                //执行在主线程上
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        //就是在主线程上操作,弹出结果
//                        Toast.makeText(MainActivity.this, state, Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//            }
//        }).start();
//
//    }

    public void login(View v) {
        // 获取点击控件的id
        int id = v.getId();
        // 根据id进行判断进行怎么样的处理
        switch (id) {
            // 登陆事件的处理
            case R.id.get_submit:
                // 获取用户名
                final String userName = etUsername.getText().toString();
                // 获取用户密码
                final String userPass = etPassword.getText().toString();
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPass)) {
                    Toast.makeText(this, "用户名或者密码不能为空", Toast.LENGTH_LONG).show();
                } else {
                    // 开启子线程
                    Toast.makeText(getApplicationContext(), "正在提交", Toast.LENGTH_SHORT).show();
                    new Thread() {
                        public void run() {
                            //loginByPost(userName, userPass); // 调用loginByPost方法,发送post请求
                            //向服务器发送post请求
                            String response = HttpUtils.doPost(userName, userPass, path);
                            if (response != null)
                                showTv.setText(response);
                            else
                                Toast.makeText(getApplicationContext(), "返回Json为空", Toast.LENGTH_SHORT).show();
                        }

                        ;
                    }.start();
                }
                break;
            case R.id.get_json:
                String json_string = HttpUtils.getJsonContent(path);
                Log.i(TAG, "1212" + json_string);
                showTv.setText(json_string);
                Toast.makeText(getApplicationContext(), "GET JSON", Toast.LENGTH_SHORT).show();

                //Json 数据转为Person对象
//                Person person= JsonTools.getPerson("person", jsonstring);
//                Log.i(TAG, person.toString());
                break;
        }
    }

    public void loginByPost(String userName, String userPass) {

        try {

            // 请求的地址
            //  path
            // 根据地址创建URL对象
            URL url = new URL(path);
            // 根据URL对象打开链接
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            // 设置请求的方式
            urlConnection.setRequestMethod("POST");
            // 设置请求的超时时间
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            // 传递的数据
            String data = "username=" + URLEncoder.encode(userName, "UTF-8")
                    + "&userpass=" + URLEncoder.encode(userPass, "UTF-8");


//            // 设置请求的头
//            urlConnection.setRequestProperty("Connection", "keep-alive");
//            // 设置请求的头
//            urlConnection.setRequestProperty("Content-Type",
//                    "application/x-www-form-urlencoded");
//            // 设置请求的头
//            urlConnection.setRequestProperty("Content-Length",
//                    String.valueOf(data.getBytes().length));
//            // 设置请求的头
//            urlConnection
//                    .setRequestProperty("User-Agent",
//                            "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");


            urlConnection.setDoOutput(true); // 发送POST请求必须设置允许输出
            urlConnection.setDoInput(true); // 发送POST请求必须设置允许输入

            //setDoInput的默认值就是true
            //获取输出流
            OutputStream os = urlConnection.getOutputStream();
            os.write(data.getBytes());
            os.flush();

            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    baos.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                baos.close();
                // 返回字符串
                final String result = new String(baos.toByteArray());

                // 通过runOnUiThread方法进行修改主线程的控件内容
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 在这里把返回的数据写在控件上
                        showTv.setText(result);
                    }
                });

            } else {
                System.out.println("链接失败.........");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
