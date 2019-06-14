package com.jl.baselibrary.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MotionEvent;

import com.jl.baselibrary.R;


/**
 * Created by JayLer on 2018/11/30.
 */
public abstract class CustomBaseDialog extends Dialog{

    /**
     * 样式
     * @param context
     */
    public CustomBaseDialog(@NonNull Context context) {
        super(context, R.style.BaseDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayoutId());
    }

    /**
     * 自定义布局
     * @return
     */
    protected abstract int getContentLayoutId();

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        dismiss();
        return super.onTouchEvent(event);
    }
}
