package com.lanchonete.mocks.entities;

import com.lanchonete.apllication.dto.pedido.PedidoDto;
import com.lanchonete.domain.entities.pedido.IPedidoState;
import com.lanchonete.domain.entities.pedido.PedidoNovo;

public class PedidoMock {

    public static PedidoDto dto(String nome) {
        PedidoDto clienteDtoMock = PedidoDto.builder().nome(nome).build();
        return clienteDtoMock;
    }

    public static IPedidoState by(String nome) {
        IPedidoState clienteDtoMock = new PedidoNovo();

        return clienteDtoMock;
    }
}