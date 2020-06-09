package com.game.netty.utils;

import com.game.entities.ServerClientMessageHeader;

public class ServerClientUtil {

    public static ServerClientMessageHeader serverClientMessageHeader(String version, long timestamp, String tokenId, String newVersion) {

        ServerClientMessageHeader.Builder builder = ServerClientMessageHeader.newBuilder()
                .setVersion(version)
                .setTimestamp(timestamp)
                .setTokenId(tokenId);

        if (newVersion != null) {
            builder.setNewVersion(version);
        }

        return builder.build();
    }
}
