package com.onion.community.util;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by OnionMac on 2018/1/27.
 */

public class AnimUtil {

    public static Animation Scale(float fromX, float toX, float fromY, float toY,
                                  int pivotXType, float pivotXValue, int pivotYType, float pivotYValue, long duration, Interpolator interpolator, Animation.AnimationListener listener){
        ScaleAnimation scaleAnimation = new ScaleAnimation(fromX,toX,fromY,toY,pivotXType,pivotXValue,pivotYType,pivotYValue);
        scaleAnimation.setDuration(duration);
        scaleAnimation.setInterpolator(interpolator);
        scaleAnimation.setAnimationListener(listener);
        return scaleAnimation;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Animator circularReveal(View view, int centerX,  int centerY, float startRadius, float endRadius,Interpolator interpolator,Animator.AnimatorListener listener){
        Animator animator = ViewAnimationUtils.createCircularReveal(view,centerX,centerY,startRadius,endRadius);
        animator.setDuration(500);
        animator.setInterpolator(interpolator);
        animator.addListener(listener);
        return animator;
    }

    public static Animation translate(int fromXType, float fromXValue, int toXType, float toXValue,
                                       int fromYType, float fromYValue, int toYType, float toYValue
            ,long duration, Interpolator interpolator, Animation.AnimationListener listener){
        TranslateAnimation translateAnimation = new TranslateAnimation(fromXType,fromXValue,toXType,toXValue,fromYType,fromYValue,toYType,toYValue);
        translateAnimation.setDuration(duration);
        translateAnimation.setInterpolator(interpolator);
        translateAnimation.setAnimationListener(listener);
        return translateAnimation;
    }

    public static Animation translate(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta
            ,long duration, Interpolator interpolator, Animation.AnimationListener listener){
        TranslateAnimation translateAnimation = new TranslateAnimation(fromXDelta,toXDelta,fromYDelta,toYDelta);
        translateAnimation.setDuration(duration);
        translateAnimation.setInterpolator(interpolator);
        translateAnimation.setAnimationListener(listener);
        return translateAnimation;
    }

    public static Animation alpha(float fromAlpha, float toAlpha,long duration, Interpolator interpolator, Animation.AnimationListener listener){
        AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha,toAlpha);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setInterpolator(interpolator);
        alphaAnimation.setAnimationListener(listener);
        return alphaAnimation;
    }

    public static AnimationSet aminSet(boolean shareInterpolator,long duration,Interpolator interpolator,Animation ...animations){
        AnimationSet animationSet = new AnimationSet(shareInterpolator);
        animationSet.setDuration(duration);
        animationSet.setInterpolator(interpolator);

        for (int i = 0; i < animations.length; i++) {
            animationSet.addAnimation(animations[i]);
        }

        return animationSet;
    }
}
