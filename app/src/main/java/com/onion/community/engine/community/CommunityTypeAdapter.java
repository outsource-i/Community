package com.onion.community.engine.community;

import android.app.Activity;
import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onion.community.R;
import com.onion.community.bean.CommunityType;

import java.util.List;

public class CommunityTypeAdapter extends BaseQuickAdapter<CommunityType, BaseViewHolder> {

    private Activity mActivity;

    public CommunityTypeAdapter(Activity activity, int layoutResId, @Nullable List<CommunityType> data) {
        super(layoutResId, data);
        mActivity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommunityType item) {

        if(item.isCheck()){
            helper.itemView.setBackgroundColor(mActivity.getResources().getColor(R.color.white));
        }else{
            helper.itemView.setBackgroundColor(mActivity.getResources().getColor(R.color.gray_f7f7f7));
        }

        helper.setText(R.id.item_communitytype_name,item.getName());
    }


}
