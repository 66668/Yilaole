package com.yilaole.adapter.institution;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yilaole.R;
import com.yilaole.bean.institution.detail.FeesBean;

import java.util.List;

/**
 * 机构详情-收费标准适配
 */

public class InstituteDetailFeesRecylerAdapter extends RecyclerView.Adapter<InstituteDetailFeesRecylerAdapter.ViewHolder> {

    private Context mContext;
    private List<FeesBean.FeesDetailBean> dataList;

    public InstituteDetailFeesRecylerAdapter(Context context) {
        mContext = context;
    }

    public void setDataList(List<FeesBean.FeesDetailBean> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_institutedetail_fees, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FeesBean.FeesDetailBean bean = dataList.get(position);

        //用图标准
        if (bean.getFeename().contains("单人床")) {
            Glide.with(mContext).load(R.mipmap.fees_bedbg)
                    .into(holder.bedImg);
        } else if (bean.getFeename().contains("双人床")) {
            Glide.with(mContext).load(R.mipmap.fees_bedbg2)
                    .into(holder.bedImg);
        } else if (bean.getFeename().contains("单人床")) {
            Glide.with(mContext).load(R.mipmap.fees_bedbg3)
                    .into(holder.bedImg);
        } else {
            Glide.with(mContext).load(R.mipmap.fees_bedbg4)
                    .into(holder.bedImg);
        }

        //feesName
        holder.tv_feesName.setText(bean.getFeename());
        holder.tv_remark.setText("备注：" + bean.getFeediscribe());
        holder.tv_price.setText(" ￥" + bean.getPrice() + bean.getUnit());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bedImg;
        TextView tv_feesName;
        TextView tv_remark;
        TextView tv_price;

        public ViewHolder(View itemView) {
            super(itemView);
            bedImg = (ImageView) itemView.findViewById(R.id.bedImg);
            tv_feesName = (TextView) itemView.findViewById(R.id.tv_feesName);
            tv_remark = (TextView) itemView.findViewById(R.id.tv_bedRemark);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
        }


    }
}
