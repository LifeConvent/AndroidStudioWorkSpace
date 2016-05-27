package com.example.ase_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    //输入密码
    private String password = "123456";
    //明文
    private String text = "AES Test";

    public static String encrypt = null;
    public static String decrypt = null;
    private Button button_encrypt;
    private Button button_decrypt;
    private TextView textView_encrypt;
    private TextView textView_decrypt;


//    private com.example.ase_demo.aesUtil aesUtil = new aesUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_encrypt = (Button) findViewById(R.id.button_encrypt);
        button_decrypt = (Button) findViewById(R.id.button_decrypt);
        textView_encrypt = (TextView) findViewById(R.id.textView_encrypt);
        textView_decrypt = (TextView) findViewById(R.id.textView_decrypt);


        encrypt = aesUtil.encrypt(password, text);
        decrypt = aesUtil.decrypt(password, encrypt);

//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------//


//        // 加密
//        try {
//            AesUtil2.startEncrypt(password,text);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------//


        button_encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_encrypt.setText(encrypt);
                if (encrypt != null)
                    Toast.makeText(getApplicationContext(), "加密成功！", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "加密失败！", Toast.LENGTH_LONG).show();
            }
        });
        button_decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_decrypt.setText(decrypt);
                if (decrypt != null)
                    Toast.makeText(getApplicationContext(), "解密成功！", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "解密失败！", Toast.LENGTH_LONG).show();
            }
        });
    }
}
