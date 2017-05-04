package com.hengda.linhai.m.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hengda.linhai.m.R;
import com.hengda.linhai.m.app.HdAppConfig;
import com.hengda.linhai.m.bean.Exhibition;
import com.hengda.linhai.m.bean.RxBusBaseMessage;
import com.hengda.linhai.m.db.HBriteDatabase;
import com.hengda.linhai.m.tools.RxBus;
import com.hengda.linhai.m.tools.ToastUtil;
import com.hengda.linhai.m.ui.PlayActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {
    @Bind(R.id.map_tablayout)
    TabLayout mapTablayout;
    @Bind(R.id.container_map)
    FrameLayout containerMap;
    @Bind(R.id.location_map)
    ImageView locationMap;
    @Bind(R.id.route_map)
    ImageView routeMap;
    @Bind(R.id.heart_map)
    ImageView heartMap;
    private List<String> tabList;
    private Exhibition exhibition;

    public MapFragment() {
        // Required empty public constructor
    }

    private FragmentManager fragmentManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);
        exhibition = (Exhibition) getActivity().getIntent().getSerializableExtra("exhibition");
        if (exhibition != null) {

            fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (exhibition.getMap_id()==1){
                Mapfrg mapfrg1 = (Mapfrg) fragmentManager.findFragmentByTag("mapfrg");
                if (mapfrg1 == null) {
                    mapfrg1 = Mapfrg.newInstance(2);
                    fragmentTransaction.add(R.id.container_map, mapfrg1, "mapfrg");
                } else {
                    fragmentTransaction.show(mapfrg1);
                }
                fragmentTransaction.commit();
            }else if (exhibition.getMap_id()==2){
                MapTwofrg mapTwofrg = (MapTwofrg) fragmentManager.findFragmentByTag("mapTwofrg");
                if (mapTwofrg == null) {
                    mapTwofrg = MapTwofrg.newInstance(2);
                    fragmentTransaction.add(R.id.container_map, mapTwofrg, "mapTwofrg");
                } else {
                    fragmentTransaction.show(mapTwofrg);
                }
            }else {
                MapThreefrg mapThreefrg = (MapThreefrg) fragmentManager.findFragmentByTag("mapThreefrg");
                if (mapThreefrg == null) {
                    mapThreefrg = MapThreefrg.newInstance(3);
                    fragmentTransaction.add(R.id.container_map, mapThreefrg, "mapThreefrg");
                } else {
                    fragmentTransaction.show(mapThreefrg);
                }
            }


        }else {

            defShowFrg();
        }
        initTabLayout();
        tabListner();

        return view;
    }

    private void initTabLayout() {
        tabList = new ArrayList<>();
        tabList.add("2F");
        tabList.add("3F");
        tabList.add("4F");
        mapTablayout.addTab(mapTablayout.newTab().setText(tabList.get(0)));
        mapTablayout.addTab(mapTablayout.newTab().setText(tabList.get(1)));
        mapTablayout.addTab(mapTablayout.newTab().setText(tabList.get(2)));
    }

    //默认显示第一展厅
    private void defShowFrg() {
        fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Mapfrg mapfrg1 = (Mapfrg) fragmentManager.findFragmentByTag("mapfrg");

        if (mapfrg1 == null) {
            mapfrg1 = Mapfrg.newInstance(2);
            fragmentTransaction.add(R.id.container_map, mapfrg1, "mapfrg");
        } else {
            fragmentTransaction.show(mapfrg1);
        }
        fragmentTransaction.commit();
    }

    //初始化tablayout
    private void tabListner() {
        mapTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                HideFragment hideFragment = new HideFragment().invoke();
                Mapfrg mapfrg = hideFragment.getMapfrg();
                FragmentTransaction ft = hideFragment.getFt();
                MapTwofrg mapTwofrg = hideFragment.getMapTwofrg();
                MapThreefrg mapThreefrg = hideFragment.getMapThreefrg();
                switch (tab.getText().toString()) {
                    case "2F":
                        if (mapfrg == null) {
                            mapfrg = Mapfrg.newInstance(2);
                            ft.add(R.id.container_map, mapfrg, "mapfrg");
                        } else {
                            ft.show(mapfrg);
                        }
                        break;
                    case "3F":
                        if (mapTwofrg == null) {
                            mapTwofrg = MapTwofrg.newInstance(3);
                            ft.add(R.id.container_map, mapTwofrg, "mapTwofrg");
                        } else {
                            ft.show(mapTwofrg);
                        }
                        break;
                    case "4F":
                        if (mapThreefrg == null) {
                            mapThreefrg = MapThreefrg.newInstance(4);
                            ft.add(R.id.container_map, mapThreefrg, "mapThreefrg");
                        } else {
                            ft.show(mapThreefrg);
                        }
                        break;
                    default:
                        if (mapfrg == null) {
                            mapfrg = Mapfrg.newInstance(2);
                            ft.add(R.id.container_map, mapfrg, "mapfrg");
                        } else {
                            ft.show(mapfrg);
                        }
                        break;
                }

                ft.commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    //隐藏Fragment
    private class HideFragment {
        private FragmentTransaction ft;
        private Mapfrg mapfrg;
        private MapTwofrg mapTwofrg;
        private MapThreefrg mapThreefrg;

        public FragmentTransaction getFt() {
            return ft;
        }

        public Mapfrg getMapfrg() {
            return mapfrg;
        }

        public MapTwofrg getMapTwofrg() {
            return mapTwofrg;
        }

        public MapThreefrg getMapThreefrg() {
            return mapThreefrg;
        }

        public HideFragment invoke() {
            FragmentManager fm = getChildFragmentManager();
            ft = fm.beginTransaction();
            mapfrg = (Mapfrg) fm.findFragmentByTag("mapfrg");
            mapTwofrg = (MapTwofrg) fm.findFragmentByTag("mapTwofrg");
            mapThreefrg = (MapThreefrg) fm.findFragmentByTag("mapThreefrg");
            if (mapfrg != null) {
                ft.hide(mapfrg);
            }
            if (mapTwofrg != null) {
                ft.hide(mapTwofrg);
            }
            if (mapThreefrg != null) {
                ft.hide(mapThreefrg);
            }
            return this;
        }
    }
}
