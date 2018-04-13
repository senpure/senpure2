package com.senpure.del;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by 罗中正 on 2018/4/12 0012.
 */
public class UdpReceiver {

    Logger logger = LoggerFactory.getLogger(getClass());
    public void  start() throws InterruptedException {
        final NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioDatagramChannel.class);
        bootstrap.group(nioEventLoopGroup);
        bootstrap.handler(new ChannelInitializer<NioDatagramChannel>() {
            @Override
            protected void initChannel(NioDatagramChannel ch) throws Exception {
                ChannelPipeline cp = ch.pipeline();
                cp.addLast("framer", new MessageToMessageDecoder<DatagramPacket>() {
                    @Override
                    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
                        out.add(msg.content().toString(Charset.forName("UTF-8")));
                    }
                }).addLast("handler", (ChannelHandler) new UdpHandler());
            }
        });
        // 监听端口
        ChannelFuture sync = bootstrap.bind(9009).sync();
        Channel udpChannel = sync.channel();
        logger.debug(udpChannel.toString());
    }
}
