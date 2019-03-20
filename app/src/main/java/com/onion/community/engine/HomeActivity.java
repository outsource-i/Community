package com.onion.community.engine;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.TextView;
import butterknife.BindView;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.onion.community.R;
import com.onion.community.adapter.HomePageAdapter;
import com.onion.community.base.BaseActivity;
import com.onion.community.bean.TabEntity;
import com.onion.community.engine.chat.NewsFragment;
import com.onion.community.engine.community.CommunityFragment;
import com.onion.community.engine.main.MainFragment;
import com.onion.community.engine.self.SelfFragment;
import com.onion.community.util.StatusBarUtil;
import com.onion.community.util.ToastUtil;
import com.onion.community.view.NoScrollViewPager;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.youth.banner.WeakHandler;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangqi on 2017/12/20.
 * e-mail : ${email}
 * desc :
 */

public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.View {

    @BindView(R.id.home_vp)
    NoScrollViewPager mHomeVp;
    @BindView(R.id.home_tab)
    CommonTabLayout mHomeTab;

    private static final int REQUEST_CODE_UPDATE = 20;

    private ArrayList<CustomTabEntity> mTabData = new ArrayList<>();

    private List<Fragment> mFramentList = new ArrayList<>();

    private String[] mTabName = {"首页","社区","信息", "我的"};

    private int[] mTabIconNormal = {R.mipmap.home_linq_normal,R.mipmap.shequ_normal, R.mipmap.shequ_normal,R.mipmap.home_wode_normal};
    private int[] mTabIconSelect = {R.mipmap.home_jiekuan_icon_press, R.mipmap.shequ_press,R.mipmap.shequ_press,R.mipmap.home_wode_dianji_press};
    public int mStatus;
    private long exitTime;
    WeakHandler weakHandler;
    /**
     * 下载地址
     */
    private String mDownloadUrl;
    /**
     * 是否强制更新
     */
    private boolean mIsUpdate;
    private Intent mIntent;
    private RxPermissions mRxPermissions;

    private TextView mMDialogVersionTv;
    private void permission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_LOGS,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.SET_DEBUG_APP,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.GET_ACCOUNTS,
                    Manifest.permission.WRITE_APN_SETTINGS,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        mRxPermissions = new RxPermissions(this);
        StatusBarUtil.setTranslucentForImageViewInFragment(HomeActivity.this, null);
        mStatus = StatusBarUtil.StatusBarLightMode(this);
        weakHandler = new WeakHandler();
        for (int i = 0; i < mTabName.length; i++) {
            mTabData.add(new TabEntity(mTabName[i], mTabIconSelect[i], mTabIconNormal[i]));
        }

        mHomeTab.setTabData(mTabData);

        mFramentList.add(new MainFragment());
        mFramentList.add(new CommunityFragment());
        mFramentList.add(new NewsFragment());
        mFramentList.add(new SelfFragment());

        mHomeVp.setAdapter(new HomePageAdapter(getSupportFragmentManager(), mFramentList));
        mHomeVp.setOffscreenPageLimit(3);
        mHomeVp.setScroll(false);
        permission();
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void initListener() {
        mHomeTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mHomeVp.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        mHomeVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mHomeTab.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showShort(this, "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIntent != null) {
            stopService(mIntent);
        }
        EventBus.getDefault().unregister(this);
    }

}
