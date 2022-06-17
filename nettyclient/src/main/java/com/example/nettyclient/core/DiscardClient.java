package com.example.nettyclient.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Queue;

public class DiscardClient {



    private int serverPort;

    public DiscardClient(int port) {
        this.serverPort = port;
    }

    private Channel activeChannel;

    public void run() throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline()
                            .addLast(new StringDecoder())
                            .addLast(new DiscardClientHandler())
                            .addLast(new StringEncoder());
                }
            });

            // Start the client.
            ChannelFuture f = b.connect("127.0.0.1", serverPort).sync(); // (5)
            activeChannel = f.channel();
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public ChannelFuture sendStr(String str){
        if(activeChannel!=null && activeChannel.isActive()){
            final ChannelFuture channelFuture = activeChannel.writeAndFlush(str);
            return channelFuture;
        }
        return  null;
    }

}
