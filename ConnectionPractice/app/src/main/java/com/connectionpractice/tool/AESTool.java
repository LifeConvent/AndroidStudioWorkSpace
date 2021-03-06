package com.connectionpractice.tool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by 彪 on 2016/4/22.
 * <p>
 * AESTool 对象aes调用加密解密方法
 * password 密码  text加密密文  存储密文
 * aes.encryptContent = aes.encrypt(password, text);
 * <p>
 * 对象调用解密方法利用密码解密密文
 * aes.decrypt(password, encryptContent);
 */

/**
 * Created by apple28 on 15/11/20.
 */

/*
*
* DEMO
*
*
* String tt;
        try {
            tt = AESTool.decrypt("+imusic2015weshiimusic2015weshi+",
                    "MXcS/e8hlZ9D8Uf+c0Te8PyKvM2zyRa1n3pWTXsAS/0=");
            if(tt.isEmpty()){
                int b=0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
*
*
*
*
*
* */
public class AESTool {
    private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
            .toCharArray();

    public static String encryptAes(String val, String func, String salt) {
        String clearText = func+"_"+val+"_"+salt;
        String secretText = null;
        try {
            secretText = encrypt(UserInfo.getInstance().getAESkey(), clearText);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return secretText;

    }
    public static String decryptAes(String val, String func, String salt) {
        String clearText = null;
        try {
            clearText = decrypt(UserInfo.getInstance().getAESkey(), val);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] elements = clearText.split("_");
        StringBuilder result= new StringBuilder();
        int len = elements.length;
        if(elements.length>=3 && elements[0].equals(func) && elements[len-1].equals(salt)) {

            for (int i =1; i<len-2; i++){
                result.append(elements[i]+"_");

            }
            result.append(elements[len-2]);
        }

        return result.toString();

    }

    public static String encode(byte[] data) {
        int start = 0;
        int len = data.length;
        StringBuffer buf = new StringBuffer(data.length * 3 / 2);

        int end = len - 3;
        int i = start;
        int n = 0;

        while (i <= end) {
            int d = ((((int) data[i]) & 0x0ff) << 16)
                    | ((((int) data[i + 1]) & 0x0ff) << 8)
                    | (((int) data[i + 2]) & 0x0ff);

            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append(legalChars[(d >> 6) & 63]);
            buf.append(legalChars[d & 63]);

            i += 3;

            if (n++ >= 14) {
                n = 0;
                buf.append(" ");
            }
        }

        if (i == start + len - 2) {
            int d = ((((int) data[i]) & 0x0ff) << 16)
                    | ((((int) data[i + 1]) & 255) << 8);

            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append(legalChars[(d >> 6) & 63]);
            buf.append("=");
        } else if (i == start + len - 1) {
            int d = (((int) data[i]) & 0x0ff) << 16;

            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append("==");
        }

        return buf.toString();
    }

    private static int decode(char c) {
        if (c >= 'A' && c <= 'Z')
            return ((int) c) - 65;
        else if (c >= 'a' && c <= 'z')
            return ((int) c) - 97 + 26;
        else if (c >= '0' && c <= '9')
            return ((int) c) - 48 + 26 + 26;
        else
            switch (c) {
                case '+':
                    return 62;
                case '/':
                    return 63;
                case '=':
                    return 0;
                default:
                    throw new RuntimeException("unexpected code: " + c);
            }
    }

    public static byte[] decode(String s) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            decode(s, bos);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        byte[] decodedBytes = bos.toByteArray();
        try {
            bos.close();
            bos = null;
        } catch (IOException ex) {
            System.err.println("Error while decoding BASE64: " + ex.toString());
        }
        return decodedBytes;
    }

    private static void decode(String s, OutputStream os) throws IOException {
        int i = 0;

        int len = s.length();

        while (true) {
            while (i < len && s.charAt(i) <= ' ')
                i++;

            if (i == len)
                break;

            int tri = (decode(s.charAt(i)) << 18)
                    + (decode(s.charAt(i + 1)) << 12)
                    + (decode(s.charAt(i + 2)) << 6)
                    + (decode(s.charAt(i + 3)));

            os.write((tri >> 16) & 255);
            if (s.charAt(i + 2) == '=')
                break;
            os.write((tri >> 8) & 255);
            if (s.charAt(i + 3) == '=')
                break;
            os.write(tri & 255);

            i += 4;
        }
    }

    private final static String HEX = "0123456789ABCDEF";

    public static String encrypt(String seed, String cleartext) {
        byte[] result = new byte[0];
        try {
            byte[] rawKey = getRawKey(seed.getBytes());
            result = encrypt(rawKey, cleartext.getBytes());
            //return toHex(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return encode(result);
    }

    public static String decrypt(String seed, String encrypted) {
        String temp ="";
        try{
            //byte[] rawKey = getRawKey(seed.getBytes());
            byte[] rawKey = getRawKey(seed.getBytes());
            //byte[] enc = toByte(encrypted);
            byte[] enc = decode(encrypted);


            int ivsize = 16;
            int secsize = enc.length-16;
            byte[] iv = new byte[ivsize];
            byte[] sec = new byte[secsize];
            System.arraycopy(enc, enc.length - 16, iv, 0, ivsize);
            System.arraycopy(enc, 0, sec, 0, secsize);
            byte[] result = decrypt(rawKey, sec, iv);
            temp = new String(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    private static byte[] getRawKey(byte[] seed) throws Exception {
        //KeyGenerator kgen = KeyGenerator.getInstance("AES");
        //SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        //sr.setSeed(seed);
        //kgen.init(128, sr); // 192 and 256 bits may not be available
        //SecretKey skey = kgen.generateKey();
        //byte[] raw = skey.getEncoded();
        //return raw;
        return seed;
    }
    private static byte[] generateIv() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] iv = new byte[16];
        random.nextBytes(iv);
        return iv;
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");


        /*
        * int ivsize = 16;
            int secsize = enc.length-16;
            byte[] iv = new byte[ivsize];
            byte[] sec = new byte[secsize];
            */
        byte[] iv = generateIv();
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);


        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(clear);

        int ivsize = iv.length;
        int secsize = encrypted.length;
        byte[] secwithiv = new byte[ivsize+secsize];
        System.arraycopy(encrypted, 0, secwithiv, 0, secsize);
        System.arraycopy(iv, 0, secwithiv, secsize, ivsize);


        return secwithiv;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted, byte[] iv)
            throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }

    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        return result;
    }

    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
}