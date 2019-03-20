package com.onion.community.engine.self;

import com.onion.community.mvp.BasePresenter;
import com.onion.community.mvp.BaseView;

public interface SelfContract {

    interface View extends BaseView{

    }

    interface Presenter extends BasePresenter<View>{

    }

}
