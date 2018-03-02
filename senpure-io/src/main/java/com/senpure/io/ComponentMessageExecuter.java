package com.senpure.io;

import com.senpure.io.message.ComponentMessageHandler;
import com.senpure.io.message.Gateway2ServerMessage;
import com.senpure.io.message.Message;
import com.senpure.io.message.Server2GatewayMessage;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by 罗中正 on 2018/3/1 0001.
 */
public class ComponentMessageExecuter {

    private ExecutorService service;

    public ComponentMessageExecuter() {
        service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    }

    public ComponentMessageExecuter(ExecutorService service) {
        this.service = service;
    }

    private Logger logger = LoggerFactory.getLogger(ComponentMessageExecuter.class);

    public void execute(Gateway2ServerMessage gsMessage) {
        service.execute(() -> {

            int playerId = gsMessage.getPlayerId();
            ComponentMessageHandler handler = ComponentMessageHandlerUtil.getHandler(gsMessage.getMessageId());
            if (handler == null) {
                logger.warn("没有找到消息出来程序{} playerId:{}", gsMessage.getMessageId(), playerId);
                return;
            }
            Message message = handler.getEmptyMessage();


        });
    }

    public Channel getChannel(Server2GatewayMessage message) {

        return null;
    }
}
