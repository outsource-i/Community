package com.onion.community.engine.community.detail.detail;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.onion.community.R;
import com.onion.community.base.SimpleActivity;
import com.onion.community.util.StatusBarUtil;

/**
 * Created by zhangqi on 2019/3/24.
 */
public class CommmentActivity extends SimpleActivity {

    public static final int REQUEST_COMMENT = 100;
    public static final int RESULT_COMMENT = 101;
    public static String data = "data";
    @BindView(R.id.toolbar_back)
    ImageView mToolbarBack;
    @BindView(R.id.toolbar_name)
    TextView mToolbarName;
    @BindView(R.id.toolbar_right)
    TextView mToolbarRight;
    @BindView(R.id.toolbar_rl)
    RelativeLayout mToolbarRl;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.comment_input)
    EditText mCommentInput;

    @Override
    protected void initView() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null);
        StatusBarUtil.StatusBarLightMode(this);
        setStatusBarColor(R.color.white);

        mToolbarBack.setOnClickListener(v -> {
            finish();
        });
        mToolbarName.setText("回复");
    }

    @Override
    protected void initListener() {
        super.initListener();
        mToolbarRight.setOnClickListener(v -> {
            String text = getText(mCommentInput);
            if(TextUtils.isEmpty(text)){
                showMessage("请输入回复内容!");
                return;
            }

            Intent intent = getIntent();
            intent.putExtra(data,text);
            setResult(RESULT_COMMENT,intent);
            finish();
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment;
    }

}
