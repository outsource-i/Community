package com.onion.community.engine.self.collection;

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
import com.onion.community.bean.Community;

import java.util.List;

public class CollectionAdapter extends BaseQuickAdapter<Article, BaseViewHolder> {

    private Activity mActivity;

    public CollectionAdapter(Activity activity, int layoutResId, @Nullable List<Article> data) {
        super(layoutResId, data);
        mActivity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, Article item) {

        helper.setText(R.id.item_collection_title,item.getTitle())
                .setText(R.id.item_collection_name,item.getUser().getNickName())
                .setText(R.id.item_collection_from,item.getCommunity().getCommunityName())
                .setText(R.id.item_collection_click,"点击: "+item.getClickCount())
                .setText(R.id.item_collection_huifu,"回复: "+item.getCommentCount());

        Glide.with(mActivity)
                .load(item.getUser().getHeadImg())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into((ImageView) helper.getView(R.id.item_collection_img));
    }


}
