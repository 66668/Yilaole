package com.yilaole.adapter.news;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yilaole.R;
import com.yilaole.base.adapterbase.BaseQuickAdapter;
import com.yilaole.base.adapterbase.BaseViewHolder;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.news.HotNewsBean;

import java.util.List;

/**
 * 资讯详情-热门文章适配
 */

public class NewsHotRecylerAdapter extends BaseQuickAdapter<HotNewsBean, BaseViewHolder> {

    private Context mContext;
    private List<HotNewsBean> dataList;

    public NewsHotRecylerAdapter(Context context, List<HotNewsBean> fruitList) {
        super(R.layout.item_news_common1, fruitList);
        dataList = fruitList;
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, HotNewsBean bean) {

        //获取img 下载图片
        ImageView img = holder.getView(R.id.img_photo);

        String path = "";
        if (!bean.getImage().contains("http")) {
            path = Constants.NEW_HTTP + bean.getImage();
        } else {
            path = bean.getImage();
        }
        Glide.with(mContext)
                .load(path)
                .error(ContextCompat.getDrawable(mContext, R.mipmap.news_bg_default))
                .into(img);

        holder.setText(R.id.tv_news_title, bean.getTitle())
                .setText(R.id.tv_time, bean.getTime())
                .addOnClickListener(R.id.layout_news_item);

    }

}
