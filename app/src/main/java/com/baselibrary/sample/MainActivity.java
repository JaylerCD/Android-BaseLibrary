package com.baselibrary.sample;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
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
import com.jl.baselibrary.utils.PermissionUtils;
import com.jl.sample.R;

import java.security.Permission;
import java.util.List;

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

        HttpManager.getInstance().get().url("http://www.biubiushop.com/bbg/py/version/bjf_get_version")
                .addParam("platform", 0).addParam("version", "1.2.0").tag(this).execute(new HttpEngineCallback() {
            @Override
            public void onFailure(Throwable e) {

            }

            @Override
            public void onSuccess(String response) {

            }
        });

        PermissionUtils.with(this).permissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).code(0x12).request(new PermissionUtils.PermissionResultCallback() {
            @Override
            public void onGranted() {
                Toast.makeText(MainActivity.this, "全部授权", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDenied(List<String> permissions) {
                for (int i = 0; i < permissions.size(); i++) {
                    Log.e("CJL 未授权：", permissions.get(i));
                }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        HttpManager.getInstance().exchangeEngine(new OkHttpEngine()).cancel(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
