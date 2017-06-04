package com.example.zhyrecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * RecyclerView给相同一类的条目数据添加头布局的设配器Adapter
 * Created by cj_28 on 2016/7/28.
 */
public abstract class SectionAdapter<T> extends MultiItemCommonAdapter<T> {

    private SectionSupport mSectionSupport;
    private static final int TYPE_SECTION = 0;
    private LinkedHashMap<String,Integer> mSections;//链式Map，顺序链表

    private MultiItemTypeSupport<T> headerItemTypeSupport = new MultiItemTypeSupport<T>() {
        @Override
        public int getItemViewType(int position, T item) {
            return mSections.values().contains(position) ? TYPE_SECTION : 1;
        }

        @Override
        public int getItemLayoutId(int itemType) {
            if(itemType == TYPE_SECTION){
                return mSectionSupport.sectionHeaderLayoutId();
            }else {
                return mLayoutId;
            }
        }
    };

    @Override
    public int getItemViewType(int position) {
        return mMultiItemTypeSupport.getItemViewType(position,null);
    }

    final RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            findSections();
        }
    };

    /**
     * 筛选头，保存的头信息是去重复的，如有重复，只会保存第一份
     * 也会将在整个列表中的索引保存下来（i+nSections）
     */
    public void findSections(){
        int n = mDatas.size();
        int nSections = 0;
        mSections.clear();

        for(int i=0;i<n;i++){
            String sectionName = mSectionSupport.getTitle(mDatas.get(i));
            if(!mSections.containsKey(sectionName)){
                mSections.put(sectionName,i+nSections);//i+nSections列表索引position
                nSections++;
            }
        }
    }



    public SectionAdapter(Context context, int layoutId, List<T> datas, SectionSupport<T> sectionSupport) {
        super(context, datas, null);
        mLayoutId = layoutId;
        mMultiItemTypeSupport = headerItemTypeSupport;
        mSectionSupport = sectionSupport;
        mSections = new LinkedHashMap<>();
        findSections();
        registerAdapterDataObserver(observer);
    }


    /**
     * 是否给条目布局设置点击事件
     * 在CommonAdapter中给条目布局设置点击事件，但是isEnabled（int）返回的必须是true
     * @param viewType
     * @return
     */
    @Override
    protected boolean isEnabled(int viewType)
    {
        if (viewType == TYPE_SECTION)
            return false;
        return super.isEnabled(viewType);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView)
    {
        super.onDetachedFromRecyclerView(recyclerView);
        unregisterAdapterDataObserver(observer);
    }

    @Override
    public int getItemCount()
    {
        return super.getItemCount() + mSections.size();
    }

    /**
     * 根据条目位置position减去该条目位置的之前的头布局的个数，就是剩下的条目（不含头，因为头信息的数据没有在数据集合中）的position
     * 在设置点击事件的时候，可以在条目点击事件中，通过position调用该方法，让其返回出去头布局所在位置的position
     * @param position
     * @return
     */
    public int getIndexForPosition(int position)
    {
        int nSections = 0;

        Set<Map.Entry<String, Integer>> entrySet = mSections.entrySet();
        for (Map.Entry<String, Integer> entry : entrySet)
        {
            if (entry.getValue() < position)
            {
                nSections++;
            }
        }
        return position - nSections;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        //多了分类条目，所以条目页相应的变大了
        position = getIndexForPosition(position);
        if (holder.getItemViewType() == TYPE_SECTION)
        {
            //根据接口返回头布局中的textview，和接口中返回的相应条目的头文本，进行设置
            holder.setText(mSectionSupport.sectionTitleTextViewId(), mSectionSupport.getTitle(mDatas.get(position)));
            return;
        }
        super.onBindViewHolder(holder, position);
    }


}
