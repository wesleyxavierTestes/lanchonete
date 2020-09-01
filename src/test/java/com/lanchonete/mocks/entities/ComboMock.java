package com.lanchonete.mocks.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lanchonete.apllication.dto.bebida.BebidaDto;
import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.apllication.dto.combo.ComboDto;
import com.lanchonete.apllication.dto.combo.ComboItemDto;
import com.lanchonete.apllication.dto.lanche.LancheDto;
import com.lanchonete.apllication.dto.produto.ProdutoDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.combo.Combo;
import com.lanchonete.domain.enuns.produto.EnumTipoProduto;
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
public class ComboMock {

    private TestRestTemplate restTemplate;
    private int port;

    public static ComboDto dto(String nome) {

        ComboDto clienteDtoMock = ComboDto.builder()
                .nome(nome)
                .valor(new BigDecimal(22.5))
                .valorTotal(new BigDecimal(22.5))
                .categoria(null)
                .lanches(null)
                .tipoProduto(EnumTipoProduto.Combo)
                .build();
        return clienteDtoMock;
    }

    public static Combo by(String nome) {
        Combo clienteDtoMock = new Combo();
        clienteDtoMock.setNome(nome);
        clienteDtoMock.setValor(new BigDecimal(22.5));
        clienteDtoMock.setValorTotal(new BigDecimal(22.5));
        clienteDtoMock.setCategoria(null);
        clienteDtoMock.setLanches(null);
        clienteDtoMock.setTipoProduto(EnumTipoProduto.Combo);
        return clienteDtoMock;
    }


    public ComboDto COMBO(String nome, CategoriaDto categoria, LancheDto lanche, ProdutoDto comboBebida) {
        ComboDto combo = ComboMock.dto(nome);
        combo.categoria = categoria;
        combo.lanches = new ArrayList<ComboItemDto>() {{ add(Mapper.map(lanche, ComboItemDto.class)); }};
        

        String urlBebida = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.BebidaConvert, port);
        ResponseEntity<BebidaDto> responseBebida = restTemplate.exchange(urlBebida, HttpMethod.PUT, new HttpEntity<>(comboBebida, null),
        BebidaDto.class);

        combo.bebidas = new ArrayList<ComboItemDto>() {{ add(Mapper.map(responseBebida.getBody(), ComboItemDto.class)); }};
        
        combo.valor = new BigDecimal("123");
        combo.valorTotal = new BigDecimal("123");

        String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ComboSave, port);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(combo, null),
                Object.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        String json = ObjectMapperUtils.toJson(response.getBody());
        combo = ObjectMapperUtils.jsonTo(json, ComboDto.class);

        assertNotNull(lanche.codigo);

        return combo;

    }

}