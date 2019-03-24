package com.onion.community.engine.community.post;

import com.onion.community.bean.Article;
import com.onion.community.bean.HttpWrapper;
import com.onion.community.mvp.BasePresenter;
import com.onion.community.mvp.BaseView;

public interface PostContract {

    interface View extends BaseView{

        void uploadOk(HttpWrapper<String> stringHttpWrapper);

        void postOk(HttpWrapper<String> stringHttpWrapper);
    }

    interface Presenter extends BasePresenter<View>{

        void upload(String uuid, String path);

        void post(Article article);
    }
}
