package com.onion.community.mvp;


import com.onion.community.common.CommonService;
import com.onion.community.di.DataManager;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhangqi on 2017/12/20.
 * e-mail : ${email}
 * desc :
 */

public class RxPresenter<T extends BaseView> implements BasePresenter<T> {

    protected T mView;

    private CommonService mCommonService;

    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    protected DataManager mDataManager;

    public RxPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
    }

    protected void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    protected void setService(CommonService commonService) {
        mCommonService = commonService;
        mCommonService.setDataManager(mDataManager);
        mCommonService.setCompositeDisposable(mCompositeDisposable);
    }

    @Override
    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();

        if (mCommonService != null) {
            mCommonService = null;
        }
    }
}
