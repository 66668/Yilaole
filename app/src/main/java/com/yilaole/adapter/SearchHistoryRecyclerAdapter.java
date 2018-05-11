package com.yilaole.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.utils.MLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 历史 recyclerView的适配
 */

public class SearchHistoryRecyclerAdapter extends RecyclerView.Adapter<SearchHistoryRecyclerAdapter.HomeNewsHolder> {
    private List<String> list = new ArrayList<>();
    LayoutInflater inflater;
    Context context;

    public SearchHistoryRecyclerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    //构造点击接口
    public OnItemClickListener onItemListener;

    public interface OnItemClickListener {
        void onItemClickListener(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemListener = onItemClickListener;
    }

    @Override
    public HomeNewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_search_history, parent, false);
        HomeNewsHolder holder = new HomeNewsHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final HomeNewsHolder holder, final int position) {
        String bean = list.get(position);
        if (bean != null) {
            MLog.e("bean!=null");

            holder.tv.setText(bean);
            //跳转监听
            holder.tv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (onItemListener != null) {
                        onItemListener.onItemClickListener(holder.tv, position);
                    }

                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    /**
     * 自定义holder
     */
    class HomeNewsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_history)
        TextView tv;

        public HomeNewsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);
        }
    }
}
