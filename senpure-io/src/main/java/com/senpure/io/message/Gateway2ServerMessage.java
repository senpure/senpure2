package com.senpure.io.message;

import io.netty.buffer.ByteBuf;

/**
 * Created by 罗中正 on 2018/3/1 0001.
 */
public class Gateway2ServerMessage {

    private int messageId;

    private int playerId;

    private ByteBuf buf;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }



    public ByteBuf getBuf() {
        return buf;
    }

    public void setBuf(ByteBuf buf) {
        this.buf = buf;
    }
}
