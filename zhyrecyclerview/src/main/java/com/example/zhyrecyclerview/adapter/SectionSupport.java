package com.example.zhyrecyclerview.adapter;

/**
 * RecyclerView给相同一类的条目数据添加头布局的接口支持
 * Created by cj_28 on 2016/7/28.
 */
public interface SectionSupport<T> {
    int sectionHeaderLayoutId();//返回头布局
    int sectionTitleTextViewId();//返回头布局中的View
    String getTitle(T item);//获取头显示的文本
}
