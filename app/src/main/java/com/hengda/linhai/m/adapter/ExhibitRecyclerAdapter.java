package com.hengda.linhai.m.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hengda.linhai.m.R;
import com.hengda.linhai.m.app.HdApplication;
import com.hengda.linhai.m.bean.ExhibitListBean;

import java.util.List;

/**
 * Created by lenovo on 2017/4/25.
 */

public class ExhibitRecyclerAdapter extends RecyclerView.Adapter<ExhibitRecyclerAdapter.ExhibitViewHolder> {
    private List<ExhibitListBean> data;
    private Context context;
    private OnItemClickLitener mOnItemClickLitener;

    public ExhibitRecyclerAdapter(List<ExhibitListBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ExhibitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_viewpager, parent, false);
        ExhibitViewHolder vh = new ExhibitViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ExhibitViewHolder holder, int position) {
        Glide.with(context).load(data.get(position).getList_img_path()).into(holder.imageView);
        if (data.get(position).getExhibitroom_name() != null) {

            holder.textView.setText(data.get(position).getExhibitroom_name().toString());
        }
        ((ExhibitViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickLitener.onItemClick(((ExhibitViewHolder) holder).itemView, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ExhibitViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        private TextView textView;

        public ExhibitViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img_viewpageritem);
            textView = (TextView) itemView.findViewById(R.id.tv_viewpgeritem);
            textView.setTypeface(HdApplication.typeface);
        }


    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
