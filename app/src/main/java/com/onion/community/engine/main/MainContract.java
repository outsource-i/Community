package com.onion.community.engine.main;

import com.onion.community.bean.Article;
import com.onion.community.bean.Banners;
import com.onion.community.bean.Community;
import com.onion.community.bean.HttpWrapper;
import com.onion.community.mvp.BasePresenter;
import com.onion.community.mvp.BaseView;

import java.util.List;

public interface MainContract {

    interface View extends BaseView{

        void getBannerSuccess(HttpWrapper<List<Banners>> listHttpWrapper);

        void getNewsSuccess(HttpWrapper<List<Article>> listHttpWrapper);

        void getFollowCommunityOk(HttpWrapper<List<Community>> listHttpWrapper);
    }

    interface Presenter extends BasePresenter<View>{

        void getBanner();

        void getNews();

        void getFollowCommunity(String id);
    }

}
