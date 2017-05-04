package com.hengda.linhai.m.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hengda.frame.hdplayer.HDExoPlayer;
import com.hengda.frame.tileview.HDTileView;
import com.hengda.linhai.m.R;
import com.hengda.linhai.m.app.HdAppConfig;
import com.hengda.linhai.m.bean.Exhibition;
import com.hengda.linhai.m.bean.FloorBean;
import com.hengda.linhai.m.db.HBriteDatabase;
import com.hengda.linhai.m.http.RequestApi;
import com.hengda.linhai.m.tools.BitmapUtils;
import com.hengda.linhai.m.tools.DebugUtil;
import com.hengda.linhai.m.tools.ViewUtil;
import com.hengda.linhai.m.ui.PlayActivity;
import com.jakewharton.rxbinding.view.RxView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;



/**
 * A simple {@link Fragment} subclass.
 */
public class Mapfrg extends Fragment {

    HDTileView tileView;
    Bitmap bitmapLoc;
    private boolean isAddIconMarker;
    List<ImageView> iconMarkers = new ArrayList<>();
    Map<String, ImageView> iconMarkerMap = new HashMap<>();
    Map<String, TextView> textMarkerMap = new HashMap<>();
    View callOut;
    private String path;
    private boolean isAddTextMarker;
    int unitNo;
private FloorBean tileBean;
    public Mapfrg() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unitNo = (int) getArguments().get("UnitNo");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bitmapLoc = BitmapUtils.decodeSampledBitmapFromResource(getResources(),
                R.mipmap.icon_loc_single, 72, 72);
        ViewUtil.showDownloadProgressDialog(getActivity(), "加载中...");
        RequestApi.getInstance().getMapInfo(new Subscriber<FloorBean>() {
            @Override
            public void onCompleted() {
                ViewUtil.hideDownloadProgressDialog();
                initTileView(tileBean);
              Log.i("mapInfo","mapInfo:---------------"+tileBean.getData().getMap_info());
            }

            @Override
            public void onError(Throwable e) {
                DebugUtil.debug("e", e.getMessage());
                ViewUtil.hideDownloadProgressDialog();
            }

            @Override
            public void onNext(FloorBean floorBean) {
                tileBean=floorBean;
            }
//                        if (!isAddIconMarker) {
//                            isAddIconMarker = true;
//                            if (iconMarkers.isEmpty()) {
//                                for (int i = 0; i < floorBean.getData().getExhibit_info().size(); i++) {
//
//                                    ImageView marker = genIconMarker(floorBean.getData().getExhibit_info().get(i));
//                                    tileView.addMarker(marker, Float.parseFloat(floorBean.getData().getExhibit_info().get(i).getAxis_x()), Float.parseFloat(floorBean.getData().getExhibit_info().get(i).getAxis_y()),
//                                            -0.5f, -1.0f);
//                                }
//                                tileView.setScale(1);
//                            } else {
//                                for (ImageView marker : iconMarkers) {
//                                    FloorBean.DataBean.ExhibitInfoBean exhibition = (FloorBean.DataBean.ExhibitInfoBean) marker.getTag();
//                                    tileView.addMarker(marker, Float.parseFloat(exhibition.getAxis_x()),
//                                            Float.parseFloat(exhibition.getAxis_y()), -0.5f, -1.0f);
//                                }
//                            }
//                        }
//                    }
//                });
//            }
        }, String.valueOf(unitNo), HdAppConfig.getLanguage(), "1");

        return tileView;
    }

    private void initTileView(FloorBean floorBean) {
        tileView = new HDTileView(getActivity());
        String baseMapPath = floorBean.getData().getMap_info().getSvg_map();
        Log.i("11", "initTileView: "+baseMapPath );
        baseMapPath="http://192.168.10.158/lhbwg/resource/floormap/2/map";

        tileView.init(2,3276, 1800, baseMapPath);
        tileView.loadMapFromHttpUsePicasso();
        tileView.setMinimumScaleFullScreen();
        tileView.addSample(baseMapPath + "img.png", true);
    }



//    private void addIconMarker(int map_id) {
//        if (!isAddIconMarker) {
//            isAddIconMarker = true;
//            if (iconMarkers.isEmpty()) {
//                StringBuilder sb = new StringBuilder();
//                String table = HdAppConfig.getLanguage();
//                sb.append("SELECT * FROM ").append(table)
//                        .append(" WHERE map_id =")
//                        .append(map_id);
//                HBriteDatabase.getInstance()
//                        .getDb()
//                        .createQuery(table, sb.toString(), new String[]{})
//                        .mapToList(new Func1<Cursor, Exhibition>() {
//                            @Override
//                            public Exhibition call(Cursor cursor) {
//                                return Exhibition.CursorToModel(cursor);
//                            }
//                        })
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .flatMap(exhibitions -> Observable.from(exhibitions))
//                        .subscribe(exhibition -> {
//                            ImageView marker = genIconMarker(exhibition);
//                            tileView.setScale(1);
//                            tileView.addMarker(marker, exhibition.getAxis_x(), exhibition.getAxis_y(),
//                                    -0.5f, -1.0f);
//                        });
//            } else {
//                for (ImageView marker : iconMarkers) {
//                    Exhibition exhibition = (Exhibition) marker.getTag();
//                    tileView.addMarker(marker, exhibition.getAxis_x(),
//                            exhibition.getAxis_y(), -0.5f, -1.0f);
//                }
//            }
//        }
//    }

    public static Mapfrg newInstance(int unitNo) {
        Mapfrg fragment = new Mapfrg();
        Bundle bundle = new Bundle();
        bundle.putInt("UnitNo", unitNo);
        fragment.setArguments(bundle);
        return fragment;
    }

    private ImageView genIconMarker(FloorBean.DataBean.ExhibitInfoBean exhibition) {
        ImageView marker = new ImageView(getActivity());
        marker.setImageBitmap(bitmapLoc);
        marker.setOnClickListener(view -> showPaoPao(exhibition));
        marker.setTag(exhibition);
        iconMarkers.add(marker);
        iconMarkerMap.put(exhibition.getTitle(), marker);
        return marker;
    }

    /**
     * 点击Marker显示Paopao
     *
     * @param exhibition
     */
    private void showPaoPao(FloorBean.DataBean.ExhibitInfoBean exhibition) {
        if (callOut != null)
            tileView.removeCallout(callOut);

        tileView.setScale(2.0f);
        ImageView ivMarker = iconMarkerMap.get(exhibition.getTitle());
        TextView tvMarker = textMarkerMap.get(exhibition.getTitle());
        callOut = View.inflate(getActivity(), R.layout.layout_tile_map_pop, null);
        TextView tvExhName = ViewUtil.getView(callOut, R.id.tvExhName);
        ImageView ivExhPicPL = ViewUtil.getView(callOut, R.id.ivExhPicPL);
        tvExhName.setText(exhibition.getTitle());
        Glide.with(Mapfrg.this).load(exhibition.getPoi_img())
                .override(353, 446).into(ivExhPicPL);
        callOut.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                if (ivMarker != null && isAddIconMarker) {
                    tileView.removeMarker(ivMarker);
                }
            }

            @Override
            public void onViewDetachedFromWindow(View v) {

                if (ivMarker != null && isAddIconMarker) {
                    tileView.addMarker(ivMarker, Float.parseFloat(exhibition.getAxis_x()), Float.parseFloat(exhibition.getAxis_y()), -0.5f, -1.0f);
                }
                if (tvMarker != null && isAddTextMarker) {
                    tileView.addMarker(tvMarker, Float.parseFloat(exhibition.getAxis_x()),
                            Float.parseFloat(exhibition.getAxis_y()), -0.5f, 0.0f);
                }
            }
        });
        tileView.addCallout(callOut, Float.parseFloat(exhibition.getAxis_x()), Float.parseFloat(exhibition.getAxis_y()), -0.5f, -0.8f);
        tileView.slideToAndCenter(Float.parseFloat(exhibition.getAxis_x()), Float.parseFloat(exhibition.getAxis_y()));
        RxView.clicks(callOut)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    Toast.makeText(getActivity(), "点击了", Toast.LENGTH_SHORT).show();
                    ivMarker.setImageBitmap(BitmapUtils
                            .decodeSampledBitmapFromFile(path, 78, 102));
                    Intent intent = new Intent(getActivity(), PlayActivity.class);
                    startActivity(intent);
                });
    }

}
