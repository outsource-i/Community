package com.onion.community.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.onion.community.AppCenter;
import com.onion.community.bean.User;
import com.onion.community.constant.Constant;
import com.onion.community.util.StatusBarUtil;
import com.onion.community.util.logger.Logger;
import me.yokeyword.fragmentation.SupportActivity;


/**
 */

public class ConfigActivity extends SupportActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Logger.i("进入"+this.getClass().getName());
        AppCenter.addActivity(this);


        if(needFullScreen()){
            StatusBarUtil.setTranslucentForImageViewInFragment(this,null);
        }

        if(needLightMode()){
            com.jaeger.library.StatusBarUtil.setLightMode(this);
        }
    }

    protected User getUser(){

        try {
            String json = AppCenter.mSpUtil.getString(Constant.USERINFO);
            if(TextUtils.isEmpty(json)){
                return null;
            }
            return AppCenter.mGson.fromJson(json,User.class);
        }catch (Exception e){
            return null;
        }

    }
    public void setStatusBarColor(int color){
        com.jaeger.library.StatusBarUtil.setColor(this,getResources().getColor(color),1);
    }

    protected boolean needLightMode() {
        return false;
    }

    protected boolean needFullScreen(){
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }





    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    protected boolean isMainThread(){
        return Looper.getMainLooper() == Looper.myLooper();
    }


}
