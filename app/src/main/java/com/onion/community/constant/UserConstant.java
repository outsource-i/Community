package com.onion.community.constant;

/**
 * Created by zhangqi on 2016/9/29.
 */

public class UserConstant {

    public static final String NS001 = "NS001";
    public static final String NS002 = "NS002";
    public static final String NS003 = "NS003";
    public static final String NS004 = "NS004";
    public static final String NS005 = "NS005";
    public static final String NS006 = "NS006";

    public static final String BS000 = "BS000";
    public static final String BS001 = "BS001";
    public static final String BS002 = "BS002";
    public static final String BS003 = "BS003";
    public static final String BS004 = "BS004";
    public static final String BS005 = "BS005";
    public static final String BS006 = "BS006";
    public static final String BS007 = "BS007";
    public static final String BS008 = "BS008";
    public static final String BS009 = "BS009";

    public static final String  BS010 = "BS010";
    public static final String  BS011 = "BS011";
    public static final String  BS012 = "BS012";
    public static final String  BS013 = "BS013";
    public static final String  BS014 = "BS014";
    public static final String  BS015 = "BS015";
    public static final String  BS016 = "BS016";
    public static final String  BS017 = "BS017";
    public static final String  BS018 = "BS018";
    public static final String  BS019 = "BS019";



    /**
     * User
     */
    public static final String USER_ID = "per_id";
    public static final String USER_ACCOUNT = "phone";
    public static final String USER_PWD = "password";
    public static final String EXIT = "exit";

    public static final String DEFAULT_ID = "";
    public static final String TOKEN = "token";
    public static final String DEFAULT_TOKEN = "";

    /**
     * 登录
     */
    public static final int LOGIN_NULL = 1;
    public static final int LOGIN_PHONEREGEX = 2;
    public static final int LOGIN_PHONEERROR = 3;

    /**
     * 注册
     */
    public static final int REGIST_NULL = 4;
    public static final int REGIST_PHONEREGEX = 5;
    public static final int REGIST_VERIFY_ERROR = 6;
    public static final int REGIST_PHONE_RIGHT = 7;
    public static final int REGIST_NOTAGREE = 8;
    public static final int REGIST_SEND = 9;
    public static final String REGIST_PHONE = "phone";

    /**
     * 身份证验证
     */
    public static final String IDCARD_FRONT = "front"; // 身份证正面照片
    public static final int IDCARD_NULL = 9;  // 拍摄的图片为空
    public static final int TOKEN_OUT = 20;  // 拍摄的图片为空
    public static final int IDCARD_ERROR = 10;
    public static final int IDCARD_CAMERA_NULL = 11;
    public static final int IDCARD_CANCEL = 12;
    public static final int IDCARD_UNKNOW = 13;
    public static final int IDCARD_YZMNULL = 14;
    public static final int IDCARD_YZMERROR = 19;
    public static final int IDCARD_PWDNULL = 15;
    public static final int IDCARD_NETERROR = 16;
    public static final int IDCARD_SIX = 17;
    public static final int IDCARD_TWO = 18;


    /**
     * 个人信息
     */
    public static final String QQ_NUM = "qq_num";
    public static final String EMAIL = "email";
    public static final String USUALLYADDRESS = "usuallyaddress";
    public static final String EDUCATION = "education";
    public static final String MARRY = "marry";
    public static final String GETCHILD = "getchild";
    public static final String CAR = "isCar";
    public static final String HOUSE = "houseCondition";

    /**
     * 职业信息
     */
    public static final String PROFESSION = "profession";
    public static final String MONTHLYPAY = "monthlypay";
    public static final String BUSINESS = "business";
    public static final String BUSI_PROVINCE = "busi_province";
    public static final String BUSI_CITY = "busi_city";
    public static final String BUSI_ADDRESS = "busi_address";
    public static final String BUSI_PHONE = "busi_phone";
    public static final String BUSI_POST = "post";

    /**
     * 社会关系
     */
    public static final String BANK_ID = "bankCode";
    public static final String BANK_PHONE = "phone";
    public static final String STATUS = "status";
    public static final String BANK_NUM = "bank_num";

    /**
     * 银行卡信息
     */
    public static final String RELATIVES = "relatives";
    public static final String RELATIVES_NAME = "relatives_name";
    public static final String RELA_PHONE = "rela_phone";
    public static final String SOCIETY = "society";
    public static final String SOCI_PHONE = "soci_phone";
    public static final String SOCIETY_NAME = "society_name";

    /**
     * 选择联系人 请求码
     */
    public static final int SELECT_PCONTACT = 16;
    public static final int SELECT_FCONTACT = 17;
    public static final int REGIST_NULL_YZM = 18;
    public static String CAMERA_PERMISSION = "camera_permission";

    public static final String BORR_ID = "borr_id";

    public static final String SERVICE_INFO = "service_info";

}
