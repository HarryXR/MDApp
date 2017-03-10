package com.harry.mdapp.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.harry.mdapp.R;
import com.harry.mdapp.ui.base.BaseActivity;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016-10-31
 */
public class H5Activity extends BaseActivity {
    
    public static final String EXTRA_URL = "url";
    public static final String EXTRA_TITLE = "title";
    
    private WebView mWebView;
    
    private String mUrl = "";
    private String mTitle;
    
    @Override
    protected int getContentLayout() {
        return R.layout.activity_h5;
    }
    
    @Override
    protected void initHeaderView() {
        super.initHeaderView();
    }
    
    @Override
    protected void initContentView(View view) {
        super.initContentView(view);
        mWebView = (WebView) view.findViewById(R.id.wv_h5);
    }
    
    @Override
    protected void initData() {
        super.initData();
        // 能使用JavaScript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        
        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWebView.setWebViewClient(new H5WebViewClient());
        
        // 设置setWebChromeClient对象
        mWebView.setWebChromeClient(new H5WebChromeClient());
        
        // 优先使用缓存
        // 不是用缓存（webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);）
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        
        // WebView加载web资源
        mWebView.loadUrl(mUrl);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();//回退
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    /**
     * @param context {@link android.content.Context}
     * @param url     请求URL（以http://开头的完整URL）
     * @param title   默认初始title
     */
    public static void forward(Context context, String url, String title) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_URL, url);
        bundle.putString(EXTRA_TITLE, title);
        intent.putExtras(bundle);
        intent.setClass(context, H5Activity.class);
        context.startActivity(intent);
    }
    
    @Override
    protected void initIntent(Bundle bundle) {
        super.initIntent(bundle);
        mUrl = bundle.getString(EXTRA_URL);
        mTitle = bundle.getString(EXTRA_TITLE);
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(EXTRA_URL, mUrl);
        outState.putString(EXTRA_TITLE, mTitle);
        super.onSaveInstanceState(outState);
    }
    
    protected void onUpdateTitle(WebView webView, String title) {
        setTitle(title);
    }
    
    protected boolean shouldOverrideUrlLoading(WebView webView, String url) {
        webView.loadUrl(url);
        return false;
    }
    
    private class H5WebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView webView, String s) {
            super.onReceivedTitle(webView, s);
            onUpdateTitle(webView, s);
        }
    }
    
    private class H5WebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            return H5Activity.this.shouldOverrideUrlLoading(webView, s);
        }
    }
}
