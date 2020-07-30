package com.lanchonete.mocks.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.apllication.dto.combo.ComboDto;
import com.lanchonete.apllication.dto.combo.ComboItemDto;
import com.lanchonete.apllication.dto.lanche.LancheDto;
import com.lanchonete.apllication.dto.produto.ProdutoDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.combo.Combo;
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
                .valor(new BigDecimal(22.5).toString())
                .valorTotal(new BigDecimal(22.5).toString())
                .categoria(null)
                .lanche(null)
                .build();
        return clienteDtoMock;
    }

    public static Combo by(String nome) {
        Combo clienteDtoMock = new Combo();
        clienteDtoMock.setNome(nome);
        clienteDtoMock.setValor(new BigDecimal(22.5));
        clienteDtoMock.setValorTotal(new BigDecimal(22.5));
        clienteDtoMock.setCategoria(null);
        clienteDtoMock.setLanche(null);
        return clienteDtoMock;
    }


    public ComboDto COMBO(String nome, CategoriaDto categoria, LancheDto lanche, ProdutoDto comboBebida) {

        ComboDto combo = ComboMock.dto(nome);
        combo.categoria = categoria;
        combo.lanche = Mapper.map(lanche, ComboItemDto.class);
        combo.bebida = Mapper.map(comboBebida, ComboItemDto.class);
        combo.valor = "123";
        combo.valorTotal = "123";

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