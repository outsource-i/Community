package com.onion.community.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.widget.RadioGroup;
import com.onion.community.R;
import com.onion.community.listener.OnCheckListener;

/**
 * Created by OnionMac on 2018/3/14.
 */

public class LoginView extends RadioGroup{

    private String TAG = "tag";

    private final int SMS = 1;
    private final int PWD = 2;
    private int type = SMS;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mWidth;
    private int mHeight;
    private OnCheckListener mOnCheckListener;
    private int mLeftDrawPoint;
    private int mDrawWidth;

    private int mOffset;

    public LoginView(Context context) {
        this(context,null);
    }

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(getResources().getColor(R.color.toolbar_color));
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);

        setWillNotDraw(false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        mLeftDrawPoint = mWidth / 2 / 2 / 2;

        mDrawWidth = (mWidth/2) - (mLeftDrawPoint * 2);

        Log.d(TAG, "onSizeChanged: "+mLeftDrawPoint+"-"+mDrawWidth+"-"+mWidth);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        check(getChildAt(0).getId());
        setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.login_rb_sms:
                    type = PWD;
                    break;
                case R.id.login_rb_pwd:
                    type = SMS;
                    break;
            }
            check(checkedId);
            scroll();
            if(mOnCheckListener != null){
                mOnCheckListener.onCheckChange(group,checkedId);
            }
        });
    }

    public void setOnCheckListener(OnCheckListener onCheckListener) {
        mOnCheckListener = onCheckListener;
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        canvas.drawLine(mLeftDrawPoint+mOffset,mHeight,mLeftDrawPoint+mDrawWidth+mOffset,mHeight,mPaint);
    }

    public void scroll(){
        int value1,value2;
        if(type == SMS){
            value1 = 0;
            value2 = mWidth / 2;
        }else{
            value2 = 0;
            value1 = mWidth / 2;
        }
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(value1, value2);
        valueAnimator.setInterpolator(new BounceInterpolator());
        valueAnimator.addUpdateListener(valueAnimator1 -> {
            float animatedValue = (float) valueAnimator1.getAnimatedValue();
            mOffset = (int)animatedValue;
            invalidate();
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                LoginView.this.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                LoginView.this.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }

}
