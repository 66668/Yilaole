package com.yilaole.adapter.institution;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yilaole.R;

import java.util.List;

/**
 * 机构详情-收住对象适配
 */

public class InstituteDetailObjectRecylerAdapter extends RecyclerView.Adapter<InstituteDetailObjectRecylerAdapter.ViewHolder> {

    private Context mContext;
    private List<String> dataList;

    public InstituteDetailObjectRecylerAdapter(Context context) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_institutedetail_object, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String bean = dataList.get(position);
        holder.tv_object.setText(bean);
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
