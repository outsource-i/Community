package com.onion.community.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onion.community.R;
import com.onion.community.bean.ProductType;

import java.util.List;

public class SelfFunAdapter extends BaseQuickAdapter<ProductType, BaseViewHolder> {

    private Activity mActivity;

    public SelfFunAdapter(Activity activity, int layoutResId, @Nullable List<ProductType> data) {
        super(layoutResId, data);
        mActivity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductType item) {
        if(item == null){
            return;
        }
        helper.setText(R.id.item_self_tv_name,item.getName())
                .setImageResource(R.id.item_self_iv_img,item.getSrc());
    }


}
