package com.game.register;

import com.game.service.ServiceRegistry;
import com.game.service.TestService;

public class RegistryFactory {

    ServiceRegistry registry = ServiceRegistry.getInstance();

    public void init() {
        registry.register("test", new TestService());
    }
}
