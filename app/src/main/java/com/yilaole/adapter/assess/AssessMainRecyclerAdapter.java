package com.yilaole.adapter.assess;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yilaole.R;
import com.yilaole.base.adapterbase.BaseQuickAdapter;
import com.yilaole.base.adapterbase.BaseViewHolder;
import com.yilaole.bean.assess.AssessOldBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 在线评估 recyclerView的适配
 * <p>
 * 单类型BaseQuickAdapter使用
 */

public class AssessMainRecyclerAdapter extends BaseQuickAdapter<AssessOldBean, BaseViewHolder>  {
    private List<AssessOldBean> list = new ArrayList<>();
    Context context;

    public AssessMainRecyclerAdapter(Context context, List<AssessOldBean> list) {
        super(R.layout.item_assessmain, list);
        this.context = context;
        this.list = list;
    }

    @Override
    protected void convert(BaseViewHolder holder, AssessOldBean bean) {

        //获取img 下载图片
        ImageView img = holder.getView(R.id.img_assess);
        Glide.with(context)
                .load(bean.getImgPath())
                .error(ContextCompat.getDrawable(context,R.mipmap.photo_default))
                .into(img);

        holder.setText(R.id.tv_assess_name, bean.getName())
                .setText(R.id.tv_birth, bean.getBirhday())
                .addOnClickListener(R.id.img_assess)
                .addOnClickListener(R.id.tv_assess_online);

    }


}
