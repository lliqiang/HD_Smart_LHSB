package com.hengda.linhai.m.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengda.linhai.m.R;
import com.hengda.linhai.m.app.HdAppConfig;
import com.hengda.linhai.m.bean.TransBean;
import com.hengda.linhai.m.http.RequestApi;
import com.hengda.linhai.m.tools.DebugUtil;
import com.hengda.linhai.m.tools.ToastUtil;
import com.hengda.linhai.m.ui.MapActivity;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class TranficFragment extends Fragment {


    WebView ticketInfo;
    ImageView imgTranfic;

    public TranficFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tranfic, container, false);
        ticketInfo = ((WebView) view.findViewById(R.id.wv_info));
        imgTranfic = ((ImageView) view.findViewById(R.id.img_tranfic));
        ticketInfo.getSettings().setUseWideViewPort(true);
        ticketInfo.setBackgroundColor(0);
        ticketInfo.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        RequestApi.getInstance().getTransInfo(new Subscriber<TransBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                DebugUtil.debug("e",e.getMessage());
            }

            @Override
            public void onNext(TransBean transBean) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (transBean.getData().getHtml_path()!=null){

                                    ticketInfo.loadUrl(transBean.getData().getHtml_path());
                                }
                            }
                        });

                    }
                });
            }
        }, HdAppConfig.getLanguage());

        ButterKnife.bind(this, view);
        imgTranfic.setOnClickListener(view1 -> {
            Intent intent=new Intent(getActivity(), MapActivity.class);
            startActivity(intent);
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
