package com.onion.community.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onion.community.R;
import com.onion.community.bean.Article;
import com.onion.community.constant.Constant;
import com.onion.community.util.U;

import java.util.Date;
import java.util.List;

public class CommunityAdapter extends BaseQuickAdapter<Article, BaseViewHolder> {

    private Activity mActivity;

    public CommunityAdapter(Activity activity, int layoutResId, @Nullable List<Article> data) {
        super(layoutResId, data);
        mActivity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, Article item) {
        helper.setText(R.id.item_community_article_name,item.getUser().getNickName())
                .setText(R.id.item_community_article_title,item.getTitle())
                .setText(R.id.item_community_article_info,item.getContent())
                .setText(R.id.item_community_article_time, U.toDate(item.getUpdateDate()))
                .setText(R.id.item_community_article_read,item.getClickCount()+"阅读 "+item.getCommentCount()+"评论");

        Glide.with(mActivity).load(item.getUser().getHeadImg()).apply(new RequestOptions()
                .placeholder(R.mipmap.zhanwei).error(R.mipmap.zhanwei))
                .into((ImageView)helper.getView(R.id.item_community_article_head));

        Glide.with(mActivity).load(Constant.IMG_BASE+item.getArticleImg()).apply(new RequestOptions()
                .placeholder(R.mipmap.zhanwei).error(R.mipmap.zhanwei))
                .into((ImageView)helper.getView(R.id.item_community_article_img));


    }


}