package com.hengda.linhai.m.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hengda.linhai.m.R;
import com.hengda.linhai.m.app.HdAppConfig;
import com.hengda.linhai.m.bean.LoginState;
import com.hengda.linhai.m.bean.PersonBean;
import com.hengda.linhai.m.bean.RegisterBean;
import com.hengda.linhai.m.bean.RxBusBaseMessage;
import com.hengda.linhai.m.http.RequestApi;
import com.hengda.linhai.m.tools.DebugUtil;
import com.hengda.linhai.m.tools.RxBus;
import com.hengda.linhai.m.tools.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

public class RegisterActicity extends Activity {

    @Bind(R.id.login_img)
    ImageView loginImg;
    @Bind(R.id.register_img)
    ImageView registerImg;
    @Bind(R.id.username_input)
    EditText usernameInput;
    @Bind(R.id.pwd_input)
    EditText pwdInput;
    @Bind(R.id.again_input)
    EditText againInput;
    @Bind(R.id.register_btn)
    Button registerBtn;
    String Info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register_acticity);
        ButterKnife.bind(this);
        Info = getIntent().getStringExtra("login");
        if (Info.equals("login")) {
            loginState();
        } else {
            registerState();
        }
        loginImg.setOnClickListener(view -> {
            againInput.setVisibility(View.GONE);
            loginState();
        });
        registerImg.setOnClickListener(view -> {
            registerState();
        });
        registerBtn.setOnClickListener(view -> {
            if (registerBtn.getText().toString().equals(getString(R.string.rigister))) {
                if (usernameInput.getText().toString()==null&&pwdInput.getText().toString().trim().equals(null) | againInput.getText().toString().trim().equals(null)) {
                    Toast.makeText(this, R.string.not_empty, Toast.LENGTH_SHORT).show();
                }
                   else if (!againInput.getText().toString().trim().equals(pwdInput.getText().toString().trim())) {
                        Toast.makeText(this, R.string.noconstent, Toast.LENGTH_SHORT).show();
                    }
                 else {
                    RequestApi.getInstance().RegisterInfo(new Subscriber<RegisterBean>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            DebugUtil.debug("e", e.getMessage());
                        }

                        @Override
                        public void onNext(RegisterBean registerBean) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (registerBean.getData().getIsSuccess() == 1) {
                                        loginState();

                                    }
                                    Toast.makeText(RegisterActicity.this, registerBean.getData().getIsSuccess(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }, usernameInput.getText().toString(), pwdInput.getText().toString(), HdAppConfig.getDeviceNo());
                }
            } else {
                RequestApi.getInstance().loginInfo(new Subscriber<PersonBean>() {
                    @Override
                    public void onCompleted() {
                        HdAppConfig.setLogin(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugUtil.debug("e", e.getMessage());
                    }

                    @Override
                    public void onNext(PersonBean personBean) {

                        if (personBean.getStatus()==1) {

                            RxBus.getDefault().post(personBean);
                            RxBus.getDefault().post(new RxBusBaseMessage(8,new LoginState(8)));
                            HdAppConfig.setNickName(getString(R.string.nickname)+personBean.getData().getNick_name());
                            HdAppConfig.setLogin(true);
                            finish();
                        }
                        Toast.makeText(RegisterActicity.this, personBean.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, usernameInput.getText().toString(), pwdInput.getText().toString(), HdAppConfig.getDeviceNo());
            }


        });
    }

    private void registerState() {
        againInput.setVisibility(View.VISIBLE);
        registerBtn.setText(getString(R.string.rigister));
        switch (HdAppConfig.getLanguage()) {
            case "CHINESE":
                loginImg.setImageResource(R.mipmap.normal_login);
                registerImg.setImageResource(R.mipmap.pressed_register);
                break;
            case "ENGLISH":
                loginImg.setImageResource(R.mipmap.normal_login_en);
                registerImg.setImageResource(R.mipmap.pressed_rigister_en);
                break;
            case "JAPANESE":
                loginImg.setImageResource(R.mipmap.normal_login_jn);
                registerImg.setImageResource(R.mipmap.pressed_register_jn);
                break;
            case "KOREAN":
                loginImg.setImageResource(R.mipmap.normal_login_kn);
                registerImg.setImageResource(R.mipmap.pressed_rigister_kn);
                break;
        }
    }

    private void loginState() {
        againInput.setVisibility(View.GONE);
        registerBtn.setText(R.string.login);
        switch (HdAppConfig.getLanguage()) {
            case "CHINESE":
                loginImg.setImageResource(R.mipmap.pressed_login);
                registerImg.setImageResource(R.mipmap.normal_register);
                break;
            case "ENGLISH":
                loginImg.setImageResource(R.mipmap.pressed_login_en);
                registerImg.setImageResource(R.mipmap.normal_register_en);
                break;
            case "JAPANESE":
                loginImg.setImageResource(R.mipmap.pressed_login_jn);
                registerImg.setImageResource(R.mipmap.normal_register_jn);
                break;
            case "KOREAN":
                loginImg.setImageResource(R.mipmap.pressed_login_kn);
                registerImg.setImageResource(R.mipmap.normal_rigister_kn);
                break;
        }
    }
}
