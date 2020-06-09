package com.game.netty.parser;

public interface Externalizer {

    <T> byte[] getBytes(T obj);

    <T> T parse(byte[] data, Class<T> clazz) throws Exception;
}
