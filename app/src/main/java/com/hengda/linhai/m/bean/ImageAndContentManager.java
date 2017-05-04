package com.hengda.linhai.m.bean;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.hengda.linhai.m.R;


/**
 * @author free46000  2017/03/17
 * @version v1.0
 */
public class ImageAndContentManager extends BaseViewHolderManager<FirstTextBean> {


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, @NonNull FirstTextBean textBean) {
        TextView textView = getView(holder, R.id.content_search_first);
        textView.setText(textBean.getText());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_search;
    }
}
