package com.game.service;

import com.game.netty.code.MessageCodec;
import com.game.netty.model.Response;
import com.game.req.TestParam;

public class TestService extends MessageCodec implements RpcService {

    @Override
    public Response processRpc(byte[] payload) throws Exception {

        TestParam testParam = decode(payload);
        System.out.println(testParam.toString());
        return new Response();
    }

    private TestParam decode(byte[] payload) throws Exception {

        return decode(payload, TestParam.class);
    }
}
