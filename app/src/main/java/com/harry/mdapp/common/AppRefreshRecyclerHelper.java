/*
 * Copyright (C) 2015-2016 QuickAF
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.harry.mdapp.common;

import com.harry.mdapp.R;

import cn.ieclipse.af.util.AppUtils;
import cn.ieclipse.af.view.refresh.EmptyView;
import cn.ieclipse.af.view.refresh.RefreshLayout;
import cn.ieclipse.af.view.refresh.RefreshRecyclerHelper;
import cn.ieclipse.af.volley.RestError;

/**
 * Description
 *
 * @author Jamling
 */
public class AppRefreshRecyclerHelper extends RefreshRecyclerHelper {

    public AppRefreshRecyclerHelper(final RefreshLayout refreshLayout) {
        super(refreshLayout);
        setDividerHeight(AppUtils.dp2px(getContext(), (float) 0.5));
        setDividerColor(AppUtils.getColor(getContext(), R.color.black_f0f0f0));
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        if (refreshLayout.getEmptyView() != null) {
            refreshLayout.getEmptyView().setRetryListener(new EmptyView.RetryListener() {
                @Override
                public void onErrorClick() {
                    refreshLayout.onRefresh();
                }

                @Override
                public void onDataEmptyClick() {
                    refreshLayout.onRefresh();
                }
            });
        }
    }

    @Override
    protected boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public void onLoadFailure(RestError error) {
        if (refreshLayout.getEmptyView() != null) {
            refreshLayout.getEmptyView().setDesc(EmptyView.LAYER_ERROR, VolleyUtils.getError(getContext(), error));
        }
        super.onLoadFailure(error);
    }
}
