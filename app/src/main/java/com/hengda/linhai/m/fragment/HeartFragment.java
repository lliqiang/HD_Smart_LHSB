package com.hengda.linhai.m.fragment;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hengda.linhai.m.R;
import com.hengda.linhai.m.adapter.LCommonAdapter;
import com.hengda.linhai.m.adapter.ViewHolder;
import com.hengda.linhai.m.app.HdAppConfig;
import com.hengda.linhai.m.bean.CollectBean;
import com.hengda.linhai.m.bean.RegisterBean;
import com.hengda.linhai.m.http.RequestApi;
import com.hengda.linhai.m.tools.DebugUtil;
import com.hengda.linhai.m.tools.ViewUtil;
import com.hengda.linhai.m.ui.PlayActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeartFragment extends Fragment {


    @Bind(R.id.lv_heart)
    ListView lvHeart;
    private LCommonAdapter<CollectBean.DataBean> adapter;
    private boolean flag=true;
    private Drawable drawable;
    private Drawable drawable1;
    public HeartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_heart, container, false);
        ButterKnife.bind(this, view);
        drawable = getResources().getDrawable(R.mipmap.img_heart);
        drawable1 = getResources().getDrawable(R.mipmap.not_heart);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        drawable1.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        ViewUtil.showDownloadProgressDialog(getActivity(), getString(R.string.going));
        RequestApi.getInstance().getRecordInfo(new Subscriber<CollectBean>() {
            @Override
            public void onCompleted() {
                ViewUtil.hideDownloadProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                DebugUtil.debug("e", e.getMessage());
                ViewUtil.hideDownloadProgressDialog();
            }

            @Override
            public void onNext(CollectBean collectBean) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lvHeart.setAdapter(adapter = new LCommonAdapter<CollectBean.DataBean>(getActivity(), R.layout.item_layout_heart, collectBean.getData()) {
                            @Override
                            public void convert(ViewHolder holder, CollectBean.DataBean dataBean) {
                                holder.setText(R.id.item_title_heart, dataBean.getExhibitname());
                                holder.setText(R.id.time_heart, dataBean.getCollect_time());
                                ImageView heart= holder.getView(R.id.item_heart_heart);
                                Glide.with(getActivity()).load(dataBean.getList_img_path()).placeholder(R.mipmap.listdef).into((ImageView) holder.getView(R.id.img_item_heart));
                                heart.setOnClickListener(view1 -> {
                                    RequestApi.getInstance().clickOk(new Subscriber<RegisterBean>() {
                                        @Override
                                        public void onCompleted() {
                                            if (flag){
                                                heart.setImageResource(R.mipmap.not_heart);
                                                Toasty.warning(getActivity(), getString(R.string.cancel_click), Toast.LENGTH_SHORT).show();
                                            }else {
                                                heart.setImageResource(R.mipmap.img_heart);
                                                Toasty.warning(getActivity(),getString(R.string.click_ok), Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            DebugUtil.debug("e", e.getMessage());
                                        }

                                        @Override
                                        public void onNext(RegisterBean registerBean) {

                                        }
                                    }, dataBean.getExhibit_id(), HdAppConfig.getDeviceNo());
                                });
                            }
                        });
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
