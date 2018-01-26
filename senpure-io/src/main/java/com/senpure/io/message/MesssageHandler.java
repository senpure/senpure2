package com.senpure.io.message;

import io.netty.channel.Channel;


/**
 * Created by 罗中正 on 2017/5/27.
 */
public interface MesssageHandler<T extends Message> {


    T getEmptyMessage();
    void execute(Channel channel, T message);
    int handlerId();


}
