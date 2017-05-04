package com.hengda.linhai.m.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hengda.linhai.m.R;
import com.hengda.linhai.m.adapter.CompanyAdapter;
import com.hengda.linhai.m.adapter.LCommonAdapter;
import com.hengda.linhai.m.adapter.MyItemDecoration;
import com.hengda.linhai.m.adapter.ViewHolder;
import com.hengda.linhai.m.app.HdAppConfig;
import com.hengda.linhai.m.bean.CreateBean;
import com.hengda.linhai.m.bean.ExistBean;
import com.hengda.linhai.m.bean.GetGroupBean;
import com.hengda.linhai.m.bean.JoinGroup;
import com.hengda.linhai.m.bean.LoginState;
import com.hengda.linhai.m.bean.RxBusBaseMessage;
import com.hengda.linhai.m.chat.fixtures.messages.MessagesListActivity;
import com.hengda.linhai.m.http.RequestApi;
import com.hengda.linhai.m.tools.DebugUtil;
import com.hengda.linhai.m.tools.RxBus;
import com.hengda.linhai.m.tools.ViewUtil;
import com.hengda.linhai.m.ui.RegisterActicity;
import com.hengda.linhai.m.view.DialogCenter;
import com.hengda.linhai.m.view.DialogClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyFragment extends Fragment {


    @Bind(R.id.lv_company)
    RecyclerView lvCompany;
    @Bind(R.id.create_group_hideshow)
    LinearLayout createGroupHideshow;
    @Bind(R.id.line_hideshow)
    View lineHideshow;
    @Bind(R.id.join_group_hideshow)
    LinearLayout join_group_hideshow;
    @Bind(R.id.ll_create_join)
    LinearLayout llCreateJoin;
    @Bind(R.id.groupId)
    TextView groupId;
    @Bind(R.id.groupNo)
    TextView groupNo;
    @Bind(R.id.id_groupNo)
    LinearLayout idGroupNo;
    @Bind(R.id.rl_group)
    RelativeLayout rlGroup;

    private LCommonAdapter<JoinGroup.DataBean.InfoBean.GroupMemberBean> adapter;
    private LCommonAdapter<GetGroupBean.DataBean.GroupMemberBean> groupAdapter;


    private List<GetGroupBean.DataBean.GroupMemberBean> groupMemberBeanList = new ArrayList<>();
private CompanyAdapter companyAdapter;
    public CompanyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_company, container, false);
        ButterKnife.bind(this, view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        lvCompany.addItemDecoration(new MyItemDecoration());
        lvCompany.setLayoutManager(linearLayoutManager);


        //根据退出登录状态显示
        RxBus.getDefault().toObservable(RxBusBaseMessage.class).subscribe(new Action1<RxBusBaseMessage>() {
            @Override
            public void call(RxBusBaseMessage rxBusBaseMessage) {
                if (rxBusBaseMessage.getCode() == 8) {
                    ViewUtil.showDownloadProgressDialog(getActivity(), "加载中...");
                    requestGroup();

                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                DebugUtil.debug("throwable", throwable.getMessage());
            }
        });
        RxBus.getDefault().toObservable(LoginState.class).subscribe(new Subscriber<LoginState>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(LoginState loginState) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (loginState.getNum() == 1) {
                            lvCompany.setVisibility(View.GONE);
                            rlGroup.setVisibility(View.GONE);
                            llCreateJoin.setVisibility(View.VISIBLE);
                        }
                    }
                });

            }
        });
        if (!HdAppConfig.getLogin()) {
            llCreateJoin.setVisibility(View.VISIBLE);

        } else {

            //登录状态下 根据是否在群组中显示隐藏
            ViewUtil.showDownloadProgressDialog(getActivity(),getString(R.string.going));
            RequestApi.getInstance().getGroupInfo(new Subscriber<GetGroupBean>() {
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
                public void onNext(GetGroupBean getGroupBean) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (getGroupBean.getData().getGroup_id() != null) {
                                idGroupNo.setVisibility(View.VISIBLE);
                                lvCompany.setVisibility(View.VISIBLE);
                                rlGroup.setVisibility(View.VISIBLE);
                                llCreateJoin.setVisibility(View.GONE);
                                groupNo.setText(getGroupBean.getData().getGroup_id());
                                HdAppConfig.setGroupNo(getGroupBean.getData().getGroup_id());
                                companyAdapter=new CompanyAdapter(getGroupBean.getData().getGroup_member(),getActivity());
                                lvCompany.setAdapter(companyAdapter);
                                companyAdapter.notifyDataSetChanged();
                                companyAdapter.setOnItemClickLitener(new CompanyAdapter.OnCompanyClickLitener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        if (position!=getGroupBean.getData().getGroup_member().size()-1){
                                            Intent intent = new Intent(getActivity(), MessagesListActivity.class);
                                            intent.putExtra("message", getGroupBean.getData().getGroup_member().get(position));
                                            startActivity(intent);
                                        }else {
                                            ViewUtil.showDownloadProgressDialog(getActivity(), getString(R.string.going));
                                            RequestApi.getInstance().existGroup(new Subscriber<ExistBean>() {
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
                                                public void onNext(ExistBean existBean) {
                                                    Toasty.warning(getActivity(), existBean.getMsg(), Toast.LENGTH_SHORT).show();
                                                }
                                            }, HdAppConfig.getDeviceNo(), HdAppConfig.getGroupNo());
                                        }
                                    }
                                });

                            } else {
                                llCreateJoin.setVisibility(View.VISIBLE);
                                idGroupNo.setVisibility(View.GONE);
                                lvCompany.setVisibility(View.GONE);
                                rlGroup.setVisibility(View.GONE);
                            }
                        }
                    });

                }
            }, HdAppConfig.getDeviceNo());


        }
        join_group_hideshow.setOnClickListener(view1 -> {
            if (HdAppConfig.getLogin()) {
                createGroupDialog();
            } else {
                Intent intent = new Intent(getActivity(), RegisterActicity.class);
                intent.putExtra("login", "login");
                startActivity(intent);
            }
        });
        createGroupHideshow.setOnClickListener(view1 -> {
            if (HdAppConfig.getLogin()) {
                RequestApi.getInstance().createGroup(new Subscriber<CreateBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugUtil.debug("e", e.getMessage());
                    }

                    @Override
                    public void onNext(CreateBean createBean) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (createBean.getData().getGroup_id() != null) {
                                    lvCompany.setVisibility(View.VISIBLE);
                                    idGroupNo.setVisibility(View.VISIBLE);
                                    llCreateJoin.setVisibility(View.GONE);
                                    if (createBean.getData().getGroup_id() != null) {
                                        groupId.setText("ID:" + createBean.getData().getGroup_id());
                                        HdAppConfig.setGroupNo(createBean.getData().getGroup_id());
                                        Toasty.success(getActivity(), createBean.getMsg(), Toast.LENGTH_SHORT).show();
                                        requestGroup();
                                    }
                                }
                            }
                        });

                    }
                }, HdAppConfig.getDeviceNo());

            } else {
                Intent intent = new Intent(getActivity(), RegisterActicity.class);
                intent.putExtra("login", "login");
                startActivity(intent);
            }
        });

//        existLogin.setOnClickListener(view1 -> {
//            ViewUtil.showDownloadProgressDialog(getActivity(), "请稍后...");
//            RequestApi.getInstance().existGroup(new Subscriber<ExistBean>() {
//                @Override
//                public void onCompleted() {
//                    ViewUtil.hideDownloadProgressDialog();
//                }
//
//                @Override
//                public void onError(Throwable e) {
//                    DebugUtil.debug("e", e.getMessage());
//                    ViewUtil.hideDownloadProgressDialog();
//                }
//
//                @Override
//                public void onNext(ExistBean existBean) {
//                    Toasty.warning(getActivity(), existBean.getMsg(), Toast.LENGTH_SHORT).show();
//                }
//            }, HdAppConfig.getDeviceNo(), HdAppConfig.getGroupNo());
//        });
        return view;
    }

    private void requestGroup() {

        RequestApi.getInstance().getGroupInfo(new Subscriber<GetGroupBean>() {
            @Override
            public void onCompleted() {
                ViewUtil.hideDownloadProgressDialog();
                join_group_hideshow.setOnClickListener(view1 -> {
                    if (HdAppConfig.getLogin()) {
                        createGroupDialog();
                    } else {
                        Intent intent = new Intent(getActivity(), RegisterActicity.class);
                        intent.putExtra("login", "login");
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                DebugUtil.debug("e", e.getMessage());
                ViewUtil.hideDownloadProgressDialog();
            }

            @Override
            public void onNext(GetGroupBean getGroupBean) {
                if (getGroupBean.getData().getGroup_id() != null) {
                    idGroupNo.setVisibility(View.VISIBLE);
                    lvCompany.setVisibility(View.VISIBLE);
                    rlGroup.setVisibility(View.VISIBLE);
                    groupId.setText("ID:"+HdAppConfig.getDeviceNo());
                    groupNo.setText(getString(R.string.group_no) + getGroupBean.getData().getGroup_id());
                    HdAppConfig.setGroupNo(getGroupBean.getData().getGroup_id());

                    companyAdapter=new CompanyAdapter(getGroupBean.getData().getGroup_member(),getActivity());
                    lvCompany.setAdapter(companyAdapter);
                    companyAdapter.notifyDataSetChanged();
                    companyAdapter.setOnItemClickLitener(new CompanyAdapter.OnCompanyClickLitener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            if (position!=getGroupBean.getData().getGroup_member().size()-1){
                                Intent intent = new Intent(getActivity(), MessagesListActivity.class);
                                intent.putExtra("message", getGroupBean.getData().getGroup_member().get(position));
                                startActivity(intent);
                            }else {
                                ViewUtil.showDownloadProgressDialog(getActivity(), getString(R.string.going));
                                RequestApi.getInstance().existGroup(new Subscriber<ExistBean>() {
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
                                    public void onNext(ExistBean existBean) {
                                        Toasty.warning(getActivity(), existBean.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }, HdAppConfig.getDeviceNo(), HdAppConfig.getGroupNo());
                            }
                        }
                    });

                } else {
                    llCreateJoin.setVisibility(View.VISIBLE);
                    idGroupNo.setVisibility(View.GONE);
                    lvCompany.setVisibility(View.GONE);
                    rlGroup.setVisibility(View.GONE);
                }
            }
        }, HdAppConfig.getDeviceNo());
    }

    /*
         * 加入群组的弹框
         * */
    private void createGroupDialog() {
        View root = View.inflate(getActivity(), R.layout.edit_joingroup, null);
        EditText edtgroup = (EditText) root.findViewById(R.id.edit_group);
        Selection.setSelection(edtgroup.getText(), edtgroup.getText().length());
        DialogCenter.showCreateDialog(getActivity(), root, new DialogClickListener() {
            @Override
            public void n() {
                super.n();
                DialogCenter.hideCreateDialog();
                ViewUtil.hideDownloadProgressDialog();
            }

            @Override
            public void p() {
                String proupId = edtgroup.getText().toString();
                proupId = proupId.replaceAll("(\r\n|\r|\n|\n\r)", "");
                if (TextUtils.isEmpty(proupId)) {
                    Toasty.warning(getActivity(), "请输入群组号", Toast.LENGTH_SHORT).show();
                } else {
                    ViewUtil.showDownloadProgressDialog(getActivity(), "请稍后...");

                    //执行加入群组请求
                    RequestApi.getInstance().joinGroup(new Subscriber<JoinGroup>() {
                        @Override
                        public void onCompleted() {
                            DialogCenter.hideCreateDialog();
                            ViewUtil.hideDownloadProgressDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("e", e.getMessage());
                            DialogCenter.hideCreateDialog();
                            ViewUtil.hideDownloadProgressDialog();
                        }

                        @Override
                        public void onNext(JoinGroup joinGroup) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    DialogCenter.hideCreateDialog();
                                    ViewUtil.hideDownloadProgressDialog();
                                    if (joinGroup.getStatus() == 1) {
                                        lvCompany.setVisibility(View.VISIBLE);
                                        idGroupNo.setVisibility(View.VISIBLE);
                                        llCreateJoin.setVisibility(View.GONE);
                                        if (joinGroup.getData().getInfo().getGroup_id() != null) {
                                            groupId.setText(joinGroup.getData().getInfo().getGroup_id());
                                            HdAppConfig.setGroupNo(joinGroup.getData().getInfo().getGroup_id());
                                            Toasty.success(getActivity(), joinGroup.getMsg(), Toast.LENGTH_SHORT).show();
                                            requestGroup();
                                        } else {
                                            Toasty.error(getActivity(), joinGroup.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });

                        }
                    }, proupId, HdAppConfig.getDeviceNo());

                }
            }
        }, new String[]{"所在群组",
                "确定",
                "取消"});

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
