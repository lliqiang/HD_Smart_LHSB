package com.hengda.linhai.m.view;

import android.content.Context;
import android.view.View;

import com.hengda.linhai.m.R;


/**
 * 作者：Tailyou （祝文飞）
 * 时间：2016/5/26 19:03
 * 邮箱：tailyou@163.com
 * 描述：Dialog中心
 */
public class DialogCenter {

    static HProgressDialog progressDialog;
    static HDialogBuilder hDialogBuilder;
    static HDialogJoin hDialogJoin;
    static HDialogName hDialogName;
    static HDialogComment hDialogComment;
static HDialogPwd hDialogPwd;
    /**
     * 显示ProgressDialog
     *
     * @param message
     * @param cancelable
     */
    public static void showProgressDialog(Context context, int message, boolean cancelable) {
        hideProgressDialog();
        progressDialog = new HProgressDialog(context);
        progressDialog
                .message(message)
                .tweenAnim(R.mipmap.progress_roate, R.anim.progress_rotate)
                .outsideCancelable(cancelable)
                .cancelable(cancelable)
                .show();
    }

    /**
     * 显示ProgressDialog
     *
     * @param message
     * @param cancelable
     */
    public static void showProgressDialog(Context context, String message, boolean cancelable) {
        hideProgressDialog();
        progressDialog = new HProgressDialog(context);
        progressDialog
                .message(message)
                .tweenAnim(R.mipmap.progress_roate, R.anim.progress_rotate)
                .outsideCancelable(cancelable)
                .cancelable(cancelable)
                .show();
    }

    /**
     * 隐藏ProgressDialog
     */
    public static void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * 显示Dialog-CustomView
     *
     * @param context
     * @param view
     * @param dialogClickListener
     * @param txt
     */
    public static void showDialog(Context context,
                                  View view,
                                  final DialogClickListener dialogClickListener,
                                  int... txt) {
        hideDialog();
        hDialogBuilder = new HDialogBuilder(context);
        hDialogBuilder
                .withIcon(R.mipmap.ic_launcher)
                .title(txt[0])
                .setCustomView(view)
                .pBtnText(txt[1])
                .pBtnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogClickListener.p();
                    }
                })
                .cancelable(false);
        if (txt.length == 3) {
            hDialogBuilder
                    .nBtnText(txt[2])
                    .nBtnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogClickListener.n();
                        }
                    });
        }
        hDialogBuilder.show();
    }

    /**
     * 显示Dialog-CustomView
     *
     * @param context
     * @param view
     * @param dialogClickListener
     * @param txt
     */
    public static void showCreateDialog(Context context,
                                        View view,
                                        DialogClickListener dialogClickListener,
                                        String... txt) {
        hideCreateDialog();
        hDialogJoin = new HDialogJoin(context);
        hDialogJoin
                .title(txt[0])
                .setCustomView(view)
                .pBtnText(txt[1])
                .pBtnClickListener(v -> dialogClickListener.p())
                .cancelable(false);
        if (txt.length == 3) {
            hDialogJoin
                    .nBtnText(txt[2])
                    .nBtnClickListener(v -> dialogClickListener.n());
        }
        hDialogJoin.show();
    }

    /**
     * 显示Dialog-CustomView
     *
     * @param context
     * @param view
     * @param dialogClickListener
     * @param txt
     */
    public static void showNameDialog(Context context,
                                      View view,
                                      DialogClickListener dialogClickListener,
                                      String... txt) {
        hideNameDialog();
        hDialogName = new HDialogName(context);
        hDialogName
                .title(txt[0])
                .setCustomView(view)
                .pBtnText(txt[1])
                .pBtnClickListener(v -> dialogClickListener.p())
                .cancelable(false);
        if (txt.length == 3) {
            hDialogName
                    .nBtnText(txt[2])
                    .nBtnClickListener(v -> dialogClickListener.n());
        }
        hDialogName.show();
    }


    /**
     * 显示Dialog-CustomView
     *
     * @param context
     * @param view
     * @param dialogClickListener
     * @param txt
     */
    public static void showPwdDialog(Context context,
                                      View view,
                                      DialogClickListener dialogClickListener,
                                      String... txt) {
        hidePwdDialog();
        hDialogPwd = new HDialogPwd(context);
        hDialogPwd
                .title(txt[0])
                .setCustomView(view)
                .pBtnText(txt[1])
                .pBtnClickListener(v -> dialogClickListener.p())
                .cancelable(false);
        if (txt.length == 3) {
            hDialogPwd
                    .nBtnText(txt[2])
                    .nBtnClickListener(v -> dialogClickListener.n());
        }
        hDialogPwd.show();
    }



    public static void showCommentDialog(Context context,
                                      View view,
                                      DialogClickListener dialogClickListener,
                                      String... txt) {
        hideCommentDialog();
        hDialogComment = new HDialogComment(context);
        hDialogComment
                .title(txt[0])
                .setCustomView(view)
                .pBtnText(txt[1])
                .pBtnClickListener(v -> dialogClickListener.p())
                .cancelable(false);
        if (txt.length == 3) {
            hDialogComment
                    .nBtnText(txt[2])
                    .nBtnClickListener(v -> dialogClickListener.n());
        }
        hDialogComment.show();
    }

    /**
     * 显示Dialog-CustomView
     *
     * @param context
     * @param view
     * @param dialogClickListener
     * @param txt
     */
    public static void showDialog(Context context,
                                  View view,
                                  final DialogClickListener dialogClickListener,
                                  String... txt) {
        hideDialog();
        hDialogBuilder = new HDialogBuilder(context);
        hDialogBuilder
                .withIcon(R.mipmap.ic_launcher)

                .title(txt[0])
                .setCustomView(view)
                .pBtnText(txt[1])
                .pBtnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogClickListener.p();
                    }
                })
                .cancelable(false);
        if (txt.length == 3) {
            hDialogBuilder
                    .nBtnText(txt[2])
                    .nBtnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogClickListener.n();
                        }
                    });
        }
        hDialogBuilder.show();
    }

    /**
     * 显示Dialog-Message
     *
     * @param context
     * @param dialogClickListener
     * @param txt
     */
    public static void showDialog(Context context,
                                  final DialogClickListener dialogClickListener,
                                  int... txt) {
        hideDialog();
        hDialogBuilder = new HDialogBuilder(context);
        hDialogBuilder
                .withIcon(R.mipmap.ic_launcher)

                .title(txt[0])
                .message(txt[1])
                .pBtnText(txt[2])
                .pBtnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogClickListener.p();
                    }
                })
                .cancelable(false);
        if (txt.length == 4) {
            hDialogBuilder
                    .nBtnText(txt[3])
                    .nBtnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogClickListener.n();
                        }
                    });
        }
        hDialogBuilder.show();
    }

    /**
     * 显示Dialog-Message
     *
     * @param context
     * @param dialogClickListener
     * @param txt
     */
    public static void showDialog(Context context,
                                  final DialogClickListener dialogClickListener,
                                  String... txt) {
        hideDialog();
        hDialogBuilder = new HDialogBuilder(context);
        hDialogBuilder
                .withIcon(R.mipmap.ic_launcher)

                .title(txt[0])
                .message(txt[1])
                .pBtnText(txt[2])
                .pBtnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogClickListener.p();
                    }
                })
                .cancelable(false);
        if (txt.length == 4) {
            hDialogBuilder
                    .nBtnText(txt[3])
                    .nBtnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogClickListener.n();
                        }
                    });
        }
        hDialogBuilder.show();
    }

    /**
     * 隐藏Dialog
     */
    public static void hideDialog() {
        if (hDialogBuilder != null) {
            hDialogBuilder.dismiss();
            hDialogBuilder = null;
        }
    }

    /**
     * 隐藏Dialog
     */
    public static void hideCreateDialog() {
        if (hDialogJoin != null) {
            hDialogJoin.dismiss();
            hDialogJoin = null;
        }
    }

    /**
     * 隐藏Dialog
     */
    public static void hideNameDialog() {
        if (hDialogName != null) {
            hDialogName.dismiss();
            hDialogName = null;
        }
    }
    /**
     * 隐藏Dialog
     */
    public static void hideCommentDialog() {
        if (hDialogComment != null) {
            hDialogComment.dismiss();
            hDialogComment = null;
        }
    }

    /**
     * 隐藏PwdDialog
     */
    public static void hidePwdDialog() {
        if (hDialogPwd != null) {
            hDialogPwd.dismiss();
            hDialogPwd = null;
        }
    }
}
