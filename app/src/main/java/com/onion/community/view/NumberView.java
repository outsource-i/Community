package com.onion.community.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

/**
 * Created by OnionMac on 2018/7/11.
 */

public class NumberView extends android.support.v7.widget.AppCompatTextView {

    private int mStart;
    private int mEnd;
    private ValueAnimator mValueAnimator;
    private float mValue;

    public NumberView(Context context) {
        this(context,null);
    }

    public NumberView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setData(int start,int end){
        mStart = start;
        mEnd = end;
    }

    public void start(){
        if(mValueAnimator != null && mValueAnimator.isRunning()){
            return;
        }
        mValueAnimator = ValueAnimator.ofFloat(mStart, mEnd);
        mValueAnimator.setDuration(2000);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(animation -> {
            mValue = (float)animation.getAnimatedValue();
            setText((int)mValue+"");
        });
        mValueAnimator.start();
    }


    // 清除动画
    public void clearAnimator() {
        if (null != mValueAnimator) {
            if (mValueAnimator.isRunning()) {
                mValueAnimator.removeAllListeners();
                mValueAnimator.removeAllUpdateListeners();
                mValueAnimator.cancel();
            }
            mValueAnimator = null;
        }
    }

}
