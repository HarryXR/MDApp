package com.harry.mdapp.ui.event;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.harry.mdapp.R;
import com.harry.mdapp.ui.base.BaseActivity;
import com.harry.rv.model.EventResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/3/16.
 */

public class EventDetailActivity extends BaseActivity {
    public static final String EXTRA_DATA = "extra.data";
    
    private EventResponse mData;
    
//    @BindView(R.id.tv_title)
//    TextView mTitle;
//    @BindView(R.id.tv_address)
//    TextView mAddress;
    @BindView(R.id.iv)
    SimpleDraweeView iv;
    @BindView(R.id.tv_content)
    TextView mContent;
    @BindView(R.id.head_bar_layout)
    AppBarLayout mHeadBarLayout;
    @BindView(R.id.collaps_layout)
    CollapsingToolbarLayout mCollLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_event_detail;
    }
    
    @Override
    protected void initContentView(View view) {
        super.initContentView(view);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
//        mToolBar.setVisibility(View.GONE);
//        setSupportActionBar(toolbar);
        mHeadBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -mHeadBarLayout.getHeight() / 2) {
//                   setTitle(mData.title);
                } else {
//                    setTitle("活动详情");
                }
            }
        });
        mCollLayout.setTitle(mData.title);
        mCollLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        mCollLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        if(mData != null){
//            mTitle.setText(mData.title);
//            mAddress.setText(mData.address);
            iv.setImageURI(Uri.parse(mData.image_hlarge));
            mContent.setText(Html.fromHtml(mData.content));
        }
        mToolBar.getMenu().add("相亲");
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event_detail,menu);
        return true;
    }
    
    @Override
    protected void initWindowFeature() {
        super.initWindowFeature();
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
        
//        }
        setImmersiveMode(true);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
    
//    @Override
//    protected int getStatusBarColor() {
//        return getResources().getColor(R.color.colorPrimaryDark);
//    }
    
    @Override
    protected void initIntent(Bundle bundle) {
        super.initIntent(bundle);
        mData = (EventResponse) bundle.getSerializable(EXTRA_DATA);
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(EXTRA_DATA, mData);
        super.onSaveInstanceState(outState);
    }
    
    public static void go(Context context, EventResponse data) {
        Intent intent = new Intent(context, EventDetailActivity.class);
        Bundle b = new Bundle();
        b.putSerializable(EXTRA_DATA, data);
        intent.putExtras(b);
        ((Activity)context).startActivity(intent);
    }
}
