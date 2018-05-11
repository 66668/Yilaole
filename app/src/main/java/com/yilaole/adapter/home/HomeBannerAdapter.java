package com.yilaole.adapter.home;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yilaole.R;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.home.BannerBean;
import com.yilaole.utils.MLog;

import java.util.List;

/**
 * 首页-图片轮播适配
 */
public class HomeBannerAdapter extends PagerAdapter {
    private List<BannerBean> adList;
    Context mContext;

    public HomeBannerAdapter(Context mContext, List<BannerBean> adList) {
        this.adList = adList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int arg2) {

        final int position = arg2 % adList.size();
        ImageView imageView = new ImageView(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);


        //点击监听
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MLog.ToastShort(mContext, "跳转：" + adList.get(position).getLink_url());
                //                if (adList != null) {
                //                    Intent intent = new Intent();
                //                    intent.setAction("android.intent.action.VIEW");
                //                    Uri uri = Uri.parse(adList.get(position).getLink_url());
                //
                //                    //                    String path = "";
                //                    //                    if (adList.get(position).getLink_url().contains("http")) {
                //                    //                        path = adList.get(position).getLink_url();
                //                    //                    } else {
                //                    //                        path = Constants.NEW_HTTP + adList.get(position).getLink_url();
                //                    //                    }
                //                    //                    intent.putExtra("url", path);
                //
                //                    intent.setData(uri);
                //                    mContext.startActivity(intent);
                //
                //                }
            }
        });

        //图片显示
        String imgUrl = "";
        if (adList.get(position).getImage_url().contains("http")) {
            imgUrl = adList.get(position).getImage_url();
        } else {
            imgUrl = Constants.NEW_HTTP + adList.get(position).getImage_url();
        }
        Glide.with(mContext)
                .load(imgUrl)
                .placeholder(R.mipmap.banner_default)
                .error(R.mipmap.banner_default)
                .into(imageView);

        container.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return imageView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}
