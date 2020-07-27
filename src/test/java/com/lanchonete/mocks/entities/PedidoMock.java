package com.lanchonete.mocks.entities;

import com.lanchonete.apllication.dto.pedido.PedidoDto;
import com.lanchonete.domain.entities.pedido.IPedidoState;
import com.lanchonete.domain.entities.pedido.PedidoNovo;

public class PedidoMock {

    public static PedidoDto dto() {
        PedidoDto clienteDtoMock = PedidoDto.builder()

                .build();
        return clienteDtoMock;
    }

    public static IPedidoState by() {
        IPedidoState clienteDtoMock = new PedidoNovo();

        return clienteDtoMock;
    }
}