package com.harry.mdapp.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.harry.mdapp.R;
import com.harry.rv.model.EventResponse;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/3/13.
 */

public class EventListItem extends LinearLayout {
    public EventListItem(Context context) {
        super(context);
    }
    
    public EventListItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    
    public EventListItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EventListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    
    TextView title;
    SimpleDraweeView iv;
    TextView time;
    
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        
        title = (TextView) findViewById(R.id.tv_title);
        iv = (SimpleDraweeView) findViewById(R.id.iv_image);
        time = (TextView) findViewById(R.id.tv_time);
    }
    
    public void setData(EventResponse data){
        title.setText(data.title);
        iv.setImageURI(Uri.parse(data.image_hlarge));
        time.setText(data.time_str);
    }
}
