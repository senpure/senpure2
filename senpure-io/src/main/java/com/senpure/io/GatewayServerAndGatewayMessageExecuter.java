package com.senpure.io;

import com.senpure.io.message.Server2GatewayMessage;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by 罗中正 on 2018/3/1 0001.
 */
public class GatewayServerAndGatewayMessageExecuter {

    private ExecutorService service;

    public GatewayServerAndGatewayMessageExecuter() {
        service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    }

    public GatewayServerAndGatewayMessageExecuter(ExecutorService service) {
        this.service = service;
    }

    private Logger logger = LoggerFactory.getLogger(GatewayServerAndGatewayMessageExecuter.class);

    public void execute(final Server2GatewayMessage message) {
        service.execute(() -> {
            Channel channel = getChannel(message);
            if (channel != null) {
                channel.writeAndFlush(message);
            } else {
                logger.warn("没有找到消息转发目的地{}", message);

            }
        });
    }

    public Channel getChannel(Server2GatewayMessage message) {

        return null;
    }
}
