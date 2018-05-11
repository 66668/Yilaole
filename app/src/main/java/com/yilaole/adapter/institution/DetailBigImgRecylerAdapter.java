package com.yilaole.adapter.institution;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.yilaole.R;
import com.yilaole.bean.institution.detail.InstituteDetailBean;
import com.yilaole.utils.DpUtils;

import java.util.List;

import lib.yilaole.gallery.PhotoView;

/**
 * 机构详情-图片大图展示适配
 */

public class DetailBigImgRecylerAdapter extends PagerAdapter {
    private Context context;
    private List<InstituteDetailBean.IMG> dataList;

    public DetailBigImgRecylerAdapter(Context context, List<InstituteDetailBean.IMG> list) {
        this.context = context;
        this.dataList = list;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_img_big, container, false);

        final PhotoView photoView = view.findViewById(R.id.img_photoView);
        final ProgressBar progressBar = view.findViewById(R.id.loading);

        // 保存网络图片的路径
        String adapter_image_Entity = (String) getItem(position);
        String imageUrl;
        if (!adapter_image_Entity.isEmpty()) {
            if (!adapter_image_Entity.contains("http")) {
                imageUrl = "https://image.efulai.cn/" + adapter_image_Entity;
            } else {
                imageUrl = adapter_image_Entity;
            }
        } else {
            imageUrl = "";
        }

        progressBar.setVisibility(View.VISIBLE);
        progressBar.setClickable(false);

        Glide.with(context).load(imageUrl)
                .crossFade(700)//0.7s的动画淡入淡出
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Toast.makeText(context, "资源加载异常", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    //这个用于监听图片是否加载完成
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        //                            Toast.makeText(getApplicationContext(), "图片加载完成", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                        /**这里应该是加载成功后图片的高*/
                        int height = photoView.getHeight();

                        int wHeight = DpUtils.getHeigthPx(context);
                        if (height > wHeight) {
                            photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        } else {
                            photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        }
                        return false;
                    }
                }).into(photoView);

        //        photoView.setOnPhotoTapListener(ViewBigImageActivity.this);
        container.addView(view, 0);
        return view;
    }

    @Override
    public int getCount() {
        if (dataList == null || dataList.size() == 0) {
            return 0;
        }
        return dataList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    Object getItem(int position) {
        return dataList.get(position).getImage();
    }
}
