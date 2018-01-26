package com.senpure.io;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;


/**
 * Created by 罗中正 on 2017/5/26.
 */

public class GameServer {
    protected Logger logger = LoggerFactory.getLogger(GameServer.class);
    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", "1234"));

    @Autowired
    private IOMessageProperties ioMessageProperties;
    @PostConstruct
    public void init() throws CertificateException, SSLException {
        {
            logger.debug("启动游戏服务器，监听端口号 {}", PORT);
            // Configure SSL.
            final SslContext sslCtx;
            if (SSL) {
                SelfSignedCertificate ssc = new SelfSignedCertificate();
                sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
            } else {
                sslCtx = null;
            }
            // Configure the server.
            final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            final EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {

                ServerBootstrap b = new ServerBootstrap();
                b.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .option(ChannelOption.SO_BACKLOG, 100)
                        .handler(new LoggingHandler(LogLevel.INFO))
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            public void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline p = ch.pipeline();
                                if (sslCtx != null) {
                                    p.addLast(sslCtx.newHandler(ch.alloc()));
                                }

                                p.addLast(new ByteBufToMessageDecoder());
                                p.addLast(new LoggingHandler(LogLevel.DEBUG));
                                p.addLast(new MessageToByteBufEncoder());
                                OffLineHandler offLineHandler = new OffLineHandler();
                                ChannelAttributeUtil.setOfflineHandler(ch, offLineHandler);
                                p.addLast(offLineHandler);
                                p.addLast(new ServerHandler());

                            }
                        });

                // Start the server.
                final ChannelFuture f = b.bind(PORT).sync();
                // f.channel().close()
                // Wait until the server socket is closed.

                new Thread(() -> {
                    try {
                        f.channel().closeFuture().sync();
                    } catch (InterruptedException e) {
                        logger.error("", e);
                    } finally {
                        bossGroup.shutdownGracefully();
                        workerGroup.shutdownGracefully();
                    }
                }).start();
            } catch (InterruptedException e) {
                logger.error("", e);
            }

        }
    }

}
