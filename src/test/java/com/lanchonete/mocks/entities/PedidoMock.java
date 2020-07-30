package com.lanchonete.mocks.entities;

import com.lanchonete.apllication.dto.pedido.PedidoDto;
import com.lanchonete.domain.entities.pedido.IPedidoState;
import com.lanchonete.domain.entities.pedido.PedidoNovo;

import org.springframework.boot.test.web.client.TestRestTemplate;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PedidoMock {

    private TestRestTemplate restTemplate;
    private int port;

    public static PedidoDto dto(String nome) {
        PedidoDto clienteDtoMock = PedidoDto.builder()
        
        .build();
        return clienteDtoMock;
    }

    public static IPedidoState by(String nome) {
        IPedidoState clienteDtoMock = new PedidoNovo();

        return clienteDtoMock;
    }
}