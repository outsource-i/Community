package com.onion.community.engine.community;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.Unbinder;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onion.community.AppCenter;
import com.onion.community.R;
import com.onion.community.base.fragment.BaseFragment;
import com.onion.community.bean.Community;
import com.onion.community.bean.CommunityType;
import com.onion.community.bean.HttpWrapper;
import com.onion.community.constant.Constant;
import com.onion.community.engine.community.detail.CommunityActivity;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends BaseFragment<CommunityPresenter> implements CommunityContract.View {

    @BindView(R.id.toolbar_back)
    ImageView mToolbarBack;
    @BindView(R.id.toolbar_name)
    TextView mToolbarName;
    @BindView(R.id.toolbar_rl)
    RelativeLayout mToolbarRl;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    Unbinder unbinder;
    @BindView(R.id.communitytype_recy)
    RecyclerView mCommunityTypeRecy;
    @BindView(R.id.community_recy)
    RecyclerView mCommunityRecy;

    private CommunityTypeAdapter communityTypeAdapter;
    private CommunityAdapter communityAdapter;

    private List<CommunityType> mCommunityTypes = new ArrayList<>();
    private List<Community> mCommunitys = new ArrayList<>();

    private int mPrePosition;
    private List<Community> myFollowCommunity;
    private Community mFollowCommunity;

    @Override
    protected void initView(View view) {
        super.initView(view);

        mToolbarBack.setVisibility(View.GONE);
        mToolbarName.setText("板块");

        mCommunityTypeRecy.setLayoutManager(new LinearLayoutManager(getActivity()));
        communityTypeAdapter = new CommunityTypeAdapter(getActivity(),R.layout.item_communitytype,mCommunityTypes);
        mCommunityTypeRecy.setAdapter(communityTypeAdapter);

        mCommunityRecy.setLayoutManager(new LinearLayoutManager(getActivity()));
        communityAdapter = new CommunityAdapter(getActivity(),R.layout.item_community,mCommunitys);
        mCommunityRecy.setAdapter(communityAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        String json = AppCenter.mSpUtil.getString(Constant.MYFOLLOW_COMMUNITY);
        myFollowCommunity = new Gson().fromJson(json, new TypeToken<List<Community>>() {}.getType());

        for (Community community : myFollowCommunity) {
            community.setFollow(true);
        }
        mPresenter.loadType();
    }

    @Override
    protected void initListener() {
        super.initListener();
        communityAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                mFollowCommunity = mCommunitys.get(position);
                if(mFollowCommunity.isFollow()){
                    return;
                }

                mPresenter.followCommunity(getUser().getId(), mFollowCommunity.getId());
            }
        });

        communityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //进入论坛
                Community community = mCommunitys.get(position);

                Intent intent = new Intent(getActivity(), CommunityActivity.class);
                intent.putExtra(CommunityActivity.COMMUNITYID,community.getId());
                startActivity(intent);
            }
        });
        communityTypeAdapter.setOnItemClickListener((adapter, view, position) -> {
            if(mPrePosition == position){
                return;
            }

            CommunityType communityType = mCommunityTypes.get(position);
            CommunityType communityType1 = mCommunityTypes.get(mPrePosition);
            communityType1.setCheck(false);
            communityType.setCheck(true);
            mPrePosition = position;
            if(communityType.getId().equals("-1")){
                communityTypeAdapter.notifyDataSetChanged();
                reMyFollow();
                return;
            }

            mPresenter.getCommunity(communityType.getId());
            communityTypeAdapter.notifyDataSetChanged();
        });
    }

    public void reMyFollow(){
        mCommunitys.clear();
        mCommunitys.addAll(myFollowCommunity);
        communityAdapter.notifyDataSetChanged();
    }

    @Override
    public void getTypeOk(HttpWrapper<List<CommunityType>> listHttpWrapper) {
        if(Constant.SUCCESS_CODE == listHttpWrapper.code){
            List<CommunityType> data = listHttpWrapper.getData();
            mCommunityTypes.clear();


            CommunityType communityType = new CommunityType();
            communityType.setId("-1");
            communityType.setCheck(true);
            communityType.setName("我的关注");
            mCommunityTypes.add(communityType);
            mCommunityTypes.addAll(data);
            communityTypeAdapter.notifyDataSetChanged();
            reMyFollow();
        }
    }

    @Override
    public void getCommunityOk(HttpWrapper<List<Community>> listHttpWrapper) {
        if(Constant.SUCCESS_CODE == listHttpWrapper.code){
            List<Community> data = listHttpWrapper.getData();
            mCommunitys.clear();
            mCommunitys.addAll(data);

            for (Community mCommunity : mCommunitys) {
                for (Community community : myFollowCommunity) {
                    if(mCommunity.getId().endsWith(community.getId())){
                        mCommunity.setFollow(true);
                    }
                }
            }

            communityAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void followOk(HttpWrapper<Community> communityHttpWrapper) {
        if(Constant.SUCCESS_CODE == communityHttpWrapper.getCode()){
            myFollowCommunity.add(communityHttpWrapper.getData());

            AppCenter.mSpUtil.putString(Constant.MYFOLLOW_COMMUNITY,new Gson().toJson(myFollowCommunity));
            mFollowCommunity.setFollow(true);
            //关注成功
            communityAdapter.notifyDataSetChanged();
        }else{
            showMessage(communityHttpWrapper.getInfo());
        }
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_community;
    }



}
