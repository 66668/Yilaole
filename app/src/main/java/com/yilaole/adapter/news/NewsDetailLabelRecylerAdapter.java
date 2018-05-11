package com.yilaole.adapter.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yilaole.R;

import java.util.List;

/**
 * 资讯详情-label适配
 */

public class NewsDetailLabelRecylerAdapter extends RecyclerView.Adapter<NewsDetailLabelRecylerAdapter.ViewHolder> {

    private Context mContext;
    private List<String> dataList;

    public NewsDetailLabelRecylerAdapter(Context context) {
        mContext = context;
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_institutedetail_label, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String bean = dataList.get(position);
        if (TextUtils.isEmpty(bean)) {
            holder.tv_object.setText("无数据");
        } else {
            holder.tv_object.setText(bean);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_object;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_object = (TextView) itemView.findViewById(R.id.tv_object);
        }


    }
}
