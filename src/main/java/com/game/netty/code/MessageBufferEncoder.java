package com.game.netty.code;

import com.game.netty.model.ServerClientMessage;
import com.game.netty.utils.IOUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class MessageBufferEncoder extends MessageCodec {

    int frameLenSize = 4; // TODO 后期通过配置设置

    protected ByteBuf encodeMessage(ServerClientMessage message) {

        byte[] data = encode(message.getHeader(), message.getResponse().getPayload());
        return writeToBuffer(data);
    }

    private ByteBuf writeToBuffer(byte[] data) {

        ByteBuf buff = Unpooled.buffer(frameLenSize + data.length);
        buff.writeBytes(IOUtil.intToByteArray(data.length));
        buff.writeBytes(data);
        return buff;
    }
}
