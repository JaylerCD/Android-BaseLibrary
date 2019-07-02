package com.jl.baselibrary.widget.banner;

import android.content.Context;
import android.util.AttributeSet;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by chenjiale on 16/9/8.
 */
public class BannerViewPage extends ViewPager {
    private boolean isCanScroll = true;

    public BannerViewPage(Context context) {
        super(context);
    }

    public BannerViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScanScroll(boolean isCanScroll){
        this.isCanScroll = isCanScroll;
    }


    @Override
    public void scrollTo(int x, int y){
        if (isCanScroll){
            super.scrollTo(x, y);
        }
    }
}
