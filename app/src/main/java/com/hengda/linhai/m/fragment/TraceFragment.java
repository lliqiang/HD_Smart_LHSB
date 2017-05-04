package com.hengda.linhai.m.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.hengda.linhai.m.R;
import com.hengda.linhai.m.adapter.LCommonAdapter;
import com.hengda.linhai.m.adapter.ViewHolder;
import com.hengda.linhai.m.app.HdAppConfig;
import com.hengda.linhai.m.bean.RecordBean;
import com.hengda.linhai.m.http.RequestApi;
import com.hengda.linhai.m.tools.ViewUtil;

;import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class TraceFragment extends Fragment {


    @Bind(R.id.lv_trace)
    ListView lvTrace;
    private LCommonAdapter<RecordBean.DataBean> adapter;

    public TraceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trace, container, false);
        ButterKnife.bind(this, view);
        ViewUtil.showDownloadProgressDialog(getActivity(), getString(R.string.going));
        RequestApi.getInstance().getScanInfo(new Subscriber<RecordBean>() {
            @Override
            public void onCompleted() {
                ViewUtil.hideDownloadProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                ViewUtil.hideDownloadProgressDialog();
            }

            @Override
            public void onNext(RecordBean recordBean) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new LCommonAdapter<RecordBean.DataBean>(getActivity(), R.layout.item_lv_trace_mine, recordBean.getData()) {
                            @Override
                            public void convert(ViewHolder holder, RecordBean.DataBean dataBean) {
                                if (dataBean.getExhibitname() != null) {
                                    holder.setText(R.id.tile_trace_mine, dataBean.getExhibitname().toString());
                                }
                                if (dataBean.getVisit_time() != null) {
                                    holder.setText(R.id.tile_trace_mine, dataBean.getVisit_time());
                                }
                                Glide.with(getActivity()).load(dataBean.getList_img_path()).placeholder(R.mipmap.listdef).into((ImageView) holder.getView(R.id.img_trace));
                            }
                        };
                        lvTrace.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }, HdAppConfig.getDeviceNo(), HdAppConfig.getLanguage());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
