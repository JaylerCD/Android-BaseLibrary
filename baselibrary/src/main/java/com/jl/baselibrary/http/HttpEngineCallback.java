package com.jl.baselibrary.http;

public interface HttpEngineCallback {

    void onFailure(Throwable e);

    void onSuccess(String response);

}
