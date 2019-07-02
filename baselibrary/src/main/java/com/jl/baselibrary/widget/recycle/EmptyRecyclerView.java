package com.jl.baselibrary.widget.recycle;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 含空白页的 RecyclerView
 * Created by JayLer on 2019/6/17.
 */
public class EmptyRecyclerView extends RecyclerView {

    private String TAG = "EmptyRecyclerView";

    private View emptyView;

    public EmptyRecyclerView(Context context) {
        this(context, null);
    }

    public EmptyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private RecyclerView.AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            checkIfEmpty();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
        }
    };

    public void setEmptyView(View view){
        emptyView = view;
    }

    private void checkIfEmpty() {
        if (emptyView == null || getAdapter() == null) {
            Log.e(TAG, "emptyView == null || getAdapter() == null");
            return;
        }

        boolean isVisible = getAdapter().getItemCount() > 0;
        emptyView.setVisibility(isVisible ? VISIBLE : GONE);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (getAdapter() != null) {
            getAdapter().unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }
        checkIfEmpty();
    }
}
