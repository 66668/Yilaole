package com.yilaole.adapter.institution;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yilaole.R;
import com.yilaole.base.adapterbase.BaseQuickAdapter;
import com.yilaole.base.adapterbase.BaseViewHolder;

import java.io.File;
import java.util.List;

/**
 * 机构点评 -拍照适配
 */
public class InstitutionDetailCommentCreatePicAddRecylerAdapter extends BaseQuickAdapter<File, BaseViewHolder> {

    private Context mContext;
    private List<File> dataList;

    public InstitutionDetailCommentCreatePicAddRecylerAdapter(Context context, List<File> listData) {
        super(R.layout.item_pic_commentcreate, listData);
        this.mContext = context;
        this.dataList = listData;
    }

    @Override
    protected void convert(BaseViewHolder holder, File file) {

        //获取img 下载图片
        ImageView img = holder.getView(R.id.pic_img);//头像
        Glide.with(mContext)
                .load(file)
                .error(R.mipmap.photo_default)
                .fitCenter()
                .into(img);
    }
}
