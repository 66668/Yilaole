package com.yilaole.adapter.mine;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yilaole.R;
import com.yilaole.base.adapterbase.BaseViewHolder;
import com.yilaole.base.adapterbase.BaseiItemMultiQuickAdapter;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.mine.MineCommentItemBean;
import com.yilaole.save.SPUtil;
import com.yilaole.utils.DpUtils;
import com.yilaole.utils.TimeUtils;

import java.util.List;

/**
 * 我的-我的评论
 * <p>
 * 多样式布局 暂定单一布局
 */

public class MineCommentListRecylerAdapter extends BaseiItemMultiQuickAdapter<MineCommentItemBean, BaseViewHolder> {

    private Context mContext;
    private List<MineCommentItemBean> dataList;

    public MineCommentListRecylerAdapter(Context context, List<MineCommentItemBean> list) {
        super(list);
        dataList = list;
        this.mContext = context;
        addItemType(Constants.MultiInstituteType.TYPE1, R.layout.item_mine_comment);
        addItemType(Constants.MultiInstituteType.TYPE2, R.layout.item_mine_comment2);
    }

    @Override
    protected void convert(BaseViewHolder holder, MineCommentItemBean bean) {
        //添加不同布局
        switch (holder.getItemViewType()) {
            //多布局样式1
            case Constants.MultiInstituteType.TYPE1://无机构回复
                //用户图片
                ImageView img_user = holder.getView(R.id.CommentUserImg);
                ImageView img_institute = holder.getView(R.id.img_institute);

                String path = "";
                if (bean.getAgency_image() != null && !bean.getAgency_image().isEmpty()) {

                    if (!bean.getAgency_image().contains("http")) {
                        path = Constants.DETAIL_HTTP + bean.getAgency_image();
                    } else {
                        path = bean.getAgency_image();
                    }
                }
                //个人图片
                Glide.with(mContext)
                        .load(R.mipmap.photo_default)
                        .error(ContextCompat.getDrawable(mContext, R.mipmap.photo_default))
                        .into(img_user);
                //机构图片
                Glide.with(mContext)
                        .load(path)
                        .error(ContextCompat.getDrawable(mContext, R.mipmap.item_bg_default1))
                        .override(DpUtils.dpToPx(mContext, 66), DpUtils.dpToPx(mContext, 66))
                        .centerCrop()
                        .into(img_institute);

                holder.setText(R.id.tv_commentUserName, SPUtil.getUserName())//用户名
                        .setText(R.id.tv_time, TimeUtils.getExactelyTime(bean.getCommenttime()))
                        .setText(R.id.tv_content, bean.getContent())
                        .setText(R.id.tv_institute_name, bean.getAgencyname() + "")
                        .setText(R.id.tv_institute_location, bean.getAgencyaddress() + "")
                        .addOnClickListener(R.id.layout_institution);
                break;
            //多布局样式2
            case Constants.MultiInstituteType.TYPE2://有机构回复

                //用户图片
                ImageView img_user2 = holder.getView(R.id.CommentUserImg2);
                ImageView img_institute2 = holder.getView(R.id.img_institute2);


                String path2 = "";
                if (bean.getAgency_image() != null && !bean.getAgency_image().isEmpty()) {

                    if (!bean.getAgency_image().contains("http")) {
                        path2 = Constants.DETAIL_HTTP + bean.getAgency_image();
                    } else {
                        path2 = bean.getAgency_image();
                    }
                }
                //个人图片
                Glide.with(mContext)
                        .load(R.mipmap.photo_default)
                        .error(ContextCompat.getDrawable(mContext, R.mipmap.photo_default))
                        .into(img_user2);

                Glide.with(mContext)
                        .load(path2)
                        .error(ContextCompat.getDrawable(mContext, R.mipmap.item_bg_default1))
                        .override(DpUtils.dpToPx(mContext, 66), DpUtils.dpToPx(mContext, 66))
                        .centerCrop()
                        .into(img_institute2);

                holder.setText(R.id.tv_commentUserName2, SPUtil.getUserName())//用户名
                        .setText(R.id.tv_time2, TimeUtils.getExactelyTime(bean.getCommenttime()))
                        .setText(R.id.tv_content2, bean.getContent())
                        .setText(R.id.tv_institute_name2, bean.getAgencyname() + "")
                        .setText(R.id.tv_institute_location2, bean.getAgencyaddress() + "")
                        .setText(R.id.tv_feedBack, bean.getChild().getContent())   //机构回复
                        .addOnClickListener(R.id.layout_institution2);


                break;
        }

    }

}
