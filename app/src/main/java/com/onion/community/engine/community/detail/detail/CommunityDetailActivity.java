package com.onion.community.engine.community.detail.detail;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.onion.community.R;
import com.onion.community.base.BaseActivity;
import com.onion.community.bean.Article;
import com.onion.community.bean.HttpWrapper;
import com.onion.community.constant.Constant;
import com.onion.community.di.component.DaggerLoanComponent;
import com.onion.community.util.StatusBarUtil;
import com.onion.community.util.U;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zyyoona7.lib.EasyPopup;
import com.zyyoona7.lib.HorizontalGravity;
import com.zyyoona7.lib.VerticalGravity;

import java.util.Date;

public class CommunityDetailActivity extends BaseActivity<CommunityDetailPresenter> implements CommunityDetailContract.View {

    public static final String ARTICLE_ID = "data";
    @BindView(R.id.toolbar_back)
    ImageView mToolbarBack;
    @BindView(R.id.toolbar_name)
    TextView mToolbarName;
    @BindView(R.id.toolbar_right)
    ImageView mToolbarRight;
    @BindView(R.id.toolbar_rl)
    RelativeLayout mToolbarRl;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.c_detail_title)
    TextView mCDetailTitle;
    @BindView(R.id.c_detail_read)
    TextView mCDetailRead;
    @BindView(R.id.c_detail_rl)
    RelativeLayout mCDetailRl;
    @BindView(R.id.c_detail_user_img)
    ImageView mCDetailUserImg;
    @BindView(R.id.c_detail_user_name)
    TextView mCDetailUserName;
    @BindView(R.id.community_follow)
    TextView mCommunityFollow;
    @BindView(R.id.c_detail_webview)
    WebView mCDetailWebview;
    @BindView(R.id.c_detail_fl)
    FrameLayout mCDetailFl;
    @BindView(R.id.c_detail_time)
    TextView mCDetailTime;
    @BindView(R.id.c_detail_hot)
    TextView mCDetailHot;
    @BindView(R.id.c_detail_recy)
    RecyclerView mCDetailRecy;
    @BindView(R.id.c_detail_smart)
    SmartRefreshLayout mCDetailSmart;
    @BindView(R.id.c_detail_other)
    LinearLayout mCDetailOther;
    @BindView(R.id.c_detail_ts)
    NestedScrollView mCDetailTs;
    @BindView(R.id.c_detail_post_date)
    TextView mCDetailPostDate;

    private Article article;

    private EasyPopup mCirclePop;
    private View view;

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null);
        StatusBarUtil.StatusBarLightMode(this);
        setStatusBarColor(R.color.white);

        mToolbarBack.setOnClickListener(v -> finish());
        mToolbarName.setText("主题详情");
        U.initWebView(mCDetailWebview, null);

        article = (Article) getIntent().getSerializableExtra(ARTICLE_ID);

        mCirclePop = new EasyPopup(this)
                .setContentView(R.layout.pop_select)
                //是否允许点击PopupWindow之外的地方消失
                .setFocusAndOutsideEnable(true)
                .setBackgroundDimEnable(true)
                .createPopup();
        view = mCirclePop.getView(R.id.pop_select_shoucang);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getArticle(article.getId());
    }

    @Override
    public void collection(HttpWrapper<String> stringHttpWrapper) {
        if(Constant.SUCCESS_CODE == stringHttpWrapper.getCode()){
            showMessage("收藏成功");
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        view.setOnClickListener(v -> {
            mCirclePop.dismiss();
            mPresenter.shoucang(getUser().getId(),article.getId());
        });
        mToolbarRight.setOnClickListener(v -> {
            mCirclePop.showAtAnchorView(mToolbar, VerticalGravity.BELOW, HorizontalGravity.RIGHT, 0, 0);
        });
    }

    @Override
    public void getArticleOk(HttpWrapper<Article> wrapper) {
        if (Constant.SUCCESS_CODE == wrapper.getCode()) {
            Article data = wrapper.getData();
            mCDetailWebview.loadData(data.getContent(), null, "UTF-8");

            mCDetailTitle.setText(data.getTitle());;

            mCDetailPostDate.setText(U.toDate(data.getCreateDate()));
            mCDetailRead.setText(data.getClickCount() + "阅读");

            Glide.with(this)
                    .load(data.getUser().getHeadImg())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(mCDetailUserImg);

            mCDetailUserName.setText(data.getUser().getNickName());
        }
    }


    @Override
    protected void initInject() {
        DaggerLoanComponent.builder()
                .activityComponent(getActivityComponent())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_community_detail;
    }

}
