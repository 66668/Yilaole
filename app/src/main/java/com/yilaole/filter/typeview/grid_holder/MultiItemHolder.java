package com.yilaole.filter.typeview.grid_holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.yilaole.R;
import com.yilaole.filter.view.FilterCheckedTextView;
import com.yilaole.utils.DpUtils;

import butterknife.ButterKnife;

/**
 *
 */
public class MultiItemHolder extends RecyclerView.ViewHolder {

    public FilterCheckedTextView textView;

    public MultiItemHolder(Context mContext, ViewGroup parent) {
        super(DpUtils.infalte(mContext, R.layout.holder_item, parent));
        textView = ButterKnife.findById(itemView, R.id.tv_item);
    }

    /**
     * tag标记的字段规则：eg:"obj_s"
     *
     * @param s
     * @param tag
     */
    public void bind(String s, Object tag) {
        textView.setText(s);
        textView.setTag(tag);
    }
}
