package com.yilaole.adapter.institution;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yilaole.R;
import com.yilaole.bean.institution.detail.InstituteDetailBean;
import com.yilaole.utils.DpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 机构详情-更多图片适配
 */

public class DetailGalleryRecylerAdapter extends RecyclerView.Adapter<DetailGalleryRecylerAdapter.ViewHolder> {

    private Context mContext;
    private List<InstituteDetailBean.IMG> dataList;

    public DetailGalleryRecylerAdapter(Context context, List<InstituteDetailBean.IMG> dataList) {
        this.dataList = dataList;
        mContext = context;
    }

    public void setDataList(List<InstituteDetailBean.IMG> dataList) {
        this.dataList = dataList;
        if (dataList == null || dataList.isEmpty()) {
            this.dataList = new ArrayList<>();
        }
    }

    //item监听设置

    public OnItemClickListner onItemClickListner;

    public interface OnItemClickListner {
        void onItemClickListener(View view, int position);
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gallery_pic, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        InstituteDetailBean.IMG bean = dataList.get(position);

        String DeanURL = "";
        if (!bean.getImage().contains("http")) {
            DeanURL = "https://image.efulai.cn/" + bean.getImage();
        } else {
            DeanURL = bean.getImage();
        }
        Glide.with(mContext).load(DeanURL)
                .override(DpUtils.dpToPx(mContext, 90), DpUtils.dpToPx(mContext, 90))
                .error(ContextCompat.getDrawable(mContext, R.mipmap.item_bg_default1))
                .placeholder(ContextCompat.getDrawable(mContext, R.mipmap.item_bg_default1))
                .centerCrop()
                .into(holder.img_pic);

        //item监听
        holder.img_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListner.onItemClickListener(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_pic;

        public ViewHolder(View itemView) {
            super(itemView);
            img_pic = itemView.findViewById(R.id.img_pic);
        }


    }
}
