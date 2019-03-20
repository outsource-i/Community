package com.onion.community.engine.login;

import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.onion.community.AppCenter;
import com.onion.community.R;
import com.onion.community.base.BaseActivity;
import com.onion.community.bean.HttpWrapper;
import com.onion.community.bean.User;
import com.onion.community.constant.Constant;
import com.onion.community.engine.HomeActivity;
import com.onion.community.util.logger.Logger;
import com.onion.community.view.LoginView;

import java.util.List;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.toolbar_back)
    ImageView mToolbarBack;
    @BindView(R.id.toolbar_name)
    TextView mToolbarName;
    @BindView(R.id.toolbar_rl)
    RelativeLayout mToolbarRl;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.login_rg)
    LoginView mLoginRg;
    @BindView(R.id.login_edt_phone)
    AppCompatEditText mLoginEdtPhone;
    @BindView(R.id.login_edt_password)
    AppCompatEditText mLoginEdtPassword;
    @BindView(R.id.login_login)
    AppCompatButton mLoginLogin;
    @BindView(R.id.login_rb_sms)
    RadioButton mLoginRbSms;
    @BindView(R.id.login_rb_pwd)
    RadioButton mLoginRbPwd;
    private boolean mIsLogin = true;

    @Override
    protected void initView() {
        super.initView();

        mToolbarName.setText("登录");


        String userInfo = AppCenter.mSpUtil.getString(Constant.USERINFO);

       User user = AppCenter.mGson.fromJson(userInfo,User.class);

       if(user != null){

           startActivity(new Intent(this,HomeActivity.class));
           finish();
       }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = getText(mLoginEdtPhone);
                String password = getText(mLoginEdtPassword);

                if(TextUtils.isEmpty(phone)){
                    showMessage("请输入手机号");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    showMessage("请输入用户密码");
                    return;
                }

                if(mIsLogin){
                    mPresenter.login(phone,password);
                }else{
                    mPresenter.register(phone,password);
                }



            }
        });

        mLoginRg.setOnCheckListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.login_rb_sms:
                    mIsLogin = true;
                    mLoginLogin.setText("登录");
                    mToolbarName.setText("登录");
                    break;
                case R.id.login_rb_pwd:
                    mIsLogin = false;
                    mLoginLogin.setText("注册");
                    mToolbarName.setText("注册");
                    break;
            }
            mLoginEdtPassword.setText("");
            mLoginEdtPhone.setText("");
            mLoginRg.check(checkedId);
        });
    }

    @Override
    public void loginSuccess(HttpWrapper<User> userHttpWrapper) {
            if(Constant.SUCCESS_CODE == userHttpWrapper.code){
                User data = userHttpWrapper.getData();
                    EMClient.getInstance().login(String.valueOf(data.getId()), data.getPassword(), new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            dissDialog();
                            EMClient.getInstance().groupManager().loadAllGroups();
                            EMClient.getInstance().chatManager().loadAllConversations();

                            AppCenter.mSpUtil.putString(Constant.USERINFO,new Gson().toJson(userHttpWrapper.getData()));
                            List<String> usernames = null;
                            try {
                                usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                            }
                            Logger.i(new Gson().toJson(usernames));
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        }

                        @Override
                        public void onError(int i, String s) {
                            showMessage("聊天服务器登录失败!");
                            dissDialog();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        }

                        @Override
                        public void onProgress(int i, String s) {

                        }
                    });


            }else{
                showMessage(userHttpWrapper.getInfo());
            }
    }

    @Override
    public void registerSuccess(HttpWrapper<User> userHttpWrapper) {
        if(Constant.SUCCESS_CODE == userHttpWrapper.code){
            /**
             * 注册成功
             */
            showMessage("注册成功,请登录!");
            mLoginRbSms.performClick();
        }else{
            showMessage(userHttpWrapper.getInfo());
        }


    }

    @Override
    protected boolean needLightMode() {
        return true;
    }

    @Override
    protected boolean needFullScreen() {
        return true;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }



}
