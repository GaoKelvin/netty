package com.game.netty.service;

import com.game.entities.ClientServerMessageHeader;
import com.game.netty.model.ClientServerMessage;
import com.game.netty.model.Response;
import com.game.service.RpcService;
import com.game.service.ServiceRegistry;

public class RequestExecutorService {

    private ServiceRegistry registry = ServiceRegistry.getInstance();

    public Response requestExecutor(ClientServerMessage inbound) throws Exception {

        ClientServerMessageHeader header = inbound.getHeader();
        String key = header.getService();
        RpcService service = registry.lookup(key);
        return service.processRpc(inbound.getPayload());
    }
}
