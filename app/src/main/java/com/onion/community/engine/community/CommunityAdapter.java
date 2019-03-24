package com.onion.community.engine.community;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onion.community.R;
import com.onion.community.bean.Community;
import com.onion.community.bean.CommunityType;

import java.util.List;

public class CommunityAdapter extends BaseQuickAdapter<Community, BaseViewHolder> {

    private Activity mActivity;

    public CommunityAdapter(Activity activity, int layoutResId, @Nullable List<Community> data) {
        super(layoutResId, data);
        mActivity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, Community item) {

        helper.setText(R.id.item_community_name,item.getCommunityName())
                .setText(R.id.item_community_taolun,"讨论: "+item.getCommunityArticleCount())
                .setText(R.id.item_community_guanzhu,"关注: "+item.getCommunityPeopleCount());

        helper.addOnClickListener(R.id.item_community_commit);
        Glide.with(mActivity).load(item.getCommunityImg()).apply(new RequestOptions()
                .placeholder(R.mipmap.zhanwei).error(R.mipmap.zhanwei))
                .into((ImageView)helper.getView(R.id.item_community_img));
        if(item.isFollow()){
            helper.setText(R.id.item_community_commit,"已关注");
        }else{
            helper.setText(R.id.item_community_commit,"未关注");
        }
    }


}
