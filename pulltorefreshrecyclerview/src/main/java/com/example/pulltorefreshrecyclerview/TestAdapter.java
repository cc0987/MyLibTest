package com.example.pulltorefreshrecyclerview;

import android.content.Context;
import android.widget.TextView;

import com.example.mytest.utils.MyRecyclerViewAdapter;

import java.util.List;

/**
 * Created by CC on 2017/6/4.
 */

public class TestAdapter extends MyRecyclerViewAdapter<String> {
    /**
     * 构造器
     *
     * @param mContext  上下文
     * @param mDataBase 数据
     * @param mLayout   item布局
     */
    public TestAdapter(Context mContext, List<String> mDataBase, int mLayout) {
        super(mContext, mDataBase, mLayout);
    }

    @Override
    public void onCCBind(MyViewHolder holder, String s, int position) {
        ((TextView)holder.getInId(R.id.itemTxt)).setText(s);
    }
}
