package com.game.netty.parser;

import com.google.protobuf.GeneratedMessageV3;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ProtoBufferExternalizer implements Externalizer {

    private static final ThreadLocal<ProtoBufferExternalizer> EXTERNALIZER_THREAD_LOCAL = new ThreadLocal<>();

    private ProtoBufferExternalizer() {
    }

    public static ProtoBufferExternalizer getInstance() {

        if (EXTERNALIZER_THREAD_LOCAL.get() == null) EXTERNALIZER_THREAD_LOCAL.set(new ProtoBufferExternalizer());
        return EXTERNALIZER_THREAD_LOCAL.get();
    }

    public <T> byte[] getBytes(T obj) {

        GeneratedMessageV3 message = (GeneratedMessageV3) obj;
        return message.toByteArray();
    }

    public <T> T parse(byte[] data, Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {

        ByteArrayInputStream input = new ByteArrayInputStream(data);

        Method builderMethod = clazz.getMethod("newBuilder");
        GeneratedMessageV3.Builder builder = (GeneratedMessageV3.Builder) builderMethod.invoke(clazz);
        builder.mergeFrom(input);
        return (T) builder.build();
    }
}
