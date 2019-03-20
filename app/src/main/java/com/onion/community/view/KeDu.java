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
 * Created by OnionMac on 2018/7/6.
 */

public class KeDu extends View {

    private Paint mLinePaint;
    private Paint mPointPaint;
    private RectF mRect;
    private RectF mPointRect;

    private int mStartAngle = -225;
    private int mPadding = 40;
    private float mValue = 3;
    private boolean move;

    private int kedu = 90;
    private ValueAnimator mValueAnimator;

    public KeDu(Context context) {
        super(context);
    }

    public KeDu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public KeDu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setDither(true);
        mLinePaint.setColor(Color.WHITE);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(15);

        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setDither(true);
        mPointPaint.setColor(Color.parseColor("#fe3f1d"));
        mPointPaint.setStrokeWidth(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mRect = new RectF(mPadding,mPadding,getMeasuredWidth()-mPadding,getMeasuredHeight()-mPadding);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x0 = (getMeasuredWidth())/2;
        int y0 = (getMeasuredHeight())/2;
        int x,y = 0;
        mPointPaint.setColor(Color.RED);

        for (int i = 0; i < kedu; i++) {
            int i1 = mStartAngle + 3 * i;
            canvas.drawArc(mRect, i1, 1, false, mLinePaint);

            if( i > 3 && i < (kedu-3)){
                x = (int) (x0 + (getMeasuredWidth() - mPadding) / 2 * Math.cos(i1 * Math.PI / 180));
                y = (int) (y0 + (getMeasuredWidth() - mPadding) / 2 * Math.sin(i1 * Math.PI / 180));
                canvas.drawCircle(x, y, 1, mPointPaint);
            }

        }

        mPointPaint.setColor(Color.WHITE);
        x = (int) (x0 + (getMeasuredWidth() - mPadding)/2 * Math.cos((mStartAngle + 3*mValue) * Math.PI / 180));
        y = (int) (y0 + (getMeasuredWidth() - mPadding)/2 * Math.sin((mStartAngle + 3*mValue) * Math.PI / 180));
        canvas.drawCircle(x,y,8,mPointPaint);
    }

    public void start(){
        if(mValueAnimator != null && mValueAnimator.isRunning()){
            return;
        }
        mValueAnimator = ValueAnimator.ofFloat(3, kedu - 1 - 3);
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
