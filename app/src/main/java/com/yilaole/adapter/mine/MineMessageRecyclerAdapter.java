package com.yilaole.adapter.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yilaole.R;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.mine.MessageItemBean;
import com.yilaole.utils.DpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的消息list 适配
 */

public class MineMessageRecyclerAdapter extends RecyclerView.Adapter<MineMessageRecyclerAdapter.HistoryHotHolder> {
    private List<MessageItemBean> list = new ArrayList<>();
    LayoutInflater inflater;
    Context context;

    public MineMessageRecyclerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<MessageItemBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    //构造点击接口
    public OnItemClickListener onItemListener;

    public interface OnItemClickListener {
        void onItemClickListener(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemListener = onItemClickListener;
    }

    @Override
    public HistoryHotHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_mine_message, parent, false);
        HistoryHotHolder holder = new HistoryHotHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final HistoryHotHolder holder, final int position) {
        MessageItemBean bean = list.get(position);
        if (bean != null) {
            // TODO: 2017/10/27  没做
            //文本
            holder.tv_assess_name.setText("");
            holder.tv_content.setText("");
            holder.tv_office.setText("");
            holder.tv_time.setText("");

            //图片
            String path = "";
            if (bean.getImgPath() != null) {
                if (!bean.getImgPath().contains("http")) {
                    path = Constants.NEW_HTTP + bean.getImgPath();
                } else {

                    path = bean.getImgPath();
                }
            }

            Glide.with(context)
                    .load(path)
                    .override(DpUtils.dip2px(context, 54), DpUtils.dip2px(context, 54))
                    .centerCrop()
                    .into(holder.img);


            //跳转监听
            holder.layout_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemListener != null) {
                        onItemListener.onItemClickListener(holder.img, position);
                    }

                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    /**
     * 自定义holder
     */
    class HistoryHotHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_item)
        RelativeLayout layout_item;
        @BindView(R.id.img_assess)
        ImageView img;

        @BindView(R.id.tv_office)
        TextView tv_office;

        @BindView(R.id.tv_assess_name)
        TextView tv_assess_name;

        @BindView(R.id.tv_time)
        TextView tv_time;

        @BindView(R.id.tv_content)
        TextView tv_content;

        public HistoryHotHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);
        }
    }
}
