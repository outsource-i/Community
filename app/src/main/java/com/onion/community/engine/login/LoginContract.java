package com.onion.community.engine.login;

import com.onion.community.bean.HttpWrapper;
import com.onion.community.bean.User;
import com.onion.community.mvp.BasePresenter;
import com.onion.community.mvp.BaseView;

public interface LoginContract {

    interface View extends BaseView{

        void loginSuccess(HttpWrapper<User> userHttpWrapper);

        void registerSuccess(HttpWrapper<User> userHttpWrapper);
    }

    interface Presenter extends BasePresenter<View>{

        void login(String phone, String password);

        void register(String phone, String password);
    }
}
