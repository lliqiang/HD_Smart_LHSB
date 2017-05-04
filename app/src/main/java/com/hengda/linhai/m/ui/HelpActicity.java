package com.hengda.linhai.m.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengda.linhai.m.R;
import com.hengda.linhai.m.view.NotifyDialog;
import com.michael.easydialog.EasyDialog;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HelpActicity extends Activity {

    @Bind(R.id.back_sos)
    ImageView backSos;
    @Bind(R.id.health_tv)
    TextView healthTv;
    @Bind(R.id.hurt_tv)
    TextView hurtTv;
    @Bind(R.id.other_sos)
    TextView otherSos;
private NotifyDialog notifyDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_acticity);
        ButterKnife.bind(this);
        notifyDialog=new NotifyDialog(this);
        notifyDialog.pBtnText("确定")
                .pBtnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        notifyDialog.dismiss();
                    }
                });
        backSos.setOnClickListener(view -> finish());
        healthTv.setOnClickListener(view -> {
           if (!notifyDialog.isShowing()){
               notifyDialog.show();
           }

        });
        hurtTv.setOnClickListener(view -> {
            if (!notifyDialog.isShowing()){
                notifyDialog.show();
            }
        });
        otherSos.setOnClickListener(view -> {
            if (!notifyDialog.isShowing()){
                notifyDialog.show();
            }
        });
    }
}
