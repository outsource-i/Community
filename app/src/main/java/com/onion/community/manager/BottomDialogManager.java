package com.onion.community.manager;

import android.app.Activity;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.onion.community.AppCenter;
import com.onion.community.R;
import com.onion.community.listener.Listener;
import com.pl.wheelview.WheelView;


import java.util.ArrayList;


/**
 * Created by zhangqi on 2017/2/15.
 * dialog管理
 */

public class BottomDialogManager {

    private ArrayList<String> mList;
    private BottomSheetDialog mBottomSheetDialog;
    private Activity mActivity;
    private Listener.SelectOKListener mSelectOKListener;
    private TextView mTv_ok;
    private TextView mTv_cancel;
    private TextView mTitle;
    private WheelView mWheelView;

    public BottomDialogManager(Activity activity){
        mActivity = activity;
        mBottomSheetDialog = new BottomSheetDialog(mActivity);

        initView();
    }

    private void initView() {
        View bottomView = LayoutInflater.from(mActivity).inflate(R.layout.bottom_all, null, false);
        mBottomSheetDialog.setContentView(bottomView);
        bottomView.findViewById(R.id.item_tv_cancel).setOnClickListener(v -> mBottomSheetDialog.dismiss());
        mTv_ok = (TextView) bottomView.findViewById(R.id.item_tv_ok);
        mTv_cancel = (TextView) bottomView.findViewById(R.id.item_tv_cancel);
        mWheelView = (WheelView) bottomView.findViewById(R.id.bottom_sign);
        mTitle = (TextView) bottomView.findViewById(R.id.item_tv_title);
        mTv_ok.setOnClickListener(v -> {
            if(mSelectOKListener != null){
                if(!mWheelView.isScrolling()){
                    dismiss();
                    mSelectOKListener.select(mWheelView.getSelectedText());
                }else{
                    Toast.makeText(AppCenter.getInstance(),"滑动选择中,无法确定",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void setTextColor(int color){
        mTv_ok.setTextColor(color);
    }
    public void setText(String str){
        mTv_cancel.setText(str);
    }
    public void setData(ArrayList<String> list){
        mList = list;
        setSelectData();
    }

    public ArrayList<String> getList(){
        return mList;
    }

    /**
     * 给城市选择器设置数据 和监听
     */
    private void setSelectData(){
        if(mList != null){
            mWheelView.setData(mList);
        }
    }

    public void setOkListener(Listener.SelectOKListener listener){
        mSelectOKListener = listener;
    }

    public void refreshData(ArrayList<String> list){
        mWheelView.refreshData(list);
    }

    public void show(){
        mBottomSheetDialog.show();
    }

    public void dismiss(){
        mBottomSheetDialog.dismiss();
    }

    public void setTitle(String data){
        mTitle.setText(data);
    }

    public void clear() {
        if(mList != null)
            mList.clear();
    }
}
