package com.game.netty.model;

import com.game.entities.ServerClientMessageHeader;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerClientMessage {

   private ServerClientMessageHeader header;

    private Response response;
}
