package com.hengda.linhai.m.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hengda.linhai.m.R;
import com.hengda.linhai.m.adapter.LCommonAdapter;
import com.hengda.linhai.m.adapter.ViewHolder;
import com.hengda.linhai.m.app.HdAppConfig;
import com.hengda.linhai.m.bean.Exhibition;
import com.hengda.linhai.m.bean.MapModel;
import com.hengda.linhai.m.common.BaseActivity;
import com.hengda.linhai.m.db.HBriteDatabase;
import com.hengda.linhai.m.http.ListBean;
import com.hengda.linhai.m.http.RequestApi;
import com.hengda.linhai.m.tools.DebugUtil;
import com.hengda.linhai.m.tools.ToastUtil;
import com.hengda.linhai.m.tools.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class HistoryActivity extends BaseActivity {

    @Bind(R.id.back_history)
    ImageView backHistory;
    @Bind(R.id.title_history)
    TextView titleHistory;
    @Bind(R.id.search_history)
    ImageView searchHistory;
    @Bind(R.id.lv_history)
    ListView lvHistory;
    private MapModel mapModel;
    private LCommonAdapter<ListBean.DataBean> adapter;
    private List<Exhibition> exhibitionList;


    private List<ListBean.DataBean> listBeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        exhibitionList = new ArrayList<>();
        listBeen = new ArrayList<>();
        ViewUtil.showDownloadProgressDialog(this,"加载中...");
        RequestApi.getInstance().getListInfo(new Subscriber<List<ListBean.DataBean>>() {
            @Override
            public void onCompleted() {
                ViewUtil.hideDownloadProgressDialog();
                lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(HistoryActivity.this, PlayActivity.class);
                        if (listBeen.size()>0){

                            intent.putExtra("exhibit_id", listBeen.get(i).getExihibit_id());
                            startActivity(intent);
                        }
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                Log.i("e", e.getMessage());
                ViewUtil.hideDownloadProgressDialog();
            }

            @Override
            public void onNext(List<ListBean.DataBean> dataBeen) {
                listBeen.addAll(dataBeen);
                HistoryActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new LCommonAdapter<ListBean.DataBean>(HistoryActivity.this, R.layout.item_history, dataBeen) {
                            @Override
                            public void convert(ViewHolder holder, ListBean.DataBean dataBean) {
                                if (dataBean.getTitle()!=null){

                                    holder.setText(R.id.item_title_history, dataBean.getTitle().toString());
                                }if (dataBean.getList_img_path()!=null){

                                    Glide.with(HistoryActivity.this).load(dataBean.getList_img_path()).into((ImageView) holder.getView(R.id.img_item_history));
                                }
                            }
                        };
                        lvHistory.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                    }
                });


            }
        }, "0001", HdAppConfig.getLanguage());


        backHistory.setOnClickListener(view -> finish());
//        mapModel = (MapModel) getIntent().getSerializableExtra("mapmodel");
//        titleHistory.setText(mapModel.getName());
//        initData();
        searchHistory.setOnClickListener(view -> {
            openActivity(this, SearchActivity.class);
        });

    }

    private void initData() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ").append(HdAppConfig.getLanguage())
                .append(" WHERE map_id =")
                .append(mapModel.getMap_id());
        HBriteDatabase.getInstance()
                .getDb()
                .createQuery(HdAppConfig.getLanguage(), sb.toString(), new String[]{})
                .mapToList(new Func1<Cursor, Exhibition>() {
                    @Override
                    public Exhibition call(Cursor cursor) {
                        return Exhibition.CursorToModel(cursor);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Exhibition>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Exhibition> exhibitions) {
                        exhibitionList.addAll(exhibitions);
                        HistoryActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                lvHistory.setAdapter(adapter = new LCommonAdapter<Exhibition>(HistoryActivity.this, R.layout.item_history, exhibitionList) {
//                                    @Override
//                                    public void convert(ViewHolder holder, Exhibition exhibition) {
//                                        holder.setText(R.id.item_title_history, exhibition.getByname());
//                                        Glide.with(HistoryActivity.this).load(HdAppConfig.getImgPath(exhibition.getExhibit_id()) + "img0.png").into((ImageView) holder.getView(R.id.img_item_history));
//                                    }
//                                });
//                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                });

    }
}
