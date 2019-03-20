package com.onion.community.listener;

/**
 * Created by zhangqi on 2017/2/10.
 */

public interface Listener {

    /**
     * mDialog的左键和右键回调
     */
    interface OnNormalAlertDialogChooseClickListener{
        void onLeft();
        void onRight();
    }

    /**
     * mDialog的单击事件
     */
    interface OnNormalAlertDialogSingleClickListener{
        void singleClick();
    }

    interface OnNormalDialogLeftListener{
        void onLeft();
    }

    interface OnNormalDialogRightListener{
        void onRight();
    }

    interface OnItemClickListener<T>{
        void onClick(int position, T t);
    }

    /**
     * 日期选择回调
     */
    interface OnDataSelectListener{
        void onDate(String date);
    }

    /**
     * 帮助中心回调
     */
    interface OnItemViewAQClickListener{
        void click(String q, String a, int position);
    }

    interface OnItemViewClickListener{
        void click(String data, boolean isCache, int position);
    }

    /**
     * 选择item
     */
    interface SelectOKListener{
        void select(String select);
    }

    /**
     * 选择省和城市的监听
     */
    interface CityOkListener{
        void select(String province, String city);
    }

    interface onCountDownEndListener{
        void onEnd();
    }
}
