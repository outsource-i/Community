package com.onion.community.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangqi on 2017/11/11 0011.
 */

public class SeekView extends View {
    public boolean mAaiming = false;
    private RectF mRectF = new RectF();
    private boolean isShowShadow;
    private boolean onDown;
    private int isTouchOffset;
    private String TAG = "zhang";
    private int mLineLeft;
    private int mLineRight;
    private List<String> mDatas;
    private List<TickMark> mTickMarks = new ArrayList<>();
    private int mTickMarkCount; // 拖点字的大小
    private int mHeight;
    private int mWidth;
    private Paint mThumbPaint;
    private Paint mLinePaint; // 直线的画笔
    private Paint mTipsPaint; // 最高最低产品的画笔
    private Paint mTickMarkPaint; // 每个点的画笔
    private int mWaiRoundRadius = DensityUtil.dp2px(3);
    private int mLineHeight = DensityUtil.dp2px(3); //线的高度
    private int mThumbRadius = DensityUtil.dp2px(10); // 拖点的高度
    private int mTickMarkHeight = DensityUtil.dp2px(8); // 标点的高度
    private int mTickMarkWidth = DensityUtil.dp2px(4); // 标点的宽度
    private int mTickMarkTextSize = DensityUtil.sp2px(14); // 标点字的大小
    private int mThumbTickMarkOffset = DensityUtil.dp2px(5); // 拖点和标点字的距离
    private int mMarginTop = DensityUtil.dp2px(15);
    private int mThumbOffset; // 每个标点的距离
    private int mTipsTextSize = DensityUtil.sp2px(12);
    private String[] tips = {"(最低可借额度)","(最高可借额度)"};
    private int mL;
    private TickMark mCurrentTickMark;
    private RectF mTickMarkRect;

    /**
     * 特殊情况 只有一个产品的特殊处理
     */
    private boolean mOnlyOne;

    private int mWaiRadiusMoveColor = Color.parseColor("#44ffffff");
    private int mWaiRadiusStopColor = Color.parseColor("#44ffffff");

    public SeekView(Context context) {
        this(context,null);
    }

    public SeekView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SeekView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    public void setDatas(List<String> datas) {
        if(datas.size() == 1){
            mOnlyOne = true;
            mDatas = new ArrayList<>();
            mDatas.add("暂无");
            mDatas.add(datas.get(0));
            mDatas.add("暂无");
        }else{
            mDatas = datas;
        }
        mTickMarkCount = mDatas.size();

        requestLayout();
        invalidate();
    }

    private void initView() {
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(mLineHeight);
        mLinePaint.setColor(Color.WHITE);

        mTipsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTipsPaint.setColor(Color.WHITE);
        mTipsPaint.setTextSize(mTipsTextSize);

        mTickMarkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTickMarkPaint.setColor(Color.WHITE);
        mTickMarkPaint.setTextSize(mTickMarkTextSize);

        mThumbPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mThumbPaint.setColor(Color.WHITE);

        mTickMarkRect = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /**
         * 计算 控件的宽和高
         */
        setMeasuredDimension(widthMeasureSpec,
                mMarginTop+(2*mThumbTickMarkOffset) + 2 * (mThumbRadius + mWaiRoundRadius)
        +mTipsTextSize+mTickMarkTextSize);

        /**
         * 计算出标点的距离
         */
        int duoyu = mTickMarkCount * mTickMarkWidth;
        mL = mThumbRadius + mWaiRoundRadius;
        isTouchOffset = mL;
        mLineLeft = mL - mTickMarkWidth / 2;
        mLineRight = mWidth-mL+mTickMarkWidth / 2;

        mThumbOffset = (mWidth - duoyu - (mL - mTickMarkWidth/2)) / (mTickMarkCount - 1);
        mThumbOffset = (mWidth - (2 * mLineLeft) - duoyu) / (mTickMarkCount - 1);
        mRectF.left = mL;
        mRectF.right = mWidth - mL;
        mRectF.top = mMarginTop;
        mRectF.bottom = mMarginTop + 2 * mL + mThumbTickMarkOffset;

        int h = mMarginTop+(mThumbRadius-mLineHeight);
        int top = h - mTickMarkHeight / 2;
        int bottom = h + mTickMarkHeight / 2;
        mTickMarks.clear();
        for (int i = 0; i < mTickMarkCount; i++) {
            int left = mLineLeft +(i * (mTickMarkWidth + mThumbOffset));
            int right = mTickMarkWidth + left;
            RectF rectF = new RectF();
            rectF.left = left;
            rectF.right = right;
            rectF.top = top;
            rectF.bottom = bottom;
            mTickMarks.add(new TickMark(rectF,mDatas.get(i)));
        }

        if(mOnlyOne){
            isTouchOffset = mTickMarks.get(1).mCenterX;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mTickMarkCount <= 0){
            return;
        }
        /**
         * 画线
         */
        int h = mMarginTop+(mThumbRadius-mLineHeight);
        canvas.drawLine(mLineLeft,h,mLineRight-1,h,mLinePaint);
        /**
         * 画TickMark
         */
        for (int i = 0; i < mTickMarkCount; i++) {
            int left = mLineLeft +(i * (mTickMarkWidth + mThumbOffset));
            int right = mTickMarkWidth + left;
            RectF rectF = mTickMarks.get(i).mRectF;
            canvas.drawRoundRect(rectF,4,4,mTickMarkPaint);
            String text = mDatas.get(i);
            float textSize = mTickMarkPaint.measureText(text);
            float tickY = h + mL + mThumbTickMarkOffset + mTickMarkPaint.getTextSize();
            if(i == 0){
                float tickX = mL + DensityUtil.dp2px(5);
                canvas.drawText(text,tickX,tickY,mTickMarkPaint);
                String tip = tips[0];
                canvas.drawText(tip,tickX,tickY+mThumbTickMarkOffset+mTipsTextSize,mTipsPaint);
            }else if(i == mTickMarkCount - 1){
                float tickX = left - DensityUtil.dp2px(5) - textSize;
                canvas.drawText(text,tickX,tickY,mTickMarkPaint);

                String tip = tips[1];
                float tipWidth = mTipsPaint.measureText(tip);
                float tipsX = left - tipWidth;
                canvas.drawText(tip,tipsX,tickY+mThumbTickMarkOffset+mTipsTextSize,mTipsPaint);
            }else{
                float tickX = (left + right)/2 - textSize/2;
                canvas.drawText(text,tickX,tickY,mTickMarkPaint);
            }
        }

        /**
         * 画thumb
         */
        if(isShowShadow){
            mThumbPaint.setColor(mWaiRadiusMoveColor);
            canvas.drawCircle(isTouchOffset,h,mThumbRadius+mWaiRoundRadius,mThumbPaint);
        }else{
            mThumbPaint.setColor(mWaiRadiusStopColor);
            canvas.drawCircle(isTouchOffset,h,mThumbRadius+mWaiRoundRadius/2,mThumbPaint);
        }
        mThumbPaint.setColor(Color.WHITE);
        canvas.drawCircle(isTouchOffset,h,mThumbRadius,mThumbPaint);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                float x = event.getX();
                float y = event.getY();
                onDown = true;
                boolean in = inRect(x,y);
//                Log.d(TAG, "onTouchEvent: down:"+x);
                isShowShadow = true;
                if(in){

                    isTouchOffset = (int) (x);
                    invalidate();
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                float currX = event.getX();
                float currY = event.getY();
                boolean currIn = inRect(currX,currY);
                if(currIn){
                    isTouchOffset = (int) (currX);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                onDown = false;
                getParent().requestDisallowInterceptTouchEvent(false);

                final float x1 = event.getX();
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(!mAaiming){
                            autoSelect(x1);
                        }
                    }
                },300);
                break;

        }
        return super.onTouchEvent(event);
    }

    private void autoSelect(float x) {

        /**
         * 如果拖拽超出left和right  则强制指定
         */
        if(x < mRectF.left){
            x = mRectF.left;
        }

        if(x > mRectF.right){
            x = mRectF.right;
        }

        isShowShadow = true;
        mCurrentTickMark = getCenterX(x);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(x, mCurrentTickMark.mCenterX);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                isTouchOffset = (int)animatedValue;
                invalidate();
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {
                mAaiming = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mAaiming = false;
                isShowShadow = false;
                invalidate();

                if(mOnSelectThumbListener != null){
                    mOnSelectThumbListener.onSelect(mCurrentTickMark);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        
        valueAnimator.start();

    }

    /**
     * 找到最接近的
     * @param x
     * @return
     */
    private TickMark getCenterX(float x) {
        if(mOnlyOne){
            return mTickMarks.get(1);
        }
        Map<Integer,TickMark> map = new HashMap<>();
        int min = 10000;
        for (int i = 0; i < mTickMarks.size(); i++) {
            TickMark tickMark = mTickMarks.get(i);
            int curr = Math.abs((int) (x - tickMark.mCenterX));
            map.put(curr,tickMark);
            if(curr < min){
                min = curr;
            }
        }

        return map.get(min);
    }

    private boolean inRect(float x, float y) {
        if(x > mRectF.left && x < mRectF.right && y > mRectF.top && y < mRectF.bottom) {
            return true;
        }

        if(onDown && x > mRectF.left && x < mRectF.right) {
            return true;
        }

        return false;
    }

    public class TickMark{
        public RectF mRectF;
        public int mCenterX;
        public String mText;

        public TickMark(RectF rectF, String text){
            mRectF = rectF;
            mText = text;

            mCenterX = (int) (mRectF.left + mTickMarkWidth/ 2);

//            Log.d(TAG, "TickMark: "+mCenterX);
        }
    }

    public void setOnSelectThumbListener(onSelectThumbListener onSelectThumbListener) {
        mOnSelectThumbListener = onSelectThumbListener;
    }

    private onSelectThumbListener mOnSelectThumbListener;

    public interface onSelectThumbListener{
        void onSelect(TickMark tickMark);
    }
}
