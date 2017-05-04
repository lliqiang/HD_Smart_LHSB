package com.hengda.linhai.m.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.hengda.linhai.m.R;
import com.hengda.linhai.m.adapter.LCommonAdapter;
import com.hengda.linhai.m.adapter.ViewHolder;
import com.hengda.linhai.m.app.HdAppConfig;
import com.hengda.linhai.m.bean.Exhibition;
import com.hengda.linhai.m.bean.NotifyBean;
import com.hengda.linhai.m.bean.RegisterBean;
import com.hengda.linhai.m.db.HBriteDatabase;
import com.hengda.linhai.m.http.RequestApi;
import com.hengda.linhai.m.tools.DebugUtil;
import com.hengda.linhai.m.ui.NotifyDetailActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotifyFragment extends Fragment {


    @Bind(R.id.lv_fragmentNotify)
    ListView lvFragmentNotify;
    private LCommonAdapter<NotifyBean.DataBean> adapter;
    private NotifyBean notiBean;

    public NotifyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notify, container, false);
        ButterKnife.bind(this, view);
        RequestApi.getInstance().getNotifyDetail(new Subscriber<NotifyBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                DebugUtil.debug("e",e.getMessage());
            }

            @Override
            public void onNext(NotifyBean notifyBean) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new LCommonAdapter<NotifyBean.DataBean>(getActivity(), R.layout.item_lv_message,notifyBean.getData()) {
                            @Override
                            public void convert(ViewHolder holder, NotifyBean.DataBean dataBean) {
                                if (dataBean.getTitle()!=null){

                                    holder.setText(R.id.title_messageFragment, dataBean.getTitle());
                                }
                                if (dataBean.getDesc()!=null){
                                    holder.setText(R.id.content_messageFragment,dataBean.getDesc());
                                }
                                Glide.with(getActivity()).load(dataBean.getImgs_path()).placeholder(R.mipmap.listdef).into((ImageView) holder.getView(R.id.img_messageFragment));
                            }
                        };
                        lvFragmentNotify.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        lvFragmentNotify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent=new Intent(getActivity(), NotifyDetailActivity.class);
                                intent.putExtra("travel_id",adapter.getItem(i).getTravel_id());
                                startActivity(intent);
                            }
                        });
                    }
                });

            }
        }, HdAppConfig.getLanguage(), HdAppConfig.getDeviceNo());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
