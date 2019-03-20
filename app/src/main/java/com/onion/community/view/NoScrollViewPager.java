package com.onion.community.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by OnionMac on 2018/1/4.
 */

public class NoScrollViewPager extends ViewPager {

    private boolean mIsScroll = true;

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScroll(boolean isScroll){
        this.mIsScroll = isScroll;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //分发
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(mIsScroll){
            return super.onInterceptTouchEvent(ev);
        }else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mIsScroll){
            return super.onTouchEvent(ev);
        }

        return true;
    }
}
