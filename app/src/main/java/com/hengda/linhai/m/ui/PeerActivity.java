package com.hengda.linhai.m.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.hengda.linhai.m.R;
import com.hengda.linhai.m.adapter.LCommonAdapter;
import com.hengda.linhai.m.adapter.ViewHolder;
import com.hengda.linhai.m.bean.JoinGroup;
import com.hengda.linhai.m.common.BaseActivity;
import com.hengda.linhai.m.tools.RxBus;
import com.hengda.linhai.m.tools.ViewUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PeerActivity extends BaseActivity {

    @Bind(R.id.back_group)
    ImageView backGroup;
    @Bind(R.id.lv_peer)
    ListView lvPeer;
    private JoinGroup joinGroup;
    private LCommonAdapter<JoinGroup.DataBean.InfoBean.GroupMemberBean> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peer);
        ButterKnife.bind(this);

        joinGroup = (JoinGroup) getIntent().getSerializableExtra("JoinGroup");
        lvPeer.setAdapter(adapter = new LCommonAdapter<JoinGroup.DataBean.InfoBean.GroupMemberBean>(PeerActivity.this, R.layout.item_lv_company, joinGroup.getData().getInfo().getGroup_member()) {


            @Override
            public void convert(ViewHolder holder, JoinGroup.DataBean.InfoBean.GroupMemberBean groupMemberBean) {
                if (groupMemberBean.getNick_name() != null) {
                    holder.setText(R.id.item_name_tv_comment, groupMemberBean.getNick_name());
                }
                if (groupMemberBean.getExhibitroom_name() != null) {
                    holder.setText(R.id.item_area_comment, groupMemberBean.getExhibitroom_name());
                }
                if (groupMemberBean.getHead_img() != null) {
                    Glide.with(PeerActivity.this).load(groupMemberBean.getHead_img()).placeholder(R.mipmap.default_image).into((ImageView) holder.getView(R.id.item_head_comment));
                }
            }


        });
        backGroup.setOnClickListener(view -> {
            RxBus.getDefault().post(joinGroup);
            finish();
        });

    }
}
