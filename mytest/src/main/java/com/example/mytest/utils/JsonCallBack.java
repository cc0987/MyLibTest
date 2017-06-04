package com.example.mytest.utils;

import com.example.mytest.bean.BaseModel;

/**
 * Created by CC on 2017/5/29.
 */

public interface JsonCallBack {
    /**
     * @param requestCode 判断回调
     * @param resultCode 回调结果：0失败 1成功
     * @param reply 返回的模型
     * */
    void resultData(int requestCode, int resultCode, BaseModel reply);
}
