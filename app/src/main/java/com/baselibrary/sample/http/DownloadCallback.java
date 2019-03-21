package com.baselibrary.sample.http;

import com.jl.baselibrary.http.HttpEngineCallback;

public interface DownloadCallback extends HttpEngineCallback {

    void onStarted();
    void onLoading(int progress);
    void onCancelled();
    void onFinished();

}
