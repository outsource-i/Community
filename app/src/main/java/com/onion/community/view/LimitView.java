package com.onion.community.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * Created by OnionMac on 2018/7/30.
 */

public class LimitView extends View {

    private int mPadding = 30;
    private Paint mLinePaint;
    private RectF mRect;
    private ValueAnimator mValueAnimator;
    private float mValue;
    private boolean move;


    public LimitView(Context context) {
        this(context,null);
    }

    public LimitView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LimitView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setDither(true);
        mLinePaint.setColor(Color.WHITE);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(mPadding);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mRect = new RectF(mPadding,mPadding,getMeasuredWidth()-mPadding,getMeasuredHeight()*2 - mPadding);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(mRect,-180,mValue,false,mLinePaint);
    }

    public void start(){
        if(mValueAnimator != null && mValueAnimator.isRunning()){
            return;
        }
        mValueAnimator = ValueAnimator.ofFloat(0, 180);
        mValueAnimator.setDuration(2000);
        mValueAnimator.setInterpolator(new BounceInterpolator());
        mValueAnimator.addUpdateListener(animation -> {
            mValue = (float)animation.getAnimatedValue();

            postInvalidate();
        });
        move = true;
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
