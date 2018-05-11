package com.yilaole.adapter.mine;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yilaole.R;
import com.yilaole.base.adapterbase.BaseQuickAdapter;
import com.yilaole.base.adapterbase.BaseViewHolder;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.mine.MineCollectInstituteBean;

import java.util.List;

/**
 * 我的收藏 list适配
 */

public class MineCollectListRecylerAdapter extends BaseQuickAdapter<MineCollectInstituteBean, BaseViewHolder> {

    private Context mContext;
    private List<MineCollectInstituteBean> dataList;

    public MineCollectListRecylerAdapter(Context context, List<MineCollectInstituteBean> fruitList) {
        super(R.layout.item_mine_collect_institution, fruitList);
        dataList = fruitList;
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, MineCollectInstituteBean bean) {
        if (bean == null) {
            return;
        }
        //获取img 下载图片
        ImageView img = holder.getView(R.id.img_photo);
        TextView tv_settled0 = holder.getView(R.id.tv_settled0);
        TextView tv_office0 = holder.getView(R.id.tv_office0);
        CheckBox checkbox = holder.getView(R.id.checkbox);

        String path = "";
        if (bean.getImage_logo() != null) {
            if (!bean.getImage_logo().contains("http")) {
                path = Constants.DETAIL_HTTP + bean.getImage_logo();
            } else {
                path = bean.getImage_logo();
            }

        }

        Glide.with(mContext)
                .load(path)
                //                        .override(DpUtils.dpToPx(mContext, 78), DpUtils.dpToPx(mContext, 92))
                .error(ContextCompat.getDrawable(mContext, R.mipmap.news_bg_default))
                .placeholder(ContextCompat.getDrawable(mContext, R.mipmap.news_bg_default))
                //                        .fitCenter()
                .centerCrop()
                .into(img);
        //公办 民办
        if (bean.getProperty() == 1) {
            tv_office0.setText(mContext.getResources().getString(R.string.instite_detail_civilianRun));
        } else {
            tv_office0.setText(mContext.getResources().getString(R.string.instite_detail_StateRun));

        }

        //入驻0 1 2
        if (bean.getStatus() == 0) {//未入驻
            tv_settled0.setText(mContext.getResources().getString(R.string.instite_detail_noEntering));
        } else if (bean.getStatus() == 1) {//已认领
            tv_settled0.setText(mContext.getResources().getString(R.string.instite_detail_entering));
        } else {//已入驻
            tv_settled0.setText(mContext.getResources().getString(R.string.instite_detail_entered));
        }
        holder.setText(R.id.tv_institution_title0, bean.getName() == null ? "" : bean.getName())
                .setText(R.id.tv_place0, "地址：" + bean.getAddress() == null ? "" : bean.getAddress())
                .setText(R.id.tv_oldPrice0, bean.getMarket_pric() == null ? "" : ("￥" + bean.getMarket_pric() + "起"))
                .setText(R.id.tv_newPrice0, "￥" + bean.getPrice() + "起")
                .addOnClickListener(R.id.layout_more)
                .addOnClickListener(R.id.layout_collect_item);

    }

}
