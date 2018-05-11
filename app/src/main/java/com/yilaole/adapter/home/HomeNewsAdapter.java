package com.yilaole.adapter.home;

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
import com.yilaole.bean.home.HomeNewsBean;
import com.yilaole.utils.DpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页-资讯:横向recyclerView的适配
 */

public class HomeNewsAdapter extends RecyclerView.Adapter<HomeNewsAdapter.HomeNewsHolder> {
    private List<HomeNewsBean> list = new ArrayList<>();
    LayoutInflater inflater;
    Context context;

    public HomeNewsAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<HomeNewsBean> list) {
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
        View view = inflater.inflate(R.layout.item_home_news, parent, false);
        HomeNewsHolder holder = new HomeNewsHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final HomeNewsHolder holder, final int position) {
        HomeNewsBean bean = list.get(position);
        if (bean != null) {
            String path = "";
            if (!(bean.getLogo_url()).contains("http")) {
                path = Constants.NEW_HTTP + bean.getLogo_url();
            } else {
                path = bean.getLogo_url();
            }
            //item赋值
            Glide
                    .with(context)
                    .load(path)
                    .override(DpUtils.dip2px(context, 195), DpUtils.dip2px(context, 90))
                    .placeholder(R.mipmap.news_bg_default)
                    .error(R.mipmap.news_bg_default)
                    .fitCenter()
                    .into(holder.img);
            holder.tv.setText(bean.getTitle());
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
        return list.size();
    }

    public HomeNewsBean getItem(int position) {
        return list.get(position);
    }

    /**
     * 自定义holder
     */
    class HomeNewsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_home_news_img)
        ImageView img;
        @BindView(R.id.item_home_news_tv)
        TextView tv;

        public HomeNewsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);
        }
    }
}
