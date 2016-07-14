package com.connectionpractice.tool;

import android.app.Activity;
import android.widget.EditText;

/**
 * Created by nnit on 5/20/16.
 */
public class CheckTool {
    public static void checkBloodSugar(CharSequence s, EditText v) {
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > 1) {
                s = s.toString().subSequence(0,
                        s.toString().indexOf(".") + 2);
                v.setText(s);
                v.setSelection(s.length());
            }
        }
        if (s.toString().trim().substring(0).equals(".")) {
            s = "0" + s;
            v.setText(s);
            v.setSelection(2);
        }

        if (s.toString().startsWith("0")
                && s.toString().trim().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                v.setText(s.subSequence(1, s.toString().trim().length()));
                v.setSelection(1);
                return;
            }
        }
    }
    public static void checkBloodPressure(CharSequence s, EditText v) {
        if (s.toString().contains(".")) {
            s = s.toString().subSequence(0,
                    s.toString().indexOf("."));
            v.setText(s);
            v.setSelection(s.length());
        }

        if (s.toString().startsWith("0")
                && s.toString().trim().length() > 1) {
                v.setText(s.subSequence(1, s.toString().trim().length()));
                v.setSelection(1);
                return;
        }
    }
    public static void checkWeight(CharSequence s, EditText v) {
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > 1) {
                s = s.toString().subSequence(0,
                        s.toString().indexOf(".") + 2);
                v.setText(s);
                v.setSelection(s.length());
            }
        }
        if (s.toString().trim().substring(0).equals(".")) {
            s = "0" + s;
            v.setText(s);
            v.setSelection(2);
        }

        if (s.toString().startsWith("0")
                && s.toString().trim().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                v.setText(s.subSequence(1, s.toString().trim().length()));
                v.setSelection(1);
                return;
            }
        }
    }
    public static void autoInput(Activity ctx, boolean hasFocus, EditText v) {
        if (hasFocus) {

            if (v.getText().toString().equals("0")) {
                v.setText("");

            }
        }else {
            if (v.getText().toString().equals("")) {
                v.setText("0");
            }
            //InputMethodManager im = (InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
            //im.hideSoftInputFromWindow(ctx.getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static String checkNormalText(CharSequence s, boolean interpunction) {

        int len = s.length();
        StringBuilder result = new StringBuilder();
        for (int i=0;i<len;i++) {

            if (interpunction){
                if(isChineseOrEgnlishOrInterPunction(s.charAt(i))) {
                    result.append(s.charAt(i));
                }
            }else{
                if(isChineseOrEgnlish(s.charAt(i))) {
                    result.append(s.charAt(i));
                }
            }

        }
        return result.toString();
    }


    public static boolean isChineseOrEgnlish(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
            //|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
            //|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
            //|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
            //|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
            // || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                ){
            return true;
        }else if (ub == Character.UnicodeBlock.BASIC_LATIN) {
            if((c>=0x0041 && c < 0x005a)// for capital letters
                    || (c>=0x0061 && c < 0x007a) //for lower case
                    ) {
                return true;
            }
        }
        return false;
    }
    public static boolean isChineseOrEgnlishOrInterPunction(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
            //|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
            //|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
            //|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
            //|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
            //|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                ) {
            return true;
        }else if (ub == Character.UnicodeBlock.BASIC_LATIN) {
            if((c>=0x0020 && c < 0x007f) || c == 0x000a) {
                return true;
            }
        }else if (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            if((c>=0xff01 && c < 0xff66)) {
                return true;
            }
        }else if (ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION){
            if((c>=0x3001 && c < 0x3003)) {
                return true;
            }
        }else if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION){
            if((c>=0x201C && c < 0x201E) || c==0x2026) {
                return true;
            }
        }
        return false;
    }
}
