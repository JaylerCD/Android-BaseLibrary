package com.jl.baselibrary.http;

public interface HttpEngineCallback {

    void onFailure(Exception e);

    void onSuccess(String response);

}
