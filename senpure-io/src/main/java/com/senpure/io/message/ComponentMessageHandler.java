package com.senpure.io.message;

/**
 * Created by 罗中正 on 2017/5/27.
 */
public interface ComponentMessageHandler<T extends Message> {


    T getEmptyMessage();
    void execute(int playerId, T message);
    int handlerId();


}
