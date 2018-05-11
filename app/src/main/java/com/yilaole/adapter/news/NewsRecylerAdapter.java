package com.yilaole.adapter.news;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yilaole.R;
import com.yilaole.base.adapterbase.BaseQuickAdapter;
import com.yilaole.base.adapterbase.BaseViewHolder;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.news.NewsBean;
import com.yilaole.utils.DpUtils;
import com.yilaole.utils.TimeUtils;

import java.util.List;

/**
 * 资讯页
 * 嵌套fragment，fragment的recyclerView的适配
 */

public class NewsRecylerAdapter extends BaseQuickAdapter<NewsBean, BaseViewHolder> {

    private Context mContext;
    private List<NewsBean> dataList;

    public NewsRecylerAdapter(Context context, List<NewsBean> fruitList) {
        super(R.layout.item_news_common1, fruitList);
        dataList = fruitList;
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, NewsBean bean) {

        //获取img 下载图片
        ImageView img = holder.getView(R.id.img_photo);
        String path = bean.getImage();
        if (!(bean.getImage()).contains("http")) {
            path = Constants.NEW_HTTP + bean.getImage();
        } else {
            path = bean.getImage();
        }

        Glide.with(mContext)
                .load(path)
                .override(DpUtils.dip2px(mContext, 94), DpUtils.dip2px(mContext, 94))
                .error(ContextCompat.getDrawable(mContext, R.mipmap.news_bg_default))
                .centerCrop()
                .into(img);


        holder.setText(R.id.tv_news_title, bean.getTitle())
                .setText(R.id.tv_time, TimeUtils.getExactelyTime(bean.getTime()))
                .addOnClickListener(R.id.layout_news_item);

    }

}
