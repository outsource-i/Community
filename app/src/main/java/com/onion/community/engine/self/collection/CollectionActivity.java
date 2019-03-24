package com.onion.community.engine.self.collection;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.onion.community.R;
import com.onion.community.base.BaseActivity;
import com.onion.community.bean.Article;
import com.onion.community.bean.HttpWrapper;
import com.onion.community.constant.Constant;
import com.onion.community.di.component.DaggerLoanComponent;
import com.onion.community.engine.community.detail.detail.CommunityDetailActivity;
import com.onion.community.util.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends BaseActivity<CollectionPresenter> implements CollectionContract.View {

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

    private CollectionAdapter collectionAdapter;
    private List<Article> mList = new ArrayList<>();

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null);
        StatusBarUtil.StatusBarLightMode(this);
        setStatusBarColor(R.color.white);

        mToolbarBack.setOnClickListener(v -> finish());
        mToolbarName.setText("我的收藏");

        collectionAdapter = new CollectionAdapter(this,R.layout.item_collection,mList);

        mCollectionRecy.setLayoutManager(new LinearLayoutManager(this));
        mCollectionRecy.setAdapter(collectionAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getMyCollection(getUser().getId());
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
        return R.layout.activity_collection;
    }


    @Override
    public void getCollectionOk(HttpWrapper<List<Article>> wrapper) {
        if(Constant.SUCCESS_CODE == wrapper.getCode()){
            mList.clear();
            mList.addAll(wrapper.getData());
            collectionAdapter.notifyDataSetChanged();
        }
    }
}
