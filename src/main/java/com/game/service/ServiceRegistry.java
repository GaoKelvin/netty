package com.game.service;

import com.game.exception.ServiceNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class ServiceRegistry {

    Map<String, RpcService> serviceMap = new HashMap<>();

    private ServiceRegistry() {
    }

    private static final ServiceRegistry REGISTRY = new ServiceRegistry();

    public static ServiceRegistry getInstance() {

        return REGISTRY;
    }

    public void register(String key, RpcService rpcService) {

        serviceMap.putIfAbsent(key, rpcService);
    }

    public RpcService lookup(String key) {

        RpcService rpcService = serviceMap.get(key);
        if (rpcService == null) {
            throw new ServiceNotFoundException("service not found of " + key);
        }
        return rpcService;
    }
}
