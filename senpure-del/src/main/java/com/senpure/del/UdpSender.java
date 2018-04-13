package com.senpure.del;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 罗中正 on 2017/8/30.
 */
public class UdpSender {
    private static Logger logger = LoggerFactory.getLogger(UdpSender.class);

    private Channel channel = null;

    public Channel getChannel() {
        if (channel != null) {
            return channel;
        }
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new LoggingHandler(LogLevel.DEBUG));

            channel = b.bind(0).sync().channel();
            logger.debug("发送方初始化完毕");
            return channel;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Search, An Error Occur ==>", e);
        } finally {
            //group.shutdownGracefully();
        }
        return null;
    }


}
