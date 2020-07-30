package com.lanchonete.mocks.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.apllication.dto.produto.ProdutoDto;
import com.lanchonete.domain.entities.produto.entities.Produto;
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
public class ProdutoMock {
    
    private TestRestTemplate restTemplate;
    private int port;

    public static ProdutoDto dto(String nome) {
            ProdutoDto clienteDtoMock = ProdutoDto.builder()
                                .categoria(null)
                                .custo("10.1")
                                .valor("10.1")
                                .id(0)
                                .nome(nome)
                                .build();
                                    return clienteDtoMock;
    }

    public static Produto by(String nome) {
        Produto clienteDtoMock = new Produto();

                clienteDtoMock.setCusto(new BigDecimal("10.1"));
                clienteDtoMock.setValor(new BigDecimal("10.1"));
                clienteDtoMock.setNome(nome);
                clienteDtoMock.setCategoria(null);
                                return clienteDtoMock;
    }

    public ProdutoDto PRODUTO(String nome, CategoriaDto categoria) {
        // SAVE
        ProdutoDto produto = ProdutoMock.dto(nome);
        produto.categoria = categoria;

        HttpEntity<ProdutoDto> requestSave = new HttpEntity<>(produto, null);
        String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ProdutoSave, port);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, requestSave, Object.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "SAVE expect Error");
        assertNotNull(response.getBody());

        String json = ObjectMapperUtils.toJson(response.getBody());
        ProdutoDto produtoSave = ObjectMapperUtils.jsonTo(json, ProdutoDto.class);

        assertNotNull(produtoSave.codigo);

        return produtoSave;
    }

}