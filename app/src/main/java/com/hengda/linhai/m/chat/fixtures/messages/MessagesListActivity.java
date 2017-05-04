package com.hengda.linhai.m.chat.fixtures.messages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hengda.linhai.m.R;
import com.hengda.linhai.m.adapter.ChatSamplesListAdapter;
import com.hengda.linhai.m.app.HdAppConfig;
import com.hengda.linhai.m.bean.ChatRecord;
import com.hengda.linhai.m.bean.GetGroupBean;
import com.hengda.linhai.m.bean.RegisterBean;
import com.hengda.linhai.m.chat.fixtures.MessagesListFixtures;
import com.hengda.linhai.m.http.RequestApi;
import com.hengda.linhai.m.tools.DebugUtil;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import rx.Subscriber;

public class MessagesListActivity extends AppCompatActivity
        implements MessagesListAdapter.SelectionListener {

    private static final String ARG_TYPE = "type";
    @Bind(R.id.back_chat)
    ImageView backChat;
    @Bind(R.id.name_chat)
    TextView nameChat;

    private MessagesList messagesList;
    private MessagesListAdapter<MessagesListFixtures.Message> adapter;
    private MessageInput input;
    private int selectionCount;
    private MessagesListFixtures.Message message;
    private GetGroupBean.DataBean.GroupMemberBean groupMemberBean;
    private Menu menu;
    private List<MessagesListFixtures.Message> messageList = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_list_attr);
        ButterKnife.bind(this);
        messagesList = (MessagesList) findViewById(R.id.messagesList);
        groupMemberBean = (GetGroupBean.DataBean.GroupMemberBean) getIntent().getSerializableExtra("message");
        initMessagesAdapter();
        backChat.setOnClickListener(view -> finish());
        if (groupMemberBean.getNick_name() != null) {
            nameChat.setText(groupMemberBean.getNick_name().toString());
        } else {
            nameChat.setText(groupMemberBean.getDevice_id());
        }

        // TODO: 2017/4/28 获取聊天记录
        getChatRecord();


        input = (MessageInput) findViewById(R.id.input);
        input.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                RequestApi.getInstance().sendMsg(new Subscriber<RegisterBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugUtil.debug("e", e.getMessage());
                    }

                    @Override
                    public void onNext(RegisterBean registerBean) {
                        Toasty.warning(MessagesListActivity.this, registerBean.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }, HdAppConfig.getDeviceNo(), groupMemberBean.getDevice_id(), input.toString());
                message = new MessagesListFixtures.Message(input.toString(), 1L);

                adapter.addToStart(message, true);
                return true;
            }
        });
    }

    private void getChatRecord() {
        RequestApi.getInstance().getMsgRecord(new Subscriber<ChatRecord>() {
            @Override
            public void onCompleted() {
adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                DebugUtil.debug("e", e.getMessage());
            }

            @Override
            public void onNext(ChatRecord chatRecord) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageList.clear();
                        for (int i = 0; i < chatRecord.getData().getChat_content().size(); i++) {
                            message = new MessagesListFixtures.Message(chatRecord.getData().getChat_content().get(i).getContent(), Long.valueOf(chatRecord.getData().getChat_content().get(i).getFrom()));
//                            adapter.addToStart(message,true);
                            messageList.add(message);
                        }
                        adapter.addToEnd(messageList, false);
                        adapter.notifyDataSetChanged();

                    }
                });

            }
        }, HdAppConfig.getDeviceNo(), groupMemberBean.getDevice_id(), "聊天内容", "1", "100");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.chat_actions_menu, menu);
        onSelectionChanged(0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                adapter.deleteSelectedMessages();
                break;
        }
        return true;
    }

    @Override
    public void onSelectionChanged(int count) {
        this.selectionCount = count;
        menu.findItem(R.id.action_delete).setVisible(count > 0);
    }

    @Override
    public void onBackPressed() {
        if (selectionCount == 0) {
            super.onBackPressed();
        } else {
            adapter.unselectAllItems();
        }
    }

    private void initMessagesAdapter() {
        //设置头像
        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.with(MessagesListActivity.this).load(url).into(imageView);
            }
        };

        //设置聊天布局
        MessagesListAdapter.HoldersConfig holdersConfig = new MessagesListAdapter.HoldersConfig();
        holdersConfig.setIncomingLayout(R.layout.item_custom_incoming_message);
        holdersConfig.setOutcomingLayout(R.layout.item_custom_outcoming_message);
        adapter = new MessagesListAdapter<>("1", holdersConfig, imageLoader);
// TODO: 2017/4/28 获取聊天记录

        messagesList.setAdapter(adapter);
    }

    public static void open(Activity activity, ChatSamplesListAdapter.ChatSample.Type type) {
        Intent intent = new Intent(activity, MessagesListActivity.class);
        intent.putExtra(ARG_TYPE, type);
        activity.startActivity(intent);
    }

    private void loadMessages() {
        new Handler().postDelayed(new Runnable() { //imitation of internet connection
            @Override
            public void run() {


            }
        }, 1000);
    }

}
