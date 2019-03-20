package com.onion.community.engine;

import com.onion.community.bean.HttpWrapper;
import com.onion.community.constant.Constant;
import com.onion.community.di.DataManager;
import com.onion.community.manager.T;
import com.onion.community.mvp.RxPresenter;
import okhttp3.internal.Version;

import javax.inject.Inject;
import java.util.Date;

/**
 * Created by zhangqi on 2017/12/25.
 * e-mail : ${email}
 * desc :
 */

public class HomePresenter extends RxPresenter<HomeContract.View> implements HomeContract.Presenter {

    @Inject
    public HomePresenter(DataManager dataManager) {
        super(dataManager);
    }

}
