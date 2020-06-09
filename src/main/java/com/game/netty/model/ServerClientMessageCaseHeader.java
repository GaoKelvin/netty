package com.game.netty.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerClientMessageCaseHeader {

    String version;

    long timestamp;

    String tokenId;
}
