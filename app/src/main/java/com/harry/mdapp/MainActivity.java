package com.harry.mdapp;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.harry.mdapp.common.MainBottomTab;
import com.harry.mdapp.ui.base.BaseFragmentAdapter;
import com.harry.mdapp.ui.event.EventFragment;
import com.harry.mdapp.ui.movie.MovieFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ieclipse.af.view.ViewPagerV4;
import kr.co.namee.permissiongen.PermissionGen;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.view_pager)
    ViewPagerV4 mViewPager;
    @BindView(R.id.main_bottom_tab)
    MainBottomTab mBottomTab;
    private BaseFragmentAdapter mAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        mAdapter = new BaseFragmentAdapter(getFragmentManager());
        mAdapter.setFragments(new MovieFragment(),new EventFragment());
        mViewPager.setDisableWipe(true);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mBottomTab.setViewPager(mViewPager);
        PermissionGen.with(this).permissions(Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE).request();
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}
