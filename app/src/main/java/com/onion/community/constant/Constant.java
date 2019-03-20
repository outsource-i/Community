package com.onion.community.constant;


/**
 * Created by zhangqi on 2017/12/20.
 * e-mail : ${email}
 * desc :
 */

public class Constant {

    //平台
    public static final String PLATFORM = "Android";
    public static final String PACKAGE = "com.kaka.qianbao";
    public static final String APPNAME = "咔咔钱包";
    public static final String PROJECT_NAME = "YHS";
    public static final String SP_LOCAL = "config";
    public static final int PAY_SUCCESS = 1000;
    public static final int PAY_FAILD = 1001;
    public static final int PAY_CANCEL = 1002;
    public static final String VERTIFY_ERROR = "204";
    public static final String LOAD = "加载中...";
    public static final String MOXIE_KEY = "66ff25e7184c4d1cb65d062333bea0ec";

    public static final int REFUES = 0;
    public static final int BLACK = 1;

    /**
     * 支付页面接口
     */
    public static String REPAY_PAGE = "loan/repayPage.action?";
    /**
     * 版本更新相关
     */
    public static final String VERSION_SUFFIX = "version/getVersion.action";
    //超时时间
    public static final int TIMEOUT = 300;
    public static final String AUTH_BASE_URL = "http://www.baidu.com";

    //    public static final String BASE_URL = "https://app.youmishanjie.com/2.0.2/";
    public static final String VERSION_ADDRESS = "http://node.youmishanjie.com/getVersionInfo.action";
        public static final String BASEURL_REGISTER = "http://app.youmishanjie.com/";
    public static final String SIGN = "jhhymsj";
    //白骑士合作编号
    public static final String APP_PARTNERID = "jinhuhang";
    //用户信息
    public static final String USERINFO = "userinfo";
    //是否加载过guide页
    public static final String GUIDE = "guide";
    public static final String TOOLBAR_COLOR = "#1e82d2";
    public static final String TOKENKEY = "tokenKey";
    public static final String DEVICE = "device";

    /**
     * 判断消息已读未读
     */
    public static final String MESSAGE_YES = "y";
    public static final String MESSAGE_NO = "n";

    public static final String PHONE_SIGN = "7";
    //返回Code
    public static final int SUCCESS_CODE = 200;
    public static final int ERROR_CODE = 201;
    public static final String VIP_WAIT = "1019";

    public static final String REGISTER_AGAIN = "1002";
    public static final String SMS_TOKEN = "1009";
    public static final String VIP_NO = "1017";
    public static final String VIP_OVERDU = "1018";
    public static final String TOKEN_TIMEOUT = "301";
    public static final String PACT_FAILD = "2001";
    public static final String REFUSE = "203";
    public static final String NODE_NO = "207";
    public static final String NODE_OK = "208";
    public static final String ZHIMA_CODE = "220";
    public static final String CONTACT_OUT = "209";
    public static final String AUTH_ALL_OK = "2"; //认证全部完成
    public static final String AUTH_NO_OK = "1"; //认证未完成
    public static final String AUTH_FAILD = "3";//认证失败未解禁
    public static final String AUTH_WAIT = "4";//有异步节点未返回报告
    /**
     * MD5错误
     */
    public static final String SIGN_CODE = "302";
    public static final String ID_CODE = "202";
    /**
     * 白骑士错误
     */
    public static final String BQSLOSE_CODE = "300";

    public static final String USERID = "per_id";

    /**
     * 银行存管开户地址
     */
    public static final String BANKCUSTODY = "http://192.168.1.107:8080/api/haikou/registAndOpenHaiKou.do";
    public static final String BANKCUSTODY_RESULT = "http://192.168.1.107:8080/api/haikou/bingBankInfo.do";
    public static final String CONTRACT_FILE = "recovery_contract";

    /**
     */
    public static final String CLIENTVERSION = "clientVersion";
    public static final String APIVERSION = "apiVersion";
    public static final String API_VERSION = "1.0.0";
    public static final String PAY_WAITING = "202";
    public static final String USERID_NEW = "perId";

    public static String SENDDYNAMIC = "sendDynamic";

}
