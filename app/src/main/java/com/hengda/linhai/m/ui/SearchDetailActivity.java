package com.hengda.linhai.m.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.hengda.linhai.m.R;
import com.hengda.linhai.m.adapter.LCommonAdapter;
import com.hengda.linhai.m.adapter.ViewHolder;
import com.hengda.linhai.m.bean.SearchContent;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchDetailActivity extends Activity {

    @Bind(R.id.back_searchDetail)
    ImageView backSearchDetail;
    @Bind(R.id.lv_search_detail)
    ListView lvSearchDetail;
    private LCommonAdapter<SearchContent.DataBean> adapter;
    private SearchContent searchContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
        ButterKnife.bind(this);
        backSearchDetail.setOnClickListener(view -> finish());
        searchContent = (SearchContent) getIntent().getSerializableExtra("searchDetail");
        lvSearchDetail.setAdapter(adapter = new LCommonAdapter<SearchContent.DataBean>(this, R.layout.item_history, searchContent.getData()) {

            @Override
            public void convert(ViewHolder holder, SearchContent.DataBean dataBean) {
                holder.setText(R.id.item_title_history, dataBean.getTitle());
                Glide.with(SearchDetailActivity.this).load(dataBean.getList_img_path()).placeholder(R.mipmap.listdef).into((ImageView) holder.getView(R.id.img_item_history));
                lvSearchDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent=new Intent(SearchDetailActivity.this,PlayActivity.class);
                        intent.putExtra("exhibit_id",dataBean.getExhibit_id());
                        startActivity(intent);
                    }
                });
            }
        });
        adapter.notifyDataSetChanged();

    }
}
