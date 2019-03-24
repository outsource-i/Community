package com.onion.community.engine.community.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onion.community.AppCenter;
import com.onion.community.R;
import com.onion.community.adapter.CommunityAdapter;
import com.onion.community.base.BaseActivity;
import com.onion.community.bean.Article;
import com.onion.community.bean.Community;
import com.onion.community.bean.HttpWrapper;
import com.onion.community.constant.Constant;
import com.onion.community.di.component.DaggerLoanComponent;
import com.onion.community.engine.TestActivity;
import com.onion.community.engine.community.detail.detail.CommunityDetailActivity;
import com.onion.community.engine.community.post.PostActivity;
import com.onion.community.util.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import jp.wasabeef.glide.transformations.BlurTransformation;

import java.util.ArrayList;
import java.util.List;

public class CommunityActivity extends BaseActivity<CommunityPresenter> implements CommunityContract.View {

    public static final String COMMUNITYID = "asd";
    @BindView(R.id.community_back)
    ImageView mCommunityBack;
    @BindView(R.id.community_img)
    ImageView mCommunityImg;
    @BindView(R.id.community_follow)
    TextView mCommunityFollow;
    @BindView(R.id.community_recy)
    RecyclerView mCommunityRecy;
    @BindView(R.id.community_smart)
    SmartRefreshLayout mCommunitySmart;
    @BindView(R.id.community_c_name)
    TextView mCommunityCName;
    @BindView(R.id.community_c_banzhu)
    TextView mCommunityCBanzhu;
    @BindView(R.id.community_c_taolun)
    TextView mCommunityCTaolun;
    @BindView(R.id.community_xie)
    ImageView mCommunityXie;
    @BindView(R.id.community_info)
    TextView mCommunityInfo;
    @BindView(R.id.community_gaosi)
    ImageView mCommunityGaosi;
    private String communityId;


    private CommunityAdapter mCommunityAdapter;

    private List<Article> mList = new ArrayList<>();
    private List<Community> myFollowCommunity;

    private int type = 1;
    private int page = 1;
    private int pageSize = 10;
    private Community data;

    @Override
    protected void initView() {
        super.initView();
        communityId = getIntent().getStringExtra(COMMUNITYID);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null);

        mCommunityRecy.setLayoutManager(new LinearLayoutManager(this));
        mCommunityAdapter = new CommunityAdapter(this, R.layout.item_community_article, mList);
        mCommunityRecy.setAdapter(mCommunityAdapter);

        String json = AppCenter.mSpUtil.getString(Constant.MYFOLLOW_COMMUNITY);
        myFollowCommunity = new Gson().fromJson(json, new TypeToken<List<Community>>() {}.getType());
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getCommunityInfo(communityId);
        mPresenter.getCommunityArticle(type,communityId,page,pageSize);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mCommunityBack.setOnClickListener(v -> finish());
        mCommunityXie.setOnClickListener(v -> {
            Intent intent = new Intent(this, PostActivity.class);
            intent.putExtra(PostActivity.COMMUNITY_DATA,data);
            startActivity(intent);
        });
        mCommunitySmart.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                mPresenter.getCommunityArticle(type,communityId,page,pageSize);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                mPresenter.getCommunityArticle(type,communityId,page,pageSize);
            }
        });

        mCommunityAdapter.setOnItemClickListener((adapter, view, position) -> {
            Article article = mList.get(position);
            Intent intent = new Intent(this,CommunityDetailActivity.class);
            intent.putExtra(CommunityDetailActivity.ARTICLE_ID,article);
            startActivity(intent);
        });
    }

    @Override
    public void getOk(HttpWrapper<Community> communityHttpWrapper) {
        if (Constant.SUCCESS_CODE == communityHttpWrapper.getCode()) {
            data = communityHttpWrapper.getData();
            mCommunityCName.setText(data.getCommunityName());
            mCommunityCBanzhu.setText("版主: "+ data.getUser().getNickName());
            mCommunityCTaolun.setText("帖子 "+ data.getCommunityArticleCount()+"  关注"+ data.getCommunityPeopleCount());
            mCommunityInfo.setText(data.getCommunityInfo());

            Glide.with(this)
                    .load(data.getCommunityImg())
//                    .apply(new RequestOptions().bitmapTransform(new BlurTransformation(this, 14, 3)))
                    .into(mCommunityGaosi);

            Glide.with(this)
                    .load(data.getCommunityImg())
                    .into(mCommunityImg);
//            是否已经关注
            for (Community community : myFollowCommunity) {
                if(community.getId().equals(data.getId())){
                    mCommunityFollow.setText("已关注");
                    return;
                }else{
                    mCommunityFollow.setText("未关注");
                }
            }
        }
    }

    @Override
    public void getArticleOk(HttpWrapper<List<Article>> wrapper, boolean loadMore) {
        closeRefresh();
        if(Constant.SUCCESS_CODE == wrapper.getCode()){
            List<Article> data = wrapper.getData();
            if(!loadMore){
                mList.clear();
            }

            mList.addAll(data);

            mCommunityAdapter.notifyDataSetChanged();
        }else{
            if(page > 1){
                page -- ;
            }
        }
    }

    private void closeRefresh() {
        mCommunitySmart.finishLoadmore(0);
        mCommunitySmart.finishRefresh(0);
    }


    @Override
    protected void initInject() {
        DaggerLoanComponent.builder()
                .activityComponent(getActivityComponent())
                .build().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_community;
    }

}
