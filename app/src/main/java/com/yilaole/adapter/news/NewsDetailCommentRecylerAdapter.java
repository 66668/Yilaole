package com.yilaole.adapter.news;

import android.content.Context;
import android.widget.ImageView;

import com.yilaole.R;
import com.yilaole.base.adapterbase.BaseQuickAdapter;
import com.yilaole.base.adapterbase.BaseViewHolder;
import com.yilaole.bean.news.NewsCommentBean;
import com.yilaole.utils.TimeUtils;

import java.util.List;

/**
 * 资讯详情-评论适配
 */

public class NewsDetailCommentRecylerAdapter extends BaseQuickAdapter<NewsCommentBean, BaseViewHolder> {

    private Context mContext;
    private List<NewsCommentBean> dataList;

    public NewsDetailCommentRecylerAdapter(Context context, List<NewsCommentBean> listData) {
        super(R.layout.item_newsdetail_comment, listData);
        this.mContext = context;
        this.dataList = listData;
    }

    @Override
    protected void convert(BaseViewHolder holder, NewsCommentBean bean) {

        //获取img 下载图片
        ImageView img = holder.getView(R.id.CommentUserImg);//头像


        //头像
        //        if (bean.get() == null | TextUtils.isEmpty(bean.getImg())) {
        //
        //        } else {
        //            Glide.with(mContext)
        //                    .load(bean.getImg())
        //                    .error(ContextCompat.getDrawable(mContext, R.mipmap.item_bg_default1))
        //                    .into(img);
        //        }

        //文本显示
        holder.setText(R.id.tv_commentUserName, bean.getName())//用户名
                .setText(R.id.tv_comment_userTime, TimeUtils.getExactelyTime(bean.getCreate_time()))//时间
                .setText(R.id.tv_UserComment_content, bean.getContent())
                .addOnClickListener(R.id.layout_comment);
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
