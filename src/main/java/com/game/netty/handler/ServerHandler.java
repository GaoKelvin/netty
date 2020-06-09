package com.game.netty.handler;

import com.game.netty.code.MessageBufferDecoder;
import com.game.netty.model.ClientServerMessage;
import com.game.netty.service.HandlerService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private MessageBufferDecoder decoder = MessageBufferDecoder.getInstance();

    private HandlerService handlerService = new HandlerService();

    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        long timestamp = System.currentTimeMillis();

        ByteBuf data = (ByteBuf) msg;
        System.out.println("timestamp -------- " + timestamp);
        List<ClientServerMessage> msgsData = decoder.decodeMessage(data, 4);
        System.out.println("msgsData -------- " + msgsData);
        Future future = executorService.submit(() ->
                msgsData.forEach(m -> handlerService.asyncMessageProcess(ctx.channel(), m, timestamp))
        );

    }
}
