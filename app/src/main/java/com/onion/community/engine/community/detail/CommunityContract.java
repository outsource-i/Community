package com.onion.community.engine.community.detail;

import com.onion.community.bean.Article;
import com.onion.community.bean.Community;
import com.onion.community.bean.HttpWrapper;
import com.onion.community.mvp.BasePresenter;
import com.onion.community.mvp.BaseView;

import java.util.List;

public interface CommunityContract {

    interface View extends BaseView{

        void getOk(HttpWrapper<Community> communityHttpWrapper);

        void getArticleOk(HttpWrapper<List<Article>> wrapper, boolean loadMore);

    }

    interface Presenter extends BasePresenter<View>{

        void getCommunityInfo(String communityId);

        void getCommunityArticle(int type, String communityId, int page, int pageSize);
    }
}
