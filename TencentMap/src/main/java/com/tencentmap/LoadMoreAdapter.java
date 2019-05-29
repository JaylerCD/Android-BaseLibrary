package com.tencentmap;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;
public abstract class LoadMoreAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // 普通布局
    private final int TYPE_ITEM = 1;
    // 脚布局
    private final int TYPE_FOOTER = 2;
    // 头布局
    public final int TYPE_ITEM_HEADER = 3;
    // 当前加载状态，默认为加载完成
    private int loadState = 2;
    // 正在加载
    public final int LOADING = 1;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;

    protected List<T> dataList;
    protected Context context;

    private boolean isItemHeaderEnable;

    public void setItemHeaderEnable(boolean isItemHeaderEnable){
        this.isItemHeaderEnable = isItemHeaderEnable;
    }

    public LoadMoreAdapter(Context context, List<T> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else if(isItemHeaderEnable && position == 0){
            return TYPE_ITEM_HEADER;
        }else{
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(getItemLayoutID(), parent, false);
            return new LoadMoreBaseViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            LoadMoreLayout moreLayout = new LoadMoreLayout(parent.getContext());
            moreLayout.setOrientation(LinearLayout.VERTICAL);
            return new FootViewHolder(moreLayout);
        }else if (viewType == TYPE_ITEM_HEADER){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(getItemHeaderLayoutID(), parent, false);
            return new LoadMoreBaseViewHolder(view);
        }
        return null;
    }

    protected abstract int getItemLayoutID();

    protected abstract int getItemHeaderLayoutID();

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (loadState) {
                case LOADING: // 正在加载
                    footViewHolder.view.showLoading();
                    break;
                case LOADING_COMPLETE: // 加载完成
                    footViewHolder.view.showFinish();
                    break;
                default:
                    break;
            }
        }else {
            bindDataToView((LoadMoreBaseViewHolder)holder, position, dataList.get(position), getItemViewType(position));
        }
    }

    protected abstract void bindDataToView(LoadMoreBaseViewHolder holder, int position, T entity, int itemViewType);

    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }

    public static class LoadMoreBaseViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> array_view;
        public LoadMoreBaseViewHolder(View itemView) {
            super(itemView);
            array_view = new SparseArray<View>();
        }

        //获取View
        public <T extends View> T getView(int viewId) {

            View view = array_view.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                array_view.put(viewId, view);
            }
            return (T) view;
        }
    }


    private class FootViewHolder extends RecyclerView.ViewHolder {

        LoadMoreLayout view;
        FootViewHolder(View itemView) {
            super(itemView);
            view = (LoadMoreLayout) itemView;
        }
    }

    /**
     * 设置上拉加载状态
     *
     * @param loadState 0.正在加载 1.加载完成 2.加载到底
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }
}
