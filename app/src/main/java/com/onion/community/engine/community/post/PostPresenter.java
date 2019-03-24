package com.onion.community.engine.community.post;

import com.onion.community.bean.Article;
import com.onion.community.bean.HttpWrapper;
import com.onion.community.di.DataManager;
import com.onion.community.manager.Result;
import com.onion.community.manager.T;
import com.onion.community.mvp.BasePresenter;
import com.onion.community.mvp.BaseView;
import com.onion.community.mvp.RxPresenter;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import javax.inject.Inject;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PostPresenter extends RxPresenter<PostContract.View> implements PostContract.Presenter {

    @Inject
    public PostPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void upload(String uuid, String path) {
        Map<String, RequestBody> map = new HashMap<>();
        File file = new File(path);
        map.put("img"+"\"; filename=\""+"img.png",RequestBody.create(MediaType.parse("image/png"),file));
        mView.showDialog(null);
       addSubscribe(mDataManager
                .getApi()
                .uploadImage(uuid,map)
                .compose(T.D())
                .subscribeWith(new Result<HttpWrapper<String>>() {
                    @Override
                    protected void onSuccess(HttpWrapper<String> stringHttpWrapper) {
                        mView.uploadOk(stringHttpWrapper);
                    }
                    @Override
                    protected void onFinish() {
                        super.onFinish();
                        mView.dissDialog();
                    }
                }));

    }

    @Override
    public void post(Article article) {
        mView.showDialog(null);
        addSubscribe(mDataManager.getApi()
                .post(article)
                .compose(T.D())
                .subscribeWith(new Result<HttpWrapper<String>>() {
                    @Override
                    protected void onSuccess(HttpWrapper<String> stringHttpWrapper) {
                        mView.postOk(stringHttpWrapper);
                    }

                    @Override
                    protected void onFinish() {
                        super.onFinish();
                        mView.dissDialog();
                    }
                }));
    }
}
