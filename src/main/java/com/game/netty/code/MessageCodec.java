package com.game.netty.code;

import com.game.netty.parser.Externalizer;
import com.game.netty.parser.ParserFactory;
import com.game.netty.parser.ProtoBufferExternalizer;
import com.game.netty.utils.IOUtil;

public class MessageCodec {

    private Externalizer externalizer = ParserFactory.getExternalizer(ProtoBufferExternalizer.class);

    public <T> T decode(byte[] data, Class<T> tClass) throws Exception {

        return externalizer.parse(data, tClass);
    }

    public <H> byte[] encode(H header, byte[] payload) {

        byte[] headerData = externalizer.getBytes(header); // 头部的数据

        int hlen = headerData.length; // 头部的长度

        byte[] headerLenData = IOUtil.intToByteArray(hlen); // 头部长度的数据

        int hdlen = headerLenData.length; // 头部长度数据的长度

        int plen = payload.length;
        byte[] payloadLenData = IOUtil.intToByteArray(plen); // 头部数据长度数据
        int pdlen = payloadLenData.length;

        byte[] data = new byte[hlen + hdlen + pdlen + plen];

        System.arraycopy(headerLenData, 0, data, 0, headerLenData.length); // 头部长度的数据

        System.arraycopy(headerData, 0, data, headerLenData.length, headerData.length); // 头部数据

        // 包体长度数据
        System.arraycopy(payloadLenData, 0, data, headerLenData.length + headerData.length, payloadLenData.length);

        // 包体数据
        System.arraycopy(payload, 0, data, headerLenData.length + headerData.length + payloadLenData.length, payload.length);

        return data;
    }
}
