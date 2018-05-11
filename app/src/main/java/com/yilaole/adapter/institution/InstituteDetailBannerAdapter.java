package com.yilaole.adapter.institution;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yilaole.R;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.institution.detail.DetailBannerBean;

import java.util.List;

/**
 * 首页-图片轮播适配
 */
public class InstituteDetailBannerAdapter extends PagerAdapter {
    private List<DetailBannerBean> adList;
    Context context;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    public InstituteDetailBannerAdapter(Context mContext, List<DetailBannerBean> adList) {
        this.adList = adList;
        context = mContext;

        //        options = ImageLoaderUtils.mImageOptions();
        //        imageLoader = ImageLoader.getInstance();
        //        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int arg2) {
        final int position = arg2 % adList.size();
        ImageView imageView = new ImageView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //点击监听
        //		imageView.setOnClickListener(new OnClickListener() {
        //
        //			@Override
        //			public void onClick(View v) {
        //				if (adList != null) {
        //					Intent mIntent = new Intent();
        //					if (adList.get(position).getTYPE().equals("1")) {
        //						Intent intent= new Intent();
        //					    intent.setAction("android.intent.action.VIEW");
        //					    Uri content_url = Uri.parse(adList.get(position).getBOOKID().trim());
        //					    intent.setData(content_url);
        //					    mContext.startActivity(intent);
        //					} else if (adList.get(position).getTYPE().equals("2")){
        //						Bundle bundle = new Bundle();
        //						bundle.putString("bookId", adList.get(position).getBOOKID().trim());
        //						mActivity.index(Constants.Code.DETAIL_CONTENT, bundle);
        //					}else if (adList.get(position).getTYPE().equals("3")){
        //
        //					}
        //					try {
        //						mContext.startActivity(mIntent);
        //					} catch (Exception e) {
        //						e.printStackTrace();
        //					}
        //				}
        //			}
        //		});

        //图片显示
        String imgUrl = "";
        if (adList.get(position).getImage().contains("http")) {
            imgUrl = adList.get(position).getImage();
        } else {
            imgUrl = Constants.DETAIL_HTTP + adList.get(position).getImage();
        }
        // TODO: 2017/11 应付大老板的假图
        Glide.with(context)
                .load(R.mipmap.xiangqing_banner)//imgUrl
                .placeholder(R.mipmap.xiangqing_banner)
                .error(R.mipmap.xiangqing_banner)
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
