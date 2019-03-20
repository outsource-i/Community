package com.onion.community.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onion.community.R;
import com.onion.community.bean.Article;

import java.util.List;

public class ArticleAdapter extends BaseQuickAdapter<Article, BaseViewHolder> {

    private Activity mActivity;

    public ArticleAdapter(Activity activity, int layoutResId, @Nullable List<Article> data) {
        super(layoutResId, data);
        mActivity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, Article item) {
        helper.setText(R.id.item_article_title,item.getTitle())
        .setText(R.id.item_article_content,item.getContent())
                .setText(R.id.item_article_name,item.getUser().getNickName())
                .setText(R.id.item_article_community,item.getCommunity().getCommunityName())
                .setText(R.id.item_article_comment,item.getCommentCount())
                .setText(R.id.item_article_click,item.getClickCount());

        Glide.with(mActivity)
                .load(item.getArticleImg())
                .into((ImageView) helper.getView(R.id.item_article_img));
    }


}
