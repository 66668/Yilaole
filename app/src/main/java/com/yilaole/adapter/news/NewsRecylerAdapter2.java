package com.yilaole.adapter.news;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yilaole.R;
import com.yilaole.base.adapterbase.BaseViewHolder;
import com.yilaole.base.adapterbase.BaseiItemMultiQuickAdapter;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.news.NewsBean;
import com.yilaole.utils.DpUtils;

import java.util.List;

/**
 * 修改中
 * 资讯页
 * 嵌套fragment，子fragment的recyclerView的适配
 * <p>
 * 多布局样式：一张大图/一张小图/三张图 的三中样式
 */

public class NewsRecylerAdapter2 extends BaseiItemMultiQuickAdapter<NewsBean, BaseViewHolder> {

    private Context mContext;
    private List<NewsBean> dataList;

    public NewsRecylerAdapter2(Context context, List<NewsBean> fruitList) {
        super(fruitList);
        dataList = fruitList;
        this.mContext = context;
        addItemType(Constants.ITEM_NEWS_TYPE1, R.layout.item_news_common1);
        addItemType(Constants.ITEM_NEWS_TYPE2, R.layout.item_news_common2);
        addItemType(Constants.ITEM_NEWS_TYPE3, R.layout.item_news_common3);
    }

    @Override
    protected void convert(BaseViewHolder holder, NewsBean bean) {
        switch (holder.getItemViewType()) {
            case Constants.ITEM_NEWS_TYPE1:
                //获取img 下载图片
                ImageView smallIimg = holder.getView(R.id.img_photo);
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
                        .into(smallIimg);


                holder.setText(R.id.tv_news_title, bean.getTitle())
                        .setText(R.id.tv_time, bean.getTime())
                        .addOnClickListener(R.id.layout_news_item);
                break;
            case Constants.ITEM_NEWS_TYPE2:
                //获取img 下载图片
                ImageView bigIimg = holder.getView(R.id.img_photo);
                String path2 = bean.getImage();
                if (!(bean.getImage()).contains("http")) {
                    path2 = Constants.NEW_HTTP + bean.getImage();
                } else {
                    path2 = bean.getImage();
                }

                Glide.with(mContext)
                        .load(path2)
                        .error(ContextCompat.getDrawable(mContext, R.mipmap.news_bg_default))
                        .centerCrop()
                        .into(bigIimg);


                holder.setText(R.id.tv_news_title2, bean.getTitle())
                        .setText(R.id.tv_time2, bean.getTime())
                        .addOnClickListener(R.id.layout_news_item2);
                break;
            case Constants.ITEM_NEWS_TYPE3:

                //获取img 下载图片
                ImageView small1 = holder.getView(R.id.img_smallphoto1);
                ImageView small2 = holder.getView(R.id.img_smallphoto1);
                ImageView small3 = holder.getView(R.id.img_smallphoto1);

                String samllPath1 = "";
                String samllPath2 = "";
                String samllPath3 = "";

                //                if (!(bean.getImage()).contains("http")) {
                //                    path2 = Constants.NEW_HTTP + bean.getImage();
                //                } else {
                //                    path2 = bean.getImage();
                //                }
                // TODO: 2017/11/10 等接口样式
                Glide.with(mContext)
                        .load(samllPath1)
                        .error(ContextCompat.getDrawable(mContext, R.mipmap.news_bg_default))
                        .centerCrop()
                        .into(small1);


                holder.setText(R.id.tv_news_title3, bean.getTitle())
                        .setText(R.id.tv_time3, bean.getTime())
                        .addOnClickListener(R.id.layout_news_item3);
                break;

        }


    }

}
