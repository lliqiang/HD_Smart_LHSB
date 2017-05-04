package com.hengda.linhai.m.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hengda.linhai.m.R;
import com.hengda.linhai.m.tools.ViewUtil;

import org.w3c.dom.Text;


/**
 * 作者：Tailyou （祝文飞）
 * 时间：2016/8/3 19:41
 * 邮箱：tailyou@163.com
 * 描述：
 */
public class FloorPopWindow extends PopupWindow {

    public OnFloorChangeListener onFloorChangeListener;

    public void setOnFloorChangeListener(OnFloorChangeListener onFloorChangeListener) {
        this.onFloorChangeListener = onFloorChangeListener;
    }

    public FloorPopWindow(Activity context, int width) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.layout_pop_window, null);
        this.setContentView(contentView);
        this.setWidth(width);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        ColorDrawable dw = new ColorDrawable(0000000000);
        this.setBackgroundDrawable(dw);
        TextView picScan = ViewUtil.getView(contentView, R.id.pic_scan);
        TextView takePhoto = ViewUtil.getView(contentView, R.id.takephoto);
        TextView sleCamera = ViewUtil.getView(contentView, R.id.sle_camera);
        TextView cancelTxt=ViewUtil.getView(contentView,R.id.cancel_take);

        picScan.setOnClickListener(v -> {
            if (onFloorChangeListener != null)
                onFloorChangeListener.change(2);
            FloorPopWindow.this.dismiss();
        });
        takePhoto.setOnClickListener(v -> {
            if (onFloorChangeListener != null)
                onFloorChangeListener.change(3);
            FloorPopWindow.this.dismiss();
        });
        sleCamera.setOnClickListener(view -> {
            if (onFloorChangeListener != null)
                onFloorChangeListener.change(4);
            FloorPopWindow.this.dismiss();
        });
        cancelTxt.setOnClickListener(v -> {
            if (onFloorChangeListener != null)
            FloorPopWindow.this.dismiss();
        });
    }

    /**
     * 显示popupWindow
     *
     * @param anchor
     */
    public void showPopupWindow(View anchor) {
        if (!this.isShowing()) {
            this.showAsDropDown(anchor);
        } else {
            this.dismiss();
        }
    }

    /**
     * 楼层切换回调
     */
    public interface OnFloorChangeListener {
        void change(int floor);
    }

}
