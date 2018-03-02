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

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

/**
 * Created by 罗中正 on 2018/2/28 0028.
 */


public class GatewayClientAndGatewayServer {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private IOServerProperties properties;

    private IOMessageProperties ioMessageProperties;
    private ChannelFuture channelFuture;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
   private  GatewayClientAndGatewayMessageExecuter messageExecuter;

    private String serverName = "网关[CS]服务器";

    public void start() throws CertificateException, SSLException {
        {
            if (properties == null) {
                properties = new IOServerProperties();
            }
            if (ioMessageProperties == null) {
                ioMessageProperties = new IOMessageProperties();
                ioMessageProperties.setInFormat(properties.isCsInFormat());
                ioMessageProperties.setOutFormat(properties.isCsOutFormat());
            }
            if (messageExecuter == null) {
                messageExecuter = new GatewayClientAndGatewayMessageExecuter();
            }
            logger.info("启动{}，监听端口号 {}", getServerName(), properties.getCsPort());
            serverName = serverName + "[" + properties.getCsPort() + "]";
            // Configure SSL.
            final SslContext sslCtx;
            if (properties.isSsl()) {
                SelfSignedCertificate ssc = new SelfSignedCertificate();
                sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
            } else {
                sslCtx = null;
            }
            // Configure the server.
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup();

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
                                p.addLast(new GatewayClientAndGatewayMessageDecoder());
                                p.addLast(new GatewayClientAndGatewayMessageEncoder());
                                p.addLast(new MessageLoggingHandler(LogLevel.DEBUG, ioMessageProperties));
                                OffLineHandler offLineHandler = new OffLineHandler();
                                ChannelAttributeUtil.setOfflineHandler(ch, offLineHandler);
                                p.addLast(offLineHandler);
                                p.addLast(new GatewayClientAndGatewayServerHandler(messageExecuter));

                            }
                        });
                // Start the server.

                channelFuture = b.bind(properties.getCsPort()).sync();
                logger.info("{}启动完成", getServerName());
            } catch (Exception e) {

                logger.error("启动" + getServerName() + " 失败", e);
                destroy();
            }

        }
    }


    public void destroy() {
        if (channelFuture != null) {
            channelFuture.channel().close();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        logger.debug("关闭{}并释放资源 ", getServerName());

    }

    public String getServerName() {
        return serverName;
    }


    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public void setProperties(IOServerProperties properties) {
        this.properties = properties;
    }

    public GatewayClientAndGatewayMessageExecuter getMessageExecuter() {
        return messageExecuter;
    }

    public void setMessageExecuter(GatewayClientAndGatewayMessageExecuter messageExecuter) {
        this.messageExecuter = messageExecuter;
    }
}
