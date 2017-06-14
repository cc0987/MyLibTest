package com.example.cc.bilibili2333;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zhy.autolayout.AutoLayoutActivity;

public class SelectPayStateDialog extends AutoLayoutActivity {
    public static int DIALOG_PAY_RESULT = 10002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pay_state_dialog);
        /**
         * TODO 界面出现动画
         * */
        overridePendingTransition(R.anim.translate_into,R.anim.translate_out);
        //设置布局宽度全屏
        setTheTheme();
    }

    /**
     * TODO 设置布局宽度全屏
     * */
    private void setTheTheme() {
        WindowManager windowManager = getWindowManager();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = widthPixels;
        window.setAttributes(attributes);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * TODO 后退点击监听
     * */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK) {
            finishAnim();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * TODO 界面点击监听
     */
    public void onClick(View view) {
        Intent data = new Intent();
        switch (view.getId()) {
            case R.id.activity_select_pay_state_dialog:
                break;
            default:
                data.putExtra("txt",((TextView)view).getText().toString());
                data.putExtra("paystate",(String) view.getTag());
                setResult(DIALOG_PAY_RESULT, data);
                break;
        }
        finishAnim();
    }

    /**
     * TODO 界面消失动画
     * */
    private void finishAnim(){
        finish();
        overridePendingTransition(R.anim.translate_close_into,R.anim.translate_close_out);
    }
}
