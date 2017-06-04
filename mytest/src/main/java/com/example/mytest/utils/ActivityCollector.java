package com.example.mytest.utils;

import android.support.v7.app.AppCompatActivity;

import java.util.LinkedList;

/**
 * Created by CC on 2017/5/30.
 */

public class ActivityCollector {
    public static LinkedList<AppCompatActivity> activities = new LinkedList<>();

    public static void addActivity(AppCompatActivity activity) {
        activities.add(activity);
    }

    public static void removeActivity(AppCompatActivity activity) {
        activities.remove(activity);
    }

    public static void removeAllActivity() {
        for (AppCompatActivity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
