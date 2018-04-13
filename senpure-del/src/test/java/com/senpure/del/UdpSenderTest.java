package com.senpure.del;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by 罗中正 on 2018/4/12 0012.
 */

public class UdpSenderTest {


    Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void clientTest()
    {

        Channel channel = new UdpSender().getChannel();


        logger.debug(channel.toString());

        String message="hello";
        DatagramPacket datagramPacket=new DatagramPacket(Unpooled.copiedBuffer(message.getBytes(Charset.forName("UTF-8"))),
                new InetSocketAddress("192.168.1.125", 9009));
        channel.writeAndFlush(datagramPacket);

    }

    @Test
    public void serverTest() throws InterruptedException {
        UdpReceiver udpReceiver = new UdpReceiver();
        udpReceiver.start();

        Thread.sleep(200000000);
    }
}
