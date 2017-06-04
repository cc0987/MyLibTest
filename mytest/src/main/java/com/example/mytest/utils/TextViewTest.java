package com.example.mytest.utils;

import android.view.View;
import android.widget.TextView;

/**
 * Created by CC on 2017/5/31
 */

public class TextViewTest {
    public static void setTxt(View textView, String txt) {
        TextView textView1 = (TextView) textView;
        if (textView != null) {
            textView1.setText(txt);
        }
    }

    public static void setTxt(View textView, String txt, String divideString) {
        String substring = startTxt(txt, divideString);
        setTxt(textView, substring);
    }

    public static String startTxt(String txt, String divideString) {
        return txt!=null?txt.substring(0, txt.indexOf(divideString)):null;
    }

    public static String incloudTxt(String txt, String startString, String endString) {
        return txt!=null?txt.substring(txt.indexOf(startString)+1, txt.indexOf(endString)):null;
    }
}
