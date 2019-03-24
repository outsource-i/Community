package com.onion.community.engine.main;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.google.gson.Gson;
import com.onion.community.AppCenter;
import com.onion.community.R;
import com.onion.community.adapter.ArticleAdapter;
import com.onion.community.adapter.HomeTypeAdapter;
import com.onion.community.base.fragment.BaseFragment;
import com.onion.community.bean.*;
import com.onion.community.constant.Constant;
import com.onion.community.util.GlideImageLoader;
import com.onion.community.view.VerticalScrollView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends BaseFragment<MainPresenter> implements MainContract.View {

    @BindView(R.id.main_banner)
    Banner mMainBanner;
    @BindView(R.id.main_scrollview)
    VerticalScrollView verticalScrollView;
    @BindView(R.id.main_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.main_community_recy)
    RecyclerView mHomeRecy;
    @BindView(R.id.main_article_recy)
    RecyclerView mHomeArticleRecy;

    private List<ProductType> mTypeList = new ArrayList<>();
    private int[] mHomeIcon = {R.mipmap.home_card, R.mipmap.home_small, R.mipmap.home_blacklist, R.mipmap.home_credit};
    private HomeTypeAdapter mHomeTypeAdapter;

    private ArticleAdapter mArticleAdapter;
    private String[] names = {"热帖", "社区", "新闻", "关注"};
    private List<Article> mArticleList = new ArrayList<>();

    @Override
    protected void initView(View view) {
        super.initView(view);
        mMainBanner.setImageLoader(new GlideImageLoader());
        mMainBanner.setBannerStyle(BannerConfig.CENTER);
        mMainBanner.setBannerAnimation(Transformer.Accordion);
        mMainBanner.setDelayTime(5000);

        swipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.toolbar_color));

        List<String> list = new ArrayList<>();
        list.add("第二次元社区今天开展下乡活动");
        list.add("今天是一个好日子吗");

        for (int i = 0; i < names.length; i++) {
            ProductType productType = new ProductType();
            productType.setName(names[i]);
            productType.setSrc(mHomeIcon[i]);
            mTypeList.add(productType);
        }

        if(list != null && list.size() > 0){
            verticalScrollView.setData(list);
            verticalScrollView.start();
        }

        mHomeRecy.setLayoutManager(new GridLayoutManager(mActivity, 4));
        mHomeTypeAdapter = new HomeTypeAdapter(mActivity, R.layout.item_home_type, mTypeList);
        mHomeRecy.setAdapter(mHomeTypeAdapter);

        mHomeArticleRecy.setLayoutManager(new LinearLayoutManager(getActivity()));
        mArticleAdapter = new ArticleAdapter(getActivity(),R.layout.item_article,mArticleList);
        mHomeArticleRecy.setAdapter(mArticleAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getBanner();
        mPresenter.getNews();


        mPresenter.getFollowCommunity(getUser().getId());
    }

    @Override
    protected void initListener() {
        super.initListener();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }

    @Override
    public void getNewsSuccess(HttpWrapper<List<Article>> listHttpWrapper) {
        swipeRefreshLayout.setRefreshing(false);
        if(Constant.SUCCESS_CODE == listHttpWrapper.code){
            mArticleList.clear();
            mArticleList.addAll(listHttpWrapper.getData());
            mArticleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getFollowCommunityOk(HttpWrapper<List<Community>> listHttpWrapper) {
        if(Constant.SUCCESS_CODE == listHttpWrapper.getCode()){
            List<Community> myFollow = listHttpWrapper.getData();

            AppCenter.mSpUtil.putString(Constant.MYFOLLOW_COMMUNITY,new Gson().toJson(myFollow));
        }
    }

    @Override
    public void getBannerSuccess(HttpWrapper<List<Banners>> listHttpWrapper) {
        if(Constant.SUCCESS_CODE == listHttpWrapper.code){
            List<String> list = new ArrayList<>();
            list.clear();
            List<Banners> data = listHttpWrapper.getData();
            for (int i = 0; i < data.size(); i++) {
                list.add(data.get(i).getBannerUrl());
            }
            mMainBanner.setImages(list);
            mMainBanner.start();

        }
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

}
