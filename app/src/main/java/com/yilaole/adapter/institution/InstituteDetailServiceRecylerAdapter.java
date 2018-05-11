package com.yilaole.adapter.institution;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yilaole.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 机构详情-机构特色适配
 */

public class InstituteDetailServiceRecylerAdapter extends RecyclerView.Adapter<InstituteDetailServiceRecylerAdapter.ViewHolder> {

    private Context mContext;
    private List<String> dataList;

    public InstituteDetailServiceRecylerAdapter(Context context) {
        mContext = context;
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
        if (dataList == null || dataList.isEmpty()) {
            dataList = new ArrayList<>();
            this.dataList = dataList;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_institutedetail_service, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        String bean = dataList.get(position);

        if (bean == null || bean.isEmpty()) {
            return;
        }
        //后台总共8种图片，需要区分
        if (bean.contains(mContext.getResources().getString(R.string.instite_detail_yiyangjiehe))) {//医养结合00
            holder.tv_object.setText(bean);
            holder.img_service.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.xiangqing_icon0));

        } else if (bean.contains(mContext.getResources().getString(R.string.instite_detail_jieshouyidi))) {//接受异地01
            holder.tv_object.setText(bean);
            holder.img_service.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.xiangqing_icon1));

        } else if (bean.contains(mContext.getResources().getString(R.string.instite_detail_jujiashangmen))) {//居家上门2
            holder.tv_object.setText(bean);
            holder.img_service.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.xiangqing_icon2));

        } else if (bean.contains(mContext.getResources().getString(R.string.instite_detail_hezuoyiyuan))) {//合作医院3
            holder.tv_object.setText(bean);
            holder.img_service.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.xiangqing_icon3));

        } else if (bean.contains(mContext.getResources().getString(R.string.instite_detail_yiliaobaoxiao))) {//医疗报销4
            holder.tv_object.setText(bean);
            holder.img_service.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.xiangqing_icon4));

        } else if (bean.contains(mContext.getResources().getString(R.string.instite_detail_changqizhaohu))) {//长青护照险5
            holder.tv_object.setText(bean);
            holder.img_service.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.xiangqing_icon5));

        } else if (bean.contains(mContext.getResources().getString(R.string.instite_detail_zhuanzhentongdao))) {//转诊通道6
            holder.tv_object.setText(bean);
            holder.img_service.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.xiangqing_icon6));

        } else if (bean.contains(mContext.getResources().getString(R.string.instite_detail_mianfeishizhu))) {//免费试住7
            holder.tv_object.setText(bean);
            holder.img_service.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.xiangqing_icon7));

        } else {
            holder.tv_object.setText(bean);
            holder.img_service.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.xiangqing_icon0));
        }
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_object;
        ImageView img_service;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_object = (TextView) itemView.findViewById(R.id.tv_object);
            img_service = (ImageView) itemView.findViewById(R.id.img_service);
        }


    }
}
