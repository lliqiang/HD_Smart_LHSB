package com.hengda.linhai.m.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hengda.linhai.m.R;
import com.hengda.linhai.m.adapter.LCommonAdapter;
import com.hengda.linhai.m.adapter.ViewHolder;
import com.hengda.linhai.m.app.HdAppConfig;
import com.hengda.linhai.m.bean.CommentBean;
import com.hengda.linhai.m.http.RequestApi;
import com.hengda.linhai.m.tools.ViewUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends Fragment {


    @Bind(R.id.lv_comment)
    ListView lvComment;
    private LCommonAdapter<CommentBean.DataBean> adapter;

    public CommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        ButterKnife.bind(this, view);
        ViewUtil.showDownloadProgressDialog(getActivity(), getString(R.string.going));
        RequestApi.getInstance().getCommentInfo(new Subscriber<CommentBean>() {
            @Override
            public void onCompleted() {
                ViewUtil.hideDownloadProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                ViewUtil.hideDownloadProgressDialog();
            }

            @Override
            public void onNext(CommentBean commentBean) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lvComment.setAdapter(adapter = new LCommonAdapter<CommentBean.DataBean>(getActivity(), R.layout.item_lv_comment, commentBean.getData()) {
                            @Override
                            public void convert(ViewHolder holder, CommentBean.DataBean dataBean) {
                                if (dataBean.getTitle() != null) {
                                    holder.setText(R.id.item_title_comment, dataBean.getTitle());
                                }
                                if (dataBean.getComment_content() != null) {
                                    holder.setText(R.id.content_comment, dataBean.getComment_content());
                                }
                                if (dataBean.getTime()!=null){
                                    holder.setText(R.id.time_comment,dataBean.getTime());
                                }

                            }
                        });
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
