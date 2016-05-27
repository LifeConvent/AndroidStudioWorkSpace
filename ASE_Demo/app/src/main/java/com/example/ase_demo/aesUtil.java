package com.example.ase_demo;

/**
 * Created by 彪 on 2016/4/5.
 */

import android.widget.Toast;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class aesUtil {

    public static final String TAG = "AESUtils";
    //基本编码格式的密钥

    public static HashMap hashMap = new HashMap();

    private static byte[] rawkey;

    //输入密码seed,加密文本clearText
    public static String encrypt(String seed, String clearText) {
        // Log.d(TAG, "加密前的seed=" + seed + ",内容为:" + clearText);
        byte[] result = null;
        try {
            //将输入密码转为byte一并加密,得到密钥
            rawkey = getRawKey(seed.getBytes());
            // System.out.println("加密的密钥为：" + rawkey);
            //使用密钥进行加密
            result = encrypt(rawkey, clearText.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String content = toHex(result);
        // Log.d(TAG, "加密后的内容为:" + content);
        return content;

    }

    public static String decrypt(String seed, String encrypted) {
        // Log.d(TAG, "解密前的seed=" + seed + ",内容为:" + encrypted);

        try {
            byte[] rawkey = getRawkey(seed);
            byte[] enc = toByte(encrypted);
//            byte[] rawkey = getRawkey(seed);
//            System.out.println("加密的密钥为：" + rawkey);
            byte[] result = decrypt(rawkey, enc);
            String content = new String(result);
            // Log.d(TAG, "解密后的内容为:" + content);
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static byte[] getRawkey(String seed) throws Exception{
//        Iterator it = hashMap.keySet().iterator();
//        while (it.hasNext()) {
//            String keygen = (String) it.next();
//            if (seed.equals(keygen)) {
//                rawkey = hashMap.get(keygen.getBytes()).toString().getBytes();
//            }
//        }
//        KeyGenerator keygen = KeyGenerator.getInstance("AES");
//        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
//        sr.setSeed(seed.getBytes());
//        keygen.init(128, sr);
//        //生成一个密钥
//        SecretKey sKey = keygen.generateKey();
//        //返回基本编码格式的密钥，如果此密钥不支持编码，则返回 null
//        rawkey = sKey.getEncoded();
        return rawkey;
    }

    private static byte[] getRawKey(byte[] seed) throws Exception {
        //生成对称密钥（生成指定算法密钥的对象）
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecureRandom sr = new SecureRandom(seed);
                //SecureRandom.getInstance("SHA1PRNG");
       // sr.setSeed(seed);
        //指定大小的密钥对象
        keygen.init(128, sr);
        //生成一个密钥
        SecretKey sKey = keygen.generateKey();
        //返回基本编码格式的密钥，如果此密钥不支持编码，则返回 null
//        byte[]
        rawkey = sKey.getEncoded();
        //参数全为Byte数组
//        hashMap.put(seed,raw);
        return rawkey;
    }

    //密钥raw,文本clear
    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        //根据给定的enCodeFormat(即raw)字节数组构造一个用AES算法加密的密钥。
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        //创建密码器
        Cipher cipher = Cipher.getInstance("AES");
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // 以加密的方式用密钥初始化此 Cipher
        //密钥skeySpec
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(
                new byte[cipher.getBlockSize()]));
        //按操作加密clear
        byte[] encrypted = cipher.doFinal(clear);
        //加密后的密文
        return encrypted;
    }

    //密钥raw,密文encryptrd
    private static byte[] decrypt(byte[] raw, byte[] encrypted)
            throws Exception {
        //构造AES算法加密的密钥
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        //创建加密器
        Cipher cipher = Cipher.getInstance("AES");
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        //以解密方式初始化cipher
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(
                new byte[cipher.getBlockSize()]));
        //按操作解密密文
        byte[] decrypted = cipher.doFinal(encrypted);
        //解密后的明文
        return decrypted;
    }

    //Byte16进制转为String
    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }

    //16to2
    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    //自定义getBytes()
    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        return result;
    }

    //转为16进制
    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    //-------------?
    private static void appendHex(StringBuffer sb, byte b) {
        final String HEX = "0123456789ABCDEF";
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

}