package com.game.netty.model;

import com.game.entities.GatewayStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {

    private GatewayStatus status;

    private byte[] payload;

    private boolean compressed;

    public Response() {
        status = GatewayStatus.GW_OK;
        compressed = false;
        payload = new byte[0];
    }

    public Response(GatewayStatus status) {
        this.status = status;
        compressed = false;
        payload = new byte[0];
    }

    public Response(byte[] payload) {

        status = GatewayStatus.GW_OK;
        compressed = false;
        this.payload = payload;
    }
}
