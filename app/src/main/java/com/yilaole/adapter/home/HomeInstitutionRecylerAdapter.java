package com.yilaole.adapter.home;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yilaole.R;
import com.yilaole.base.adapterbase.BaseViewHolder;
import com.yilaole.base.adapterbase.BaseiItemMultiQuickAdapter;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.home.HomeInstituteItemBean;

import java.util.List;

import static com.yilaole.R.id.img_photo;

/**
 * 首页--嵌套fragment，fragment中recyclerView的适配
 * <p>
 * <p>
 * 多布局item显示：
 */

public class HomeInstitutionRecylerAdapter extends BaseiItemMultiQuickAdapter<HomeInstituteItemBean, BaseViewHolder> {

    private Context mContext;
    private List<HomeInstituteItemBean> dataList;

    public HomeInstitutionRecylerAdapter(Context context, List<HomeInstituteItemBean> listData) {
        super(listData);
        this.mContext = context;
        this.dataList = listData;
        addItemType(Constants.MultiInstituteType.TYPE1, R.layout.item_home_institution);
        addItemType(Constants.MultiInstituteType.TYPE2, R.layout.item_home_institution_multi);
    }

    @Override
    protected void convert(BaseViewHolder holder, HomeInstituteItemBean bean) {
        //添加不同布局
        switch (holder.getItemViewType()) {
            case Constants.MultiInstituteType.TYPE1://

                //logo图片
                ImageView img_photo0 = holder.getView(img_photo);
                String img_photoPath0 = "";
                if (bean.getImage_logo() == null || bean.getImage_logo().isEmpty()) {
                    img_photoPath0 = "";
                } else {
                    if (!(bean.getImage_logo()).contains("http")) {
                        img_photoPath0 = Constants.DETAIL_HTTP + bean.getImage_logo();
                    } else {
                        img_photoPath0 = bean.getImage_logo();
                    }
                }


                Glide.with(mContext)
                        .load(img_photoPath0)
//                        .override(DpUtils.dpToPx(mContext, 78), DpUtils.dpToPx(mContext, 92))
                        .error(ContextCompat.getDrawable(mContext, R.mipmap.item_bg_default1))
                        .placeholder(ContextCompat.getDrawable(mContext, R.mipmap.item_bg_default1))
                        //                        .fitCenter()
                        .centerCrop()
                        .into(img_photo0);


                //文本显示+监听回调
                TextView tv_settled0 = holder.getView(R.id.tv_settled0);//是否入驻
                TextView tv_office0 = holder.getView(R.id.tv_office0);//公办
                TextView tv_grade0 = holder.getView(R.id.tv_grade0);//评分
                TextView tv_oldPrice0 = holder.getView(R.id.tv_oldPrice0);//

                tv_oldPrice0.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                //评分
                tv_grade0.setText(bean.getNumber() + "分");

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


                holder.setText(R.id.tv_institution_title0, bean.getName())//标题
                        .setText(R.id.tv_newPrice0, "￥" + bean.getShop_price() + "起")
                        .setText(R.id.tv_oldPrice0, bean.getMarket_pric())
                        .setText(R.id.tv_hotNumber0, bean.getPopularity())
                        .setText(R.id.tv_place0, "地址：" + bean.getAddress())//地址
                        .addOnClickListener(R.id.layout_home_institution0);

                break;

            case Constants.MultiInstituteType.TYPE2:
                //获取img 下载图片
                ImageView img_photo = holder.getView(R.id.img_photo);
                //图片显示
                String img_photoPath = "";

                if (bean.getImage_logo() == null || bean.getImage_logo().isEmpty()) {
                    img_photoPath = "";
                } else {
                    if (!(bean.getImage_logo()).contains("http")) {
                        img_photoPath = Constants.DETAIL_HTTP + bean.getImage_logo();
                    } else {
                        img_photoPath = bean.getImage_logo();
                    }
                }
                //logo图片
                Glide.with(mContext)
                        .load(img_photoPath)
//                        .override(DpUtils.dpToPx(mContext, 78), DpUtils.dpToPx(mContext, 92))
                        .error(ContextCompat.getDrawable(mContext, R.mipmap.item_bg_default1))
                        .placeholder(ContextCompat.getDrawable(mContext, R.mipmap.item_bg_default1))
                        //                        .fitCenter()
                        .centerCrop()
                        .into(img_photo);


                TextView tv_settled = holder.getView(R.id.tv_settled);//是否入驻
                TextView tv_office = holder.getView(R.id.tv_office);//公办
                TextView tv_grade = holder.getView(R.id.tv_grade);//评分
                TextView tv_oldPrice = holder.getView(R.id.tv_oldPrice);

                tv_oldPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

                //评分
                tv_grade.setText(bean.getNumber() + "分");

                //公办 民办
                if (bean.getProperty() == 1) {
                    tv_office.setText(mContext.getResources().getString(R.string.instite_detail_civilianRun));
                } else {
                    tv_office.setText(mContext.getResources().getString(R.string.instite_detail_StateRun));

                }

                //入驻0 1 2
                if (bean.getStatus() == 0) {//未入驻
                    tv_settled.setText(mContext.getResources().getString(R.string.instite_detail_noEntering));
                } else if (bean.getStatus() == 1) {//已认领
                    tv_settled.setText(mContext.getResources().getString(R.string.instite_detail_entering));
                } else {//已入驻
                    tv_settled.setText(mContext.getResources().getString(R.string.instite_detail_entered));
                }

                //文本显示+监听回调
                holder.setText(R.id.tv_institution_title, bean.getName())//标题
                        .setText(R.id.tv_newPrice, "￥" + bean.getShop_price() + "起")
                        .setText(R.id.tv_oldPrice, bean.getMarket_pric())
                        .setText(R.id.tv_hotNumber, bean.getPopularity())
                        .setText(R.id.tv_place, "地址：" + bean.getAddress())//地址
                        .addOnClickListener(R.id.layout_home_institution);


                /**
                 * ========================================================================================
                 * ========================================================================================
                 * ========================================================================================
                 */
                ImageView img1 = holder.getView(R.id.child1_img);
                ImageView img2 = holder.getView(R.id.child2_img);

                String path1 = "";
                if (bean.getChild().get(0).getImage_logo() == null || bean.getChild().get(0).getImage_logo().isEmpty()) {
                    path1 = "";
                } else {
                    if (!(bean.getImage_logo()).contains("http")) {
                        path1 = Constants.DETAIL_HTTP + bean.getChild().get(0).getImage_logo();
                    } else {
                        path1 = bean.getChild().get(0).getImage_logo();
                    }
                }

                String path2 = "";
                if (bean.getChild().get(1).getImage_logo() == null || bean.getChild().get(1).getImage_logo().isEmpty()) {
                    path2 = "";
                } else {
                    if (!(bean.getChild().get(1).getImage_logo()).contains("http")) {
                        path2 = Constants.DETAIL_HTTP + bean.getChild().get(1).getImage_logo();
                    } else {
                        path2 = bean.getChild().get(1).getImage_logo();
                    }
                }
                //logo图片子1
                Glide.with(mContext)
                        .load(path1)
//                        .override(DpUtils.dpToPx(mContext, 160), DpUtils.dpToPx(mContext, 90))
                        .error(ContextCompat.getDrawable(mContext, R.mipmap.news_bg_default))
                        .placeholder(ContextCompat.getDrawable(mContext, R.mipmap.news_bg_default))
                        //                        .fitCenter()
                        .centerCrop()
                        .into(img1);


                //logo图片子2
                Glide.with(mContext)
                        .load(path2)
//                        .override(DpUtils.dpToPx(mContext, 160), DpUtils.dpToPx(mContext, 90))
                        .error(ContextCompat.getDrawable(mContext, R.mipmap.news_bg_default))
                        .placeholder(ContextCompat.getDrawable(mContext, R.mipmap.news_bg_default))
                        //                        .fitCenter()
                        .centerCrop()
                        .into(img2);


                //文本显示
                holder
                        .setText(R.id.child1_newPrice, "￥" + bean.getChild().get(0).getShop_price() + "起")
                        .setText(R.id.child2_newPrice, "￥" + bean.getChild().get(1).getShop_price() + "起")
                        .setText(R.id.child1_title, bean.getChild().get(0).getName())
                        .setText(R.id.child2_title, bean.getChild().get(1).getName())
                        .addOnClickListener(R.id.layout_child1)
                        .addOnClickListener(R.id.layout_child2);
                break;

        }


    }

    /**
     * ======================================================================================
     * ==========================================标准recyclerView适配步骤============================================
     * ======================================================================================
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
