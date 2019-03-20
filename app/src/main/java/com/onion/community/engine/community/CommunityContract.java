package com.onion.community.engine.community;

import com.onion.community.mvp.BasePresenter;
import com.onion.community.mvp.BaseView;

public interface CommunityContract {

    interface View extends BaseView{

    }

    interface Presenter extends BasePresenter<View>{

    }

}
