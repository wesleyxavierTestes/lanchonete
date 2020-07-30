package com.lanchonete.mocks.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.domain.entities.categoria.Categoria;
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
public class CategoriaMock {
    
    private TestRestTemplate restTemplate;
    private int port;

    public static CategoriaDto dto(String nome) {

        CategoriaDto clienteDtoMock = CategoriaDto.builder()
            .nome(nome)
        .build();

        return clienteDtoMock;
    }

    public static Categoria by(String nome) {
        Categoria clienteDtoMock = new Categoria();
        clienteDtoMock.setNome(nome);
        return clienteDtoMock;
    }

    public CategoriaDto CATEGORIA(String nome) {
        CategoriaDto categoria = CategoriaMock.dto(nome);

        String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.CategoriaSave, port);

        HttpEntity<CategoriaDto> requestSave = new HttpEntity<>(categoria, null);

        ResponseEntity<CategoriaDto> response = restTemplate.exchange(url, HttpMethod.POST, requestSave,
                CategoriaDto.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        String json = ObjectMapperUtils.toJson(response.getBody());
        CategoriaDto categoriaNew = ObjectMapperUtils.jsonTo(json, CategoriaDto.class);

        return categoriaNew;
    }
}