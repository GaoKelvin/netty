package com.game.netty.parser;

public class ParserFactory {

    public static Externalizer getExternalizer(Class<? extends Externalizer> parserType) {

        if (ProtoBufferExternalizer.class.equals(parserType)) {
            return getProtoBufferExternalizer();
        } else if (JsonExternalizer.class.equals(parserType)) {
            return getJsonExternalizer();
        } else {
            return null;
        }
    }

    private static ProtoBufferExternalizer getProtoBufferExternalizer() {

        return ProtoBufferExternalizer.getInstance();
    }

    private static JsonExternalizer getJsonExternalizer() {

        return JsonExternalizer.getInstance();
    }
}
