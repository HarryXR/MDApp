package com.harry.mdapp.common;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.harry.mdapp.R;

import cn.ieclipse.af.util.DialogUtils;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/3/10.
 */

public abstract class CompatActivity extends AppCompatActivity {
    protected Toolbar mToolBar;
    private boolean overlay = false;
    private boolean showTitleBar = true;
    private int windowBgColor = 0;
    private RelativeLayout mRootView;
    private LayoutInflater mLayoutInflater;
    private FrameLayout mContentView;
    protected FrameLayout mBottomBar;
    private SystemBarTintManager mTintManager;
    private boolean skipCreate = false;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Bundle bundle = this.getIntent().getExtras();
        if (savedInstanceState != null) {
            bundle = savedInstanceState;
        }
        
        if (bundle != null) {
            initIntent(bundle);
        }
        initInitData();
        initWindowFeature();
        if (!skipCreate) {
            initRootView();
            if (isShowTitleBar()) {
                initHeaderView();
            }
            
            initContentView(mContentView);
            initContentView();
            initBottomView();
            initData();
            if (Build.VERSION.SDK_INT >= 19) {
                initStatusBar();
            }
        }
    }
    
    protected void initContentView() {
    }
    
    protected void initData() {
    }
    
    protected void initBottomView() {
    }
    
    protected void initContentView(View view) {
        
    }
    
    
    protected void initHeaderView() {
    }
    
    protected boolean isShowTitleBar() {
        return this.showTitleBar || mToolBar != null && mToolBar.getVisibility() != View.GONE;
    }
    
    protected void initRootView() {
        mLayoutInflater = LayoutInflater.from(this);
        mRootView = new RelativeLayout(this);
        mRootView.setFitsSystemWindows(true);
        mRootView.setBackgroundColor(windowBgColor);
        mToolBar = new Toolbar(this);
        mToolBar.setId(R.id.titleBar);
        setSupportActionBar(mToolBar);
        mContentView = new FrameLayout(this);
        mBottomBar = new FrameLayout(this);
        mBottomBar.setId(R.id.bottomBar);
        
        RelativeLayout.LayoutParams lpTitle = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT);
        lpTitle.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        lpTitle.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        mToolBar.setLayoutParams(lpTitle);
        // init set title bar visibility, can't use isShowTitleBar()
        mToolBar.setVisibility(showTitleBar ? View.VISIBLE : View.GONE);
        
        RelativeLayout.LayoutParams lpBottom = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT);
        lpBottom.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        lpBottom.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        mBottomBar.setLayoutParams(lpBottom);
        mRootView.addView(mBottomBar);
        
        RelativeLayout.LayoutParams lpContent = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lpContent.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        lpContent.addRule(RelativeLayout.ABOVE, mBottomBar.getId());
        
        setContentViewLayoutParams(lpContent, isOverlay(), isShowTitleBar());
        
        mRootView.addView(mContentView);
        mRootView.addView(mToolBar);
        
        int rootLayoutId = getContentLayout();
        if (rootLayoutId > 0)
        
        {
            mLayoutInflater.inflate(rootLayoutId, mContentView, true);
        }
        
        setContentView(mRootView,
            new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }
    
    protected abstract int getContentLayout();
    
    public boolean isOverlay() {
        return overlay;
    }
    
    private void setContentViewLayoutParams(RelativeLayout.LayoutParams lp, boolean overlay, boolean showTitleBar) {
        if (lp != null) {
            if (overlay || !showTitleBar) {
                lp.getRules()[RelativeLayout.BELOW] = 0;
                lp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            }
            else {
                lp.getRules()[RelativeLayout.ALIGN_PARENT_TOP] = 0;
                lp.addRule(RelativeLayout.BELOW, mToolBar.getId());
            }
            mContentView.setLayoutParams(lp);
        }
    }
    
    protected void initWindowFeature() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    
    public void setImmersiveMode(boolean immersiveMode) {
        if (mRootView != null) {
            throw new IllegalStateException("Can't set immersive mode after the content view has been set, if you need immersive mode, please call it in initWindowFeature()");
        }
        setTranslucentStatus(immersiveMode);
    }
    
    @TargetApi(19)
    private void setTranslucentStatus(boolean immersiveMode) {
        WindowManager.LayoutParams winParams = getWindow().getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        boolean oldOn = (winParams.flags | bits) == winParams.flags;
        if (immersiveMode == oldOn) {
            return;
        }
        if (immersiveMode) {
            winParams.flags |= bits;
        }
        else {
            winParams.flags &= ~bits;
        }
        getWindow().setAttributes(winParams);
        if (mRootView != null) {
//            mRootView.setFitsSystemWindows(immersiveMode);
        }
    }
    
    protected void initInitData() {
    }
    
    protected void initStatusBar() {
        mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setStatusBarTintColor(getStatusBarColor());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(getStatusBarColor());
//        }else{
//
//        }
    }
    
    protected int getStatusBarColor(){
        return getResources().getColor(R.color.colorPrimaryDark);
    }
    
    
    
    protected void initIntent(Bundle bundle) {
        
    }
    
    private DialogFragment mLoadingDialog;
    
    public void showLoadingDialog() {
        showLoadingDialog(getString(R.string.common_loading));
    }
    
    public void showLoadingDialog(String message) {
        hideLoadingDialog();
        mLoadingDialog = DialogUtils.showProgress(this, android.R.style.Widget_Holo_Light_ProgressBar_Large, message,
            null);
    }
    
    public void hideLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }
}
