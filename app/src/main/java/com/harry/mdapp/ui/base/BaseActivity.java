/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.mdapp.ui.base;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.harry.mdapp.R;
import com.harry.mdapp.common.CompatActivity;
import com.harry.rv.util.ErrorUtils;

import cn.ieclipse.af.util.AppUtils;
import cn.ieclipse.af.util.DialogUtils;
import cn.ieclipse.af.util.KeyboardUtils;

/**
 * 类/接口描述
 *
 * @author Harry
 */
public abstract class BaseActivity extends CompatActivity implements View.OnClickListener {

    @Override
    public void onClick(View v) {
       
    }

    @Override
    protected void initWindowFeature() {
        super.initWindowFeature();
    }

    protected void initHeaderView() {
        super.initHeaderView();
        int padding = AppUtils.dp2px(this, 8);
        mToolBar.setPadding(padding, 0, padding, 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (!isOverlay()) {
            mToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mToolBar.setTitleTextColor(getResources().getColor(R.color.white));

        }

    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    
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
        super.setTitle(title);
        mToolBar.setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
       mToolBar.setTitle(titleId);
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

    public void toastError(com.harry.rv.common.RestError error) {
        hideLoadingDialog();
        ErrorUtils.toastError(this, error);
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
