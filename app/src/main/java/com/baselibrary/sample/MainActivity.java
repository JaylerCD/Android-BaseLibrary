package com.baselibrary.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baselibrary.sample.http.OkHttpEngine;
import com.jl.baselibrary.Core;
import com.jl.baselibrary.http.HttpEngineCallback;
import com.jl.baselibrary.http.HttpManager;
import com.jl.baselibrary.ioc.annotation.ContentView;
import com.jl.baselibrary.ioc.annotation.OnClick;
import com.jl.baselibrary.ioc.annotation.ViewById;
import com.jl.sample.R;

@ContentView(R.layout.activity_main)
public class MainActivity extends FragmentActivity {
    @ViewById(R.id.tvText)
    private TextView mTvText;
    @ViewById(R.id.fragmentHome)
    private FrameLayout mFragmentHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Core.Api.view().inject(this);
        mTvText.setText("哈哈");

        Fragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentHome, homeFragment).commit();
    }

    @OnClick({R.id.tvText})
    private void onClick(View view) {

        HttpManager.with(this).exchangeEngine(new OkHttpEngine()).get().url("http://www.biubiushop.com/bbg/py/version/bjf_get_version")
                .addParam("platform", 0).addParam("version", "1.2.0").execute(new HttpEngineCallback() {
            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onSuccess(String response) {

            }
        });
    }
}
