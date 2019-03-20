package com.onion.community.base.fragment;

import com.onion.community.base.RxBaseActivity;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhangqi on 2016/10/13.
 */

public abstract class RxFragment extends SimpleFragment {

    public CompositeDisposable getCompositeSubscription() {
        if(getActivity() instanceof RxBaseActivity){
            RxBaseActivity rxBaseActivity = (RxBaseActivity) getActivity();
            return rxBaseActivity.getCompositeSubscription();
        }
        return null;
    }

    public void addSubscription(Disposable s) {
        if(getActivity() instanceof RxBaseActivity){
            RxBaseActivity rxBaseActivity = (RxBaseActivity) getActivity();
            rxBaseActivity.addSubscription(s);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
