package com.example.nettyclient.core;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class DiscardClientHandler extends ChannelInboundHandlerAdapter {

    private ByteBuf buf;

    public static LinkedBlockingQueue<String> serverReStrQueue = new LinkedBlockingQueue();



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // Discard the received data silently.
        try {
            serverReStrQueue.add(msg.toString());
            System.out.println("receive server msg->"+msg);
        } finally {
            ReferenceCountUtil.release(msg);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
