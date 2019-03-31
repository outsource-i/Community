package com.onion.community.engine.self;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onion.community.AppCenter;
import com.onion.community.R;
import com.onion.community.adapter.SelfFunAdapter;
import com.onion.community.base.fragment.BaseFragment;
import com.onion.community.bean.ProductType;
import com.onion.community.bean.User;
import com.onion.community.constant.Constant;
import com.onion.community.engine.login.LoginActivity;
import com.onion.community.engine.self.activity.ActivityActivity;
import com.onion.community.engine.self.collection.CollectionActivity;
import com.onion.community.engine.self.mycommunity.MyCommunityActivity;
import com.onion.community.view.OvalBg;

import java.util.ArrayList;
import java.util.List;

/**
 * created by zhangqi on 2018/12/19
 */
public class SelfFragment extends BaseFragment<SelfPresenter> implements SelfContract.View {

    @BindView(R.id.toolbar_back)
    ImageView mToolbarBack;
    @BindView(R.id.toolbar_name)
    TextView mToolbarName;
    @BindView(R.id.toolbar_setting)
    ImageView mToolbarSetting;
    @BindView(R.id.toolbar_rl)
    RelativeLayout mToolbarRl;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.self_oval)
    OvalBg mSelfOval;
    @BindView(R.id.self_headimg)
    ImageView mSelfHeadimg;
    @BindView(R.id.self_phone)
    TextView mSelfPhone;
    @BindView(R.id.self_open_vip)
    TextView mSelfOpenVip;
    @BindView(R.id.self_card_ll1)
    LinearLayout mSelfCardLl1;
    @BindView(R.id.self_wallet)
    ImageView mSelfWallet;
    @BindView(R.id.self_my_account)
    TextView mSelfMyAccount;
    @BindView(R.id.self_view_1)
    View mSelfView1;
    @BindView(R.id.self_signin)
    ImageView mSelfSignin;
    @BindView(R.id.self_card)
    CardView mSelfCard;
    @BindView(R.id.self_recy_fun)
    RecyclerView mSelfRecyFun;
    @BindView(R.id.self_nested)
    NestedScrollView mSelfNested;
    @BindView(R.id.self_swipe)
    SwipeRefreshLayout mSelfSwipe;
    @BindView(R.id.self_rl_account)
    RelativeLayout mSelfRlAccount;
    @BindView(R.id.self_my_sign)
    TextView mSelfMySign;
    @BindView(R.id.self_isvip)
    TextView mSelfIsVip;
    @BindView(R.id.self_vip_ll)
    LinearLayout mSelfVipLl;
    @BindView(R.id.self_rl_sign)
    RelativeLayout mSelfRlSign;
    @BindView(R.id.self_ll2)
    LinearLayout mSelfLl2;

    private List<ProductType> mFunList = new ArrayList<>();
    private SelfFunAdapter mSelfFunAdapter;

    private int[] mRes = {R.mipmap.my_community, R.mipmap.my_fa, R.mipmap.my_activity,
            R.mipmap.my_guanzhu, R.mipmap.my_strategy, R.mipmap.my_service,
            R.mipmap.my_share};
    private String[] mNames = {"我的社区", "我的收藏", "我的活动", "我的关注"};
    private User mUser;

    public static SelfFragment getInstance(String value) {
        SelfFragment selfFragment = new SelfFragment();
        Bundle bundle = new Bundle();
        bundle.putString("value", value);
        selfFragment.setArguments(bundle);
        return selfFragment;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mToolbarName.setText("个人中心");

        mSelfSwipe.setColorSchemeColors(getResources().getColor(R.color.toolbar_color));

        for (int i = 0; i < mNames.length; i++) {
            ProductType productType = new ProductType();
            productType.setName(mNames[i]);
            productType.setSrc(mRes[i]);
            mFunList.add(productType);
        }

        mSelfRecyFun.setLayoutManager(new GridLayoutManager(mActivity, 4));
        mSelfFunAdapter = new SelfFunAdapter(mActivity, R.layout.item_self_fun, mFunList);
        mSelfRecyFun.setAdapter(mSelfFunAdapter);

        mToolbarSetting.setOnClickListener(v -> {
            AppCenter.mSpUtil.putString(Constant.USERINFO,null);
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mSelfSwipe.setRefreshing(false);
    }

    @Override
    protected void initData() {
        super.initData();
    }



    public void setData(User user){

        if(user == null){
            return;
        }

        mSelfMySign.setText(user.getGoldCount()+"");

        String cPhone = user.getPhone();
        String phone = cPhone.substring(0,3) +
                " **** "
                + cPhone.substring(7,cPhone.length());

        mSelfPhone.setText(phone);

    }

    @Override
    protected void initListener() {
        super.initListener();
        mSelfFunAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (position == 0){
                    startActivity(new Intent(getActivity(), MyCommunityActivity.class));
                }

                if(position == 1){
                    startActivity(new Intent(getActivity(), CollectionActivity.class));
                }
                if(position == 2){
                    startActivity(new Intent(getActivity(), ActivityActivity.class));
                }
            }
        });
        mSelfNested.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (nestedScrollView, i, i1, i2, i3) -> mSelfOval.setData(i1));
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_self;
    }

}
