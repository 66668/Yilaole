package com.yilaole.adapter.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yilaole.R;
import com.yilaole.bean.mine.DoorAssessItemBean;

import java.util.List;

/**
 * 我的-上门评估适配
 * 已不用（没做item子控件监听）
 */

public class MineDoorAssessRecyclerViewAdapter extends RecyclerView.Adapter<MineDoorAssessRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<DoorAssessItemBean> dataList;

    public MineDoorAssessRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public void setDataList(List<DoorAssessItemBean> dataList) {
        this.dataList = dataList;
    }

    //添加监听
    public interface OnItemClickListener {
        void onItemClickListener(View view, int position);
    }

    public OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_institutedetail_assessdoor, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        DoorAssessItemBean bean = dataList.get(position);
        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClickListener(view, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout layout_item;

        public ViewHolder(View itemView) {
            super(itemView);
            layout_item = itemView.findViewById(R.id.layout_item);
        }


    }
}
