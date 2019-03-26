package com.onion.community.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 */

public abstract class SimpleFragment extends MessageFragment {

    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    private Unbinder mUnBinder;
    protected boolean isInited = false;

    protected boolean visiable = false;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);

        return mView;
    }

    /**
     * 当对用户可见时调用
     */
    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        visiable = true;
    }

    /**
     * 当对用户不可见时调用
     */
    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        visiable = false;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        isInited = true;
        initView(getView());
        initData();
        initListener();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        doOnceOnFragmentLifecycle();
    }

    /**
     * 生命周期内 只调用一次的初始化方法
     */
    protected void doOnceOnFragmentLifecycle(){}

    /**
     * 初始化数据 只走一遍
     */
    protected void initView(View view) {}

    /**
     * 初始化数据 只走一遍
     */
    protected void initListener() {}

    /**
     * 初始化数据 只走一遍
     */
    protected void initData() {}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    protected abstract int getLayoutId();


}
