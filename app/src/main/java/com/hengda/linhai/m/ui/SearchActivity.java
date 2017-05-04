package com.hengda.linhai.m.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.hengda.linhai.m.R;
import com.hengda.linhai.m.app.HdAppConfig;
import com.hengda.linhai.m.bean.Exhibition;
import com.hengda.linhai.m.bean.FirstTextBean;
import com.hengda.linhai.m.bean.ImageAndTextManager;
import com.hengda.linhai.m.bean.SearchBean;
import com.hengda.linhai.m.bean.SearchContent;
import com.hengda.linhai.m.bean.TextBean;
import com.hengda.linhai.m.bean.TextViewManager;
import com.hengda.linhai.m.common.BaseActivity;
import com.hengda.linhai.m.db.HResDdUtil;
import com.hengda.linhai.m.http.RequestApi;
import com.hengda.linhai.m.tools.ViewUtil;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import simplezxing.activity.CaptureActivity;

public class SearchActivity extends BaseActivity {

    @Bind(R.id.back_search)
    ImageView backSearch;
    @Bind(R.id.rl_search)
    RecyclerView rlSearch;
    @Bind(R.id.scan_search)
    ImageView scanSearch;
    private static final int REQ_CODE_PERMISSION = 1;
    @Bind(R.id.search_img)
    ImageView searchImg;
    @Bind(R.id.edit_search)
    EditText editSearch;

    private Exhibition exhibition;
    List<Object> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        backSearch.setOnClickListener(view -> finish());
        rlSearch.setLayoutManager(new LinearLayoutManager(this));
        //初始化adapter
        BaseItemAdapter adapter = new BaseItemAdapter();
        //为XXBean数据源注册XXManager管理类
        adapter.register(TextBean.class, new TextViewManager());
        adapter.register(FirstTextBean.class, new ImageAndTextManager());

        rlSearch.setAdapter(adapter);
        RequestApi.getInstance().getSearchHistory(new Subscriber<SearchBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(SearchBean searchBean) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.add(new FirstTextBean(getString(R.string.search_history)));
                        for (int i = 0; i < searchBean.getData().getHistory_content().size(); i++) {
                            list.add(new TextBean(searchBean.getData().getHistory_content().get(i)));
                        }
                        list.add(new FirstTextBean(getString(R.string.search_hot)));
                        for (int i = 0; i < searchBean.getData().getHistory_content().size(); i++) {
                            list.add(new TextBean(searchBean.getData().getHot_search().get(i)));
                        }
                        adapter.setDataItems(list);
                    }
                });

            }
        }, HdAppConfig.getDeviceNo());
        searchImg.setOnClickListener(view -> {
            String content = editSearch.getText().toString();
            content = content.replaceAll("(\r\n|\r|\n|\n\r)", "");
            if (TextUtils.isEmpty(content)) {
                Toasty.warning(SearchActivity.this, getString(R.string.input_empty), Toast.LENGTH_SHORT).show();
            } else {
                ViewUtil.showDownloadProgressDialog(SearchActivity.this,getString(R.string.going));
                RequestApi.getInstance().getSearchContent(new Subscriber<SearchContent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SearchContent searchContent) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(SearchActivity.this, SearchDetailActivity.class);
                                intent.putExtra("searchDetail",searchContent);
                                ViewUtil.hideDownloadProgressDialog();
                                startActivity(intent);
                            }
                        });
                    }
                }, HdAppConfig.getDeviceNo(), content);
            }

        });
        scanSearch.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(SearchActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SearchActivity.this, new String[]{Manifest.permission.CAMERA}, REQ_CODE_PERMISSION);
            } else {
                //跳转至扫描页面
                startCaptureActivityForResult();
            }
        });
    }

    /**
     * 跳转至扫描页面
     */
    private void startCaptureActivityForResult() {
        Intent intent = new Intent(SearchActivity.this, CaptureActivity.class);
        startActivityForResult(intent, CaptureActivity.REQ_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CaptureActivity.REQ_CODE:
                switch (resultCode) {
                    case RESULT_OK:
                        String result = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);
                        boolean isNum = result.matches("[0-9]+");

                        if (isNum) {
                            Cursor cursor = HResDdUtil.getInstance().QueryExhibit(result);

                            if (cursor.getCount() != 0) {
                                while (cursor.moveToNext()) {
                                    exhibition = Exhibition.CursorToModel(cursor);
                                }
                                cursor.close();
                                if (exhibition != null) {
                                    Intent intent = new Intent(SearchActivity.this, PlayActivity.class);
                                    intent.putExtra("exhibition", exhibition);
                                    startActivity(intent);
                                }
                            }
                        }
                        break;
                    case RESULT_CANCELED:
                        if (data != null) {

                        }
                        break;
                }
                break;
        }
    }
}
