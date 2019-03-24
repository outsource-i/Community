package com.onion.community.util;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.text.*;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.hyphenate.easeui.domain.EaseUser;
import com.onion.community.AppCenter;
import com.onion.community.constant.Constant;
import com.onion.community.constant.UserConstant;
import com.onion.community.util.logger.Logger;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangqi on 2017/12/20.
 * e-mail : ${email}
 * desc :
 */

public class U {

    public static String toDate(String time) {
        Long timeLong = Long.parseLong(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//要转换的时间格式
        Date date;
        try {
            date = sdf.parse(sdf.format(timeLong));
            return sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static EaseUser getEaseUiInfo(String userName){
        String json = AppCenter.mSpUtil.getString(userName+"ease_user_info");
        return AppCenter.mGson.fromJson(json,EaseUser.class);
    }

    public static void saveEaseUiInfo(EaseUser easeUser){
        AppCenter.mSpUtil.putString(easeUser.getUsername()+"ease_user_info",AppCenter.mGson.toJson(easeUser));
    }
    public static boolean isFixedPhone(String fixedPhone) {
        String reg = "(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|" +
                "(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)";
        return Pattern.matches(reg, fixedPhone);
    }

    public static String getModel() {
        return android.os.Build.MODEL;
    }

    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }

    public static void editTextMoneyListener(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            private int selectionStart;
            private int selectionEnd;
            private CharSequence temp;

            public boolean isOnlyPointNumber(String number) {
                Pattern pattern = Pattern.compile("^\\d+\\.?\\d{0,2}$");
                Matcher matcher = pattern.matcher(number);
                return matcher.matches();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    return;
                }
                selectionStart = editText.getSelectionStart();
                selectionEnd = editText.getSelectionEnd();

                if (!isOnlyPointNumber(editText.getText().toString())) {
                    //删除多余输入的字
                    s.delete(selectionStart - 1, selectionEnd);
                    editText.setText(s);
                    editText.setSelection(s.length());
                }
            }
        });
    }


    public static BigDecimal monthlyAccount(BigDecimal account, BigDecimal apr, BigDecimal monthSum) {
        // :每月月供额=〔贷款本金×月利率×(1＋月利率)＾还款月数〕÷〔(1＋月利率)＾还款月数-1〕
        BigDecimal monthApr = apr.divide(new BigDecimal("1200"), 20, BigDecimal.ROUND_HALF_UP);
        BigDecimal flag = new BigDecimal("1");
        // (1＋月利率)＾还款月数
        BigDecimal temp = (flag.add(monthApr)).pow(monthSum.intValue());
        BigDecimal first = account.multiply(monthApr).multiply(temp);
        BigDecimal second = temp.subtract(flag);
        return first.divide(second, 2, BigDecimal.ROUND_HALF_UP);
    }



    /**
     * 提供精确加法计算的add方法
     *
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public static double add(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确减法运算的sub方法
     *
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public static double sub(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确乘法运算的mul方法
     *
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static double mul(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供精确的除法运算方法div
     *
     * @param value1 被除数
     * @param value2 除数
     * @param scale  精确范围
     * @return 两个参数的商
     * @throws IllegalAccessException
     */
    public static double div(double value1, double value2, int scale) throws IllegalAccessException {
        //如果精确范围小于0，抛出异常信息
        if (scale < 0) {
            throw new IllegalAccessException("精确度不能小于0");
        }
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.divide(b2, scale).doubleValue();
    }

    /**
     * 四舍五入
     *
     * @param value
     * @return
     */
    public static double convert(double value) {
        long l1 = Math.round(value * 100); //四舍五入
        double ret = l1 / 100.0; //注意:使用 100.0 而不是 100
        return ret;
    }

    /*
     * 判断输入的是否为数字
     *
     * @返回true说明是数字，false说明不全是数字
     */
    private static boolean isNum(String phoneNum) {
        for (int i = 0; i < phoneNum.length(); i++) {
            if (!Character.isDigit(phoneNum.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static String CMCC = "移动";
    private static String UNICOM = "联通";
    private static String TELECOM = "电信";
    private static String UNKNOWN = "未知";

    /*
     * 判断运营商
     */
    public static String execute(String phone) {
        String head1 = "";
        String head2 = "";
        // 去除前后的空白
        phone = phone.trim();

        // 判断输入的电话号码是否合法
        if (phone == null || phone.length() < 11) {
            System.out.println("length<11");
            return UNKNOWN;
        } else {
            // 处理国内的+86开头
            if (phone.startsWith("+")) {
                phone = phone.substring(1);
            }
            if (phone.startsWith("86")) {
                phone = phone.substring(2);
            }
        }
        // 去除+86后电话号码应为11位
        if (phone.length() != 11) {
            System.out.println("not = 11");
            return UNKNOWN;
        }
        // 判断去除+86后剩余的是否全为数字
        if (!isNum(phone)) {
            System.out.println(" not num");
            return UNKNOWN;
        }
        // 截取前3或前4位电话号码，判断运营商
        head1 = phone.substring(0, 3);
        head2 = phone.substring(0, 4);

        // 移动前三位
        boolean cmcctemp3 = head1.equals("134") || head1.equals("135") || head1.equals("136")
                || head1.equals("137") || head1.equals("138")
                || head1.equals("139") || head1.equals("147")
                || head1.equals("150") || head1.equals("151")
                || head1.equals("152") || head1.equals("157")
                || head1.equals("158") || head1.equals("159")
                || head1.equals("182") || head1.equals("183")
                || head1.equals("184") || head1.equals("178")
                || head1.equals("187") || head1.equals("188");
        if (cmcctemp3) {
            return CMCC;
        }
        // 移动前4位
        boolean cmcctemp4 = head2.equals("1340") || head2.equals("1341")
                || head2.equals("1342") || head2.equals("1343")
                || head2.equals("1344") || head2.equals("1345")
                || head2.equals("1346") || head2.equals("1347")
                || head2.equals("1348") || head2.equals("1705");
        if (cmcctemp4) {
            return CMCC;
        }
        // 联通前3位
        boolean unicomtemp = head1.equals("130") || head1.equals("131")
                || head1.equals("132") || head1.equals("145")
                || head1.equals("155") || head1.equals("156") || head1.equals("176") || head1.equals("166")
                || head1.equals("185") || head1.equals("186");
        if (unicomtemp) {
            return UNICOM;
        }
        //unicom 4
        boolean unicomtemp4 = head1.equals("1709");
        if (unicomtemp4) {
            return UNICOM;
        }
        // 电信前3位
        boolean telecomtemp = head1.equals("133") || head1.equals("153")
                || head1.equals("181") || head1.equals("177")
                || head1.equals("180") || head1.equals("189")
                || head1.equals("173");
        if (telecomtemp) {
            return TELECOM;
        }
        //telecom 4
        boolean telecomtemp4 = head1.equals("1700");
        if (telecomtemp4) {
            return TELECOM;
        }

        return UNKNOWN;
    }

    public static SpannableStringBuilder toRichText(String info, int color, int from, int to) {
        SpannableStringBuilder builder = new SpannableStringBuilder(info);
        ForegroundColorSpan redSpan = new ForegroundColorSpan(color);
        ForegroundColorSpan redSpan1 = new ForegroundColorSpan(color);
        builder.setSpan(redSpan, from, to, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return builder;
    }

    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                //全角空格为12288，半角空格为32
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                //其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    public static String ToSBC(String input) {
        //半角转全角：
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    public static void initWebView(WebView webView, ProgressBar progressBar) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setSupportZoom(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setPluginState(WebSettings.PluginState.ON);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
//                if (newProgress == 100) {
//                    progressBar.setVisibility(View.GONE);
//                } else {
//                    progressBar.setVisibility(View.VISIBLE);
//                    progressBar.setProgress(newProgress);
//                }
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

    /**
     * 验证码倒计时
     *
     * @param time
     * @return
     */
    public static Observable<String> countdown(int time) {
        if (time < 0) time = 0;
        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(increaseTime -> (countTime - increaseTime.intValue()) + "")
                .take(countTime + 1);
    }

    public static Flowable<String> countdownFlowable(int time) {
        if (time < 0) time = 0;
        final int countTime = time;
        return Flowable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .map(increaseTime -> (countTime - increaseTime.intValue()) + "")
                .take(countTime + 1);
    }

    public static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(16[6-8])|(17[0,3,5-8])|(18[0-9])|(19[8,9])|(147))\\d{8}$";

    /**
     * 验证手机号（精确）
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobileExact(String string) {
        return isMatch(REGEX_MOBILE_EXACT, string);
    }

    /**
     * string是否匹配regex
     *
     * @param regex  正则表达式字符串
     * @param string 要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    private static boolean isMatch(String regex, String string) {
        return !StringUtils.isEmpty(string) && Pattern.matches(regex, string);
    }

    /**
     * 正则：邮箱
     */
    public static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /**
     * 验证邮箱
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isEmail(String string) {
        return isMatch(REGEX_EMAIL, string);
    }

    /**
     * 集中处理 网络请求的错误
     *
     * @param error
     */
    public static void errorUtil(Throwable error) {

        if (!NetworkUtils.isAvailable(AppCenter.getInstance())) {
            ToastUtil.showShort(AppCenter.getInstance(), "当前无网络,请检测当前是否联网");
            return;
        }

        Logger.i(error.toString());
        if (error instanceof SocketTimeoutException) {
            Toast.makeText(AppCenter.getInstance(), "服务器未响应", Toast.LENGTH_SHORT).show();
        } else if (error instanceof UnknownHostException) {
            Toast.makeText(AppCenter.getInstance(), "服务器未响应", Toast.LENGTH_SHORT).show();
        } else if (error instanceof HttpException) {
            if (((HttpException) error).code() == 403) {

            } else {
                Toast.makeText(AppCenter.getInstance(), "服务器未响应", Toast.LENGTH_SHORT).show();
            }
        } else if (error instanceof ConnectException) {
            Toast.makeText(AppCenter.getInstance(), "服务器未响应", Toast.LENGTH_SHORT).show();
        } else {
            Logger.i(error.toString());
        }
    }

    public static String connectString(String... str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length; i++) {
            sb.append(str[i]);
        }
        return sb.toString();
    }

    public static String Sign(String... sign) {
        sign = sort(sign);
        if (sign == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < sign.length; i++) {
            sb.append(sign[i]);
        }
        sb.append(Constant.SIGN);
        return encodeToMd5(sb.toString());
    }

    public static String Sign(Map<String, String> map, String... sign) {
        List<String> list = new ArrayList<>();

        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            list.add(stringStringEntry.getValue());
        }
        for (int i = 0; i < sign.length; i++) {
            list.add(sign[i]);
        }
        sign = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            sign[i] = list.get(i);
        }
        sign = sort(sign);
        if (sign == null) {
            ToastUtil.showShort(AppCenter.getInstance(), "网络错误,请连接网络");
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < sign.length; i++) {
            sb.append(sign[i]);
        }
        sb.append(Constant.SIGN);
        return encodeToMd5(sb.toString());
    }

    public static String[] sort(String... sign) {
        for (int i = 0; i < sign.length; i++) {
            if (sign[i] == null) {
                return null;
            }
        }
        Arrays.sort(sign);
        return sign;
    }

    public static String encodeToMd5(String source) {
        if (source == null)
            return null;

        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");

            char hexDigits[] = {
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                    'e', 'f'};

            byte[] strTemp = source.getBytes("utf-8");
            mdTemp.update(strTemp);

            byte[] md = mdTemp.digest();
            int k = 0;
            int j = md.length;
            char str[] = new char[j * 2];

            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }





    public static void saveImage(Context context, int id) {
        // getFilesDir().getAbsolutePath()+"/image"\
        //在本地创建一个文件夹
        File file = new File(Environment.getExternalStorageDirectory() + "/image");
        // File absoluteFile = getFilesDir().getAbsoluteFile();
        //判断本地是否存在，防止每次启动App都要创建
        if (file.exists()) {
            return;
        }
        //使用BitmapFactory把res下的图片转换成Bitmap对象
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
        FileOutputStream fos = null;
        try {
            //获得一个可写的输入流
            fos = context.openFileOutput("image", Context.MODE_PRIVATE);
            //使用图片压缩对图片进行处理  压缩的格式  可以是JPEG、PNG、WEBP
            //第二个参数是图片的压缩比例，第三个参数是写入流
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static String mapIsNull(Map<String, String> map) {
        Logger.i("那一页的参数个数:" + map.size());
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getValue() == null || entry.getValue().equals("")) {
                return (String) entry.getKey();
            }
        }
        return null;
    }

    public static String toChinaString(String info) {
        String tips = null;

        switch (info) {
            case UserConstant.QQ_NUM:
                tips = "QQ";
                break;
            case UserConstant.EMAIL:
                tips = "电子邮箱";
                break;
            case UserConstant.USUALLYADDRESS:
                tips = "常住地址";
                break;
            case UserConstant.EDUCATION:
                tips = "学历";
                break;
            case UserConstant.MARRY:
                tips = "婚姻状况";
                break;
            case UserConstant.GETCHILD:
                tips = "子女状况";
                break;
            case UserConstant.PROFESSION:
                tips = "职业";
                break;
            case UserConstant.MONTHLYPAY:
                tips = "收入";
                break;
            case UserConstant.BUSINESS:
                tips = "单位";
                break;
            case UserConstant.BUSI_PROVINCE:
                tips = "省份";
                break;
            case UserConstant.BUSI_CITY:
                tips = "城市";
                break;
            case UserConstant.BUSI_ADDRESS:
                tips = "单位地址";
                break;
            case UserConstant.BUSI_PHONE:
                tips = "单位电话";
                break;
            case UserConstant.RELATIVES:
                tips = "亲属关系";
                break;
            case UserConstant.RELA_PHONE:
                tips = "亲属电话";
                break;
            case UserConstant.SOCIETY:
                tips = "社会关系";
                break;
            case UserConstant.SOCI_PHONE:
                tips = "社会关系电话";
                break;
            case UserConstant.BANK_ID:
                tips = "银行名称";
                break;
            case UserConstant.BANK_PHONE:
                tips = "银行卡预留手机号";
                break;
            case UserConstant.BANK_NUM:
                tips = "银行卡号码";
                break;
            case "per_id":
                tips = "系统错误,请退出APP重新登录";
                break;
        }

        return tips;
    }

    private static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Photo.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID};

    private static final int PHONES_DISPLAY_NAME_INDEX = 0;

    /**
     * 电话号码
     **/
    private static final int PHONES_NUMBER_INDEX = 1;


    public static String filterEmoji(String source) {
        if (source != null) {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                source = emojiMatcher.replaceAll("*");
                return source;
            }
            return source;
        }
        return source;
    }

    /**
     * 正则：汉字
     */
    public static final String REGEX_CHZ = "^[\\u4e00-\\u9fa5]+$";

    public static boolean isChz(String string) {
        return isMatch(REGEX_CHZ, string);
    }

    /**
     * 状态栏的高度
     *
     * @param application
     * @return
     */
    public static int getBarHeight(Application application) {
        int statusBarHeight = (int) Math.ceil(20 * application.getResources().getDisplayMetrics().density);
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = application.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
            return statusBarHeight;
        }

        if (sbar == 0) {
            return statusBarHeight;
        }
        return sbar;
    }

    private Bundle getAppMetaDataBundle(PackageManager packageManager,
                                        String packageName) {
        Bundle bundle = null;
        try {
            ApplicationInfo ai = packageManager.getApplicationInfo(packageName,
                    PackageManager.GET_META_DATA);
            bundle = ai.metaData;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("getMetaDataBundle", e.getMessage(), e);
        }
        return bundle;
    }


}
