package com.hengda.linhai.m.chat.fixtures.messages;

import android.view.View;

import com.hengda.linhai.m.R;
import com.hengda.linhai.m.chat.fixtures.MessagesListFixtures;
import com.hengda.linhai.m.chat.fixtures.dialogs.DefaultUser;
import com.stfalcon.chatkit.messages.MessagesListAdapter;


public class CustomIncomingMessageViewHolder
        extends MessagesListAdapter.IncomingMessageViewHolder<MessagesListFixtures.Message> {
    private View onlineView;

    public CustomIncomingMessageViewHolder(View itemView) {
        super(itemView);
        onlineView = itemView.findViewById(R.id.online);
    }

    @Override
    public void onBind(MessagesListFixtures.Message message) {
        super.onBind(message);

        boolean isOnline = ((DefaultUser) message.getUser()).isOnline();
        if (isOnline) {
            onlineView.setBackgroundResource(R.drawable.shape_bubble_online);
        } else {
            onlineView.setBackgroundResource(R.drawable.shape_bubble_offline);
        }
    }
}
