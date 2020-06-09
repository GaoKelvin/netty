package com.game.netty.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonExternalizer implements Externalizer {

    private static final ThreadLocal<JsonExternalizer> EXTERNALIZER_THREAD_LOCAL = new ThreadLocal<>();

    private JsonExternalizer() {
    }

    public static JsonExternalizer getInstance() {

        if (EXTERNALIZER_THREAD_LOCAL.get() == null) EXTERNALIZER_THREAD_LOCAL.set(new JsonExternalizer());
        return EXTERNALIZER_THREAD_LOCAL.get();
    }

    @Override
    public <T> byte[] getBytes(T obj) {

        return JSON.toJSONBytes(obj, SerializerFeature.UseISO8601DateFormat);
    }

    @Override
    public <T> T parse(byte[] data, Class<T> clazz) {

        return JSON.parseObject(data, clazz);
    }
}
