package com.onion.community.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public abstract class SimpleActivity extends MessageActivity {

    protected Activity mContext;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnBinder = ButterKnife.bind(this);
        mContext = this;

        initView();

        initData();

        initListener();
    }

    protected String getText(TextView tv){
        return tv.getText().toString();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initDataOnStart();
    }

    protected void initDataOnStart() {

    }

    protected void initData() {

    }

    protected void initListener() {

    }

    protected abstract void initView();

    protected abstract int getLayoutId();

    public CompositeDisposable mCompositeSubscription = new CompositeDisposable();

    public CompositeDisposable getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeDisposable();
        }
        return this.mCompositeSubscription;
    }

    public void addSubscription(Disposable s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeDisposable();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        if(mCompositeSubscription != null)
            mCompositeSubscription.clear();
    }
}
