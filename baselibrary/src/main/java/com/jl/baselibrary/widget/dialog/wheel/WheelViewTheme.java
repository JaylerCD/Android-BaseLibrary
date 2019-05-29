package com.jl.baselibrary.widget.dialog.wheel;

import android.content.Context;

import com.jl.baselibrary.R;


/**
 * Created by user on 2016/8/9.
 * 滚动控件主题颜色
 */
public class WheelViewTheme {

    public static WheelViewTheme getDefaultTheme(Context context) {
        WheelViewTheme theme = new WheelViewTheme();
        theme.setLineColor(context.getResources().getColor(R.color.common_theme_auxiliary));
        theme.setNormalTextColor(context.getResources().getColor(R.color.common_font_one));
        theme.setSelectTextColor(context.getResources().getColor(R.color.common_theme_auxiliary));
        return theme;
    }

    //未被选择时的颜色
    private int normalTextColor;
    //被选择后的颜色
    private int selectTextColor;
    //线条颜色
    private int lineColor;

    public int getNormalTextColor() {
        return normalTextColor;
    }

    public void setNormalTextColor(int normalTextColor) {
        this.normalTextColor = normalTextColor;
    }

    public int getSelectTextColor() {
        return selectTextColor;
    }

    public void setSelectTextColor(int selectTextColor) {
        this.selectTextColor = selectTextColor;
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }
}
