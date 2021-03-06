package com.lanchonete.mocks.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.lanchonete.apllication.dto.cardapio.CardapioDto;
import com.lanchonete.apllication.dto.cardapio.CardapioItemDto;
import com.lanchonete.apllication.dto.cliente.ClienteGenericDto;
import com.lanchonete.apllication.dto.pedido.PedidoDto;
import com.lanchonete.apllication.dto.pedido.PedidoItemDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.pedido.IPedidoState;
import com.lanchonete.domain.entities.pedido.PedidoNovo;
import com.lanchonete.domain.enuns.pedidos.EnumEstadoPedido;
import com.lanchonete.utils.ObjectMapperUtils;
import com.lanchonete.utils.URL_CONSTANTS_TEST;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PedidoMock {

    private TestRestTemplate restTemplate;
    private int port;

    public static PedidoDto dto(String nome) {
        PedidoDto pedido = PedidoDto.builder().build();
        pedido.estado = EnumEstadoPedido.Novo;
        pedido.valor = new BigDecimal("0");
        pedido.valorDesconto = new BigDecimal("0");
        pedido.valorTotal= new BigDecimal("123");
        pedido.pedidoitens = new ArrayList<>();
        return pedido;
    }

    public static IPedidoState by(String nome) {
        IPedidoState clienteDtoMock = new PedidoNovo();

        return clienteDtoMock;
    }

    public PedidoDto PEDIDO(String nome, ClienteGenericDto cliente, CardapioDto cardapio) {
        // SAVE
        PedidoDto pedido = (PedidoDto) PedidoMock.dto(nome);
        pedido.cliente = cliente;
        
        int size = cardapio.itensDisponiveis.size();
        for (int i = 0; i <  size -1 ; i++) {
            
            CardapioItemDto item = cardapio.itensDisponiveis.get(i);
            PedidoItemDto produto = Mapper.map(item, PedidoItemDto.class);

            pedido.pedidoitens.add(produto);
        }

        String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.PedidoSave, port);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(pedido, null),
                Object.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        String json = ObjectMapperUtils.toJson(response.getBody());
        pedido = ObjectMapperUtils.jsonTo(json, PedidoDto.class);

        assertNotNull(pedido);

        return pedido;
    }

}