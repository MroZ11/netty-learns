package com.example.nettyserver.core;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // Discard the received data silently.
        try {
            System.out.println("receive client msg->"+msg);
            ctx.writeAndFlush(String.format("your input : %s,is deal with ",msg));

        } finally {
            ReferenceCountUtil.release(msg);
        }
    }




}
