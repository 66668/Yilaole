package com.yilaole.adapter.institution;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yilaole.R;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.institution.detail.CharacteristicBean;

import java.util.List;

/**
 * 机构详情-医养特色适配
 */

public class InstituteDetailCharacteristicRecylerAdapter extends RecyclerView.Adapter<InstituteDetailCharacteristicRecylerAdapter.ViewHolder> {

    private Context mContext;
    private List<CharacteristicBean.CharacteristicDetailBean> dataList;

    public InstituteDetailCharacteristicRecylerAdapter(Context context) {
        mContext = context;
    }

    public void setDataList(List<CharacteristicBean.CharacteristicDetailBean> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_institutedetail_characteristic, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CharacteristicBean.CharacteristicDetailBean bean = dataList.get(position);

        //item显示
        String logoURL = "";
        if (bean.getImage() != null) {
            if (!bean.getImage().contains("http")) {
                logoURL = Constants.DETAIL_HTTP + bean.getImage();
            } else {
                logoURL = bean.getImage();
            }
        }
        Glide.with(mContext).load(logoURL)
                .error(ContextCompat.getDrawable(mContext, R.mipmap.banner_default))
                .placeholder(ContextCompat.getDrawable(mContext, R.mipmap.banner_default))
                .into(holder.chImg);

        holder.tv_chtitle.setText(bean.getTitle());
        holder.tv_content.setText(bean.getContent());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView chImg;
        TextView tv_chtitle;
        TextView tv_content;

        public ViewHolder(View itemView) {
            super(itemView);
            chImg = itemView.findViewById(R.id.chImg);
            tv_chtitle = itemView.findViewById(R.id.tv_chtitle);
            tv_content = itemView.findViewById(R.id.tv_content);
        }


    }
}
