package com.example.mytest.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by CC on 2017/5/29.
 */

public class FragmentUtil {
    private static Fragment mOneFragment;

    public static void getAndSetPages(AppCompatActivity activity, int resFrameLayout, String tag, Class<? extends Fragment> cls){
        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (mOneFragment != null) transaction.hide(mOneFragment);
        mOneFragment = fm.findFragmentByTag(tag);
        if (mOneFragment != null) {
            transaction.show(mOneFragment);
        }else{
            try {
                mOneFragment = cls.getConstructor().newInstance();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            transaction.add(resFrameLayout,mOneFragment,tag);
        }
        transaction.commit();
    }

    /**
     * 公有变量设为null清除缓存
     * */
    public static void clearStorage(){
        mOneFragment = null;
    }
}
