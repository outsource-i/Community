package com.onion.community.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.onion.community.util.ToastUtil;
import com.onion.community.util.logger.Logger;
import com.onion.community.view.IOSLoadingDialog;


/**
 * Created by zhangqi on 2016/10/12.
 */

public abstract class MessageActivity extends ConfigActivity{

    protected IOSLoadingDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IOSLoadingDialog.Builder loadBuilder = new IOSLoadingDialog.Builder(this)
                .setMessage("加载中...")
                .setShowMessage(false)
                .setCancelable(true)
                .setCancelOutside(false);
        mProgressDialog = loadBuilder.create();
    }

    public void showMessage(String msg){
        ToastUtil.showShort(this,msg);
    }

    public void showMessage(int strId){
        ToastUtil.showShort(this,getString(strId));
    }

    public void showMessageLong(String msg){
        ToastUtil.showLong(this,msg);
    }

    public void showMessageLong(int strId){
        ToastUtil.showLong(this,getString(strId));
    }

    public void showDialog(String msg){
        try {
            if(isMainThread()){
                mProgressDialog.setMessage(msg);
                if (!mProgressDialog.isShowing())
                    mProgressDialog.show();
            }else{
                runOnUiThread(() -> {
                    mProgressDialog.setMessage(msg);
                    if (!mProgressDialog.isShowing())
                        mProgressDialog.show();
                    Logger.i("您并不在子线程");
                });
            }
        }catch (Exception e){

        }
    }

    public void dissDialog(){
        try {
            if(isMainThread()){
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            }else{
                runOnUiThread(() -> {
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    Logger.i("您并不在子线程");
                });
            }
        }catch (Exception e){

        }
    }
}
