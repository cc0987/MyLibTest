package com.example.cc.bilibili2333;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecycler.setLayoutManager(layoutManager);
        //GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        //mRecycler.setLayoutManager(layoutManager);

        mRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        mRecycler.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        mPull.setColorSchemeColors(Color.BLACK,Color.CYAN,Color.YELLOW);

        mDatas = new ArrayList<>();
        for(int i=0;i<220;i++){
//            if(i==217)
//                mDatas.add("woagangawgb abg eab giaeu gaeb ari arehgaerhg aerh eagetahetahatehetaheathetahhhhhheh");
//            else
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
        //mHeaderAndFooterWrapper.addFooterView(t1);
        //mHeaderAndFooterWrapper.addFooterView(t2);
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

        //SectionAdapter设置分类头的点击事件的position注意获取方式
//        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(RecyclerView.Adapter<?> parent, RecyclerView.ViewHolder viewHolder, View view, int position,int viewType) {
//                int realPosition = adapter.getIndexForPosition(position);//去除头布局所占的位置后，真正数据条目所占的位置position
//                Toast.makeText(getApplication(),""+realPosition,Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    private List<String> getDataA_Z(){
        List<String> data = new ArrayList<>();
        for(int i='A';i<'Z';i++){
            for(int j=0;j<10;j++)
                data.add((char)i+"" + j);
        }
        return data;
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
