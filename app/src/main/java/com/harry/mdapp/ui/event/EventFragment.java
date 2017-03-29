package com.harry.mdapp.ui.event;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.harry.mdapp.R;
import com.harry.mdapp.ui.base.BaseListFragment;
import com.harry.mdapp.view.EventListItem;
import com.harry.rv.common.RestError;
import com.harry.rv.controller.EventListController;
import com.harry.rv.model.BaseResponse;
import com.harry.rv.model.EventRequest;
import com.harry.rv.model.EventResponse;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ieclipse.af.adapter.AfRecyclerAdapter;
import cn.ieclipse.af.adapter.AfViewHolder;

/**
 * 同城 活动
 *
 * @author Harry
 * @date 2017/3/13.
 */

public class EventFragment extends BaseListFragment<EventResponse> implements AfRecyclerAdapter.OnItemClickListener,
    EventListController.LoadListener {
    
    @BindView(R.id.ts)
    TextSwitcher mTs;
    public EventListController mController;
    StickyRecyclerHeadersDecoration mHeadDecoration;
    
    private String[] mStrs = {"今天即将有新活动了", "马上就要放假啦", "天气有点冷啊"};
    private int index = 0;
    
    MyHandler handler = new MyHandler();
    
    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mTs.setText(mStrs[index]);
            index++;
            if (index == mStrs.length) {
                index = 0;
            }
        }
    }
    
    @Override
    protected AfRecyclerAdapter generateAdapter() {
        MyAdapter adapter = new MyAdapter(getActivity().getApplicationContext());
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                mHeadDecoration.invalidateHeaders();
            }
        });
        mHeadDecoration = new StickyRecyclerHeadersDecoration(adapter);
        mListView.addItemDecoration(mHeadDecoration);
        adapter.setOnItemClickListener(this);
        return adapter;
    }
    
    @Override
    protected int getContentLayout() {
        return R.layout.fragment_event;
    }
    
    @Override
    protected void initContentView(View view) {
        super.initContentView(view);
        ButterKnife.bind(this, view);
        mRefreshHelper.setDividerColor(getResources().getColor(R.color.white));
        mController = new EventListController(getActivity().getApplicationContext(), this);
        addSwitchView();
    }
    
    private void addSwitchView() {
        mTs.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(EventFragment.this.getActivity().getApplicationContext());
                textView.setSingleLine();
                textView.setTextSize(15);
                textView.setTextColor(getResources().getColor(R.color.black_999999));
                textView.setEllipsize(TextUtils.TruncateAt.END);
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.gravity = Gravity.CENTER;
                textView.setLayoutParams(lp);
                return textView;
            }
        });
        new MyThread().start();
    }
    
    @Override
    protected void initData() {
        super.initData();
        load(false);
    }
    
    @Override
    protected void load(boolean needCache) {
        EventRequest request = new EventRequest();
        request.loc = "108289";
        request.dayType = "week";
        request.type = "all";
        mController.load(request);
    }
    
    @Override
    public void onItemClick(AfRecyclerAdapter afRecyclerAdapter, View view, int i) {
        EventDetailActivity.go(getActivity(), (EventResponse) afRecyclerAdapter.getItem(i));
    }
    
    @Override
    public void onSuccess(BaseResponse<List<EventResponse>> out) {
        List<EventResponse> res = out.events;
        if (res != null && res.size() > 0) {
            for (EventResponse response : res) {
                if (response.category != null) {
                    String a = response.category;
                    if (a.length() > 1) {
                        a = a.substring(0, 1);
                        response.setInitial(a);
                    }
                }
                else {
                    response.setInitial(" ");
                }
            }
            Collections.sort(res);
            mAdapter.clear();
            mAdapter.setDataList(res);
            mAdapter.notifyDataSetChanged();
            mRefreshLayout.onRefreshComplete();
//            mRefreshHelper.onLoadFinish(res);
        }
    }
    
    @Override
    public void onError(RestError e) {
        mRefreshHelper.onLoadFailure(null);
        toastError(e);
    }
    
    public class MyAdapter extends AfRecyclerAdapter<EventResponse> implements
        StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
        public MyAdapter(Context context) {
            super(context);
        }
        
        @Override
        public int getLayout() {
            return R.layout.list_item_event;
        }
        
        @Override
        public void onUpdateView(RecyclerView.ViewHolder holder, EventResponse data, int position) {
            EventListItem listItem = (EventListItem) holder.itemView;
            listItem.setData(data);
        }
        
        // 实现以下方法,支持浮动头部
        @Override
        public long getHeaderId(int position) {
            return Math.abs(getItem(position).category.hashCode());
        }
        
        @Override
        public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_head, parent, false);
            
            return new AfViewHolder(view);
        }
        
        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextView textView = (TextView) holder.itemView.findViewById(android.R.id.text1);
            String showValue = String.valueOf(getItem(position).subcategory_name==null?"":getItem(position).subcategory_name);
            textView.setText(showValue);
        }
    }
    
    private class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (index < mStrs.length) {
                try {
                    synchronized (this) {
                        handler.sendEmptyMessage(0);
                        this.sleep(2000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    @Override
    public void onDestroy() {
        
        super.onDestroy();
    }
}
