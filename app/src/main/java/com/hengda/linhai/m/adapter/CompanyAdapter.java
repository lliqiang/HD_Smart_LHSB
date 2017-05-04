package com.hengda.linhai.m.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hengda.linhai.m.R;
import com.hengda.linhai.m.bean.GetGroupBean;

import java.util.List;

/**
 * Created by lenovo on 2017/5/2.
 */

public class CompanyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<GetGroupBean.DataBean.GroupMemberBean> groupMemberBeanList;
    public static final int ONE_ITEM = 1;
    public static final int TWO_ITEM = 2;

    private Context context;
    private CompanyViewHolder companyViewHolder;
    private ExistViewHolder existViewHolder;
    private   OnCompanyClickLitener mOnItemClickLitener;

    public  void setOnItemClickLitener(OnCompanyClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    public CompanyAdapter(List<GetGroupBean.DataBean.GroupMemberBean> groupMemberBeanList, Context context) {
        this.groupMemberBeanList = groupMemberBeanList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lv_company, parent, false);
        companyViewHolder = new CompanyViewHolder(view);
        View view1 = LayoutInflater.from(context).inflate(R.layout.layout_btn_exist, parent, false);
        existViewHolder = new ExistViewHolder(view1);
        if (viewType == ONE_ITEM) {
            return companyViewHolder;
        } else{
            return existViewHolder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder == companyViewHolder) {
            if (groupMemberBeanList.get(position).getNick_name() != null) {
                companyViewHolder.nameTxt.setText(groupMemberBeanList.get(position).getNick_name().toString());
            } else {
                companyViewHolder.nameTxt.setText(groupMemberBeanList.get(position).getDevice_id().toString());
            }
            if (groupMemberBeanList.get(position).getExhibitroom_name() != null) {
                companyViewHolder.floorTxt.setText(groupMemberBeanList.get(position).getExhibitroom_name());
            }
            Glide.with(context).load(groupMemberBeanList.get(position).getHead_img()).placeholder(R.mipmap.def_head).into(companyViewHolder.headImg);
        }

        holder.itemView.setOnClickListener(view -> {
            mOnItemClickLitener.onItemClick(holder.itemView, position);
        });
    }

    @Override
    public int getItemCount() {
        return groupMemberBeanList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position != groupMemberBeanList.size() - 1) {
            return ONE_ITEM;
        } else {
            return TWO_ITEM;
        }
    }

    public static class CompanyViewHolder extends RecyclerView.ViewHolder {
        private ImageView headImg;
        private TextView nameTxt;
        private TextView floorTxt;
        private ImageView locImg;

        public CompanyViewHolder(View itemView) {
            super(itemView);
            headImg = (ImageView) itemView.findViewById(R.id.item_head_comment);
            nameTxt = (TextView) itemView.findViewById(R.id.item_name_tv_comment);
            floorTxt = (TextView) itemView.findViewById(R.id.item_area_comment);
            locImg = (ImageView) itemView.findViewById(R.id.location_company);
        }


    }

    public static class ExistViewHolder extends RecyclerView.ViewHolder {
        private TextView existBtn;

        public ExistViewHolder(View itemView) {
            super(itemView);
            existBtn = (TextView) itemView.findViewById(R.id.exist_login);
        }

    }

    public static interface OnCompanyClickLitener {
        void onItemClick(View view, int position);
    }

//    public void removeItem(DataModel model) {
//        int position = datas.indexOf(model);
//        datas.remove(position);
//        notifyItemRemoved(position);//Attention!
//    }
}
