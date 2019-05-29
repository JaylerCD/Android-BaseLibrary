package com.jl.baselibrary.widget.dialog.wheel;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.jl.baselibrary.R;


public abstract class WheelViewDialog extends Dialog {


    private boolean mIsFullWindows; //记录是否是全屏显示
    private boolean mIsFullHorizontalWindows; //是否是横向全屏
    private boolean mIsFullVerticalWindows; //是否是纵向全屏

    public WheelViewDialog(@NonNull Context context) {
        this(context, R.style.AppDialogStyle);
    }

    public WheelViewDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        getWindow().setWindowAnimations(R.style.CustomDialogAnimationDefaultIn);
        initView();
    }

    private void initView() {
        setContentView(getLayoutId());
    }

    /**
     * 设置内容重力方向
     *
     * @param gravity {@link Gravity#CENTER,Gravity#BOTTOM,Gravity#RIGHT,Gravity#LEFT}
     */
    public void setGravity(int gravity) {
        if (gravity == Gravity.TOP) {
            getWindow().setWindowAnimations(R.style.DialogAnimationTopInTime200);
        } else if (gravity == Gravity.BOTTOM) {
            getWindow().setWindowAnimations(R.style.DialogAnimationBottomInTime200);
        } else if (gravity == Gravity.LEFT) {
            getWindow().setWindowAnimations(R.style.DialogAnimationLeftInTime200);
        } else if (gravity == Gravity.RIGHT) {
            getWindow().setWindowAnimations(R.style.DialogAnimationRightInTime200);
        } else {
            getWindow().setWindowAnimations(R.style.CustomDialogAnimationDefaultIn);
        }
        WindowManager.LayoutParams arb = getWindow().getAttributes();
        arb.gravity = gravity;
        getWindow().setAttributes(arb);
    }

    /**
     * 获取当前是否处于在全屏状态
     *
     * @return 是否
     */
    public boolean isIsFullWindows() {
        return mIsFullWindows;
    }


    /**
     * 还原窗口的状态为默认
     */
    public void resetWindowsStatus() {
        this.mIsFullWindows = false;
        this.mIsFullHorizontalWindows = false;
        this.mIsFullVerticalWindows = false;
        WindowManager.LayoutParams arb = getWindow().getAttributes();
        //全屏
        arb.width = WindowManager.LayoutParams.WRAP_CONTENT;
        arb.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(arb);
    }

    /**
     * 设置全屏显示
     */
    public void setFullWindows() {
        this.mIsFullWindows = true;
        this.mIsFullHorizontalWindows = false;
        this.mIsFullVerticalWindows = false;
        WindowManager.LayoutParams arb = getWindow().getAttributes();
        //全屏
        arb.width = WindowManager.LayoutParams.MATCH_PARENT;
        arb.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(arb);

    }


    /**
     * 设置为横向全屏
     */
    public void setFullHorizontalWindows() {
        this.mIsFullHorizontalWindows = true;
        this.mIsFullWindows = false;
        this.mIsFullVerticalWindows = false;

        WindowManager.LayoutParams arb = getWindow().getAttributes();
        arb.width = ViewGroup.LayoutParams.MATCH_PARENT;
        arb.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(arb);

    }

    /**
     * 设置为纵向全屏
     */
    public void setFullVerticalWindows() {
        this.mIsFullVerticalWindows = true;
        this.mIsFullHorizontalWindows = false;
        this.mIsFullWindows = false;
        WindowManager.LayoutParams arb = getWindow().getAttributes();
        arb.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        arb.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(arb);

    }

    public abstract int getLayoutId();
}
