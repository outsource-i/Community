package com.onion.community.engine.community.detail.detail;

import com.onion.community.bean.Article;
import com.onion.community.bean.HttpWrapper;
import com.onion.community.mvp.BasePresenter;
import com.onion.community.mvp.BaseView;

public interface CommunityDetailContract {


    interface View extends BaseView{

        void getArticleOk(HttpWrapper<Article> articleHttpWrapper);

        void collection(HttpWrapper<String> stringHttpWrapper);

        void huifuOk(HttpWrapper<String> stringHttpWrapper);

        void signupOk(HttpWrapper<String> stringHttpWrapper);

    }

    interface Presenter extends BasePresenter<View>{

        void getArticle(String articleId);

        void shoucang(String id, String id1);

        void huifu(String data, String id,String userId);

        void baoming(String id, String id1);
    }
}
