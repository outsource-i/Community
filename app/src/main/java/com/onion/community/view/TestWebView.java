package com.onion.community.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class TestWebView extends WebView {

    public TestWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public interface OnOverScrollListener {
        void onOverScrolled(TestWebView v, boolean onBottom);
    }
    private OnOverScrollListener mOnOverScrollListener;

    public void setOnOverScrollListener(OnOverScrollListener listener) {
        this.mOnOverScrollListener = listener;
    }
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (mOnOverScrollListener != null) {
            // clampedY=true的前提下，scrollY=0时表示滑动到顶部，scrollY!=0时表示到底部
            mOnOverScrollListener.onOverScrolled(this, scrollY != 0 && clampedY);
        }
    }
}