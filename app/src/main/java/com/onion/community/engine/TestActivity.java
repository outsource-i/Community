package com.onion.community.engine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.onion.community.R;
import com.onion.community.util.U;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.test_web_view)
    WebView mTestWebView;
    @BindView(R.id.test_others_layout)
    LinearLayout mTestOthersLayout;
    @BindView(R.id.test_scroll_view)
    NestedScrollView mTestScrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ButterKnife.bind(this);

        U.initWebView(mTestWebView,null);
        mTestWebView.loadUrl("https://www.baidu.com");

    }
}
