package com.example.mytest.bean;

import java.util.List;

/**
 * Created by CC on 2017/5/27
 * 基本对象，存储最外层信息，视情况进行改变。
 */

public class BaseModel {

    private int status;
    private List<MyOrderPay> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<MyOrderPay> getData() {
        return data;
    }

    public void setData(List<MyOrderPay> data) {
        this.data = data;
    }
}
