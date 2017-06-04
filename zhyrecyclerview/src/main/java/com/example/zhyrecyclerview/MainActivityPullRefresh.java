package com.example.zhyrecyclerview;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.zhyrecyclerview.adapter.CommonAdapter;
import com.example.zhyrecyclerview.adapter.HeaderAndFooterWrapper;
import com.example.zhyrecyclerview.adapter.MultiItemCommonAdapter;
import com.example.zhyrecyclerview.adapter.MultiItemTypeSupport;
import com.example.zhyrecyclerview.adapter.SectionAdapter;
import com.example.zhyrecyclerview.adapter.SectionSupport;
import com.example.zhyrecyclerview.adapter.ViewHolder;
import com.example.zhyrecyclerview.refresh.PullRefreshLayout;
import com.example.zhyrecyclerview.split.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivityPullRefresh extends AppCompatActivity {

    private RecyclerView mRecycler;
    private SwipeRefreshLayout mSwipe;
    private PullRefreshLayout mPull;
    private ArrayList<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pull_refresh);

        mRecycler = (RecyclerView) findViewById(R.id.recycler);
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        mPull = (PullRefreshLayout) findViewById(R.id.pull);


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPull.setRefreshing(true);
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPull.setAllowLoadMore(!mPull.isAllowLoadMore());
            }
        });

//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecycler.setLayoutManager(layoutManager);
        //GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        //mRecycler.setLayoutManager(layoutManager);

        mRecycler.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));

        mRecycler.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        mPull.setColorSchemeColors(Color.BLACK,Color.CYAN,Color.YELLOW);

        mDatas = new ArrayList<>();
        for(int i=0;i<220;i++){
                mDatas.add(i+"");
        }
        MySectionAdapter adapter = new MySectionAdapter(this, R.layout.recycler_item2, getDataA_Z(), new SectionSupport<String>() {
            @Override
            public int sectionHeaderLayoutId() {

                return R.layout.recycler_item;
            }

            @Override
            public int sectionTitleTextViewId() {
                return R.id.textview;
            }

            @Override
            public String getTitle(String item) {
//
                return item.substring(0,1);
            }
        });

        final HeaderAndFooterWrapper mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(adapter);

        TextView t1 = new TextView(this);
        t1.setText("Header 1");
        TextView t2 = new TextView(this);
        t2.setText("Header 2");
        mHeaderAndFooterWrapper.addHeaderView(t1);
        mHeaderAndFooterWrapper.addHeaderView(t2);
        mRecycler.setAdapter(mHeaderAndFooterWrapper);

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        mDatas.clear();
                        for(int i=0;i<220;i++){
                            mDatas.add(i+"");
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mHeaderAndFooterWrapper.notifyDataSetChanged();
                                mSwipe.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });

        mPull.setScrollLoadEnabled(false);

        mPull.setOnPullListener(new PullRefreshLayout.OnPullListener() {
            @Override
            public void onLoadMore(final PullRefreshLayout pullRefreshLayout) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        mDatas.add(mDatas.size() + "");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mHeaderAndFooterWrapper.notifyDataSetChanged();
                                pullRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });

        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.Adapter<?> parent, RecyclerView.ViewHolder viewHolder, View view, int position,int viewType) {
                System.out.println("-----------------position:"+position+",header:"+mHeaderAndFooterWrapper.getHeadersCount());
            }
        });
    }

    private List<String> getDataA_Z(){
        List<String> data = new ArrayList<>();
        for(int i='A';i<'Z';i++){
            for(int j=0;j<10;j++)
                data.add((char)i+"" + j);
        }
        return data;
    }

    class MyAdapter extends CommonAdapter<String> {

        public MyAdapter(Context context, int layoutId, List<String> datas) {
            super(context, layoutId, datas);
        }

        @Override
        public void convert(ViewHolder viewHolder, String item, int position) {
            viewHolder.setText(R.id.recyclerView_tv,item);

        }
    }

    class MultiAdapter extends MultiItemCommonAdapter<String> {

        public MultiAdapter(Context context, List<String> datas, MultiItemTypeSupport multiItemTypeSupport) {

            super(context, datas, multiItemTypeSupport);
        }

        @Override
        public void convert(ViewHolder viewHolder, String item, int position) {
            int viewType = getItemViewType(position);
            if(viewType % 2 == 0) {
                viewHolder.setText(R.id.textview, item);
            }else{
                viewHolder.setImage(R.id.imageview,R.mipmap.ic_launcher);
            }
        }
    }

     class MySectionAdapter extends SectionAdapter<String> {

         public MySectionAdapter(Context context, int layoutId, List<String> datas, SectionSupport<String> sectionSupport) {
             super(context, layoutId, datas, sectionSupport);
         }

         @Override
         public void convert(ViewHolder viewHolder, String item, int position) {
            viewHolder.setImage(R.id.imageview,R.mipmap.ic_launcher);
         }
     }
}
