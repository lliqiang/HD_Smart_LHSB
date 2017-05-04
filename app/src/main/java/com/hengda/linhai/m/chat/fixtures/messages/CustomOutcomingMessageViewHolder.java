package com.hengda.linhai.m.chat.fixtures.messages;

import android.view.View;

import com.hengda.linhai.m.chat.fixtures.MessagesListFixtures;
import com.stfalcon.chatkit.messages.MessagesListAdapter;


public class CustomOutcomingMessageViewHolder
        extends MessagesListAdapter.OutcomingMessageViewHolder<MessagesListFixtures.Message> {

    public CustomOutcomingMessageViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBind(MessagesListFixtures.Message message) {
        super.onBind(message);

        time.setText(message.getStatus() + " " + time.getText());
    }
}
