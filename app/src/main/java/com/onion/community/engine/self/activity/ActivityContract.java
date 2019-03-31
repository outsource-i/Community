package com.onion.community.engine.self.activity;

import com.onion.community.bean.Article;
import com.onion.community.bean.HttpWrapper;
import com.onion.community.mvp.BasePresenter;
import com.onion.community.mvp.BaseView;

import java.util.List;

public interface ActivityContract {


    interface View extends BaseView{

        void getActivityOk(HttpWrapper<List<Article>> wrapper);
    }

    interface Presenter extends BasePresenter<View>{

        void getActivity(String id);
    }
}
