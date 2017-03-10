package com.harry.mdapp.ui.movie;

import android.support.design.widget.TabLayout;
import android.view.View;

import com.harry.mdapp.R;
import com.harry.mdapp.ui.base.BaseFragment;
import com.harry.mdapp.ui.base.BaseFragmentAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ieclipse.af.view.ViewPagerV4;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/3/9.
 */

public class MovieFragment extends BaseFragment {
    
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.vp)
    ViewPagerV4 mVp;
    
    private BaseFragmentAdapter mAdapter;
    
    @Override
    protected int getContentLayout() {
        return R.layout.fragment_movie;
    }
    
    @Override
    protected void initContentView(View view) {
        super.initContentView(view);
        ButterKnife.bind(this, view);
        
        mAdapter = new BaseFragmentAdapter(getFragmentManager());
        mAdapter.setFragments(new MovieTopFragment(), new MovieComingFragment(), new MovieHotFragment());
        mVp.setDisableWipe(true);
        mVp.setAdapter(mAdapter);
        mVp.setOffscreenPageLimit(4);
        mTab.setupWithViewPager(mVp);
    }
}
