package com.example.mytest.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by CC
 */

public abstract class MyRecyclerViewAdapter<T> extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private Context mContext;
    private List<T> mDataBase;
    private int mLayout;

    /**
     * 更新数据 TODO ??????????
     * */
    public void update(List<T> mDataBase){
        this.mDataBase = mDataBase;
        notifyItemRangeChanged(0,mDataBase.size());
    }


    /**
     * list头部的添加
     * */
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_HEADER = 0;
    private int subPosition = 0;
    private View mHeaderView;

    public void setHeaderView(View headerView) {
        subPosition = 1;
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }


    public void add(T content) {
        mDataBase.add(content);
        notifyItemInserted(mDataBase.size());
    }

    /**
     * 我们直接删除最后一个item
     **/
    public void delete() {
        mDataBase.remove(mDataBase.size() - subPosition);  // 6 条 数据
        notifyItemRemoved(mDataBase.size());
    }

    /**
     * 增加新数据 TODO ??????????
     * */
    public void addDatas(List<T> datas) {
        mDataBase.addAll(datas);
        notifyDataSetChanged();
//        notifyItemRangeInserted(mDataBase.size()-subPosition,datas.size());
    }

    public void upDatas(List<T> datas) {
        mDataBase.clear();
        if (datas!=null) {
            mDataBase.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - subPosition;
    }

    @Override
    public int getItemViewType(int position) {
        if(mHeaderView == null) return TYPE_NORMAL;
        if(position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    /**
     * 点击和长按方法回调
     * */
    private CallBack mCallBack;
    private LongClickListener mLongClickListener;
    public void setOnLongClickListener(LongClickListener listener){
        mLongClickListener = listener;
    }

    public interface  LongClickListener{
        void longClick(Integer position, View view);
    }

    public interface CallBack{
        void onItemClick(Integer position, View view);
    }

    public void setCallBackListener(CallBack back){
        mCallBack = back;
    }

    @Override
    public void onClick(View v) {
        Integer position = (Integer) v.getTag();
        if (position!=null){
            // position  和控件 v 回传到Activity里面
            if (mCallBack!=null){
                mCallBack.onItemClick(position,v);
            }
        }

    }

    @Override
    public boolean onLongClick(View v) {
        Integer position = (Integer) v.getTag();
        if (position!=null&&mLongClickListener!=null){
            mLongClickListener.longClick(position,v);
        }
        return true;
    }

    /**
     * 构造器
     * @param mContext 上下文
     * @param mDataBase 数据
     * @param mLayout item布局
     * */
    public MyRecyclerViewAdapter(Context mContext, List<T> mDataBase, int mLayout) {
        this.mContext = mContext;
        this.mDataBase = mDataBase;
        this.mLayout = mLayout;
    }

    /**
     * holder
     * */
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private View itemView;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public View getInId(int viewId){
            View view = itemView.findViewById(viewId);
            return view;
        }

        public View getItemView() {
            return itemView;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) return new MyViewHolder(mHeaderView);

        View view = LayoutInflater.from(mContext).inflate(mLayout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    /**
     * 设置分割线
     * */
    private int backColor;
    private int divideColor;
    private boolean isShowDivider=false;
    public void showDivider(int backColor ,int divideColor){
        isShowDivider = true;
        this.backColor = backColor;
        this.divideColor = divideColor;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_HEADER) return;

        View itemView = holder.getItemView();
        if (isShowDivider&&position!=0) {
            Drawable[] layers = new Drawable[2];
            ColorDrawable whileDrawable = new ColorDrawable(divideColor);
            layers[0] = whileDrawable;
            ColorDrawable blackDrawable = new ColorDrawable(backColor);
            layers[1] = blackDrawable;
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            layerDrawable.setLayerInset(0,0,0,0,0);
            layerDrawable.setLayerInset(1,0,1,0,0);
            itemView.setBackground(layerDrawable);
        }else{
            ColorDrawable background = new ColorDrawable(backColor);
            itemView.setBackground(background);
        }

        T t = mDataBase.get(position-subPosition);
        holder.itemView.setTag(position-subPosition);
        holder.itemView.setOnClickListener(this);
        holder.itemView.setOnLongClickListener(this);
        onCCBind(holder,t,position-subPosition);
    }
    public abstract void onCCBind(MyViewHolder holder, T t ,int position);

    @Override
    public int getItemCount() {
        return mDataBase!=null?mDataBase.size()+subPosition:0;
    }
}
