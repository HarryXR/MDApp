package com.harry.mdapp.ui.event;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.cooltechworks.views.ScratchImageView;
import com.harry.mdapp.R;
import com.harry.mdapp.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ieclipse.af.util.DialogUtils;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/4/10.
 */

public class ScratchActivity extends BaseActivity {
    
    @BindView(R.id.iv_scratch)
    ScratchImageView mScratchView;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_scratch;
    }
    
    @Override
    protected void initContentView(View view) {
        super.initContentView(view);
        ButterKnife.bind(this,view);
        setTitle("刮刮乐");
    }
    
    @Override
    protected void initData() {
        super.initData();
        mScratchView.setRevealListener(new ScratchImageView.IRevealListener() {
            @Override
            public void onRevealed(ScratchImageView scratchImageView) {
                DialogUtils.showToast(ScratchActivity.this,"哇，中奖啦，南京河西一套房");
            }
        
            @Override
            public void onRevealPercentChangedListener(ScratchImageView scratchImageView, float v) {
                Log.e(getClass().getSimpleName(),v+"");
            }
        });
    }
    
    public static void go(Context context) {
        Intent intent = new Intent(context, ScratchActivity.class);
        context.startActivity(intent);
    }
}
