package com.lanchonete.mocks.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import com.lanchonete.apllication.dto.estoque.EstoqueDto;
import com.lanchonete.apllication.dto.estoque.EstoqueProdutoDto;
import com.lanchonete.apllication.dto.produto.ProdutoDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.estoque.EstoqueEntrada;
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
public class EstoqueMock {

    private TestRestTemplate restTemplate;
    private int port;

    public static EstoqueDto dto() {
        EstoqueDto clienteDtoMock = EstoqueDto.builder()

                .build();
        return clienteDtoMock;
    }

    public static EstoqueEntrada by() {
        EstoqueEntrada clienteDtoMock = new EstoqueEntrada();

        return clienteDtoMock;
    }

    public void ESTOQUE(ProdutoDto produto) {
        EstoqueDto estoque = EstoqueMock.dto();
        estoque.produto = Mapper.map(produto, EstoqueProdutoDto.class);
        estoque.quantidade = 1L;

        HttpEntity<EstoqueDto> requestSave = new HttpEntity<>(estoque, null);

        String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.EstoqueSaveAdd, port);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, requestSave, Object.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}