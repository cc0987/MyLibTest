package com.example.mytest.utils;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

import com.example.mytest.bean.BaseModel;

/**
 * Created by CC on 2017/5/27
 */

public class HttpJsonUtils {

    public void getData(final Activity activity, final JsonCallBack jsonCallBack, final int requestCode, String url, Map<String, String> params, final Class className) {
        RequestParams entity = new RequestParams(url);
        for(String key : params.keySet()){
            String value = params.get(key);
            entity.addBodyParameter(key,value);
        }
        x.http().get(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("metest", "onSuccess: "+result);
                Gson gson = new Gson();
                BaseModel baseModel = (BaseModel) gson.fromJson(result, className);
                jsonCallBack.resultData(requestCode,1,baseModel);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("metest", "onError: "+ex.getMessage());
                jsonCallBack.resultData(requestCode,0,null);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("metest", "onCancelled: "+"Cancel");
                jsonCallBack.resultData(requestCode,0,null);
            }

            @Override
            public void onFinished() {
                Log.e("metest", "onFinished: "+"Finish");

            }
        });
    }

    public void getDataLoading(final Activity activity, final JsonCallBack jsonCallBack, final int requestCode, String url, Map<String, String> params, final Class className) {
        LoadDialog.show(activity,"加载中");
        RequestParams entity = new RequestParams(url);
        for(String key : params.keySet()){
            String value = params.get(key);
            entity.addBodyParameter(key,value);
        }
        x.http().get(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("metest", "onSuccess: "+result);
                Gson gson = new Gson();
                BaseModel baseModel = (BaseModel) gson.fromJson(result, className);
                jsonCallBack.resultData(requestCode,1,baseModel);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("metest", "onError: "+ex.getMessage());
                jsonCallBack.resultData(requestCode,0,null);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("metest", "onCancelled: "+"Cancel");
                jsonCallBack.resultData(requestCode,0,null);
            }

            @Override
            public void onFinished() {
                Log.e("metest", "onFinished: "+"Finish");
                LoadDialog.dismiss(activity);
            }
        });
    }
}
