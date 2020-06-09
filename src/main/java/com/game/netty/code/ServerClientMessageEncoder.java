package com.game.netty.code;

import com.game.entities.ServerClientMessageHeader;
import com.game.netty.model.ClientServerMessage;
import com.game.netty.model.ServerClientMessage;
import io.netty.buffer.ByteBuf;

public class ServerClientMessageEncoder extends MessageBufferEncoder {

    public ByteBuf encodeMessage(ClientServerMessage inbound, ServerClientMessage outbound) {

        ServerClientMessageHeader header = outbound.getHeader().toBuilder().setCompressed(outbound.getResponse().isCompressed()).build();
        return super.encodeMessage(new ServerClientMessage(header, outbound.getResponse()));
    }
}
