package com.hengda.linhai.m.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.listener.OnItemClickListener;
import com.hengda.linhai.m.R;
import com.hengda.linhai.m.adapter.ExhibitRecyclerAdapter;
import com.hengda.linhai.m.adapter.ImageAndTextManager;
import com.hengda.linhai.m.app.HdAppConfig;
import com.hengda.linhai.m.bean.ExhibitListBean;
import com.hengda.linhai.m.bean.MapModel;
import com.hengda.linhai.m.db.HBriteDatabase;
import com.hengda.linhai.m.http.RequestApi;
import com.hengda.linhai.m.tools.DebugUtil;
import com.hengda.linhai.m.tools.ViewUtil;
import com.hengda.linhai.m.ui.ExhibitActivity;
import com.hengda.linhai.m.ui.GuideActivity;
import com.hengda.linhai.m.ui.MessageActicity;
import com.hengda.linhai.m.ui.SearchActivity;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    @Bind(R.id.img_edit)
    ImageView imgEdit;
    @Bind(R.id.line_main)
    View lineMain;
    @Bind(R.id.img_message_main)
    ImageView imgMessageMain;
    @Bind(R.id.discretee_scroll)
    DiscreteScrollView discreteeScroll;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    @Bind(R.id.search_homeFragment)
    ImageView searchHomeFragment;
    private List<MapModel> modelList;
    private List<ExhibitListBean> listBeanList;
    private BaseItemAdapter adapter;
    private ExhibitRecyclerAdapter exhibitRecyclerAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        listBeanList = new ArrayList<>();
        modelList = new ArrayList<>();
        exhibitRecyclerAdapter = new ExhibitRecyclerAdapter(listBeanList, getActivity());

//        discreteeScroll.setLayoutManager(new LinearLayoutManager(getActivity()));
        discreteeScroll.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());

        imgEdit.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), GuideActivity.class);
            startActivity(intent);
        });
        searchHomeFragment.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });
        imgMessageMain.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), MessageActicity.class);
            startActivity(intent);
        });
        ViewUtil.showDownloadProgressDialog(getActivity(),getString(R.string.going));
        getExhbitInfo();
        exhibitRecyclerAdapter.setOnItemClickLitener(new ExhibitRecyclerAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(),ExhibitActivity.class);
                intent.putExtra("exhibitroom_id",listBeanList.get(position).getExhibitroom_id());
                startActivity(intent);
            }
        });

        return view;
    }

    private void getExhbitInfo() {
        RequestApi.getInstance().getExhibitInfo(new Subscriber<List<ExhibitListBean>>() {
            @Override
            public void onCompleted() {
                ViewUtil.hideDownloadProgressDialog();
                if (exhibitRecyclerAdapter!=null){
                    discreteeScroll.setAdapter(exhibitRecyclerAdapter);
                    exhibitRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable e) {
                DebugUtil.debug("Throwable", e.getMessage());
                ViewUtil.hideDownloadProgressDialog();
            }

            @Override
            public void onNext(List<ExhibitListBean> exhibitListBeen) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listBeanList.addAll(exhibitListBeen);


                    }
                });
            }
        }, HdAppConfig.getLanguage());
    }

    public void QueryMapInfo(int language) {

/*
* String sql = String.format("SELECT * FROM %s WHERE language = %d",
                "mapmodel", langugage);
* */
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ").append("mapmodel")
                .append(" WHERE language =1");
        HBriteDatabase.getInstance()
                .getDb()
                .createQuery("mapmodel", sb.toString(), new String[]{})
                .mapToList(new Func1<Cursor, MapModel>() {
                    @Override
                    public MapModel call(Cursor cursor) {
                        return MapModel.CursorToMap(cursor);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<List<MapModel>, Observable<MapModel>>() {
                    @Override
                    public Observable<MapModel> call(List<MapModel> mapModelList) {
                        return Observable.from(mapModelList);
                    }
                }).subscribe(new Action1<MapModel>() {
            @Override
            public void call(MapModel mapModel) {


            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                DebugUtil.debug("Throwable", "throwable------------------------" + throwable);
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }
}
