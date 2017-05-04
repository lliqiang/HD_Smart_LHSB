package com.hengda.linhai.m.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hengda.linhai.m.R;
import com.hengda.linhai.m.app.HdAppConfig;
import com.hengda.linhai.m.bean.CardBean;
import com.hengda.linhai.m.bean.CityBean;
import com.hengda.linhai.m.bean.HeadBean;
import com.hengda.linhai.m.bean.JsonBean;
import com.hengda.linhai.m.bean.LoginState;
import com.hengda.linhai.m.bean.PersonBean;
import com.hengda.linhai.m.bean.PersonalInfo;
import com.hengda.linhai.m.bean.RegisterBean;
import com.hengda.linhai.m.http.RequestApi;
import com.hengda.linhai.m.tools.DebugUtil;
import com.hengda.linhai.m.tools.FragmentUtil;
import com.hengda.linhai.m.tools.GetJsonDataUtil;
import com.hengda.linhai.m.tools.RxBus;
import com.hengda.linhai.m.tools.ViewUtil;
import com.hengda.linhai.m.tools.imgpicker.GlideImageLoader;
import com.hengda.linhai.m.ui.AdviceActicity;
import com.hengda.linhai.m.ui.DragPhotoActivity;
import com.hengda.linhai.m.ui.HelpActicity;
import com.hengda.linhai.m.ui.NegativeActivity;
import com.hengda.linhai.m.ui.RegisterActicity;
import com.hengda.linhai.m.ui.SettingActivity;
import com.hengda.linhai.m.view.DialogCenter;
import com.hengda.linhai.m.view.DialogClickListener;
import com.hengda.linhai.m.view.FloorPopWindow;
import com.hengda.linhai.m.view.SelectDialog;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {


    @Bind(R.id.nevagate_tv)
    TextView nevagateTv;
    @Bind(R.id.advice_tv)
    TextView adviceTv;
    @Bind(R.id.setting_mine)
    ImageView settingMine;
    @Bind(R.id.help_tv)
    TextView helpTv;
    @Bind(R.id.head_mine)
    ImageView headMine;
    @Bind(R.id.name_tv_mine)
    TextView nameTvMine;
    @Bind(R.id.rb_company)
    RadioButton rbCompany;
    @Bind(R.id.rb_trace)
    RadioButton rbTrace;
    @Bind(R.id.rb_comment)
    RadioButton rbComment;
    @Bind(R.id.rb_jeart)
    RadioButton rbJeart;
    @Bind(R.id.rg_mine)
    RadioGroup rgMine;
    @Bind(R.id.container_mine)
    FrameLayout containerMine;
    @Bind(R.id.help_show_hide)
    LinearLayout helpShowHide;
    @Bind(R.id.login_hideshow)
    TextView loginHideshow;
    @Bind(R.id.register_hideshow)
    TextView registerHideshow;
    @Bind(R.id.ll_log_register)
    LinearLayout llLogRegister;
    @Bind(R.id.city)
    TextView city;
    @Bind(R.id.six)
    TextView six;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.year)
    TextView year;
    @Bind(R.id.modify_pwd)
    ImageView modifyPwd;

    @Bind(R.id.ll_info_modify)
    LinearLayout llInfoModify;
    @Bind(R.id.rl_city)
    RelativeLayout rlCity;
    @Bind(R.id.rl_sex)
    RelativeLayout rlSex;
    @Bind(R.id.rl_year)
    RelativeLayout rlYear;
    @Bind(R.id.back_infoModify)
    TextView backInfoModify;
    @Bind(R.id.line1_hideshow)
    View line1Hideshow;
    @Bind(R.id.navagate_adcice)
    LinearLayout navagateAdcice;
    private CompanyFragment companyFragment;
    private TraceFragment traceFragment;
    private CommentFragment commentFragment;
    private HeartFragment heartFragment;
    private FloorPopWindow floorPopWindow;
    private View view;
    private ImagePicker imagePicker;
    private OptionsPickerView cityOptions, sexOptions;
    private TimePickerView timeOptions;
    private ArrayList<CardBean> cardItem = new ArrayList<>();
    private ArrayList<ArrayList<String>> proviceList = new ArrayList<>();
    private ArrayList<String> cityList = new ArrayList<>();
    private ArrayList<String> areaList = new ArrayList<>();
    private ArrayList<CityBean.DataBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();


    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        ButterKnife.bind(this, view);
        RxBus.getDefault().toObservable(PersonBean.class).subscribe(new Action1<PersonBean>() {
            @Override
            public void call(PersonBean personBean) {
                llLogRegister.setVisibility(View.GONE);
                nameTvMine.setVisibility(View.VISIBLE);
                navagateAdcice.setVisibility(View.VISIBLE);
                helpShowHide.setVisibility(View.VISIBLE);
                Log.i("personBean", "------------------------" + personBean.getData().toString());
                nameTvMine.setText(getString(R.string.nickname) + HdAppConfig.getNickname());

                if (personBean.getData().getNick_name() != null) {
                    nameTvMine.setText(getString(R.string.nickname) + personBean.getData().getNick_name());
                }
                if (personBean.getData().getProvince() != null) {
                    city.setText(personBean.getData().getProvince());
                }
                if (personBean.getData().getSex() != null) {
                    if (Integer.parseInt(personBean.getData().getSex()) == 1) {
                        six.setText(R.string.man);
                    } else {
                        six.setText(R.string.woman);
                    }
                }
                if (personBean.getData().getBirthday() != null) {
                    year.setText(personBean.getData().getBirthday().toString());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i("throwable", throwable.getMessage());
            }
        });

        RxBus.getDefault().toObservable(LoginState.class).subscribe(new Action1<LoginState>() {
            @Override
            public void call(LoginState loginState) {
                if (loginState.getNum() == 1) {
                    nameTvMine.setVisibility(View.GONE);
                    llLogRegister.setVisibility(View.VISIBLE);
                    loginHideshow.setVisibility(View.VISIBLE);
                    line1Hideshow.setVisibility(View.VISIBLE);
                    registerHideshow.setVisibility(View.VISIBLE);
                    navagateAdcice.setVisibility(View.GONE);
                    helpShowHide.setVisibility(View.GONE);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
        if (!HdAppConfig.getLogin()) {
            hideForUnLogin();
        } else {
            showForLogin();
        }
        //点击头像修改信息状态
        headMine.setOnClickListener(view1 -> {
            if (HdAppConfig.getLogin() && llInfoModify.getVisibility() == View.GONE) {
                llInfoModify.setVisibility(View.VISIBLE);
                showForInfoChange();
                RequestApi.getInstance().moidifyPersonalInfo(new Subscriber<PersonalInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(PersonalInfo personalInfo) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (personalInfo.getData().getProvince() != null) {

                                    city.setText(personalInfo.getData().getProvince());
                                }
                                if (personalInfo.getData().getSex() != null) {
                                    if (Integer.parseInt(personalInfo.getData().getSex()) == 1) {
                                        six.setText("男");
                                    } else {
                                        six.setText("女");
                                    }

                                }
                                if (personalInfo.getData().getBirthday() != null) {

                                    year.setText(personalInfo.getData().getBirthday().toString());
                                }
                                Log.i("personal", "---------------" + personalInfo.getData().toString());

                            }
                        });
                    }
                }, HdAppConfig.getDeviceNo());
                picker();
            } else {
                if (!HdAppConfig.getLogin()) {
                    Intent intent = new Intent(getActivity(), RegisterActicity.class);
                    intent.putExtra("login", "login");
                    getActivity().startActivity(intent);
                } else {
                    if (llInfoModify.getVisibility() == View.VISIBLE) {
                        initPopWindow();
                    }
                }
            }
        });
        backInfoModify.setOnClickListener(view1 -> {
            showForLogin();
        });
        nameTvMine.setOnClickListener(view1 -> {
            View root = View.inflate(getActivity(), R.layout.edit_name, null);
            ImageView clearImg = (ImageView) root.findViewById(R.id.clear_name);
            EditText edtgroup = (EditText) root.findViewById(R.id.edit_name);
            Selection.setSelection(edtgroup.getText(), edtgroup.getText().length());

            if (edtgroup.getText().toString() != null) {
                clearImg.setVisibility(View.VISIBLE);
                clearImg.setOnClickListener(view2 -> {
                    edtgroup.setText("");
                    clearImg.setVisibility(View.GONE);
                });
            }
            DialogCenter.showNameDialog(getActivity(), root, new DialogClickListener() {
                @Override
                public void n() {
                    super.n();
                    DialogCenter.hideNameDialog();

                }

                @Override
                public void p() {
                    String proupId = edtgroup.getText().toString();
                    proupId = proupId.replaceAll("(\r\n|\r|\n|\n\r)", "");
                    if (TextUtils.isEmpty(proupId)) {
                        Toasty.warning(getActivity(), getString(R.string.not_empty), Toast.LENGTH_SHORT).show();
                    } else {
                        ViewUtil.showDownloadProgressDialog(getActivity(),getString(R.string.going));

                        //执行修改名字的请求
                        String finalProupId = proupId;
                        RequestApi.getInstance().modifyName(new Subscriber<RegisterBean>() {
                            @Override
                            public void onCompleted() {
                                DialogCenter.hideNameDialog();
                                ViewUtil.hideDownloadProgressDialog();
                                nameTvMine.setText(getString(R.string.nickname) + finalProupId);
                                HdAppConfig.setNickName(finalProupId);
                            }

                            @Override
                            public void onError(Throwable e) {
                                DialogCenter.hideNameDialog();
                                ViewUtil.hideDownloadProgressDialog();
                            }

                            @Override
                            public void onNext(RegisterBean registerBean) {
                                DialogCenter.hideCreateDialog();
                                ViewUtil.hideDownloadProgressDialog();
                                if (registerBean.getData().getIsSuccess() == 1) {
                                    Toasty.success(getActivity(), registerBean.getMsg(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toasty.error(getActivity(), registerBean.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, HdAppConfig.getNickname(), HdAppConfig.getDeviceNo());
                    }
                }
            }, new String[]{"所在群组",
                    "确定",
                    "取消"});
        });
        if (llInfoModify.getVisibility() == View.VISIBLE) {
            headMine.setOnClickListener(view1 -> {
                initPopWindow();
            });
        }

        rgMine.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_company:
                        if (companyFragment == null)
                            companyFragment = new CompanyFragment();
                        FragmentUtil.replaceFragment(getChildFragmentManager(), R.id.container_mine,
                                companyFragment, true);
                        break;
                    case R.id.rb_trace:
                        if (!HdAppConfig.getLogin()) {
                            Intent intent = new Intent(getActivity(), RegisterActicity.class);
                            intent.putExtra("login", "login");
                            getActivity().startActivity(intent);
                        } else {
                            if (traceFragment == null)
                                traceFragment = new TraceFragment();
                            FragmentUtil.replaceFragment(getChildFragmentManager(), R.id.container_mine,
                                    traceFragment, true);
                        }
                        break;
                    case R.id.rb_comment:

                        if (!HdAppConfig.getLogin()) {
                            Intent intent = new Intent(getActivity(), RegisterActicity.class);
                            intent.putExtra("login", "login");
                            getActivity().startActivity(intent);
                        } else {
                            if (commentFragment == null)
                                commentFragment = new CommentFragment();
                            FragmentUtil.replaceFragment(getChildFragmentManager(), R.id.container_mine,
                                    commentFragment, true);
                        }
                        break;
                    case R.id.rb_jeart:
                        if (!HdAppConfig.getLogin()) {
                            Intent intent = new Intent(getActivity(), RegisterActicity.class);
                            intent.putExtra("login", "login");
                            getActivity().startActivity(intent);
                        } else {
                            if (heartFragment == null)
                                heartFragment = new HeartFragment();
                            FragmentUtil.replaceFragment(getChildFragmentManager(), R.id.container_mine,
                                    heartFragment, true);
                        }
                        break;
                }
            }
        });
        rbCompany.setChecked(true);

        nevagateTv.setOnClickListener(view1 -> {
            //问卷调查
            Intent intent = new Intent(getActivity(), AdviceActicity.class);
            startActivity(intent);
        });
        adviceTv.setOnClickListener(view1 -> {
            //意见反馈
            Intent intent = new Intent(getActivity(), NegativeActivity.class);
            startActivity(intent);
        });
        helpTv.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), HelpActicity.class);
            startActivity(intent);
        });
        settingMine.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), SettingActivity.class);
            startActivity(intent);
        });
        loginHideshow.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), RegisterActicity.class);
            intent.putExtra("login", "login");
            startActivity(intent);
        });
        registerHideshow.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), RegisterActicity.class);
            intent.putExtra("login", "register");
            startActivity(intent);
        });
        return view;
    }

    private void picker() {
        //初始化条件选择数据
        initOptionData();
        //初始化城市选择器

        //初始化性别选择器
        initCustomOptionPicker();
        //初始化时间选择器
        initTimeOptionPicker();
        city.setOnClickListener(view1 -> {
            cityOptions.show();
        });
        six.setOnClickListener(view1 -> {
            sexOptions.show();
        });
        year.setOnClickListener(view1 -> {
            timeOptions.show();
        });
    }

    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(getActivity(), R.style
                .transparentFrameWindowStyle,
                listener, names, getString(R.string.set_imghead));
        if (!getActivity().isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    private void initPopWindow() {

        List<String> names = new ArrayList<>();
        names.add(getString(R.string.scan_bigpic));
        names.add(getString(R.string.take_pass));
        names.add(getString(R.string.select_camera));
        showDialog(new SelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // 直接调起相机
                        startPhotoActivity(getActivity(), headMine);
                        break;
                    case 1:
                        ImagePicker.getInstance().setSelectLimit(1);
                        Intent intent1 = new Intent(getActivity(), ImageGridActivity.class);
                        intent1.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                        startActivityForResult(intent1, 100);
                        break;
                    case 2:
                        imagePicker.setImageLoader(new GlideImageLoader());
                        imagePicker.setMultiMode(false);
                        imagePicker.setStyle(CropImageView.Style.CIRCLE);
                        Integer radius = 140;
                        radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, getResources().getDisplayMetrics());
                        imagePicker.setFocusWidth(radius * 2);
                        imagePicker.setFocusHeight(radius * 2);
                        imagePicker.setOutPutX(800);
                        imagePicker.setOutPutY(800);
                        Intent intent = new Intent(getActivity(), ImageGridActivity.class);
                        startActivityForResult(intent, 100);
                    default:
                        break;
                }

            }
        }, names);


//        floorPopWindow = new FloorPopWindow(getActivity(), LinearLayoutCompat.LayoutParams.MATCH_PARENT);
//        floorPopWindow.setOutsideTouchable(true);
//        floorPopWindow.setOnFloorChangeListener(floor -> {
//            switch (floor) {
//                //查看大图
//                case 2:
//                    //打开预览
//                    startPhotoActivity(getActivity(), headMine);
//                    break;
//                //拍照上传
//                case 3:
//                    ImagePicker.getInstance().setSelectLimit(1);
//                    Intent intent1 = new Intent(getActivity(), ImageGridActivity.class);
//                    intent1.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
//                    startActivityForResult(intent1, 100);
//                    break;
//                //从相册中选择
//                case 4:
//                    imagePicker.setImageLoader(new GlideImageLoader());
//                    imagePicker.setMultiMode(false);
//                    imagePicker.setStyle(CropImageView.Style.CIRCLE);
//                    Integer radius = 140;
//                    radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, getResources().getDisplayMetrics());
//                    imagePicker.setFocusWidth(radius * 2);
//                    imagePicker.setFocusHeight(radius * 2);
//                    imagePicker.setOutPutX(800);
//                    imagePicker.setOutPutY(800);
//                    Intent intent = new Intent(getActivity(), ImageGridActivity.class);
//                    startActivityForResult(intent, 100);
//                    break;
//            }
//        });
//        floorPopWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                imagePicker.getImageLoader().displayImage(getActivity(), images.get(0).path, headMine, 320, 320);
                Glide.with(this).load(images.get(0).path).into(headMine);
                RequestApi.getInstance().uploadPic(new Subscriber<HeadBean>() {
                    @Override
                    public void onCompleted() {
//                        hideForUnLogin();
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugUtil.debug("e", e.getMessage());
                    }

                    @Override
                    public void onNext(HeadBean s) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("HeadBean", s.getData().getUrl() + s.getData().getIsSuccess());
                                Toasty.success(getActivity(), s.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        Log.i("s", s.toString());
                    }
                }, HdAppConfig.getDeviceNo(), HdAppConfig.getLanguage(), images.get(0).path);
            } else {

            }
        }
    }

    private void showForInfoChange() {
        llLogRegister.setVisibility(View.GONE);
        navagateAdcice.setVisibility(View.GONE);
        helpShowHide.setVisibility(View.GONE);
        nameTvMine.setVisibility(View.VISIBLE);
        rgMine.setVisibility(View.GONE);
        containerMine.setVisibility(View.GONE);
        llInfoModify.setVisibility(View.VISIBLE);
        settingMine.setVisibility(View.GONE);
        backInfoModify.setVisibility(View.VISIBLE);
        //执行修改密码的操作
        modifyPwd.setOnClickListener(view1 -> {
            View root = View.inflate(getActivity(), R.layout.pwd_edt, null);
            EditText edtgroup = (EditText) root.findViewById(R.id.pwd_init);
            EditText newPwd = (EditText) root.findViewById(R.id.pwd_new);
            EditText surePwd = (EditText) root.findViewById(R.id.pwd_tosure);
            Selection.setSelection(edtgroup.getText(), edtgroup.getText().length());

            DialogCenter.showPwdDialog(getActivity(), root, new DialogClickListener() {
                @Override
                public void n() {
                    super.n();
                    DialogCenter.hidePwdDialog();

                }

                @Override
                public void p() {
                    String proupId = edtgroup.getText().toString();
                    proupId = proupId.replaceAll("(\r\n|\r|\n|\n\r)", "");
                    String pwdNew = newPwd.getText().toString();
                    pwdNew = pwdNew.replaceAll("(\r\n|\r|\n|\n\r)", "");
                    String pwdToSure = surePwd.getText().toString();
                    pwdToSure = pwdToSure.replaceAll("(\r\n|\r|\n|\n\r)", "");
                    if (TextUtils.isEmpty(proupId) | TextUtils.isEmpty(pwdNew) | TextUtils.isEmpty(pwdToSure)) {
                        Toasty.warning(getActivity(), getString(R.string.not_empty), Toast.LENGTH_SHORT).show();
                    } else if (!pwdNew.equals(pwdToSure)) {
                        Toasty.warning(getActivity(), getString(R.string.noconstent), Toast.LENGTH_SHORT).show();
                    } else {
                        ViewUtil.showDownloadProgressDialog(getActivity(), getString(R.string.going));

                        //执行修改名字的请求
                        String finalProupId = proupId;
                        RequestApi.getInstance().modifyPwd(new Subscriber<RegisterBean>() {
                            @Override
                            public void onCompleted() {
                                ViewUtil.hideDownloadProgressDialog();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(RegisterBean registerBean) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toasty.success(getActivity(), registerBean.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }, HdAppConfig.getDeviceNo(), proupId, pwdNew);

                    }
                }
            }, new String[]{"修改密码",
                    "确定",
                    "取消"});
        });

    }

    private void showForLogin() {
        navagateAdcice.setVisibility(View.VISIBLE);
        llLogRegister.setVisibility(View.GONE);
        loginHideshow.setVisibility(View.GONE);
        registerHideshow.setVisibility(View.GONE);
        line1Hideshow.setVisibility(View.GONE);
        helpShowHide.setVisibility(View.VISIBLE);
        nameTvMine.setVisibility(View.VISIBLE);
        rgMine.setVisibility(View.VISIBLE);
        containerMine.setVisibility(View.VISIBLE);
        llInfoModify.setVisibility(View.GONE);
        backInfoModify.setVisibility(View.GONE);
        settingMine.setVisibility(View.VISIBLE);
        if (HdAppConfig.getNickname() != null) {
            nameTvMine.setText(getString(R.string.nickname) + HdAppConfig.getNickname());
        }

    }

    private void hideForUnLogin() {
        llLogRegister.setVisibility(View.VISIBLE);
        loginHideshow.setVisibility(View.VISIBLE);
        line1Hideshow.setVisibility(View.VISIBLE);
        registerHideshow.setVisibility(View.VISIBLE);
        rgMine.setVisibility(View.VISIBLE);
        helpShowHide.setVisibility(View.GONE);
        nameTvMine.setVisibility(View.GONE);
        containerMine.setVisibility(View.VISIBLE);
        llInfoModify.setVisibility(View.GONE);
        navagateAdcice.setVisibility(View.GONE);
        settingMine.setVisibility(View.GONE);
        backInfoModify.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void startPhotoActivity(Context context, ImageView imageView) {
        Intent intent = new Intent(context, DragPhotoActivity.class);
        int location[] = new int[2];

        imageView.getLocationOnScreen(location);
        intent.putExtra("left", location[0]);
        intent.putExtra("top", location[1]);
        intent.putExtra("height", imageView.getHeight());
        intent.putExtra("width", imageView.getWidth());

        context.startActivity(intent);
        getActivity().overridePendingTransition(0, 0);
    }

    private void initOptionPicker() {
        /**
         * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
         */
        cityOptions = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(options2);
                city.setText(tx);
                RequestApi.getInstance().modifyInfo(new Subscriber<RegisterBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugUtil.debug("e", e.getMessage());
                    }

                    @Override
                    public void onNext(RegisterBean registerBean) {
                        Toasty.success(getActivity(), registerBean.getMsg(), Toast.LENGTH_SHORT).show();

                    }
                }, HdAppConfig.getDeviceNo(), "1", options1Items.get(options1).getProvince_id());
            }
        })
                .setTitleText(getString(R.string.city_select))
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.GREEN)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.WHITE)
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(Color.BLACK)
                .setSubmitColor(Color.RED)
                .setTextColorCenter(Color.LTGRAY)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。

                .build();
        cityOptions.setPicker(options1Items, options2Items);//二级选择器

    }

    private void initCustomOptionPicker() {//条件选择器初始化，自定义布局
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        sexOptions = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = cardItem.get(options1).getPickerViewText();
                int num;
                six.setText(tx);
                if (tx.equals(getString(R.string.man))) {
                    num = 1;
                } else {
                    num = 2;
                }
                RequestApi.getInstance().modifyInfo(new Subscriber<RegisterBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugUtil.debug("e", e.getMessage());
                    }

                    @Override
                    public void onNext(RegisterBean registerBean) {
                        Toasty.success(getActivity(), registerBean.getMsg(), Toast.LENGTH_SHORT).show();

                    }
                }, HdAppConfig.getDeviceNo(), "2", String.valueOf(num));
            }

        }).setBgColor(Color.WHITE)
                .setCancelColor(Color.BLACK)
                .setSubmitColor(Color.RED)
                .setTextColorCenter(Color.LTGRAY)
                .isCenterLabel(false) //是否只显
                .isDialog(false)
                .build();
        sexOptions.setPicker(cardItem);//添加数据

    }

    private void initTimeOptionPicker() {//条件选择器初始化，自定义布局
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 23);
        Calendar endDate = Calendar.getInstance();
//        endDate.set(2017, 5, 9);
        endDate = selectedDate;

        timeOptions = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调

                RequestApi.getInstance().modifyInfo(new Subscriber<RegisterBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugUtil.debug("e", e.getMessage());
                    }

                    @Override
                    public void onNext(RegisterBean registerBean) {
                        Toasty.success(getActivity(), registerBean.getMsg(), Toast.LENGTH_SHORT).show();

                    }
                }, HdAppConfig.getDeviceNo(), "3", getTime(date));
            }
        }).setType(TimePickerView.Type.YEAR_MONTH_DAY)
                //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentSize(20)
                .setCancelColor(Color.BLACK)
                .setSubmitColor(Color.RED)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .build();
    }

    private void initOptionData() {
        getCardData();
        initJsonData();

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private void getCardData() {

        cardItem.add(new CardBean(0, "男"));
        cardItem.add(new CardBean(1, "女"));

    }

    private void initJsonData() {//解析数据
        RequestApi.getInstance().getCityInfo(new Subscriber<CityBean>() {
            @Override
            public void onCompleted() {
                initOptionPicker();
            }

            @Override
            public void onError(Throwable e) {
                Log.i("e", e.getMessage());
            }

            @Override
            public void onNext(CityBean cityBean) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        options1Items.addAll(cityBean.getData());
                        for (int i = 0; i < cityBean.getData().size(); i++) {
                            ArrayList<String> list = new ArrayList<String>();
                            for (int j = 0; j < cityBean.getData().get(i).getCity_info().size(); j++) {
                                if (cityBean.getData().get(i).getProvince_id().equals(cityBean.getData().get(i).getCity_info().get(j).getCity_id())) {
                                    list.add(cityBean.getData().get(i).getCity_info().get(j).getCity_name());
                                }
                            }
                            if (list.size() != 0) {
                                options2Items.add(list);
                            }
                        }


                    }
                });
            }
        });

    }

}
