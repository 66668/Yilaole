package com.yilaole.adapter.institution;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yilaole.R;
import com.yilaole.base.adapterbase.BaseQuickAdapter;
import com.yilaole.base.adapterbase.BaseViewHolder;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.institution.filter.InstituteItemBean;
import com.yilaole.utils.DpUtils;

import java.util.List;

/**
 * 机构筛选 数据列表适配
 * <p>
 * 有上拉下拉
 */

public class InstitutionFilterRecylerAdapter extends BaseQuickAdapter<InstituteItemBean, BaseViewHolder> {

    private Context mContext;
    private List<InstituteItemBean> dataList;

    public InstitutionFilterRecylerAdapter(Context context, List<InstituteItemBean> listData) {
        super(R.layout.item_institute_filter, listData);
        this.mContext = context;
        this.dataList = listData;
    }

    @Override
    protected void convert(BaseViewHolder holder, InstituteItemBean bean) {

        //获取img 下载图片
        ImageView img = holder.getView(R.id.img_photo);
        TextView tv_settled = holder.getView(R.id.tv_settled);//入驻
        TextView tv_office = holder.getView(R.id.tv_office);//性质
        TextView tv_oldPrice = holder.getView(R.id.tv_oldPrice);
        TextView tv_newPrice = holder.getView(R.id.tv_newPrice);


        //入驻
        if (bean.getStatus() == 0) {//未入驻
            tv_settled.setText(mContext.getResources().getString(R.string.instite_detail_noEntering));
        } else if (bean.getStatus() == 1) {//已认领
            tv_settled.setText(mContext.getResources().getString(R.string.instite_detail_entering));
        } else {//已入驻
            tv_settled.setText(mContext.getResources().getString(R.string.instite_detail_entered));
        }

        //公办 民办
        if (bean.getProperty() == 1) {
            tv_office.setText(mContext.getResources().getString(R.string.instite_detail_civilianRun));
        } else if (bean.getProperty() == 2) {
            tv_office.setText(mContext.getResources().getString(R.string.instite_detail_StateRun));

        } else {
            tv_office.setText("空");
        }
        //价格
        if (bean.getOldprice() != null && !bean.getOldprice().isEmpty()) {//未入驻
            if (bean.getOldprice().contains("null")) {
                tv_oldPrice.setText("暂无");
            } else {
                tv_oldPrice.setText("￥" + bean.getOldprice() + "起");
            }
            tv_oldPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);//添加中间划线
        } else {
            tv_oldPrice.setText("暂无");
        }


        if (bean.getPrice() != null && !bean.getPrice().isEmpty()) {//未入驻
            if (bean.getPrice().contains("null")) {
                tv_newPrice.setText("暂无");
            } else {
                tv_newPrice.setText("￥" + bean.getPrice() + "起");
            }
        } else {
            tv_newPrice.setText("暂无");
        }


        //图片显示
        String path = "";
        if (bean.getLogo_url() != null) {
            if (!(bean.getLogo_url()).contains("http")) {
                path = Constants.DETAIL_HTTP + bean.getLogo_url();
            } else {
                path = bean.getLogo_url();
            }
        }

        Glide.with(mContext)
                .load(path)
                .override(DpUtils.dpToPx(mContext, 78), DpUtils.dpToPx(mContext, 92))
                .error(ContextCompat.getDrawable(mContext, R.mipmap.item_bg_default1))
                .placeholder(ContextCompat.getDrawable(mContext, R.mipmap.item_bg_default1))
                //                        .fitCenter()
                .centerCrop()
                .into(img);

        //文本显示+监听回调
        holder.setText(R.id.tv_institution_title, bean.getName())//标题
                .setText(R.id.tv_grade, bean.getNum() + "分")
                .setText(R.id.tv_place, "地址：" + bean.getAddress())//地址
                .setText(R.id.tv_hotNumber, bean.getPopularity())//收藏人数
                .addOnClickListener(R.id.layout_home_institution);


    }
}
