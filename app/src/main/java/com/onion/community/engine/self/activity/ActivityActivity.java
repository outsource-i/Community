package com.onion.community.engine.self.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.onion.community.R;
import com.onion.community.base.BaseActivity;
import com.onion.community.bean.Article;
import com.onion.community.bean.HttpWrapper;
import com.onion.community.constant.Constant;
import com.onion.community.di.component.DaggerLoanComponent;
import com.onion.community.engine.community.detail.detail.CommunityDetailActivity;
import com.onion.community.engine.self.collection.CollectionAdapter;
import com.onion.community.util.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class ActivityActivity extends BaseActivity<ActivityPresenter> implements ActivityContract.View {


    @BindView(R.id.toolbar_back)
    ImageView mToolbarBack;
    @BindView(R.id.toolbar_name)
    TextView mToolbarName;
    @BindView(R.id.toolbar_right)
    TextView mToolbarRight;
    @BindView(R.id.toolbar_rl)
    RelativeLayout mToolbarRl;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_recy)
    RecyclerView mActivityRecy;
    @BindView(R.id.activity_smart)
    SmartRefreshLayout mActivitySmart;

    private CollectionAdapter collectionAdapter;
    private List<Article> mList = new ArrayList<>();


    @Override
    protected void initView() {
        super.initView();
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null);
        StatusBarUtil.StatusBarLightMode(this);
        setStatusBarColor(R.color.white);
        mToolbarBack.setOnClickListener(v -> finish());
        mToolbarName.setText("我的活动");

        collectionAdapter = new CollectionAdapter(this,R.layout.item_collection,mList);

        mActivityRecy.setLayoutManager(new LinearLayoutManager(this));
        mActivityRecy.setAdapter(collectionAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getActivity(getUser().getId());
    }

    @Override
    protected void initListener() {
        super.initListener();
        collectionAdapter.setOnItemClickListener((adapter, view, position) -> {
            Article article = mList.get(position);
            Intent intent = new Intent(this, CommunityDetailActivity.class);
            intent.putExtra(CommunityDetailActivity.ARTICLE_ID,article);
            startActivity(intent);
        });
    }

    @Override
    protected void initInject() {
        DaggerLoanComponent.builder()
                .activityComponent(getActivityComponent())
                .build().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_activity;
    }

    @Override
    public void getActivityOk(HttpWrapper<List<Article>> wrapper) {
        if(Constant.SUCCESS_CODE == wrapper.getCode()){
            mList.clear();
            mList.addAll(wrapper.getData());
            collectionAdapter.notifyDataSetChanged();
        }
    }
}
