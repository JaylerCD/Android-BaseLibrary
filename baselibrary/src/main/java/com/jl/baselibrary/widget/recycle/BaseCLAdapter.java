package com.jl.baselibrary.widget.recycle;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by JayLer on 2019/6/17.
 */
public abstract class BaseCLAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<T> mData;
    private Context mContext;

    public BaseCLAdapter() {
    }

    public BaseCLAdapter(Context context, List<T> data) {
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(getItemLayoutId(), parent, false);
        return new BaseCLAdapterHolder(view);
    }

    protected abstract int getItemLayoutId();

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        bindDataToView((BaseCLAdapterHolder)holder, position, mData.get(position));
    }

    protected abstract void bindDataToView(BaseCLAdapterHolder holder, int position, T t);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class BaseCLAdapterHolder extends RecyclerView.ViewHolder{
        private SparseArray<View> array_view;

        public BaseCLAdapterHolder(View itemView) {
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
