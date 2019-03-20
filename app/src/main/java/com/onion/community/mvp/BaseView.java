package com.onion.community.mvp;

/**
 * Created by zhangqi on 2017/12/20.
 * e-mail : ${email}
 * desc :
 */

public interface BaseView {

    /**
     * 网络进度条
     * @param msg
     */
    void showDialog(String msg);

    /**
     * 结束网络进度条
     */
    void dissDialog();

    void showMessage(String msg);
}
