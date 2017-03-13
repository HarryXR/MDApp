package com.harry.mdapp.ui.event;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import java.util.List;

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
    
    public EventListController mController;
    StickyRecyclerHeadersDecoration mHeadDecoration;
    
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
        return adapter;
    }
    
    @Override
    protected int getContentLayout() {
        return super.getContentLayout();
    }
    
    @Override
    protected void initContentView(View view) {
        super.initContentView(view);
        mRefreshHelper.setDividerColor(getResources().getColor(R.color.white));
        mRefreshHelper.setPageSize(20);
        mController = new EventListController(getActivity().getApplicationContext(), this);
    }
    
    @Override
    protected void initData() {
        super.initData();
        load(false);
    }
    
    @Override
    protected void load(boolean needCache) {
        EventRequest request = new EventRequest();
        request.loc = "108288";
        request.dayType = "week";
        request.type = "all";
        mController.load(request);
    }
    
    @Override
    public void onItemClick(AfRecyclerAdapter afRecyclerAdapter, View view, int i) {
        
    }
    
    @Override
    public void onSuccess(BaseResponse<List<EventResponse>> out) {
        mRefreshHelper.onLoadFinish(out.events);
    }
    
    @Override
    public void onError(RestError e) {
        mRefreshLayout.onRefreshComplete();
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
            //view.setBackgroundResource(R.color.black_cccccc);
            return new AfViewHolder(view);
        }
        
        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextView textView = (TextView) holder.itemView.findViewById(android.R.id.text1);
            String showValue = String.valueOf(getItem(position).subcategory_name);
            textView.setText(showValue);
        }
        
        public int getPositionForSection(char section) {
            for (int i = 0; i < getItemCount(); i++) {
                String sortStr = getDataList().get(i).category;
                char firstChar = sortStr.toUpperCase().charAt(0);
                if (firstChar == section) {
                    return i;
                }
            }
            return -1;
        }
    }
    
    @Override
    public void onDestroy() {
        
        super.onDestroy();
    }
}
