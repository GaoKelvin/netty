package com.game.netty.command;

public enum Commands {

    PING(0,"ping"),

    PONG(1,"pong"),

    ECHO(2,"echo"),

    WRITE(3,"write");

    private int index;

    private String value;

    private Commands(int i, String command) {
        index = i;
        value = command;
    }
}
