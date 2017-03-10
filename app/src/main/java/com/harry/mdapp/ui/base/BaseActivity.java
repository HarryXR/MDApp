/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.mdapp.ui.base;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.harry.mdapp.R;

import cn.ieclipse.af.app.AfActivity;
import cn.ieclipse.af.util.AppUtils;
import cn.ieclipse.af.util.DialogUtils;
import cn.ieclipse.af.util.KeyboardUtils;
import cn.ieclipse.af.volley.RestError;

/**
 * 类/接口描述
 *
 * @author Harry
 */
public abstract class BaseActivity extends AfActivity implements View.OnClickListener {
    protected TextView mTitleLeftView;
    protected TextView mTitleTextView;

    @Override
    public void onClick(View v) {
        if (v == mTitleLeftView) { // default back.
            finish();
        }
    }

    protected void initIntent(Bundle bundle) {

    }

    @Override
    protected void initWindowFeature() {
        super.initWindowFeature();
        setWindowBackground(android.R.color.white);
        setImmersiveMode(true);
    }

    protected void initContentView(View view) {

    }

    protected void initHeaderView() {
//        mTitleLeftView = (TextView) View.inflate(this, R.layout.title_left_tv, null);
//        mTitleTextView = (TextView) View.inflate(this, R.layout.title_middle_tv, null);

//        mTitleBar.setLeft(mTitleLeftView);
//        mTitleBar.setMiddle(mTitleTextView);

        int padding = AppUtils.dp2px(this, 8);
        mTitleBar.setPadding(padding, 0, padding, 0);
        if (!isOverlay()) {
            mTitleBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//            mTitleBar.setBottomDrawable(AppUtils.getColor(this, R.color.divider));
        }
//        setOnClickListener(mTitleLeftView);
    }

    protected void initData() {

    }

    protected void initBottomView() {

    }

//    protected ImageView createRightIcon(int icon) {
//        ImageView iv = (ImageView) View.inflate(this, R.layout.title_right_iv, null);
//        if (icon > 0) {
//            iv.setImageResource(icon);
//        }
//        return iv;
//    }

//    protected TextView createRightText(String text) {
//        TextView tv = (TextView) View.inflate(this, R.layout.title_right_tv, null);
//        tv.setText(text);
//        return tv;
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        KeyboardUtils.autoHideSoftInput(this, ev);
        return super.dispatchTouchEvent(ev);
    }

//    @Override
//    protected int getStatusBarColor() {
//        return AppUtils.getColor(this, R.color.black);
//    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitleTextView.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        mTitleTextView.setText(titleId);
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

    public void toastError(RestError error) {
        hideLoadingDialog();
//        VolleyUtils.toastError(this, error);
    }
    
    protected static void startActivity(Intent intent, Fragment f, Context context, int requestCode) {
        if (f != null) {
            f.startActivityForResult(intent, requestCode);
        }
        else if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, requestCode);
        }
        else {
            context.startActivity(intent);
        }
    }
}
