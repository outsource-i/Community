package com.onion.community.util.logger;


/**
 * Created by zhangqi on 2016/10/25.
 */

public class MyLogger {

    private static boolean sIsDebug;
    private static String sTag;
    public MyLogger(){

    }

    public static class Hide{
        public static MyLogger sMyLogger = new MyLogger();
    }

    public static MyLogger getInstance(){
        return Hide.sMyLogger;
    }

    public static void init(String tag, boolean isOpen){
        sTag = tag;
        initLogger(isOpen);
    }

    public static void close(){
        sIsDebug = false;
    }

    public static void open(){
        sIsDebug = true;
    }

    private static void initLogger(boolean isOpen){
        Logger.init(sTag,isOpen);
    }

    public static void i(String tag, String msg){
        if(!sIsDebug)
            return;
        sTag = tag;
        Logger.t(sTag).i(msg);
    }

    public static void i(String msg){
        i(sTag,msg);
    }
}
