package com.hengda.linhai.m.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.hengda.linhai.m.R;
import com.hengda.linhai.m.app.HdAppConfig;
import com.hengda.linhai.m.bean.MapModel;


/**
 * @author free46000  2017/03/17
 * @version v1.0
 */
public class ImageAndTextManager extends BaseViewHolderManager<MapModel> {
private Context mContext;

    public ImageAndTextManager(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, @NonNull MapModel mapModel) {
       TextView name=getView(holder,R.id.tv_viewpgeritem);
        ImageView imageView=getView(holder,R.id.img_viewpageritem);
        name.setText(mapModel.getName());
        Glide.with(mContext).load(HdAppConfig.getMapFilePath(HdAppConfig.getLanguage(), Integer.parseInt(mapModel.getMap_id())) + "/" + "list.png").into(imageView);

    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_home_viewpager;
    }
}
