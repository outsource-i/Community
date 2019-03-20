package com.onion.community.common;

import com.onion.community.di.DataManager;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by OnionMac on 2018/7/30.
 */

public class CommonService {

    protected CompositeDisposable mCompositeDisposable;

    protected DataManager mDataManager;

    public void setDataManager(DataManager dataManager){
        mDataManager = dataManager;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable){
        mCompositeDisposable = compositeDisposable;
    }

    protected CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    protected DataManager getDataManager() {
        return mDataManager;
    }

    protected void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }
}
