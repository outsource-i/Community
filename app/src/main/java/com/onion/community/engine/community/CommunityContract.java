package com.onion.community.engine.community;

import com.onion.community.bean.Community;
import com.onion.community.bean.CommunityType;
import com.onion.community.bean.HttpWrapper;
import com.onion.community.mvp.BasePresenter;
import com.onion.community.mvp.BaseView;

import java.util.List;

public interface CommunityContract {

    interface View extends BaseView{

        void getTypeOk(HttpWrapper<List<CommunityType>> listHttpWrapper);

        void getCommunityOk(HttpWrapper<List<Community>> listHttpWrapper);

        void followOk(HttpWrapper<Community> communityHttpWrapper);

    }

    interface Presenter extends BasePresenter<View>{

        void loadType();

        void getCommunity(String id);

        void followCommunity(String id, String communityId);
    }

}
