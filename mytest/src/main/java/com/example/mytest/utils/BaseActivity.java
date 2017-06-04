package com.example.mytest.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zhy.autolayout.AutoLayoutActivity;

/**
 * Created by CC on 2017/5/30
 */

public class BaseActivity extends AutoLayoutActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    /**
     * 关闭全部界面
     */
    protected void ActivitysExit() {
        ActivityCollector.removeAllActivity();
    }

    /**
     * 关闭全部界面以及服务?
     */
    protected void AppExit() {
        try {
            ActivitysExit();
            ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.killBackgroundProcesses(getPackageName());
            System.exit(0);
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    protected void hideTitleUIMenu() {

    }

    protected void hideActionUIMenu() {
        getActionBar().hide();
    }

    /**
     * 隐藏虚拟按键，并且全屏
     *///metest
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    protected void showToast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    protected void showLoge(String logeString) {
        Log.e("test_e", logeString);
    }
}
