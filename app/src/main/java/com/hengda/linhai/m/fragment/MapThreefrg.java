package com.hengda.linhai.m.fragment;



import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hengda.frame.tileview.HDTileView;
import com.hengda.linhai.m.R;
import com.hengda.linhai.m.app.HdAppConfig;
import com.hengda.linhai.m.bean.Exhibition;
import com.hengda.linhai.m.db.HBriteDatabase;
import com.hengda.linhai.m.tools.BitmapUtils;
import com.hengda.linhai.m.tools.ViewUtil;
import com.hengda.linhai.m.ui.PlayActivity;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapThreefrg extends Fragment {

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

    public MapThreefrg() {
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
        addDetailLevel();
        addIconMarker(unitNo);
        return tileView;
    }

    /**
     * 添加各级瓦片+底图
     */
    private void addDetailLevel() {
        //添加各级瓦片
        tileView = new HDTileView(getActivity());
        String baseMapPath = HdAppConfig.getMapFilePath(HdAppConfig.getLanguage(), unitNo);
        tileView.init(2, 1920, 1080, baseMapPath);
        tileView.loadMapFromDisk();
        tileView.setMinimumScaleFullScreen();
        tileView.addSample(baseMapPath + "/img.png", false);

    }

    private void addIconMarker(int map_id) {
        if (!isAddIconMarker) {
            isAddIconMarker = true;
            if (iconMarkers.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                String table = HdAppConfig.getLanguage();
                sb.append("SELECT * FROM ").append(table)
                        .append(" WHERE map_id =")
                        .append(map_id);
                HBriteDatabase.getInstance()
                        .getDb()
                        .createQuery(table, sb.toString(), new String[]{})
                        .mapToList(cursor -> {
                            return Exhibition.CursorToModel(cursor);
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(exhibitions -> Observable.from(exhibitions))
                        .subscribe(exhibition -> {
                            ImageView marker = genIconMarker(exhibition);
                            tileView.setScale(1);
                            tileView.addMarker(marker, exhibition.getAxis_x(), exhibition.getAxis_y(),
                                    -0.5f, -1.0f);
                        });
            } else {
                for (ImageView marker : iconMarkers) {
                    Exhibition exhibition = (Exhibition) marker.getTag();
                    tileView.addMarker(marker, exhibition.getAxis_x(),
                            exhibition.getAxis_y(), -0.5f, -1.0f);
                }
            }
        }
    }

    public static MapThreefrg newInstance(int unitNo) {
        MapThreefrg fragment = new MapThreefrg();
        Bundle bundle = new Bundle();
        bundle.putInt("UnitNo", unitNo);
        fragment.setArguments(bundle);
        return fragment;
    }

    private ImageView genIconMarker(Exhibition exhibition) {
        ImageView marker = new ImageView(getActivity());
        marker.setImageBitmap(bitmapLoc);
        marker.setOnClickListener(view -> showPaoPao(exhibition));
        marker.setTag(exhibition);
        iconMarkers.add(marker);
        iconMarkerMap.put(exhibition.getByname(), marker);
        return marker;
    }

    /**
     * 点击Marker显示Paopao
     *
     * @param exhibition
     */
    private void showPaoPao(Exhibition exhibition) {
        if (callOut != null)
            tileView.removeCallout(callOut);

        tileView.setScale(2.0f);
        ImageView ivMarker = iconMarkerMap.get(exhibition.getByname());
        TextView tvMarker = textMarkerMap.get(exhibition.getByname());
        callOut = View.inflate(getActivity(), R.layout.layout_tile_map_pop, null);
        TextView tvExhName = ViewUtil.getView(callOut, R.id.tvExhName);
        ImageView ivExhPicPL = ViewUtil.getView(callOut, R.id.ivExhPicPL);
        tvExhName.setText(exhibition.getByname());
        path = HdAppConfig.getImgPath(exhibition.getExhibit_id()) + "map_icon.png";
        Glide.with(MapThreefrg.this).load(path)
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
                    tileView.addMarker(ivMarker, exhibition.getAxis_x(),
                            exhibition.getAxis_y(), -0.5f, -1.0f);
                }
                if (tvMarker != null && isAddTextMarker) {
                    tileView.addMarker(tvMarker, exhibition.getAxis_x(),
                            exhibition.getAxis_y(), -0.5f, 0.0f);
                }
            }
        });
        tileView.addCallout(callOut, exhibition.getAxis_x(), exhibition.getAxis_y(), -0.5f, -0.8f);
        tileView.slideToAndCenter(exhibition.getAxis_x(), exhibition.getAxis_y());
        if (this.path == null)
            RxView.clicks(callOut)
                    .throttleFirst(2, TimeUnit.SECONDS)
                    .subscribe(aVoid -> {
                        ivMarker.setImageBitmap(BitmapUtils
                                .decodeSampledBitmapFromFile(path, 78, 102));
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("exhibition", exhibition);
                        Intent intent = new Intent(getActivity(), PlayActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    });
    }

}
