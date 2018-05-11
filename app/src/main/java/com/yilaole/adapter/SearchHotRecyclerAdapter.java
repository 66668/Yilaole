package com.yilaole.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yilaole.R;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.TextBean;
import com.yilaole.utils.DpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * =======
 * 历史搜索-热门机构 recyclerView的适配
 */

public class SearchHotRecyclerAdapter extends RecyclerView.Adapter<SearchHotRecyclerAdapter.HistoryHotHolder> {
    private List<TextBean> list = new ArrayList<>();
    LayoutInflater inflater;
    Context context;

    public SearchHotRecyclerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<TextBean> list) {
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
    public HistoryHotHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_search_hot, parent, false);
        HistoryHotHolder holder = new HistoryHotHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final HistoryHotHolder holder, final int position) {
        TextBean bean = list.get(position);
        if (bean != null) {
            //文本
            holder.tv.setText(bean.getName());
            
            //图片
            String path = "";
            if (!bean.getImgPath().contains("http")) {
                path = Constants.NEW_HTTP + bean.getImgPath();
            } else {

                path = bean.getImgPath();
            }
            Glide
                    .with(context)
                    .load(path)
                    .override(DpUtils.dip2px(context, 195), DpUtils.dip2px(context, 90))
                    .centerCrop()
                    .into(holder.img);


            //跳转监听
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemListener != null) {
                        onItemListener.onItemClickListener(holder.img, position);
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
    class HistoryHotHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_hot_img)
        ImageView img;
        @BindView(R.id.item_hot_tv)
        TextView tv;

        public HistoryHotHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);
        }
    }
}
