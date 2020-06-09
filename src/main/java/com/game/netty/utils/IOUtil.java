package com.game.netty.utils;

public class IOUtil {

    public static byte[] intToByteArray(int value) {
        return new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value};
    }

    public static int byteArrayToInt(byte[] data) {

        int value = 0;
        for (byte datum : data) {
            value = (value << 8) + (datum & 0xff);
        }
        return value;
    }
}
