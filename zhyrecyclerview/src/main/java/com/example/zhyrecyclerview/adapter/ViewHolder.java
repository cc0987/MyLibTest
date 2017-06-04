package com.example.zhyrecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 通用的ViewHolder生成类
 * Created by chenj on 2016/7/28.
 */
public class ViewHolder extends RecyclerView.ViewHolder {

//    public ViewHolder(View itemView) {
//        super(itemView);
//    }

    private Context mContext;
    private View mConvertView;//条目布局
    private SparseArray<View> mViews;//保存条目布局中的所有控件（一个ViewHolder对象对应一个条目布局）

    public ViewHolder(Context context , View itemView, ViewGroup viewGroup) {
        super(itemView);
        this.mContext = context;
        this.mConvertView = itemView;
        mViews = new SparseArray<View>();
    }

    public View getConvertView(){
        return mConvertView;
    }

    /**
     * 得到ViewHolder对象
     * @param context
     * @param parent
     * @param layoutId 条目布局的资源id
     * @return
     */
    public static ViewHolder get(Context context ,ViewGroup parent,int layoutId){
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(context,itemView,parent);
        return viewHolder;
    }

    /**
     * 得到ViewHolder对象
     * @param context
     * @param parent
     * @param layoutView 条目布局View
     * @return
     */
    public static ViewHolder get(Context context ,ViewGroup parent,View layoutView){
        ViewHolder viewHolder = new ViewHolder(context,layoutView,parent);
        return viewHolder;
    }

    /**
     * 根据控件id获取条目布局(ViewHolder)中的控件
     * @param viewId 控件id
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if(view == null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T)view;
    }

    /**
     * 给TextView设置文本
     * @param viewId TextView的id
     * @param text 显示的文本
     * @return
     */
    public ViewHolder setText(int viewId,String text){
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    /**
     * 给ImageView设置图片
     * @param viewId
     * @param resId
     * @return
     */
    public ViewHolder setImage(int viewId,int resId){
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }


}
