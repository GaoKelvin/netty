package com.game.netty.service;

import ch.qos.logback.classic.util.LogbackMDCAdapter;
import com.game.entities.GatewayStatus;
import com.game.entities.Platform;
import com.game.entities.ServerClientMessageHeader;
import com.game.netty.code.ServerClientMessageEncoder;
import com.game.netty.model.ClientServerMessage;
import com.game.netty.model.Response;
import com.game.netty.model.ServerClientMessage;
import com.game.netty.utils.ServerClientUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

public class HandlerService {

    private static final String PING = "ping";
    private static final String ECHO = "echo";
    private static final LogbackMDCAdapter MDC = new LogbackMDCAdapter();

    private ServerClientMessageEncoder encoder = new ServerClientMessageEncoder();

    private RequestExecutorService executorService = new RequestExecutorService();

    public void asyncMessageProcess(Channel channel, ClientServerMessage inbound, long timestamp) {

        String service = inbound.getHeader().getService();
        if (PING.equals(service)) {
            pong(channel, inbound, timestamp);
        } else if (ECHO.equals(service)) {
            echo(channel, inbound, timestamp);
        } else {
            messageProcess(timestamp, inbound, channel);
        }
    }

    private void messageProcess(long timestamp, ClientServerMessage inbound, Channel channel) {
        ServerClientMessageHeader.Builder respHeader = ServerClientMessageHeader.newBuilder();
        try {
            respHeader
                    .setVersion("version1.0.0")
                    .setTimestamp(timestamp)
                    .setTokenId(inbound.getHeader().getTokenId())
                    .setStatus(GatewayStatus.GW_OK);

            MDC.put("TokenId", inbound.getHeader().getTokenId());
            MDC.put("MessageVersion", "version1.0.0");
            MDC.put("Platform", inbound.getHeader().getPlatform().toString());

            if (!isAllowedToPass(inbound, channel)) {
                respHeader.setStatus(GatewayStatus.GW_UNDER_MAINTENANCE);
                output(channel, inbound, new ServerClientMessage(respHeader.build(), new Response(GatewayStatus.GW_UNDER_MAINTENANCE)), timestamp);
            } else if (!inbound.getHeader().getIgnoreMaintenance()) {
                respHeader.setStatus(GatewayStatus.GW_WAIT_AND_RETRY);
                output(channel, inbound, new ServerClientMessage(respHeader.build(), new Response(GatewayStatus.GW_WAIT_AND_RETRY)), timestamp);
            } else {
                try {
                    validateClientVersion(inbound, respHeader);
                    Response response = executorService.requestExecutor(inbound);
                    respHeader.setStatus(response.getStatus());
                    output(channel, inbound, new ServerClientMessage(respHeader.build(), response), timestamp);
                } catch (Exception e) {
                    output(channel, inbound, new ServerClientMessage(respHeader.build(), new Response(GatewayStatus.GW_ERROR)), timestamp);
                }
            }
        } finally {
            MDC.clear();
        }
    }

    private void pong(Channel channel, ClientServerMessage inbound, long timestamp) {

        ServerClientMessageHeader outboundHeader = ServerClientUtil.serverClientMessageHeader("version1.0.0", System.currentTimeMillis(), PING, null);
        output(channel, inbound, new ServerClientMessage(outboundHeader, new Response()), timestamp);
    }

    private void echo(Channel channel, ClientServerMessage inbound, Long timestamp) {

        ServerClientMessageHeader outboundHeader = ServerClientUtil.serverClientMessageHeader("version1.0.0", System.currentTimeMillis(), inbound.getHeader().getTokenId(), null);
        output(channel, inbound, new ServerClientMessage(outboundHeader, new Response(GatewayStatus.GW_OK, inbound.getPayload(), false)), timestamp);
    }

    private void output(Channel channel, ClientServerMessage inbound, ServerClientMessage outbound, long timestamp) {

        if (channel != null && channel.isOpen()) {
            ByteBuf buff = encoder.encodeMessage(inbound, outbound);
            channel.writeAndFlush(buff);
        }
    }

    private boolean isAllowedToPass(ClientServerMessage inbound, Channel channel) {

        boolean isAndroid = inbound.getHeader().getPlatform() == Platform.ANDROID;

        boolean underMaintenance = false; //TODO 是否停机维护 如果停机维护将关闭所有请求

        if (!underMaintenance) return true;

        if (inbound.getHeader().getIgnoreMaintenance()) return true;

        if (true) return false; // 软更验证
        return true;
    }

    private void validateClientVersion(ClientServerMessage inbound, ServerClientMessageHeader.Builder respHeader) {

        try {
            // TODO 版本验证
        } catch (Exception e) {

        }
    }
}
