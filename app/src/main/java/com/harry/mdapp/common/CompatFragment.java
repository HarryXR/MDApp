package com.harry.mdapp.common;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.harry.mdapp.R;
import com.harry.mdapp.view.TitleBar;

import cn.ieclipse.af.util.AppUtils;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/3/17.
 */

public abstract class CompatFragment extends Fragment implements View.OnClickListener{
    private boolean overlay = false;
    private boolean showTitleBar = false;
    private int windowBgColor = 0;
    private RelativeLayout mRootView;
    protected LayoutInflater mLayoutInflater;
    protected TitleBar mTitleBar;
    private FrameLayout mContentView;
    protected FrameLayout mBottomBar;
    private boolean isFirstVisible = true;
    private boolean isUIInitialize = false;
    private boolean isInViewPager = false;
    private boolean trimMode = false;
    
    public CompatFragment() {
    }
    
    protected abstract int getContentLayout();
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(savedInstanceState != null) {
            bundle = savedInstanceState;
        }
        
        if(bundle != null) {
            initIntent(bundle);
        }
        
        initInitData();
        if(isTrimMode()) {
            View view = inflater.inflate(getContentLayout(), container, false);
            initContentView(view);
            initData();
            return view;
        } else {
            initRootView(inflater, container);
            if(isShowTitleBar()) {
                initHeaderView();
            }
            
            initContentView(mContentView);
            initBottomView();
            initData();
            return mRootView;
        }
    }
    
    public void onClick(View v) {
    }
    
    protected void setOnClickListener(View... views) {
        if(views != null) {
            View[] var2 = views;
            int var3 = views.length;
            
            for(int var4 = 0; var4 < var3; ++var4) {
                View view = var2[var4];
                if(view != null) {
                    view.setOnClickListener(this);
                }
            }
        }
        
    }
    
    protected void initIntent(Bundle bundle) {
    }
    
    protected void initInitData() {
    }
    
    protected void initHeaderView() {
    }
    
    protected void initContentView(View view) {
    }
    
    protected void initBottomView() {
    }
    
    protected void initData() {
    }
    
    public boolean isOverlay() {
        return overlay;
    }
    
    public void setOverlay(boolean overlay) {
        if(overlay != overlay && mContentView != null) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)mContentView.getLayoutParams();
            setContentViewLayoutParams(lp, overlay, isShowTitleBar());
        }
        
        overlay = overlay;
    }
    
    public boolean isShowTitleBar() {
        return showTitleBar || mTitleBar != null && mTitleBar.getVisibility() != View.GONE;
    }
    
    public void setShowTitleBar(boolean showTitleBar) {
        if(showTitleBar != showTitleBar) {
            showTitleBar = showTitleBar;
            if(mTitleBar != null) {
                mTitleBar.setVisibility(showTitleBar? View.VISIBLE:View.GONE);
            }
            
            if(mContentView != null) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)mContentView.getLayoutParams();
                setContentViewLayoutParams(lp, isOverlay(), isShowTitleBar());
            }
        }
        
    }
    
    public void setWindowBackground(int colorId) {
        if(colorId > 0) {
            windowBgColor = AppUtils.getColor(getActivity(), colorId);
        }
        
    }
    
    private void initRootView(LayoutInflater inflater, ViewGroup container) {
        Activity context = getActivity();
        mLayoutInflater = inflater;
        mRootView = new RelativeLayout(context);
        mRootView.setFitsSystemWindows(true);
        mRootView.setBackgroundColor(windowBgColor);
        mTitleBar = new TitleBar(context);
        mTitleBar.setId(R.id.titleBar);
        mContentView = new FrameLayout(context);
        mBottomBar = new FrameLayout(context);
        mBottomBar.setId(R.id.bottomBar);
        RelativeLayout.LayoutParams lpTitle = new RelativeLayout.LayoutParams(-1, -2);
        lpTitle.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        lpTitle.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        mTitleBar.setLayoutParams(lpTitle);
        mTitleBar.setVisibility(showTitleBar?View.VISIBLE:View.GONE);
        RelativeLayout.LayoutParams lpBottom = new RelativeLayout.LayoutParams(-1, -2);
        lpBottom.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        lpBottom.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        mBottomBar.setLayoutParams(lpBottom);
        mRootView.addView(mBottomBar);
        RelativeLayout.LayoutParams lpContent = new RelativeLayout.LayoutParams(-1, -2);
        lpContent.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        lpContent.addRule(RelativeLayout.ABOVE, mBottomBar.getId());
        setContentViewLayoutParams(lpContent, isOverlay(), isShowTitleBar());
        mRootView.addView(mContentView);
        mRootView.addView(mTitleBar);
        int rootLayoutId = getContentLayout();
        if(rootLayoutId > 0) {
            mLayoutInflater.inflate(rootLayoutId, mContentView, true);
        }
        
        if(container != null) {
            mRootView.setLayoutParams(container.getLayoutParams());
        } else {
            mRootView.setLayoutParams(new android.view.ViewGroup.LayoutParams(-1, -1));
        }
        
    }
    
    private void setContentViewLayoutParams(RelativeLayout.LayoutParams lp, boolean overlay, boolean showTitleBar) {
        if(lp != null) {
            if(!overlay && showTitleBar) {
                lp.getRules()[RelativeLayout.BELOW] = 0;
                lp.addRule(RelativeLayout.ALIGN_PARENT_TOP, mTitleBar.getId());
            } else {
                lp.getRules()[RelativeLayout.ALIGN_PARENT_TOP] = 0;
                lp.addRule(RelativeLayout.BELOW, mTitleBar.getId());
            }
            
            mContentView.setLayoutParams(lp);
        }
        
    }
    
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }
    
    private synchronized void init() {
        if(isInViewPager && !isUIInitialize) {
            isUIInitialize = true;
        } else {
            onFirstUserVisible();
        }
        
    }
    
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isInViewPager = true;
        if(isVisibleToUser && isFirstVisible) {
            isFirstVisible = false;
            init();
        }
        
        if(isUIInitialize) {
            onUserVisible(isVisibleToUser);
        }
        
    }
    
    protected void onFirstUserVisible() {
    }
    
    protected void onUserVisible(boolean visible) {
    }
    
    public void setTrimMode(boolean trimMode) {
        trimMode = trimMode;
    }
    
    public boolean isTrimMode() {
        return trimMode;
    }
    
    public FragmentManager getSubFragmentManager() {
        return Build.VERSION.SDK_INT >= 17 ? getChildFragmentManager() : getFragmentManager();
    }
}
