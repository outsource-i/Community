package com.onion.community.util;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;

/**
 * Created by zhangqi on 2017/12/20.
 * e-mail : ${email}
 * desc :
 */

public class Test {
    //Android 中同一个TextView设置不同大小字体
//    price = Tools.formatToSepara(item.price);
// if (TextUtils.isEmpty(price))
//            return;
//    price = "¥ " + price + "元";
//    Spannable sp = new SpannableString(price);
// sp.setSpan(new AbsoluteSizeSpan(12, true), 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
// sp.setSpan(new AbsoluteSizeSpan(16, true), 2, 3, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
// sp.setSpan(new AbsoluteSizeSpan(12, true), 3, price.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
// tv_price.setText(sp);

    public static  Spannable lastWordzoomOut(String text,int zoomSize) {
        if (TextUtils.isEmpty(text)) {
            return null;
        } else {
            Spannable sp = new SpannableString(text);
            sp.setSpan(new AbsoluteSizeSpan(zoomSize, true), text.length() - 1, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            return sp;
        }


    }

}
