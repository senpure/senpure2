package com.senpure.io;



import com.senpure.io.message.Client2GatewayMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by 罗中正 on 2018/2/28 0028.
 */
public class GatewayClientAndGatewayServerHandler extends SimpleChannelInboundHandler<Client2GatewayMessage> {


    private GatewayClientAndGatewayMessageExecuter messageExecuter;

    public GatewayClientAndGatewayServerHandler(GatewayClientAndGatewayMessageExecuter messageExecuter) {
        this.messageExecuter=messageExecuter;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Client2GatewayMessage msg) throws Exception {
       messageExecuter.execute(ctx.channel(), msg);
    }

}
