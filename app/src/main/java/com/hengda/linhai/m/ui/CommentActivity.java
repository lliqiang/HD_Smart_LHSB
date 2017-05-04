package com.hengda.linhai.m.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hengda.linhai.m.R;
import com.hengda.linhai.m.adapter.LCommonAdapter;
import com.hengda.linhai.m.adapter.ViewHolder;
import com.hengda.linhai.m.app.HdAppConfig;
import com.hengda.linhai.m.bean.CommentDetailBean;
import com.hengda.linhai.m.bean.JoinGroup;
import com.hengda.linhai.m.bean.MessageDetailBean;
import com.hengda.linhai.m.bean.RegisterBean;
import com.hengda.linhai.m.http.RequestApi;
import com.hengda.linhai.m.tools.DebugUtil;
import com.hengda.linhai.m.tools.ViewUtil;
import com.hengda.linhai.m.view.DialogCenter;
import com.hengda.linhai.m.view.DialogClickListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import rx.Subscriber;

public class CommentActivity extends Activity {

    @Bind(R.id.back_comment)
    ImageView backComment;
    @Bind(R.id.lv_comment)
    ListView lvComment;
    @Bind(R.id.makeComment)
    EditText makeComment;
    @Bind(R.id.count_comment)
    TextView countComment;
    @Bind(R.id.activity_comment)
    LinearLayout activityComment;
    private CommentDetailBean detailBean;
    private LCommonAdapter<CommentDetailBean.DataBean> adapter;
    private String exhibit_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        exhibit_id = getIntent().getStringExtra("exhibit_id");
        backComment.setOnClickListener(view -> finish());
        makeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCommentDialog();
            }
        });
        RequestApi.getInstance().getCommentDetail(new Subscriber<CommentDetailBean>() {
            @Override
            public void onCompleted() {
                countComment.setText(detailBean.getData().size()+"");
                adapter = new LCommonAdapter<CommentDetailBean.DataBean>(CommentActivity.this, R.layout.item_comment, detailBean.getData()) {
                    @Override
                    public void convert(ViewHolder holder, CommentDetailBean.DataBean dataBean) {
                        holder.setText(R.id.username_comment, dataBean.getNick_name());
                        holder.setText(R.id.time_comment, dataBean.getTime());
                        holder.setText(R.id.content_comment,dataBean.getContent());
                        Glide.with(CommentActivity.this).load(dataBean.getHead_img()).placeholder(R.mipmap.def_head).into((ImageView)holder.getView(R.id.img_head_comment));

                    }
                };
                lvComment.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                DebugUtil.debug("e", e.getMessage());
            }

            @Override
            public void onNext(CommentDetailBean commentDetailBean) {
                detailBean = commentDetailBean;
            }
        }, exhibit_id);

    }

    /*
        * 发表评论的弹框
        * */
    private void makeCommentDialog() {
        View root = View.inflate(CommentActivity.this, R.layout.edit_comment, null);
        EditText edtgroup = (EditText) root.findViewById(R.id.edit_makeComment);
        Selection.setSelection(edtgroup.getText(), edtgroup.getText().length());
        DialogCenter.showCommentDialog(CommentActivity.this, root, new DialogClickListener() {
            @Override
            public void n() {
                super.n();
                DialogCenter.hideCommentDialog();
                ViewUtil.hideDownloadProgressDialog();
            }

            @Override
            public void p() {
                String proupId = edtgroup.getText().toString();
                proupId = proupId.replaceAll("(\r\n|\r|\n|\n\r)", "");
                if (TextUtils.isEmpty(proupId)) {
                    Toasty.warning(CommentActivity.this, "评论内容为空", Toast.LENGTH_SHORT).show();
                } else {
                    ViewUtil.showDownloadProgressDialog(CommentActivity.this, "请稍后...");

                    RequestApi.getInstance().makeComment(new Subscriber<RegisterBean>() {
                        @Override
                        public void onCompleted() {
                            DialogCenter.hideCommentDialog();
                            ViewUtil.hideDownloadProgressDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("e", e.getMessage());
                            DialogCenter.hideCommentDialog();
                            ViewUtil.hideDownloadProgressDialog();
                        }

                        @Override
                        public void onNext(RegisterBean registerBean) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (registerBean.getStatus() == 1) {
                                        Toasty.success(CommentActivity.this, registerBean.getMsg(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toasty.error(CommentActivity.this, registerBean.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    }, HdAppConfig.getDeviceNo(), exhibit_id, proupId);

                }
            }
        }, new String[]{"所在群组",
                "确定",
                "取消"});

    }
}
