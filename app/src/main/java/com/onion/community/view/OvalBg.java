package com.onion.community.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.onion.community.R;

/**
 * created by zhangqi on 2018/12/19
 */
public class OvalBg extends View {

    private float mHeight = 0;
    private RectF mRect = new RectF();
    private float mMult = 1;
    private float mMost = 0;
    private float mSpeed = 1;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public OvalBg(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setBackgroundColor(Color.WHITE);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OvalBg);

        mMult = a.getFloat(R.styleable.OvalBg_mult,1f);
        mSpeed = a.getFloat(R.styleable.OvalBg_speed,1f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        float m = width / mMult;

        mRect.left = - m;
        mRect.top = 0;
        mRect.right = width + m;
        mRect.bottom = height * 2;
        mHeight = (int) mRect.bottom;
        mMost = mHeight;
        int left = getResources().getColor(R.color.toolbar_color_l);
        int right = getResources().getColor(R.color.toolbar_color);

        LinearGradient linearGradient = new LinearGradient(0,0,width,height*2,right,left,Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(0,-mHeight / 2);
        canvas.drawOval(mRect,mPaint);
    }

    public void setData(float y){

        y *= mSpeed;
        if(y >= mMost){
            y = mMost;
        }

        mRect.bottom = mMost - y;
        mHeight = (int) mRect.bottom;

        invalidate();
    }
}
