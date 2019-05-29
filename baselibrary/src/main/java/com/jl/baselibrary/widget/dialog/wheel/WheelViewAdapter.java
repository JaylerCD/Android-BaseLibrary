package com.jl.baselibrary.widget.dialog.wheel;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.jl.baselibrary.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by user on 2016/8/9.
 * 滚动控件适配器
 */
public class WheelViewAdapter extends BaseAdapter {
    private WheelView wheelView;
    private WheelViewTheme mTheme;
    private int mItemCount;
    private List<String> mList;
    private Context mContext;

    public WheelViewAdapter(WheelView wheelView, List<String> list, Context context, int mItemCount, WheelViewTheme theme) {
        this.mList = list;
        this.mContext = context;
        this.mItemCount = mItemCount;
        this.mTheme = theme;
        this.wheelView = wheelView;
        addNoneData();
    }

    private void addNoneData() {
        int count = mItemCount / 2;
        for (int i = 0; i < count; i++) {
            this.mList.add("");
            this.mList.add(0, "");
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    List<View> mVhs = new ArrayList<>();

    public List<View> getmVhs() {
        return mVhs;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.wheel_view_item_layout, null);
            vh.tv = (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(vh);
            mVhs.add(convertView);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        String str = mList.get(position);
        vh.tv.setText(str);
        vh.tv.setTextSize(14);
        vh.tv.setTextColor(mTheme.getNormalTextColor());
        vh.position = position;
        return convertView;
    }


    public static class ViewHolder {
        public TextView tv;
        public int position;
    }

    public View getPositionViewHolder(int position) {
        for (int i = 0; i < mVhs.size(); i++) {
            View view = mVhs.get(i);
            ViewHolder vh = (ViewHolder) view.getTag();
            if (vh.position == position) {
                return view;
            }
        }
        return null;
    }

    public void resetView(int selectPosition, final WheelView.OnSelectItemListener mOnSelectItemListener) {
        for (int i = 0; i < mVhs.size(); i++) {
            View view = mVhs.get(i);
            final ViewHolder vh = (ViewHolder) view.getTag();
            if (vh.position == selectPosition) {
                setSelectText(vh.tv);
                vh.tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnSelectItemListener != null) {
                            mOnSelectItemListener.onClickSelectItem(wheelView, vh.tv.getText().toString(), vh.position);
                        }
                    }
                });
            } else {
                vh.tv.setOnClickListener(null);
                setDefaultText(vh.tv);
            }

        }
    }

    public void setSelectText(TextView tv) {
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,tv.getContext().getResources().getDimension(R.dimen.common_font_one));
        tv.setTextColor(mTheme.getSelectTextColor());
    }

    /**
     * 默认样式
     *
     * @param tv
     */
    public void setDefaultText(TextView tv) {
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,tv.getContext().getResources().getDimension(R.dimen.common_font_two));
        tv.setTextColor(mTheme.getNormalTextColor());
    }
}
