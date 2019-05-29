package com.jl.baselibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jl.baselibrary.app.ActivityStackManager;

/**
 * Created by JayLer on 2019/5/13.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String FRAGMENTS_TAG = "android:support:fragments";
            //移除保存过的fragment
            savedInstanceState.remove(FRAGMENTS_TAG);
        }
        super.onCreate(savedInstanceState);
        ActivityStackManager.getActivityStackManager().pushActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackManager.getActivityStackManager().popActivity(this);
    }
}

