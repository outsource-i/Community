package com.onion.community.engine.chat;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.onion.community.R;
import com.onion.community.base.SimpleActivity;
import com.onion.community.bean.User;
import com.onion.community.util.StatusBarUtil;
import com.onion.community.util.U;

/**
 * Created by zhangqi on 2019/3/7.
 */
public class ChatActivity extends SimpleActivity {


    public static final String USERID = "userId";
    public static final String COMPANYNAME = "companyName";
    public static final String HEADER_URL = "headerurl";
    public static final String NICKNAME = "nickname";

    private String mChatId;
    private String mCompanyName;
    private String mNickName;
    private String mHeader;
    /**
     * 聊天类型
     */
    private int mChatType;

    private InputMethodManager mInputManager;
    private EaseChatFragment chatFragment;


    @Override
    protected void initView() {
        StatusBarUtil.setColor(this, Color.WHITE, 70);

        mChatId = getIntent().getStringExtra(USERID);
        mCompanyName = getIntent().getStringExtra(COMPANYNAME);
        mHeader = getIntent().getStringExtra(HEADER_URL);
        mNickName = getIntent().getStringExtra(NICKNAME);
        /**
         * 保存聊天对象
         */
        if(!TextUtils.isEmpty(mNickName) && !TextUtils.isEmpty(mHeader)){
            EaseUser easeUser = new EaseUser(mChatId);
            easeUser.setNickname(mNickName);
            easeUser.setAvatar(mHeader);
            U.saveEaseUiInfo(easeUser);
        }

        /**
         * 保存自己
         */
        User user = getUser();
        if(user != null){
            EaseUser easeUserMe = new EaseUser(user.getId());
            easeUserMe.setNickname(user.getNickName());
            easeUserMe.setAvatar(user.getHeadImg());

            U.saveEaseUiInfo(easeUserMe);
        }

        mChatType = EaseConstant.CHATTYPE_SINGLE;

        chatFragment = new ChatFragment();

        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_zq;
    }

    public String getToChatUsername(){
        return mChatId;
    }
}
