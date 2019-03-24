package com.onion.community;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.onion.community.constant.Constant;
import com.onion.community.di.component.AppComponent;
import com.onion.community.di.component.DaggerAppComponent;
import com.onion.community.di.module.AppModule;
import com.onion.community.di.module.HttpModule;
import com.onion.community.util.SpUtil;
import com.onion.community.util.U;
import com.onion.community.util.logger.MyLogger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by zhangqi on 2017/12/20.
 * e-mail : ${email}
 * desc : appcenter
 */

public class AppCenter extends MultiDexApplication {

    private final String TAG = "YHS";

    public static SpUtil mSpUtil;

    /**
     * debug模式
     */
    public static boolean DEBUG = true;

    /**
     * 白骑士tokenkey
     */
    public static String TOKEN_KEY;

    /**
     * 序列化
     */
    public static Gson mGson;

    /**
     * dagger2 DI组件
     */
    public static AppComponent appComponent;

    /**
     * activity 栈
     */
    private static LinkedList<Activity> mActivityList = new LinkedList<>();
    /**
     * fragment 栈
     */
    private static LinkedList<Fragment> mFragmentList = new LinkedList<>();

    /**
     * AppCenter
     */
    private static AppCenter mInstance;
    //暂无
    public static String nodeCode;
    //暂无
    public static String org;
    //金钱格式化
    public static DecimalFormat mDf;

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater((context, layout) -> {
            layout.setPrimaryColorsId(R.color.gray_e, R.color.gray_3);//全局设置主题颜色
            return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            layout.setPrimaryColorsId(R.color.gray_e, R.color.gray_3);//全局设置主题颜色
            return new ClassicsFooter(context).setDrawableSize(20);
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            if (getPackageName().equals(getProcessName(this, Process.myPid()))) {
                appInit();
            }
        } catch (Exception e) {
            appInit();
        }
    }

    public void appInit() {
        mInstance = this;

        mDf = new DecimalFormat("0.00");
        mSpUtil = new SpUtil(this, Constant.SP_LOCAL);

        mGson = new Gson();
        MyLogger.init(Constant.PROJECT_NAME, DEBUG);

//        BaiQiShi.initBaiQiShi(this.getApplicationContext());
//        Logger.i("App入口,调用的接口地址为:"+Constant.BASE_URL+TOKEN_KEY);
        Log.d(TAG, "=======================DEBUG: " + DEBUG);


        // 调用初始化方法初始化sdk
        EMClient.getInstance().init(this, initOptions());
        // 设置开启debug模式
        EMClient.getInstance().setDebugMode(true);
        EaseUI.getInstance().init(this,initOptions());
        EaseUI.getInstance().setUserProfileProvider(U::getEaseUiInfo);

    }

    /**
     * 添加activity
     */
    public static void addActivity(Activity activity) {
        if (activity != null) {
            mActivityList.addFirst(activity);
        }
    }

    /**
     * 关闭App
     */
    public static void cloaseApp() {
        for (int i = 0, size = mActivityList.size(); i < size; i++) {
            mActivityList.get(i).finish();
        }
    }

    public static String getApiVersion() {
        return Constant.API_VERSION;
    }

    /**
     * 移除activity
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        mActivityList.remove(activity);
    }

    /**
     * dagger2组件初始化
     *
     * @return
     */
    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(mInstance))
                    .httpModule(new HttpModule())
                    .build();
        }
        return appComponent;
    }

    public static AppCenter getInstance() {
        return mInstance;
    }


    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    /**
     * SDK初始化的一些配置
     * 关于 EMOptions 可以参考官方的 API 文档
     * [图片]http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1chat_1_1_e_m_options.html
     */
    private EMOptions initOptions() {

        EMOptions options = new EMOptions();
        // 设置Appkey，如果配置文件已经配置，这里可以不用设置
        // options.setAppKey("lzan13#hxsdkdemo");
        // 设置自动登录
        options.setAutoLogin(true);
        // 设置是否需要发送已读回执
        options.setRequireAck(true);
        // 设置是否需要发送回执，
        options.setRequireDeliveryAck(true);
        // 设置是否根据服务器时间排序，默认是true
        options.setSortMessageByServerTime(false);
        // 收到好友申请是否自动同意，如果是自动同意就不会收到好友请求的回调，因为sdk会自动处理，默认为true
        options.setAcceptInvitationAlways(true);
        // 设置是否自动接收加群邀请，如果设置了当收到群邀请会自动同意加入
        options.setAutoAcceptGroupInvitation(false);
        // 设置（主动或被动）退出群组时，是否删除群聊聊天记录
        options.setDeleteMessagesAsExitGroup(false);
        // 设置是否允许聊天室的Owner 离开并删除聊天室的会话
        options.allowChatroomOwnerLeave(true);
        // 设置google GCM推送id，国内可以不用设置
        // options.setGCMNumber(MLConstants.ML_GCM_NUMBER);
        // 设置集成小米推送的appid和appkey
        // options.setMipushConfig(MLConstants.ML_MI_APP_ID, MLConstants.ML_MI_APP_KEY);

        return options;
    }
}
