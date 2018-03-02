package com.senpure.io;

import com.senpure.io.message.Client2GatewayMessage;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by 罗中正 on 2018/3/1 0001.
 */

public class GatewayClientAndGatewayMessageExecuter {
    private static Logger logger = LoggerFactory.getLogger(GatewayClientAndGatewayMessageExecuter.class);
    private ExecutorService service;
    private int loginMessageId = 0;
    private  int logoutMessageId = 1;



    public GatewayClientAndGatewayMessageExecuter() {
        service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    }

    public GatewayClientAndGatewayMessageExecuter(ExecutorService service) {
        this.service = service;
    }


    public void execute(final Channel channel, final Client2GatewayMessage message) {

        service.execute(() -> {
            logger.info("messageId {} data {}", message.getMessageId(), message.getData()[0]);
            //登录
            if (message.getMessageId() == loginMessageId) {
            }
            //退出
            else if (message.getMessageId() == logoutMessageId) {
            }
            //转发到具体的子服务器
            else {
                Integer playerId = ChannelAttributeUtil.getPlayerId(channel);
                if (playerId == null) {
                } else {
                    message.setPlayerId(playerId);
                }
            }
        });
    }
}
