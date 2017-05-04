package com.hengda.linhai.m.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.hengda.linhai.m.bean.MessageBean;
import com.hengda.linhai.m.db.HBriteDatabase;
import com.hengda.linhai.m.http.RequestApi;
import com.hengda.linhai.m.tools.DebugUtil;
import com.hengda.linhai.m.ui.HistoryActivity;
import com.hengda.linhai.m.ui.MessageDetailActivity;

import java.util.ArrayList;
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
public class MessageFragment extends Fragment {


    @Bind(R.id.lv_MessageFragment)
    ListView lvMessageFragment;
    private List<MessageBean> messageBeanList;
    private LCommonAdapter<MessageBean.DataBean> adapter;

    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, view);
        messageBeanList = new ArrayList<>();
        RequestApi.getInstance().getMessageInfo(new Subscriber<MessageBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                DebugUtil.debug("Throwable", e.getMessage());
            }

            @Override
            public void onNext(MessageBean messageBeen) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lvMessageFragment.setAdapter(adapter = new LCommonAdapter<MessageBean.DataBean>(getActivity(), R.layout.item_lv_message, messageBeen.getData()) {
                            @Override
                            public void convert(ViewHolder holder, MessageBean.DataBean dataBean) {
                                holder.setText(R.id.title_messageFragment, dataBean.getTitle());
                                holder.setText(R.id.time_messageFragment,dataBean.getTime());
                                holder.setText(R.id.content_messageFragment,dataBean.getDesc());
                                Glide.with(getActivity()).load(dataBean.getImgs_path()).placeholder(R.mipmap.default_image).into((ImageView) holder.getView(R.id.img_messageFragment));
                            }
                        });
                        adapter.notifyDataSetChanged();
                        lvMessageFragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent=new Intent(getActivity(), MessageDetailActivity.class);
                                intent.putExtra("Zixun_id",messageBeen.getData().get(i).getZixun_id());
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        }, HdAppConfig.getLanguage());
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
