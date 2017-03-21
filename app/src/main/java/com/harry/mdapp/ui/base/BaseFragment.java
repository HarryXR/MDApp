/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.mdapp.ui.base;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.harry.mdapp.R;
import com.harry.mdapp.common.CompatFragment;
import com.harry.rv.common.RestError;
import com.harry.rv.util.ErrorUtils;

import cn.ieclipse.af.util.AppUtils;
import cn.ieclipse.af.util.DialogUtils;
import cn.ieclipse.af.util.KeyboardUtils;

/**
 * 类/接口描述
 *
 * @author harry
 */
public abstract class BaseFragment extends CompatFragment implements View.OnClickListener {

    protected TextView mTitleLeftView;
    protected TextView mTitleTextView;
    protected abstract int getContentLayout();

    @Override
    public void onClick(View v) {

    }

    protected void setOnClickListener(View... views) {
        if (views != null) {
            for (View view : views) {
                if (view != null) {
                    view.setOnClickListener(this);
                }
            }
        }
    }

    @Override
    protected void initIntent(Bundle bundle) {
        super.initIntent(bundle);
    }

    @Override
    protected void initHeaderView() {
        super.initHeaderView();
        mTitleLeftView = (TextView) View.inflate(mTitleBar.getContext(), R.layout.title_left_tv, null);
        mTitleTextView = (TextView) View.inflate(mTitleBar.getContext(), R.layout.title_middle_tv, null);

        mTitleBar.setLeft(mTitleLeftView);
        mTitleBar.setMiddle(mTitleTextView);

        
        int padding = AppUtils.dp2px(mTitleBar.getContext(), 8);
        mTitleBar.setPadding(padding, 0, padding, 0);
        if(!isOverlay())
        {
            mTitleBar.setBackgroundColor(AppUtils.getColor(mTitleBar.getContext(), R.color.colorPrimary));
            mTitleBar.setBottomDrawable(AppUtils.getColor(mTitleBar.getContext(), R.color.divider));
        }
        setOnClickListener(mTitleLeftView);
    }

    @Override
    protected void initContentView(View view) {
        super.initContentView(view);
        KeyboardUtils.autoHideSoftInput(view);
    }

    private DialogFragment mLoadingDialog;

    public void showLoadingDialog() {
        showLoadingDialog(getString(R.string.common_loading));
    }
    public void showLoadingDialog(String message) {
        hideLoadingDialog();
        mLoadingDialog = DialogUtils.showProgress(getActivity(), android.R.style
            .Widget_Holo_Light_ProgressBar_Large, message, null);
    }

    public void hideLoadingDialog() {
        if(mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    public void toastError(RestError error){
        hideLoadingDialog();
        ErrorUtils.toastError(getActivity(), error);
    }

    protected CharSequence getTitle(){
        return getClass().getSimpleName();
    }
}
