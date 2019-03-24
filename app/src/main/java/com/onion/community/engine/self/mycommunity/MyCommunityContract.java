package com.onion.community.engine.self.mycommunity;

import com.onion.community.mvp.BasePresenter;
import com.onion.community.mvp.BaseView;

public interface MyCommunityContract {

    interface View extends BaseView{

    }

    interface Presenter extends BasePresenter<View>{

    }
}
