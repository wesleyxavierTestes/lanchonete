package com.lanchonete.mocks.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import com.lanchonete.apllication.dto.pedido.PedidoDto;
import com.lanchonete.apllication.dto.venda.VendaDto;
import com.lanchonete.apllication.dto.venda.VendaItemDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.venda.Venda;
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
public class VendaMock {

    private TestRestTemplate restTemplate;
    private int port;

    public static VendaDto dto(String nome) {
        VendaDto clienteDtoMock = VendaDto.builder()
        .valor("1000")
        .valorDesconto("0")
        .valorTotal("100")
        .vendaItens(null)
        .build();
        return clienteDtoMock;
    }

    public static Venda by(String nome) {
        Venda clienteDtoMock = new Venda();

        return clienteDtoMock;
    }

    public VendaDto VENDA(String nome,  List<PedidoDto> pedidos) {
        // SAVE
        VendaDto venda = (VendaDto) VendaMock.dto(nome);
        venda.vendaItens = new ArrayList<>();
        for (PedidoDto pedidoDto : pedidos) {
            VendaItemDto vendaItemDto = Mapper.map(pedidoDto, VendaItemDto.class);
            venda.vendaItens.add(vendaItemDto);
        }        

        String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.VendaSave, port);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(venda, null),
                Object.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        String json = ObjectMapperUtils.toJson(response.getBody());
        venda = ObjectMapperUtils.jsonTo(json, VendaDto.class);

        assertNotNull(venda);

        return venda;
    }

}