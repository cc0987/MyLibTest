package com.example.pulltorefreshrecyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.mytest.utils.BaseActivity;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements PullToRefreshListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        测试1();
    }

    TestAdapter testAdapter;
    ArrayList<String> strings;
    PullToRefreshRecyclerView pullToRefreshRV;
    private void 测试1() {
        pullToRefreshRV = (PullToRefreshRecyclerView) findViewById(R.id.pullToRefreshRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pullToRefreshRV.setLayoutManager(layoutManager);
        strings = new ArrayList<>();
        strings.add("test1");
        testAdapter = new TestAdapter(this, strings, R.layout.item);
        pullToRefreshRV.setAdapter(testAdapter);
        //是否开启下拉刷新功能
        pullToRefreshRV.setPullRefreshEnabled(true);
        //是否开启上拉加载功能
        pullToRefreshRV.setLoadingMoreEnabled(true);
        //设置是否显示上次刷新的时间
        pullToRefreshRV.displayLastRefreshTime(true);
        //设置刷新回调
        pullToRefreshRV.setPullToRefreshListener(this);
        //主动触发下拉刷新操作
        //pullToRefreshRV.onRefresh();
    }

    @Override
    public void onRefresh() {
        showToast("onRefresh");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                strings.clear();

                pullToRefreshRV.setRefreshComplete();
            }
        },1500);
    }

    @Override
    public void onLoadMore() {
        showToast("onLoadMore");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                strings.add("test"+strings.size());
//                testAdapter.notifyItemInserted(strings.size());

                testAdapter.add("test"+strings.size());

                pullToRefreshRV.setLoadMoreComplete();
            }
        },1500);
    }
}
