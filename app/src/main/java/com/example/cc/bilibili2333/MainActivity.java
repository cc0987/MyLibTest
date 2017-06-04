package com.example.cc.bilibili2333;

import android.os.Bundle;

import com.example.mytest.utils.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        MutilColorTextView m_test = (MutilColorTextView) findViewById(R.id.test);
//        m_test.setDivideColor(Color.BLUE," : ");
//        测试1();
        测试0();
    }

    private void 测试0() {

    }

    /*private void 测试1() {
        final PullRefreshLayout m_mSw = (PullRefreshLayout) findViewById(R.id.mSw);
        m_mSw.setRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void refreshFinished() {
                showLoge("refreshFinished");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        m_mSw.refreshFinished();
                    }
                },1500);
            }

            @Override
            public void loadMoreFinished() {
                showLoge("loadMoreFinished");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        m_mSw.loadMoreFinished();
                    }
                },1500);
            }
        });
    }*/
}
