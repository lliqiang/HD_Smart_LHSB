package com.hengda.linhai.m.chat.fixtures;

import android.util.Log;

import com.hengda.linhai.m.chat.fixtures.dialogs.DefaultUser;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/*
 * Created by troy379 on 12.12.16.
 */
public final class MessagesListFixtures extends FixturesData {
    private MessagesListFixtures() {
        throw new AssertionError();
    }



    public static class Message implements IMessage {

        private long id;
        private String text;
        private Date createdAt;



        public Message(String text,Long id) {
            this.text = text;
            this.id = id;
        }

        @Override
        public Date getCreatedAt() {
            return createdAt == null ? new Date() : createdAt;
        }

        @Override
        public String getId() {
            return String.valueOf(id);
        }

        @Override
        public IUser getUser() {
            return new DefaultUser(id % 2 == 0 ? "0" : "1", id % 2 == 0 ? names.get(0) : names.get(1),
                    id % 2 == 0 ? avatars.get(0) : avatars.get(1), true);
        }

        @Override
        public String getText() {
            return text;
        }

        public String getStatus() {
            return "Sent";
        }
    }
}
