package com.hengda.linhai.m.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.rxpermisson.PermissionAppCompatActivity;
import com.hengda.linhai.m.tools.StatusBarCompat;


/**
 * 作者：Tailyou
 * 时间：2016/1/18 08:50
 * 邮箱：tailyou@163.com
 * 描述：
 */
public class BaseActivity extends PermissionAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.translucentStatusBar(this);
    }

    protected void openActivity(Context context, Class<?> pClass) {
        openActivity(context, pClass, null, null);
    }

    protected void openActivity(Context context, Class<?> pClass, Bundle pBundle) {
        openActivity(context, pClass, pBundle, null);
    }

    protected void openActivity(Context context, Class<?> pClass, String action) {
        openActivity(context, pClass, null, action);
    }

    protected void openActivity(Context context, Class<?> pClass, Bundle pBundle, String action) {
        Intent intent = new Intent(context, pClass);
        if (action != null) {
            intent.setAction(action);
        }
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }


}
