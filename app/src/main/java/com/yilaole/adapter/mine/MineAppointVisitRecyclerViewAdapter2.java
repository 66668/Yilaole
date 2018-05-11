package com.yilaole.adapter.mine;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.base.adapterbase.BaseQuickAdapter;
import com.yilaole.base.adapterbase.BaseViewHolder;
import com.yilaole.bean.mine.AppointVisitItemBean;

import java.util.List;

/**
 * 我的-预约参观适配
 */


public class MineAppointVisitRecyclerViewAdapter2 extends BaseQuickAdapter<AppointVisitItemBean, BaseViewHolder> {

    private Context mContext;
    private List<AppointVisitItemBean> dataList;

    public MineAppointVisitRecyclerViewAdapter2(Context context, List<AppointVisitItemBean> listData) {
        super(R.layout.item_institutedetail_appoint, listData);
        this.mContext = context;
        this.dataList = listData;
    }

    @Override
    protected void convert(BaseViewHolder holder, AppointVisitItemBean bean) {

        //获取img 下载图片

        TextView tv_assess = holder.getView(R.id.tv_assess);

        //文本显示+监听回调
        holder.setText(R.id.tv_name, bean.getAgencyname())//标题
                .setText(R.id.tv_buyTime, bean.getOrder_time())
                .setText(R.id.tv_status, bean.getOrderstate())
                .addOnClickListener(R.id.layout_item);

        //评价状态
        if (bean.getOrderstate().contains("已完成")) {
            tv_assess.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrice));
            tv_assess.setBackground(ContextCompat.getDrawable(mContext, R.drawable.appointvisit_assess_true));
            holder.addOnClickListener(R.id.tv_assess);
        } else {
            tv_assess.setTextColor(ContextCompat.getColor(mContext, R.color.home_text2));
            tv_assess.setBackground(ContextCompat.getDrawable(mContext, R.drawable.appointvisit_assess_false));

        }


    }

}
