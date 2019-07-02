package com.tencentmap;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> dataList;
    protected Context context;


    public BaseAdapter(Context context, List<T> dataList) {
        this.dataList = dataList;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        View view = LayoutInflater.from(parent.getContext())
                .inflate(getItemLayoutID(), parent, false);
        return new BaseViewHolder(view);
    }

    protected abstract int getItemLayoutID();

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindDataToView((BaseViewHolder)holder, position, dataList.get(position));
    }

    protected abstract void bindDataToView(BaseViewHolder holder, int position, T entity);

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> array_view;
        public BaseViewHolder(View itemView) {
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
}
