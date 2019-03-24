package com.onion.community.engine.self.collection;

import com.onion.community.bean.Article;
import com.onion.community.bean.Collection;
import com.onion.community.bean.Community;
import com.onion.community.bean.HttpWrapper;
import com.onion.community.mvp.BasePresenter;
import com.onion.community.mvp.BaseView;

import java.util.List;

public interface CollectionContract {


    interface View extends BaseView{

        void getCollectionOk(HttpWrapper<List<Article>> wrapper);


    }

    interface Presenter extends BasePresenter<View>{
        void getMyCollection(String userId);
    }
}
