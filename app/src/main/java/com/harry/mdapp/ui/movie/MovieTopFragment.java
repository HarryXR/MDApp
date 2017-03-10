package com.harry.mdapp.ui.movie;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.harry.mdapp.R;
import com.harry.mdapp.common.H5Activity;
import com.harry.mdapp.ui.base.BaseListFragment;
import com.harry.rv.controller.MovieController;
import com.harry.rv.model.MovieResponse;
import com.harry.rv.retrofit.MovieRequest;

import java.util.List;

import cn.ieclipse.af.adapter.AfRecyclerAdapter;
import cn.ieclipse.af.adapter.AfViewHolder;
import cn.ieclipse.af.adapter.delegate.AdapterDelegate;
import cn.ieclipse.af.util.AppUtils;
import cn.ieclipse.af.view.FlowLayout;
import cn.ieclipse.af.view.RoundButton;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/3/9.
 */

public class MovieTopFragment extends BaseListFragment<MovieResponse> implements MovieController.LoadListener,
    AfRecyclerAdapter.OnItemClickListener {
    
    private MovieController mController;
    
    @Override
    protected CharSequence getTitle() {
        return "Top250";
    }
    
    @Override
    protected AfRecyclerAdapter<MovieResponse> generateAdapter() {
        return new AfRecyclerAdapter<>();
    }
    
    @Override
    protected int getContentLayout() {
        return super.getContentLayout();
    }
    
    @Override
    protected void initContentView(View view) {
        super.initContentView(view);
        mAdapter.registerDelegate(new MovieDelegate(getActivity()));
        mAdapter.setOnItemClickListener(this);
        mController = new MovieController(getActivity(), this);
        mRefreshHelper.setDividerColor(getResources().getColor(R.color.white));
    }
    
    @Override
    protected void initData() {
        super.initData();
        load(false);
    }
    
    @Override
    protected void load(boolean needCache) {
        MovieRequest request = new MovieRequest();
        request.start = (mRefreshHelper.getCurrentPage() - 1)*10;//0开始
        mController.loadTop(request);
    }
    
    @Override
    public void onSuccess(List<MovieResponse> out) {
        mRefreshHelper.onLoadFinish(out);
    }
    
    @Override
    public void onError(Throwable error) {
        
    }
    
    @Override
    public void onComplete() {
        
    }
    
    @Override
    public void onItemClick(AfRecyclerAdapter afRecyclerAdapter, View view, int i) {
        H5Activity.forward(getActivity(),mAdapter.getItem(i).alt,mAdapter.getItem(i).original_title);
    }
    
    public static class MovieDelegate extends AdapterDelegate<MovieResponse> {
        
        Context context;
        
        public MovieDelegate(Context context) {
            this.context = context;
        }
        
        @Override
        public int getLayout() {
            return R.layout.list_item_movie;
        }
        
        @Override
        public void onUpdateView(RecyclerView.ViewHolder viewHolder, MovieResponse data, int i) {
            MyViewHolder holder = (MyViewHolder) viewHolder;
            holder.fl.removeAllViews();
            holder.iv.setImageURI(Uri.parse(data.images.large));
            holder.title.setText(data.title);
            for (String genre : data.genres) {
                RoundButton tag = null;
                if (tag == null) {
                    tag = (RoundButton) View.inflate(context, R.layout.layout_tag, null);
                }
                else {
                    tag = (RoundButton) holder.fl.getTag();
                }
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(AppUtils.dp2px(context, 47),
                    AppUtils.dp2px(context, 23));
                tag.setLayoutParams(params);
                holder.fl.setTag(tag);
                tag.setText(genre);
                holder.fl.addView(tag);
            }
        }
        
        @Override
        public Class<? extends RecyclerView.ViewHolder> getViewHolderClass() {
            return MyViewHolder.class;
        }
    }
    
    private static class MyViewHolder extends AfViewHolder {
        
        SimpleDraweeView iv;
        TextView title;
        FlowLayout fl;
        
        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (SimpleDraweeView) itemView.findViewById(R.id.iv);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            fl = (FlowLayout) itemView.findViewById(R.id.fl);
        }
    }
}
