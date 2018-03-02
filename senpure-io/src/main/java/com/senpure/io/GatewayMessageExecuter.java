package com.senpure.io;

import com.senpure.io.message.Client2GatewayMessage;
import com.senpure.io.message.SCRegistrationMessage;
import com.senpure.io.message.Server2GatewayMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by 罗中正 on 2018/3/1 0001.
 */

public class GatewayMessageExecuter {
    private static Logger logger = LoggerFactory.getLogger(GatewayMessageExecuter.class);
    private ExecutorService service;
    private int csLoginMessageId = 1;
    private int csLogoutMessageId = 2;

    private int scLoginMessageId = 3;
    private int scLogoutMessageId = 4;
    private int serverRegMessageId = 100102;
    private ComponentChannelServer componentChannelServer;

    private ConcurrentMap<Integer, Channel> prepLoginChannels = new ConcurrentHashMap<>(128);


    private ConcurrentMap<String, List<ComponentChannelServer>> handleMessageMap = new ConcurrentHashMap<>(128);


    private ConcurrentMap<Integer, ComponentChannelServer> handleComponentMap = new ConcurrentHashMap<>(128);

    public GatewayMessageExecuter() {
        service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    }

    public GatewayMessageExecuter(ExecutorService service) {
        this.service = service;
    }


    public void execute(final Channel channel, final Client2GatewayMessage message) {
        service.execute(() -> {
            logger.info("messageId {} data {}", message.getMessageId(), message.getData()[0]);
            //登录
            if (message.getMessageId() == csLoginMessageId) {
                prepLoginChannels.putIfAbsent(channel.hashCode(), channel);
                message.setPlayerId(channel.hashCode());
            }
            //转发到具体的子服务器
            Integer playerId = ChannelAttributeUtil.getPlayerId(channel);
            if (playerId != null) {
                message.setPlayerId(playerId);
            }
            Channel componentChannel = null;
            if (componentChannel == null) {
                logger.info("没有找到消息的接收服务器{}", message.getMessageId());
                return;
            }
            componentChannel.writeAndFlush(message);

        });
    }

    public Channel getChannel(Client2GatewayMessage message) {

        return null;
    }

    public void execute(Channel channel, final Server2GatewayMessage message) {
        if (message.getMessageId() == serverRegMessageId) {
            regHandleMessage(channel, message);
            return;
        }
        if (message.getMessageId() == scLoginMessageId) {
            int playerId = message.getPlayerIds()[0];
            int channelId = message.getPlayerIds()[1];
            Channel clientChannel = prepLoginChannels.remove(channelId);
            if (clientChannel != null) {
                ChannelAttributeUtil.setPlayerId(clientChannel, playerId);

            }
            int[] data = new int[1];
            data[0] = playerId;
            message.setPlayerIds(data);
        }
        for (int playerId : message.getPlayerIds()) {
            Channel clientChannel = null;
            if (clientChannel == null) {
                logger.warn("没有找到玩家{}", playerId);
            } else {
                clientChannel.writeAndFlush(message);
            }
        }
    }

    public synchronized void regHandleMessage(Channel channel, final Server2GatewayMessage message) {
        SCRegistrationMessage registrationMessage = new SCRegistrationMessage();
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(message.getData());
        registrationMessage.read(buf);
        List<ComponentChannelServer> componentChannelServers = handleMessageMap.get(registrationMessage.getServerName());
        //一个实例服务器也没注册
        if (componentChannelServers == null) {
            componentChannelServers = new ArrayList<>();
            ComponentChannelServer componentChannelServer = createComponentChannelServer(channel, registrationMessage.getMessageIds());
            componentChannelServers.add(componentChannelServer);
            handleMessageMap.putIfAbsent(registrationMessage.getServerName(), componentChannelServers);
        }
        //已经注册过了
        else {
            Set<Integer> allIds = new HashSet<>();
            allIds.addAll(registrationMessage.getMessageIds());
            for (ComponentChannelServer handle : componentChannelServers) {
                boolean canUseThis = false;
                for (int i = 0; i < registrationMessage.getMessageIds().size(); i++) {
                    Integer messageId = registrationMessage.getMessageIds().get(i);
                    if (handle.handleMessageId(messageId)) {
                        allIds.remove(messageId);
                        canUseThis = true;
                    }
                }
                if (canUseThis) {
                    handle.addChannel(channel);
                }
            }
            if (allIds.size() > 0) {
                ComponentChannelServer componentChannelServer = createComponentChannelServer(channel, allIds);
                componentChannelServers.add(componentChannelServer);
            }

        }
        for (int i = 0; i < registrationMessage.getMessageIds().size(); i++) {
            logger.info("{} 注册处理函数 {}-> {} ", registrationMessage.getServerName(), registrationMessage.getMessageIds().get(i), registrationMessage.getMessageClasses().get(i));
            int messageId = registrationMessage.getMessageIds().get(i);
        }
    }


    private ComponentChannelServer createComponentChannelServer(Channel channel, Collection<Integer> messageIds) {

        ComponentChannelServer componentChannelServer = new ComponentChannelServer();
        for (Integer id : messageIds) {
            componentChannelServer.markMessageId(id);
            handleComponentMap.putIfAbsent(id, componentChannelServer);
        }
        componentChannelServer.addChannel(channel);
        return componentChannelServer;
    }
}
