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
import com.yilaole.bean.institution.detail.QualificationBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 机构详情-资质适配
 */

public class InstituteDetailQualificationRecylerAdapter extends RecyclerView.Adapter<InstituteDetailQualificationRecylerAdapter.ViewHolder> {

    private Context mContext;
    private List<QualificationBean.IMGS> dataList;

    public InstituteDetailQualificationRecylerAdapter(Context context) {
        mContext = context;
    }

    public void setDataList(List<QualificationBean.IMGS> dataList) {
        this.dataList = dataList;
        if (dataList == null || dataList.isEmpty()) {
            this.dataList = new ArrayList<>();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_institutedetail_qualification, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        QualificationBean.IMGS bean = dataList.get(position);

        String DeanURL = "";
        if (!bean.getImage().contains("http")) {
            DeanURL = "https://image.efulai.cn/" + bean.getImage();
        } else {
            DeanURL = bean.getImage();
        }
        Glide.with(mContext).load(DeanURL)
                .error(ContextCompat.getDrawable(mContext, R.mipmap.banner_default))
                .placeholder(ContextCompat.getDrawable(mContext, R.mipmap.banner_default))
                .into(holder.tv_object);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView tv_object;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_object = itemView.findViewById(R.id.qualification_img);
        }


    }
}
