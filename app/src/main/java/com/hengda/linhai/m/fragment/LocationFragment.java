package com.hengda.linhai.m.fragment;


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
import com.hengda.linhai.m.tools.ViewUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment {
    HDTileView tileView;
    Exhibition exhibition;
    View callOut;
    private String path;
    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exhibition = (Exhibition) getArguments().get("exhibition");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        addDetailLevel();
        callOut = View.inflate(getActivity(), R.layout.layout_tile_map_pop, null);
        TextView tvExhName = ViewUtil.getView(callOut, R.id.tvExhName);
        ImageView ivExhPicPL = ViewUtil.getView(callOut, R.id.ivExhPicPL);
        tvExhName.setText(exhibition.getByname());
        path = HdAppConfig.getImgPath(exhibition.getExhibit_id()) + "map_icon.png";
        Glide.with(LocationFragment.this).load(path)
                .override(353, 446).into(ivExhPicPL);
        tileView.addMarker(callOut,exhibition.getAxis_x(),exhibition.getAxis_y(),0.5f,0.5f);
        return tileView;
    }
    /**
     * 添加各级瓦片+底图
     */
    private void addDetailLevel() {
        //添加各级瓦片
        tileView = new HDTileView(getActivity());
        String baseMapPath = HdAppConfig.getMapFilePath(HdAppConfig.getLanguage(), exhibition.getMap_id());
        tileView.init(2, 1920, 1080, baseMapPath);
        tileView.loadMapFromDisk();
        tileView.setMinimumScaleFullScreen();
        tileView.addSample(baseMapPath + "/img.png", false);
    }
    public static LocationFragment newInstance(Exhibition exhibition) {
        LocationFragment fragment = new LocationFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("exhibition", exhibition);
        fragment.setArguments(bundle);
        return fragment;
    }
}
