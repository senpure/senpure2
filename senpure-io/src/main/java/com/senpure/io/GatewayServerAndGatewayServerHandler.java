package com.senpure.io;



import com.senpure.io.message.Server2GatewayMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by 罗中正 on 2018/2/28 0028.
 */
public class GatewayServerAndGatewayServerHandler extends SimpleChannelInboundHandler<Server2GatewayMessage> {

    private GatewayServerAndGatewayMessageExecuter messageExecuter;

    public GatewayServerAndGatewayServerHandler(GatewayServerAndGatewayMessageExecuter messageExecuter) {
        this.messageExecuter= messageExecuter;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Server2GatewayMessage msg) throws Exception {
        messageExecuter.execute(msg);
    }

}
