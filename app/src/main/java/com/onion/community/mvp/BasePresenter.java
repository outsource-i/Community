package com.onion.community.mvp;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by zhangqi on 2017/12/20.
 * e-mail : ${email}
 * desc :
 */

public interface BasePresenter<T extends BaseView> {

    void attachView(T view);

    void detachView();

    CompositeDisposable getCompositeDisposable();
}
