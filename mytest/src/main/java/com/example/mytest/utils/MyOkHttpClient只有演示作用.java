package com.example.mytest.utils;

import android.os.AsyncTask;

import java.util.Map;

/**
 * Created by CC on 2017/6/2
 */

public class MyOkHttpClient只有演示作用 extends AsyncTask<String,Integer,String> {

    private Map<String,String> params;
    private String url;

    public MyOkHttpClient只有演示作用(String url, Map<String,String> params) {
        this.params = params;
        this.url = url;
    }

    @Override
    protected String doInBackground(String... inParams) {
        /*OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : params.keySet()){
            String value = params.get(key);
            builder.add(key, value);
        }
        FormBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });*/
        return null;
    }
}
