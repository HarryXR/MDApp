/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.mdapp.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.harry.mdapp.R;
import com.harry.mdapp.common.AppRefreshRecyclerHelper;

import cn.ieclipse.af.adapter.AfRecyclerAdapter;
import cn.ieclipse.af.view.refresh.RefreshLayout;
import cn.ieclipse.af.view.refresh.RefreshRecyclerHelper;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/12/27.
 */
public abstract class BaseListActivity<T> extends BaseActivity implements RefreshLayout.OnRefreshListener{
    protected RefreshLayout mRefreshLayout;
    protected RefreshRecyclerHelper mRefreshHelper;
    protected RecyclerView mListView;
    protected AfRecyclerAdapter<T> mAdapter;

    @Override
    protected int getContentLayout() {
        return R.layout.base_refresh_recycler;
    }

    @Override
    protected void initContentView(View view) {
        super.initContentView(view);

        mRefreshLayout = (RefreshLayout) view.findViewById(R.id.refresh);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setMode(RefreshLayout.REFRESH_MODE_BOTH);
        mListView = (RecyclerView) mRefreshLayout.findViewById(R.id.rv);

        mRefreshHelper = generateRefreshHelper();
        mAdapter = generateAdapter();
        mRefreshHelper.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        load(false);
    }

    @Override
    public void onLoadMore() {
        load(false);
    }

    protected void load(boolean needCache) {
    }

    protected RefreshRecyclerHelper generateRefreshHelper() {
        AppRefreshRecyclerHelper helper = new AppRefreshRecyclerHelper(mRefreshLayout);
        return helper;
    }

    protected abstract AfRecyclerAdapter<T> generateAdapter();
}
