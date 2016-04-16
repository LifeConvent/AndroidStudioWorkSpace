package com.example.ase_demo;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by 彪 on 2016/4/8.
 */
public class AesUtil2 {
    /*
     * @param content:
     *
     * @param password:
     */
    public static byte[] encrypt(String content, String password) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        // 使用128 位
        kgen.init(128, new SecureRandom(password.getBytes()));
        SecretKey secretKey = kgen.generateKey();
        byte[] encodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(encodeFormat, "AES");
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("AES");
        // 加密内容进行编码
        byte[] byteContent = content.getBytes();
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 正式执行加密操作
        byte[] result = cipher.doFinal(byteContent);
        return result;
    }

    public static byte[] decrypt(byte[] content, String password) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        // 使用128 位
        kgen.init(128, new SecureRandom(password.getBytes("utf-8")));
        SecretKey secretKey = kgen.generateKey();
        byte[] encodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(encodeFormat, "AES");
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("AES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, key);
        // 正式执行解密操作
        byte[] result = cipher.doFinal(content);
        return result;
    }

    public static void startEncrypt(String password, String text) throws Exception {
        byte[] encryptResult = AesUtil2.encrypt(text, password);
        MainActivity.encrypt = AesUtil2.parseByte2HexStr(encryptResult);
        // 解密
        //byte[] decryptFrom = AesUtil2.parseHexStr2Byte(encrypt);
        byte[] decryptResult = AesUtil2.decrypt(encryptResult, password);
        // 解密内容进行解码
        MainActivity.decrypt = new String(decryptResult, "utf-8").toString(); //decryptResult.toString();
    }

    /**
     * 二进制--》十六进制转化
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 十六进制--》二进制转化
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }

        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
