package com.onion.community.manager;

import com.onion.community.util.U;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by zhangqi on 2017/12/20.
 * e-mail : ${email}
 * desc :
 */

public abstract class Result<T> extends ResourceSubscriber<T> {

    @Override
    public void onNext(T t) {
        onFinish();

        onSuccess(t);
    }

    @Override
    public void onError(Throwable t) {
        U.errorUtil(t);
        onFinish();
        onFaild();
    }

    @Override
    public void onComplete() {

    }

    protected void onFaild() {
    }


    protected void onFinish() {
    }

    protected abstract void onSuccess(T t);
}
