package com.onion.community.engine.community;

import android.view.View;
import com.onion.community.R;
import com.onion.community.base.fragment.BaseFragment;

public class CommunityFragment extends BaseFragment<CommunityPresenter> implements CommunityContract.View{

    @Override
    protected void initView(View view) {
        super.initView(view);
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
