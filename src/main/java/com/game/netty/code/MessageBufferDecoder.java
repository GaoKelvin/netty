package com.game.netty.code;

import com.game.entities.ClientServerMessageHeader;
import com.game.netty.model.ClientServerMessage;
import com.game.netty.utils.IOUtil;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageBufferDecoder extends MessageCodec {

    private static final MessageBufferDecoder MESSAGE_BUFFER_DECODER = new MessageBufferDecoder();

    private MessageBufferDecoder() {
    }

    public static MessageBufferDecoder getInstance() {

        return MESSAGE_BUFFER_DECODER;
    }

    public List<ClientServerMessage> decodeMessage(ByteBuf buffer, int frameLen) throws Exception {

        List<ClientServerMessage> messages = new ArrayList<>();

        while (buffer.isReadable()) {
            byte[] headerData = readFromBuffer(buffer, frameLen);
            byte[] bodyData = readFromBuffer(buffer, frameLen);
            messages.add(generate(decode(headerData, ClientServerMessageHeader.class), bodyData));
        }

        Collections.reverse(messages);
        return messages;
    }

    private byte[] readFromBuffer(ByteBuf in, int frameLenSize) {

        byte[] lenData = new byte[frameLenSize]; // 头部长度数据
        in.readBytes(lenData, 0, frameLenSize);
        int msgLen = IOUtil.byteArrayToInt(lenData); // 头部长度

        byte[] msgData = new byte[msgLen];
        in.readBytes(msgData, 0, msgLen);
        in.markReaderIndex();
        return msgData;
    }

    private ClientServerMessage generate(ClientServerMessageHeader header, byte[] payload) {

        return new ClientServerMessage(header, payload);
    }
}
