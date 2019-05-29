package com.jl.baselibrary.widget.dialog.wheel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import com.jl.baselibrary.utils.ScreenUtils;

import java.util.List;


/**
 * Created by zhongq on 2016/8/9.
 * 滚动选择view
 */
public class WheelView extends ListView implements AbsListView.OnScrollListener {

    //主题
    private WheelViewTheme mSelectTheme;
    //画笔
    private Paint mPaint;
    //显示内容的集合
    private List<String> mList;
    //内容适配器
    private WheelViewAdapter mAdapter;
    //显示item的个数
    private int mItemCount = 3;
    private WheelView mConnectionView;
    //当前选择状态下的view
    private View mSelectView;
    //当前选择状态下的坐标
    private int selectItemPosition;

    public WheelView(Context context) {
        this(context, null);
    }

    public WheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSelectTheme = WheelViewTheme.getDefaultTheme(getContext());
        mPaint.setColor(mSelectTheme.getLineColor());
        mPaint.setAlpha(150);
        mPaint.setAntiAlias(true);
        setOverScrollMode(OVER_SCROLL_NEVER);
        setSelector(new ColorDrawable(Color.TRANSPARENT));
        setOnScrollListener(this);
        itemHeight = ScreenUtils.dip2px(getContext(), 40);
    }

    @Override
    public void setSelection(int position) {
        super.setSelection(position - (mItemCount / 2));
    }


    public void setData(List<String> list) {
        this.mList = list;
        this.mAdapter = new WheelViewAdapter(this, mList, getContext(), mItemCount, mSelectTheme);
        setAdapter(mAdapter);
    }

    public String getSelectString() {
        return mList.get(selectItemPosition);
    }


    private int itemHeight;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画两条居中的水平线
        canvas.drawLine(0, getMeasuredHeight() / 2 - itemHeight / 2, getMeasuredWidth(), getMeasuredHeight() / 2 - itemHeight / 2, mPaint);
        canvas.drawLine(0, getMeasuredHeight() / 2 + itemHeight / 2, getMeasuredWidth(), getMeasuredHeight() / 2 + itemHeight / 2, mPaint);
    }

    /**
     * 设置View的高度为itemHeight的整数倍数
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec = MeasureSpec.makeMeasureSpec(mItemCount * itemHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            setSelection(selectItemPosition);
            if (mOnSelectItemListener != null && mSelectView != null) {
                WheelViewAdapter.ViewHolder vh = (WheelViewAdapter.ViewHolder) mSelectView.getTag();
                mOnSelectItemListener.onSelectItem(this, vh.tv.getText().toString(), selectItemPosition);
            }
        }
    }

    int mListenPosition = -1;//记录监听时记录的坐标，避免重复调用监听
    int mFirstVisibleItem;
    int mVisibleItemCount;
    int mTotalItemCount;

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.mFirstVisibleItem = firstVisibleItem;
        this.mVisibleItemCount = visibleItemCount;
        this.mTotalItemCount = totalItemCount;
        if (mAdapter == null) return;
        selectItemPosition = getSelectPosition(firstVisibleItem, visibleItemCount);
        mSelectView = this.mAdapter.getPositionViewHolder(selectItemPosition);
        if (mSelectView != null) {
            mAdapter.resetView(selectItemPosition, mOnSelectItemListener);
        }
    }

    /**
     * 获取当前被选择item的坐标
     *
     * @param firstVisibleItem
     * @param visibleItemCount
     * @return
     */
    private int getSelectPosition(int firstVisibleItem, int visibleItemCount) {
        int height = getHeight();
        int middle = height / 2;
        for (int i = 0; i < mAdapter.getmVhs().size(); i++) {
            View sView = mAdapter.getmVhs().get(i);
            if (sView == null) {
                Log.e("sView", "sView=null");
                continue;
            }
            int y = (int) sView.getY();
            int itemHeight = (int) (sView.getY() + sView.getHeight());
            WheelViewAdapter.ViewHolder sVh = (WheelViewAdapter.ViewHolder) sView.getTag();
            if (y <= middle && itemHeight >= middle) {
                return sVh.position;
            }
        }
        Log.e("getSelectPosition", "getSelectPosition =0  error");
        return (firstVisibleItem + visibleItemCount) / 2;
    }

    public int getFirstVisibleItemPosition() {
        return mFirstVisibleItem;
    }


    public void setConnectionView(WheelView mConnectionView) {
        this.mConnectionView = mConnectionView;
    }

    /**
     * view中要先是item的数量 ，只能为奇数
     *
     * @param itemCount
     */
    public void setItemCount(int itemCount) {
        if (itemCount % 2 != 1) {
            itemCount += 1;
        } else if (itemCount == 1) {
            itemCount = 3;
        }
        this.mItemCount = itemCount;
    }

    public int getItemCount() {
        return mItemCount;
    }

    public void setOnSelectItemListener(OnSelectItemListener mOnSelectItemListener) {
        this.mOnSelectItemListener = mOnSelectItemListener;
    }

    OnSelectItemListener mOnSelectItemListener;

    public interface OnSelectItemListener {
        void onSelectItem(WheelView view, String str, int position);

        void onClickSelectItem(WheelView view, String str, int position);
    }
}
