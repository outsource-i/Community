package com.onion.community.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.onion.community.AppCenter;
import com.onion.community.R;
import com.onion.community.util.U;

/**
 * Created by OnionMac on 2018/7/6.
 */

public class HeaderBar extends LinearLayout {

    private View mToolbar;
    private boolean mT ;
    public HeaderBar(Context context) {
        this(context,null);
    }

    public HeaderBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HeaderBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HeaderBar);
        mT = a.getBoolean(R.styleable.HeaderBar_headerbar_t,false);

        if(mT){
            setBackgroundColor(Color.WHITE);
        }else{
            setBackground(context.getResources().getDrawable(R.drawable.status_gra));

        }
        setOrientation(LinearLayout.VERTICAL);
        int barHeight = U.getBarHeight(AppCenter.getInstance());
        setPadding(0,barHeight,0,0);
//        mToolbar = LayoutInflater.from(context).inflate(R.layout.widget_toolbar,this,false);


        a.recycle();
    }

}
