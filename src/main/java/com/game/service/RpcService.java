package com.game.service;

import com.game.netty.model.Response;

public interface RpcService {

    Response processRpc(byte[] payload) throws Exception;
}
