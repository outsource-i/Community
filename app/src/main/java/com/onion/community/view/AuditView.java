package com.onion.community.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class AuditView extends View {
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


    public AuditView(Context context) {
        super(context);
    }

    public AuditView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AuditView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        mPointPaint.setColor(Color.RED);
        mPointPaint.setStrokeWidth(1);
    }




}
