package com.onion.community.engine.community.post;

import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.even.mricheditor.ActionType;
import com.even.mricheditor.RichEditorAction;
import com.even.mricheditor.RichEditorCallback;
import com.even.mricheditor.ui.ActionImageView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.onion.community.R;
import com.onion.community.base.BaseActivity;
import com.onion.community.bean.Article;
import com.onion.community.bean.Community;
import com.onion.community.bean.HttpWrapper;
import com.onion.community.constant.Constant;
import com.onion.community.di.component.DaggerLoanComponent;
import com.onion.community.engine.community.detail.detail.CommunityDetailActivity;
import com.onion.community.post.GlideImageLoader;
import com.onion.community.post.fragment.EditHyperlinkFragment;
import com.onion.community.post.fragment.EditTableFragment;
import com.onion.community.post.fragment.EditorMenuFragment;
import com.onion.community.post.interfaces.OnActionPerformListener;
import com.onion.community.post.keyboard.KeyboardHeightObserver;
import com.onion.community.post.keyboard.KeyboardHeightProvider;
import com.onion.community.post.keyboard.KeyboardUtils;
import com.onion.community.post.util.FileIOUtil;
import com.onion.community.util.StatusBarUtil;
import com.onion.community.util.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PostActivity extends BaseActivity<PostPresenter> implements PostContract.View, KeyboardHeightObserver {


    public static final String COMMUNITY_DATA = "DATA";
    private Community community;

    @BindView(R.id.wv_container)
    WebView mWebView;
    @BindView(R.id.fl_action)
    FrameLayout flAction;
    @BindView(R.id.ll_action_bar_container)
    LinearLayout llActionBarContainer;

    @BindView(R.id.toolbar_back)
    TextView back;
    @BindView(R.id.toolbar_name)
    TextView name;
    @BindView(R.id.toolbar_right)
    TextView right;
    @BindView(R.id.post_subject)
    EditText mPostSubject;
    @BindView(R.id.toolbar_tu)
    TextView mToollbarTu;

    private KeyboardHeightProvider keyboardHeightProvider;
    private boolean isKeyboardShowing;
    private String htmlContent = "<p>呵呵 说点啥把</p>";

    private RichEditorAction mRichEditorAction;
    private RichEditorCallback mRichEditorCallback;
    private EditorMenuFragment mEditorMenuFragment;
    private List<ActionType> mActionTypeList =
            Arrays.asList(ActionType.BOLD, ActionType.ITALIC, ActionType.UNDERLINE,
                    ActionType.STRIKETHROUGH, ActionType.SUBSCRIPT, ActionType.SUPERSCRIPT,
                    ActionType.NORMAL, ActionType.H1, ActionType.H2, ActionType.H3, ActionType.H4,
                    ActionType.H5, ActionType.H6, ActionType.INDENT, ActionType.OUTDENT,
                    ActionType.JUSTIFY_LEFT, ActionType.JUSTIFY_CENTER, ActionType.JUSTIFY_RIGHT,
                    ActionType.JUSTIFY_FULL, ActionType.ORDERED, ActionType.UNORDERED, ActionType.LINE,
                    ActionType.BLOCK_CODE, ActionType.BLOCK_QUOTE, ActionType.CODE_VIEW);

    private List<Integer> mActionTypeIconList =
            Arrays.asList(R.drawable.ic_format_bold, R.drawable.ic_format_italic,
                    R.drawable.ic_format_underlined, R.drawable.ic_format_strikethrough,
                    R.drawable.ic_format_subscript, R.drawable.ic_format_superscript,
                    R.drawable.ic_format_para, R.drawable.ic_format_h1, R.drawable.ic_format_h2,
                    R.drawable.ic_format_h3, R.drawable.ic_format_h4, R.drawable.ic_format_h5,
                    R.drawable.ic_format_h6, R.drawable.ic_format_indent_decrease,
                    R.drawable.ic_format_indent_increase, R.drawable.ic_format_align_left,
                    R.drawable.ic_format_align_center, R.drawable.ic_format_align_right,
                    R.drawable.ic_format_align_justify, R.drawable.ic_format_list_numbered,
                    R.drawable.ic_format_list_bulleted, R.drawable.ic_line, R.drawable.ic_code_block,
                    R.drawable.ic_format_quote, R.drawable.ic_code_review);

    private static final int REQUEST_CODE_CHOOSE = 0;

    private String uuid;

    @Override
    protected void initView() {
        super.initView();
        uuid = UUID.randomUUID()+"";
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null);
        StatusBarUtil.StatusBarLightMode(this);
        setStatusBarColor(R.color.white);

        back.setOnClickListener(v -> finish());
        name.setText("发表主题");

        community = (Community) getIntent().getSerializableExtra(COMMUNITY_DATA);

        initImageLoader();
        initViewo();

        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40,
                getResources().getDisplayMetrics());
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9,
                getResources().getDisplayMetrics());
        for (int i = 0, size = mActionTypeList.size(); i < size; i++) {
            final ActionImageView actionImageView = new ActionImageView(this);
            actionImageView.setLayoutParams(new LinearLayout.LayoutParams(width, width));
            actionImageView.setPadding(padding, padding, padding, padding);
            actionImageView.setActionType(mActionTypeList.get(i));
            actionImageView.setTag(mActionTypeList.get(i));
            actionImageView.setActivatedColor(R.color.colorAccent);
            actionImageView.setDeactivatedColor(R.color.tintColor);
            actionImageView.setRichEditorAction(mRichEditorAction);
            actionImageView.setBackgroundResource(R.drawable.btn_colored_material);
            actionImageView.setImageResource(mActionTypeIconList.get(i));
            actionImageView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    actionImageView.command();
                }
            });
            llActionBarContainer.addView(actionImageView);
        }

        mEditorMenuFragment = new EditorMenuFragment();
        mEditorMenuFragment.setActionClickListener(new MOnActionPerformListener(mRichEditorAction));
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.fl_action, mEditorMenuFragment, EditorMenuFragment.class.getName())
                .commit();
    }

    public void initViewo() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.setWebChromeClient(new CustomWebChromeClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mRichEditorCallback = new MRichEditorCallback();
        mWebView.addJavascriptInterface(mRichEditorCallback, "MRichEditor");
        mWebView.loadUrl("file:///android_asset/richEditor.html");
        mRichEditorAction = new RichEditorAction(mWebView);

        keyboardHeightProvider = new KeyboardHeightProvider(this);
        findViewById(R.id.fl_container).post(new Runnable() {
            @Override public void run() {
                keyboardHeightProvider.start();
            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        name.setOnClickListener(v -> {
            mRichEditorAction.refreshHtml(mRichEditorCallback, onGetHtmlListener);
        });

        mToollbarTu.setOnClickListener(v -> {
            Intent intent = new Intent(this, ImageGridActivity.class);
            startActivityForResult(intent, REQUEST_CODE_CHOOSE);
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRichEditorAction.refreshHtml(mRichEditorCallback, onGetHtmlListener);
            }
        });
    }

    @Override
    public void postOk(HttpWrapper<String> stringHttpWrapper) {
        if(Constant.SUCCESS_CODE == stringHttpWrapper.getCode()){
            showMessage("发布成功");

            Article article = new Article();
            article.setId(stringHttpWrapper.getData());
            Intent intent = new Intent(this, CommunityDetailActivity.class);
            intent.putExtra(CommunityDetailActivity.ARTICLE_ID,article);
            startActivity(intent);
            finish();
        }
    }

    private RichEditorCallback.OnGetHtmlListener onGetHtmlListener =
            new RichEditorCallback.OnGetHtmlListener() {
                @Override public void getHtml(String html) {
//                    if (TextUtils.isEmpty(html)) {
//                        Toast.makeText(PostActivity.this, "Empty Html String", Toast.LENGTH_SHORT)
//                                .show();
//                        return;
//                    }
//                    Toast.makeText(PostActivity.this, html, Toast.LENGTH_SHORT).show();

                    String title = getText(mPostSubject);

                    if(TextUtils.isEmpty(title)){
                        showMessage("请输入标题");
                        return;
                    }

                    if(TextUtils.isEmpty(html)){
                        showMessage("请输入内容");
                        return;
                    }

                    Article article = new Article();
                    article.setCommunityId(community.getId());
                    article.setTitle(title);
                    article.setContent(html);
                    article.setUserId(getUser().getId());
                    article.setUuid(uuid);
                    mPresenter.post(article);
                }
            };

    @Override
    protected void initInject() {
        DaggerLoanComponent.builder()
                .activityComponent(getActivityComponent())
                .build().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post;
    }

    @Override
    public void onKeyboardHeightChanged(int height, int orientation) {
//        isKeyboardShowing = height > 0;
//        if (height > 0) {
//            height = height + DensityUtil.dp2px(120);
//            flAction.setVisibility(View.INVISIBLE);
//            ViewGroup.LayoutParams params = flAction.getLayoutParams();
//            params.height = height;
//            flAction.setLayoutParams(params);
//            performInputSpaceAndDel();
//        } else if (flAction.getVisibility() != View.VISIBLE) {
//            flAction.setVisibility(View.GONE);
//        }
    }

    //TODO not a good solution
    private void performInputSpaceAndDel() {
        new Thread(new Runnable() {
            @Override public void run() {
                try {
                    Thread.sleep(100);
                    Instrumentation instrumentation = new Instrumentation();
                    instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_SPACE);
                    instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_DEL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void uploadOk(HttpWrapper<String> stringHttpWrapper) {
        if(Constant.SUCCESS_CODE == stringHttpWrapper.getCode()){
            String url = Constant.IMG_BASE + stringHttpWrapper.getData();
            Logger.i(url);
            mRichEditorAction.insertImageUrl(url);
        }
    }

    public class MOnActionPerformListener implements OnActionPerformListener {
        private RichEditorAction mRichEditorAction;

        public MOnActionPerformListener(RichEditorAction mRichEditorAction) {
            this.mRichEditorAction = mRichEditorAction;
        }

        @Override public void onActionPerform(ActionType type, Object... values) {
            if (mRichEditorAction == null) {
                return;
            }

            String value = null;
            if (values != null && values.length > 0) {
                value = (String) values[0];
            }

            switch (type) {
                case SIZE:
                    mRichEditorAction.fontSize(Double.valueOf(value));
                    break;
                case LINE_HEIGHT:
                    mRichEditorAction.lineHeight(Double.valueOf(value));
                    break;
                case FORE_COLOR:
                    mRichEditorAction.foreColor(value);
                    break;
                case BACK_COLOR:
                    mRichEditorAction.backColor(value);
                    break;
                case FAMILY:
                    mRichEditorAction.fontName(value);
                    break;
                case IMAGE:
                    onClickInsertImage();
                    break;
                case LINK:
                    break;
                case TABLE:
                    break;
                case BOLD:
                case ITALIC:
                case UNDERLINE:
                case SUBSCRIPT:
                case SUPERSCRIPT:
                case STRIKETHROUGH:
                case JUSTIFY_LEFT:
                case JUSTIFY_CENTER:
                case JUSTIFY_RIGHT:
                case JUSTIFY_FULL:
                case CODE_VIEW:
                case ORDERED:
                case UNORDERED:
                case INDENT:
                case OUTDENT:
                case BLOCK_QUOTE:
                case BLOCK_CODE:
                case NORMAL:
                case H1:
                case H2:
                case H3:
                case H4:
                case H5:
                case H6:
                case LINE:
                    ActionImageView actionImageView =
                            (ActionImageView) llActionBarContainer.findViewWithTag(type);
                    if (actionImageView != null) {
                        actionImageView.performClick();
                    }
                    break;
                default:
                    break;
            }
        }
    }
    class MRichEditorCallback extends RichEditorCallback {

        @Override public void notifyFontStyleChange(ActionType type, final String value) {
            ActionImageView actionImageView =
                    (ActionImageView) llActionBarContainer.findViewWithTag(type);
            if (actionImageView != null) {
                actionImageView.notifyFontStyleChange(type, value);
            }

            if (mEditorMenuFragment != null) {
                mEditorMenuFragment.updateActionStates(type, value);
            }
        }
    }
//    @OnClick(R.id.iv_get_html) void onClickGetHtml() {
//        mRichEditorAction.refreshHtml(mRichEditorCallback, onGetHtmlListener);
//    }
//
//    @OnClick(R.id.iv_action_undo) void onClickUndo() {
//        mRichEditorAction.undo();
//    }
//
//    @OnClick(R.id.iv_action_redo) void onClickRedo() {
//        mRichEditorAction.redo();
//    }

    @OnClick(R.id.iv_action_txt_color) void onClickTextColor() {
        mRichEditorAction.foreColor("blue");
    }

    @OnClick(R.id.iv_action_txt_bg_color) void onClickHighlight() {
        mRichEditorAction.backColor("red");
    }

    @OnClick(R.id.iv_action_line_height) void onClickLineHeight() {
        mRichEditorAction.lineHeight(20);
    }

    @OnClick(R.id.iv_action_insert_image) void onClickInsertImage() {
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, REQUEST_CODE_CHOOSE);
    }

    /**
     * ImageLoader for insert Image
     */
    private void initImageLoader() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setShowCamera(true);
        imagePicker.setCrop(false);
        imagePicker.setMultiMode(false);
        imagePicker.setSaveRectangle(true);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        imagePicker.setFocusWidth(800);
        imagePicker.setFocusHeight(800);
        imagePicker.setOutPutX(256);
        imagePicker.setOutPutY(256);
    }
    private class CustomWebChromeClient extends WebChromeClient {
        @Override public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                if (!TextUtils.isEmpty(htmlContent)) {
                    mRichEditorAction.insertHtml(htmlContent);
                }
                KeyboardUtils.showSoftInput(PostActivity.this);
            }
        }

        @Override public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    }

    @Override public void onResume() {
        super.onResume();
        keyboardHeightProvider.setKeyboardHeightObserver(this);
    }

    @Override public void onPause() {
        super.onPause();
        keyboardHeightProvider.setKeyboardHeightObserver(null);
        if (flAction.getVisibility() == View.INVISIBLE) {
            flAction.setVisibility(View.GONE);
        }
    }

    @Override public void onDestroy() {
        super.onDestroy();
        keyboardHeightProvider.close();
    }

    private static String encodeFileToBase64Binary(String filePath) {
        byte[] bytes = FileIOUtil.readFile2BytesByStream(filePath);
        byte[] encoded = Base64.encode(bytes, Base64.NO_WRAP);
        return new String(encoded);
    }

    @OnClick(R.id.iv_action_insert_link) void onClickInsertLink() {
        KeyboardUtils.hideSoftInput(PostActivity.this);
        EditHyperlinkFragment fragment = new EditHyperlinkFragment();
        fragment.setOnHyperlinkListener(new EditHyperlinkFragment.OnHyperlinkListener() {
            @Override public void onHyperlinkOK(String address, String text) {
                mRichEditorAction.createLink(text, address);
            }
        });
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, fragment, EditHyperlinkFragment.class.getName())
                .commit();
    }

    @OnClick(R.id.iv_action_table) void onClickInsertTable() {
        KeyboardUtils.hideSoftInput(PostActivity.this);
        EditTableFragment fragment = new EditTableFragment();
        fragment.setOnTableListener(new EditTableFragment.OnTableListener() {
            @Override public void onTableOK(int rows, int cols) {
                mRichEditorAction.insertTable(rows, cols);
            }
        });
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, fragment, EditHyperlinkFragment.class.getName())
                .commit();
    }
    @OnClick(R.id.iv_action) void onClickAction() {
        if (flAction.getVisibility() == View.VISIBLE) {
            flAction.setVisibility(View.GONE);
        } else {
            if (isKeyboardShowing) {
                KeyboardUtils.hideSoftInput(PostActivity.this);
            }
            flAction.setVisibility(View.VISIBLE);
        }
    }



    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS
                && data != null
                && requestCode == REQUEST_CODE_CHOOSE) {
            ArrayList<ImageItem> images =
                    (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images != null && !images.isEmpty()) {
                /**
                 * 上传图片
                 */
                ImageItem imageItem = images.get(0);

                mPresenter.upload(uuid,imageItem.path);
                //1.Insert the Base64 String (Base64.NO_WRAP)

//                mRichEditorAction.insertImageData(imageItem.name,
//                        encodeFileToBase64Binary(imageItem.path));

                //2.Insert the ImageUrl
                //mRichEditorAction.insertImageUrl(
                //    "https://avatars0.githubusercontent.com/u/5581118?v=4&u=b7ea903e397678b3675e2a15b0b6d0944f6f129e&s=400");
            }
        }
    }


}
