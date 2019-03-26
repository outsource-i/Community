package com.onion.community.listener;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import cn.jpush.android.api.JPushInterface;
import com.onion.community.engine.HomeActivity;
import com.onion.community.util.logger.Logger;

/**
 * Created by OnionMac on 2018/4/18.
 */

public class JReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();
        Logger.i( "onReceive - " + intent.getAction() + ", extras: " );

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Logger.i("JPush用户注册成功"+JPushInterface.getRegistrationID(context));
//            Log.d("JPush", "JPush用户注册成功: "+JPushInterface.getRegistrationID(context));

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Logger.i("接受到推送下来的自定义消息");

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Logger.i("接受到推送下来的通知");

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Logger.i("用户点击打开了通知");

            Intent splash = new Intent(context, HomeActivity.class);
            splash.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(splash);
        } else {
            Logger.i( "Unhandled intent - " + intent.getAction());
        }
    }
}
