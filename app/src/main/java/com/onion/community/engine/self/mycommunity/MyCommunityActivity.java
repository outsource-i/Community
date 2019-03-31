package com.onion.community.engine.self.mycommunity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onion.community.AppCenter;
import com.onion.community.R;
import com.onion.community.base.BaseActivity;
import com.onion.community.bean.Community;
import com.onion.community.constant.Constant;
import com.onion.community.di.component.DaggerLoanComponent;
import com.onion.community.engine.community.CommunityAdapter;
import com.onion.community.engine.community.detail.CommunityActivity;
import com.onion.community.util.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

public class MyCommunityActivity extends BaseActivity<MyCommunityPresenter> implements MyCommunityContract.View {


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
    @BindView(R.id.collection_recy)
    RecyclerView mCollectionRecy;
    @BindView(R.id.collection_smart)
    SmartRefreshLayout mCollectionSmart;

    private CommunityAdapter communityAdapter;
    private List<Community> mCommunitys;

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null);
        StatusBarUtil.StatusBarLightMode(this);
        setStatusBarColor(R.color.white);

        mToolbarBack.setOnClickListener(v -> finish());
        mToolbarName.setText("我的社区");

        String json = AppCenter.mSpUtil.getString(Constant.MYFOLLOW_COMMUNITY);
        mCommunitys = new Gson().fromJson(json, new TypeToken<List<Community>>() {}.getType());
        for (Community mCommunity : mCommunitys) {
            mCommunity.setFollow(true);
        }
        mCollectionRecy.setLayoutManager(new LinearLayoutManager(this));
        communityAdapter = new CommunityAdapter(this,R.layout.item_community,mCommunitys);
        mCollectionRecy.setAdapter(communityAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        communityAdapter.setOnItemClickListener((adapter, view, position) -> {
            //进入论坛
            Community community = mCommunitys.get(position);

            Intent intent = new Intent(this, CommunityActivity.class);
            intent.putExtra(CommunityActivity.COMMUNITYID,community.getId());
            startActivity(intent);
        });
    }

    @Override
    protected void initInject() {
        DaggerLoanComponent
                .builder()
                .activityComponent(getActivityComponent())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection;
    }

}
