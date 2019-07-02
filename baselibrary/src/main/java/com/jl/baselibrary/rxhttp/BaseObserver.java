package com.jl.baselibrary.rxhttp;

import android.content.Context;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 *
 * @author tim
 * @date 2017/8/22
 */

public abstract class BaseObserver<T> implements Observer<T>, Disposable {

    private static final String TAG = BaseObserver.class.getSimpleName();
    protected Disposable mDisposable;
    protected Context mContext;
    private boolean terminated;

    public BaseObserver(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onNext(T value) {
        if (!isDisposed()) {
            try {
                onSuccess(value);
            } catch (Exception e) {
                terminated = true;
                onError(e);
            }
        }
    }

    @Override
    public void onComplete() {
        if (!terminated) {
            onFinal();
        }
    }

    @Override
    public void onError(Throwable e) {
        onFail(e);
        onFinal();
    }

    @Override
    public void dispose() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    @Override
    public boolean isDisposed() {
        return mDisposable == null || mDisposable.isDisposed();
    }

    public abstract void onSuccess(T result);

    public abstract void onFail(Throwable e);

    public void onFinal() {
    }

}
