package com.edianjucai.eshop.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.edianjucai.eshop.R;
import com.edianjucai.eshop.app.App;
import com.edianjucai.eshop.base.BaseActivity;
import com.edianjucai.eshop.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by user on 2016-09-26.
 */
public class WebViewActivity extends BaseActivity {
    /**
     * webview 要加载的链接
     */
    public static final String EXTRA_URL = "extra_url";
    /**
     * webview 界面标题
     */
    public static final String EXTRA_TITLE = "extra_title";
    /**
     * 要显示的文章的ID
     */
    public static final String EXTRA_ARTICLE_ID = "extra_article_id";
    /**
     * 要显示的HTML内容
     */
    public static final String EXTRA_HTML_CONTENT = "extra_html_content";
    @BindView(R.id.act_project_detail_webview_pgb_progress)
    ProgressBar mActProjectDetailWebviewPgbProgress;
    @BindView(R.id.act_project_detail_webview_web)
    WebView mActProjectDetailWebviewWeb;
    @BindView(R.id.iv_back)
    LinearLayout mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    private String mStrUrl = null;
    private String mStrTitle = null;
    private String mStrArticleId = null;
    private String mStrHtmlContent = null;


    private void init() {
        App.getApplication().getmRuntimeConfig().setProjectDetailWebviewActivity(this);
        initIntentData();
        initTitle();
        initWebView();
        startLoadData();
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.act_project_detail_webview;
    }

    @Override
    public void doBusiness(Context mContext) {
        init();
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return null;
    }

    private void startLoadData() {
        if (mStrHtmlContent != null) {
            loadHtmlContent(mStrHtmlContent);
            return;
        }

        if (mStrArticleId != null) {
            return;
        }

        if (mStrUrl != null) {
            loadUrl(mStrUrl);
            return;
        }

    }

    private void initIntentData() {
        mStrUrl = getIntent().getStringExtra(EXTRA_URL);
        mStrTitle = getIntent().getStringExtra(EXTRA_TITLE);
        mStrArticleId = getIntent().getStringExtra(EXTRA_ARTICLE_ID);
        mStrHtmlContent = getIntent().getStringExtra(EXTRA_HTML_CONTENT);
    }

    private void initWebView() {
        WebSettings settings = mActProjectDetailWebviewWeb.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        //        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // settings.setUseWideViewPort(true);
        //         settings.setLoadWithOverviewMode(true);
        mActProjectDetailWebviewWeb.setWebViewClient(new ProjectDetailWebviewActivity_WebViewClient());
        mActProjectDetailWebviewWeb.setWebChromeClient(new ProjectDetailWebviewActivity_WebChromeClient());
    }

    private void loadHtmlContent(String htmlContent) {
        if (htmlContent != null) {
            mActProjectDetailWebviewWeb.loadDataWithBaseURL("about:blank", htmlContent, "text/html", "utf-8", null);
        }
    }

    private void loadUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            mActProjectDetailWebviewWeb.loadUrl(mStrUrl);
        }
    }


    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }


    class ProjectDetailWebviewActivity_WebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            super.onPageFinished(view, url);
        }
    }

    class ProjectDetailWebviewActivity_WebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mActProjectDetailWebviewPgbProgress.setVisibility(View.GONE);
            } else {
                if (mActProjectDetailWebviewPgbProgress.getVisibility() == View.GONE) {
                    mActProjectDetailWebviewPgbProgress.setVisibility(View.VISIBLE);
                }
                mActProjectDetailWebviewPgbProgress.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mActProjectDetailWebviewWeb.canGoBack()) {
            mActProjectDetailWebviewWeb.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initTitle() {
        if (mStrTitle != null) {
            mTvTitle.setText(mStrTitle);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // App.getApplication().getmRuntimeConfig().setProjectDetailWebviewActivity(null);
    }
}
