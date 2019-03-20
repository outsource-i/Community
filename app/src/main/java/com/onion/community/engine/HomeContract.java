package com.onion.community.engine;


import com.onion.community.bean.HttpWrapper;
import com.onion.community.mvp.BasePresenter;
import com.onion.community.mvp.BaseView;
import okhttp3.internal.Version;

/**
 * Created by zhangqi on 2017/12/25.
 * e-mail : ${email}
 * desc :
 */

public interface HomeContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {

    }

}
