package com.onion.community.manager;


import com.onion.community.bean.HttpWrapper;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by zhangqi on 2017/12/25.
 * e-mail : ${email}
 * desc :
 */

public abstract class ResultOnList<T extends HttpWrapper> extends ResourceSubscriber<T> {

    @Override
    public void onNext(T t) {
        onFinish();
    }

    @Override
    public void onError(Throwable t) {
        onFinish();
    }

    @Override
    public void onComplete() {

    }

    protected void onFinish(){}

    protected abstract void onSuccess(T t);
}
