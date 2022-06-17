package com.example.nettyclient.controller;

import com.example.nettyclient.NettyclientApplication;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.nettyclient.core.DiscardClientHandler.serverReStrQueue;

@RestController
@RequestMapping("/msg/send")
public class MsgSenderController {

    @PostMapping("/str")
    public String sendStr(String str){
        NettyclientApplication.discardClient.sendStr(str);
        try {
            final String take = serverReStrQueue.take();
            return  take;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  null;
    }



}
