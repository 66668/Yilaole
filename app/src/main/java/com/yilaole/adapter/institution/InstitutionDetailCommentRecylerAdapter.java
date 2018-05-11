package com.yilaole.adapter.institution;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.yilaole.R;
import com.yilaole.base.adapterbase.BaseQuickAdapter;
import com.yilaole.base.adapterbase.BaseViewHolder;
import com.yilaole.bean.institution.detail.DetailCommentBean;
import com.yilaole.utils.TimeUtils;

import java.util.List;

/**
 * 机构详情-评论适配
 * 多布局样式
 */
// TODO: 2017/11/14  多布局样式
public class InstitutionDetailCommentRecylerAdapter extends BaseQuickAdapter<DetailCommentBean, BaseViewHolder> {

    private Context mContext;
    private List<DetailCommentBean> dataList;

    public InstitutionDetailCommentRecylerAdapter(Context context, List<DetailCommentBean> listData) {
        super(R.layout.item_institutedetail_comment, listData);
        this.mContext = context;
        this.dataList = listData;
    }

    @Override
    protected void convert(BaseViewHolder holder, DetailCommentBean bean) {

        //获取img 下载图片
        ImageView img = holder.getView(R.id.CommentUserImg);//头像

        RatingBar bar1 = holder.getView(R.id.institute_ratingBar);
        RatingBar bar2 = holder.getView(R.id.service_ratingBar);

        //评分
        if (bean.getCommentscore() != null | !TextUtils.isEmpty(bean.getCommentscore())) {

            bar1.setRating((float) Double.parseDouble(bean.getCommentscore()));
        } else {
            bar1.setRating(0f);
        }

        if (bean.getServicescore() != null | !TextUtils.isEmpty(bean.getServicescore())) {

            bar2.setRating((float) Double.parseDouble(bean.getServicescore()));
        } else {
            bar2.setRating(0f);
        }


        //头像
        if (bean.getImg() == null | TextUtils.isEmpty(bean.getImg())) {

        } else {
            Glide.with(mContext)
                    .load(bean.getImg())
                    .error(ContextCompat.getDrawable(mContext, R.mipmap.item_bg_default1))
                    .into(img);
        }

        //文本显示
        holder.setText(R.id.tv_commentUserName, bean.getName())//用户名
                .setText(R.id.tv_comment_userTime, TimeUtils.getExactelyTime(bean.getCommenttime()))//时间
                .setText(R.id.tv_UserComment_content, bean.getContent());

        //                .addOnClickListener(R.id.img_photo)
        //                .addOnClickListener(R.id.toCollect)
        //                .addOnClickListener(R.id.layout_home_institution);


    }
    /**
     *======================================================================================
     *==========================================为标准recyclerView适配步骤============================================
     *======================================================================================
     */

    //    @Override
    //    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    //
    //        if (mContext == null) {
    //            mContext = parent.getContext();
    //        }
    //        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_institution, parent, false);
    //        //        ButterKnife.bind(this, view);
    //        return new ViewHolder(view);
    //    }
    //
    //    @Override
    //    public void onBindViewHolder(ViewHolder holder, int position) {
    //        HomeTabDataBean bean = dataList.get(position);
    //        holder.textView.setText(bean.getName());
    //
    //        Glide.with(mContext).load(bean.getImage_logo()).into(holder.img_photo);
    //
    //    }
    //
    //    @Override
    //    public int getItemCount() {
    //        return dataList.size();
    //    }
    //
    //    static class ViewHolder extends RecyclerView.ViewHolder {
    //
    //        RelativeLayout layout_home_institution;
    //        ImageView img_photo;
    //        ImageView toCollect;
    //        TextView tv_institution_title;
    //        TextView tv_settled;
    //        TextView tv_office;
    //        TextView tv_grade;
    //        TextView tv_place;
    //        TextView tv_oldPrice;
    //        TextView tv_newPrice;
    //        FrameLayout frameLayout;
    //
    //        public ViewHolder(View itemView) {
    //            super(itemView);
    //            layout_home_institution = (RelativeLayout) itemView.findViewById(R.id.layout_home_institution);
    //            img_photo = (ImageView) itemView.findViewById(R.id.img_photo);
    //            tv_institution_title = (TextView) itemView.findViewById(R.id.tv_institution_title);
    //            tv_settled = (TextView) itemView.findViewById(R.id.tv_settled);
    //            tv_office = (TextView) itemView.findViewById(R.id.tv_office);
    //            tv_grade = (TextView) itemView.findViewById(R.id.tv_grade);
    //            tv_place = (TextView) itemView.findViewById(R.id.tv_place);
    //            toCollect = (ImageView) itemView.findViewById(R.id.toCollect);
    //            tv_oldPrice = (TextView) itemView.findViewById(R.id.tv_oldPrice);
    //            tv_newPrice = (TextView) itemView.findViewById(R.id.tv_newPrice);
    //        }
    //
    //
    //    }
}
