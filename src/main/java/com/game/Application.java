package com.game;

import com.game.netty.SocketServer;
import com.game.register.RegistryFactory;

public class Application {

    public static void main(String[] args) throws InterruptedException {

        RegistryFactory factory = new RegistryFactory();
        factory.init();
        SocketServer socketServer = new SocketServer();
        socketServer.start();
    }
}
