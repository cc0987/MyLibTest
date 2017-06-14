package com.example.mytest.utils;

import android.app.Activity;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CC
 */

public class JavaInterfaceUtils {

    private static int sssmall_padding = 10;
    private static int small_padding = 25;
    private static int big_padding = 50;
    private static int small_textSize = 32;
    private static int medium_textSize = 35;
    private static int big_textSize = 39;
    private static int title_height = 30;
    private static int windows_Wight = 768;

    public static void initLayout(AutoLayoutActivity activity){
        sssmall_padding = 10;
        small_padding = 25;
        big_padding = 50;
        small_textSize = 32;
        medium_textSize = 35;
        big_textSize = 39;
        title_height = 30;
        //获取屏幕高宽
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int windowsHeight = Math.max(metric.heightPixels,metric.widthPixels);
        int windowsWight = Math.min(metric.heightPixels,metric.widthPixels);
        windows_Wight = windowsWight;
        sssmall_padding = (int) ((double)sssmall_padding/1280d*windowsHeight);
        small_padding = (int) ((double)small_padding/1280d*windowsHeight);
        big_padding = (int) ((double)big_padding/1280d*windowsHeight);
        small_textSize = (int) ((double)small_textSize/1280d*windowsHeight);
        medium_textSize = (int) ((double)medium_textSize/1280d*windowsHeight);
        big_textSize = (int) ((double)big_textSize/1280d*windowsHeight);
        title_height = (int) ((double)title_height/1280d*windowsHeight);

    }

    //标题
    public static TextView createTitle(Activity activity, String text) {
        TextView textView = new TextView(activity);
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, medium_textSize);
        textView.setPadding(big_padding, big_padding, big_padding, big_padding);
        textView.setBackgroundColor(Color.WHITE);
        return textView;
    }

    //占位 或者 小标题
    public static TextView createTextViewEmpty(Activity activity, String text) {
        TextView textView = new TextView(activity);
        if (text != null) {
            textView.setText(text);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, small_textSize);
            textView.setPadding(small_padding, small_padding, small_padding, small_padding);
        } else {
            textView.setHeight(title_height);
        }
        return textView;
    }

    //发车到站地点
    public static LinearLayout createAddr(Activity activity, String startAddr, String toAddr, String fromTime, String toTime) {
        LinearLayout linear = new LinearLayout(activity);
        linear.setOrientation(LinearLayout.VERTICAL);
        linear.setBackgroundColor(Color.WHITE);
        linear.setPadding(small_padding,sssmall_padding,small_padding,sssmall_padding);
        linear.addView(createText(activity, startAddr, big_textSize, Color.BLUE));
        linear.addView(createText(activity, "始发地", small_textSize));
        linear.addView(createText(activity, toAddr, big_textSize, Color.BLUE));
        linear.addView(createText(activity, "目的地", small_textSize));
        linear.addView(createText(activity, "装车时间：" + fromTime + " → " + toTime, medium_textSize));
        return linear;
    }

    public static final class GridBulid{
        private final List<List<String>> tables = new ArrayList<>();
        public GridBulid add(String... txt){
            if (tables.size() == 0) {
                for (int i = 0; i < txt.length; i++) {
                    ArrayList<String> list = new ArrayList<>();
                    tables.add(list);
                }
            }
            for (int i = 0; i < txt.length; i++) {
                tables.get(i).add(txt[i]);
            }
            return this;
        }
    }

    //货源信息
    public static GridLayout createStoreInfo(Activity activity, GridBulid gridBulid, double... widthPercent) {
        GridLayout grid = new GridLayout(activity);
        grid.setColumnCount(gridBulid.tables.size());
        grid.setPadding(0,small_padding,0,small_padding);
        grid.setBackgroundColor(Color.WHITE);
        for (int i = 0; i < gridBulid.tables.get(0).size(); i++) {
            for (int j = 0; j < gridBulid.tables.size(); j++) {
                TextView text = createText(activity, gridBulid.tables.get(j).get(i), medium_textSize, j==0?Color.parseColor("#7b7a7a"):Color.parseColor("#494949"));
                text.setPadding(j==0?big_padding:0,sssmall_padding,sssmall_padding,sssmall_padding);
                grid.addView(text, (int) (windows_Wight*widthPercent[j]),medium_textSize*2);
            }
        }
        return grid;
    }

    //承运方信息
    public static LinearLayout createPerson(Activity activity,int imgSource, GridBulid gridBulid2) {
        LinearLayout linear = new LinearLayout(activity);
        linear.setGravity(Gravity.CENTER_VERTICAL);
        linear.setOrientation(LinearLayout.HORIZONTAL);
        linear.setBackgroundColor(Color.WHITE);
        linear.setPadding(small_padding,0,0,0);
        ImageView img = new ImageView(activity);
        img.setImageResource(imgSource);
        linear.addView(img);
        GridLayout storeInfo = createStoreInfo(activity, gridBulid2, 0.3, 0.7);
        storeInfo.setPadding((-1*small_padding),0,0,0);
        linear.addView(storeInfo);
        return linear;
    }


    /**
     * public
     */
    public static TextView createText(Activity activity, String text, int textSize, int textColor) {
        TextView textView = createText(activity, text, textSize);
        textView.setTextColor(textColor);
        return textView;
    }

    public static TextView createText(Activity activity, String text, int textSize) {
        TextView textView = new TextView(activity);
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        textView.setPadding(small_padding, sssmall_padding, small_padding, sssmall_padding);
        return textView;
    }
}
