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
import android.view.animation.AccelerateDecelerateInterpolator;
import com.onion.community.R;

/**
 * Created by OnionMac on 2018/4/3.
 */

public class ProgressView extends View {

    private Paint mPaint;
    private Paint mCirclePaint;
    private Paint mBottomCirclePaint;
    private int mAngle;
    private RectF mRect;
    private RectF mCircleRect;
    private int mHeight;
    private int mWidth;

    private int mCircleWidth = DensityUtil.dp2px(2);
    private int mBottomCircleWidth = DensityUtil.dp2px(6);
    private int mSpacing = DensityUtil.dp2px(13);

    private int mCircleColor = Color.parseColor("#335899f5");
    private int mPaintColor = getResources().getColor(R.color.main_color);
    private int mBottomCircleColor = Color.parseColor("#e5e5e5");
    private boolean mStart = false;

    public ProgressView(Context context) {
        this(context,null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(Color.TRANSPARENT);
        initView();
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(mPaintColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBottomCircleWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setDither(true);
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(mCircleWidth);
        mCirclePaint.setStrokeCap(Paint.Cap.ROUND);

        mBottomCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBottomCirclePaint.setDither(true);
        mBottomCirclePaint.setColor(mBottomCircleColor);
        mBottomCirclePaint.setStyle(Paint.Style.STROKE);
        mBottomCirclePaint.setStrokeWidth(mBottomCircleWidth);
        mBottomCirclePaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mHeight = h;
        mWidth = w;

        mCircleRect = new RectF(mCircleWidth,mCircleWidth,h - mCircleWidth,w - mCircleWidth);
        mRect = new RectF(mCircleWidth+mSpacing,mCircleWidth+mSpacing,h - mSpacing - mCircleWidth,w - mSpacing - mCircleWidth);
    }

    public void start(){
        computer();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(mCircleRect,0,360,false,mCirclePaint);
        canvas.drawArc(mRect,0,360,false,mBottomCirclePaint);
        canvas.drawArc(mRect,0,-mAngle,false,mPaint);
    }

    public void computer(){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 360);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAngle = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        valueAnimator.start();
    }

}
