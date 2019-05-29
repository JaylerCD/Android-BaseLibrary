package com.rxokhttplibrary.base;

import android.content.Context;

import com.rxokhttplibrary.error.BaseException;
import com.rxokhttplibrary.error.HttpException;

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
            if (value instanceof BaseResponse) {
                int code = ((BaseResponse) value).getCode();
                String msg = ((BaseResponse) value).getMessage();
                switch (code) {
                    case BaseResponse.STATE_SUCCESS:
                        try {
                            onSuccess(value);
                        } catch (Exception e) {
                            terminated = true;
                            onError(e);
                        }
                        break;
                    default:
                        terminated = true;
                        onError(new HttpException(code, msg));
                        break;
                }
            } else {
                try {
                    onSuccess(value);
                } catch (Exception e) {
                    terminated = true;
                    onError(e);
                }
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
        onFailed(e);
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

    public abstract void onSuccess(T result) throws Exception;

    public abstract void onFailed(Throwable e);

    public void onFinal() {
    }

    public boolean showToast(Throwable e) {
        return false;
    }


}
