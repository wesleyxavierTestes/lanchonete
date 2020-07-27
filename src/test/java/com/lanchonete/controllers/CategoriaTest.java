package com.lanchonete.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;

import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.apllication.validations.CustomErro;
import com.lanchonete.domain.entities.categoria.Categoria;
import com.lanchonete.domain.services.categoria.CategoriaService;
import com.lanchonete.mocks.pages.CategoriaUtilsPageMock;
import com.lanchonete.utils.URL_CONSTANTS_TEST;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CategoriaTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    protected CategoriaService _service;

    @Test
    @DisplayName("Deve converter uma CategoriaDto para Categoria incluindo Endereco")
    public void converterClientDto() throws Exception {

        Categoria categoria = Mapper.map(new CategoriaDto());
        assertNotNull(categoria);
    }

    @Nested
    @DisplayName(value = "Testes com categorias Invalidos")
    class CategoriaInvalid {
        @Test
        @DisplayName("Deve listar todos categorias com lista vazia")
        public void listar() throws Exception {
            String url = String.format(URL_CONSTANTS_TEST.CategoriaList + "/?page=1", port);

            ResponseEntity<CategoriaUtilsPageMock> response = restTemplate.getForEntity(new URL(url).toString(),
                    CategoriaUtilsPageMock.class);
            CategoriaUtilsPageMock page = response.getBody();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(page);
            assertNotNull(page.content);
        }

        @Test
        @DisplayName("Deve buscar um categoria")
        public void find_inexistente() throws Exception {
            String url = String.format(URL_CONSTANTS_TEST.CategoriaFind + "/?id=100000", port);

            ResponseEntity<String> response = restTemplate.getForEntity(new URL(url).toString(), String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @Test
        @DisplayName("Deve tentar salvar categoria invalido")
        public void save_invalid_test() throws Exception {
            String url = String.format(URL_CONSTANTS_TEST.CategoriaSave, port);

            CategoriaDto entity = new CategoriaDto();
            HttpEntity<CategoriaDto> requestUpdate = new HttpEntity<>(entity, null);

            ResponseEntity<CustomErro[]> response = restTemplate.exchange(new URL(url).toString(), HttpMethod.POST,
                    requestUpdate, CustomErro[].class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
            assertTrue(response.getBody().length > 0);
        }

        @ParameterizedTest
        @ValueSource(strings = { "bebida", "alimentacao" })
        @DisplayName("Deve tentar alterar categoria inexistente")
        public void update(String parametro) throws Exception {

            String url = String.format(URL_CONSTANTS_TEST.CategoriaUpdate, port);

            CategoriaDto entity = CategoriaDto.builder().id(0).nome(parametro).build();

            HttpEntity<CategoriaDto> requestUpdate = new HttpEntity<>(entity, null);

            ResponseEntity<String> response = restTemplate.exchange(new URL(url).toString(), HttpMethod.PUT,
                    requestUpdate, String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
        }
    
        @Test
        @DisplayName("Deve tentar excluir categoria inexistente")
        public void delete() throws Exception {
            String url = String.format(URL_CONSTANTS_TEST.CategoriaDelete+ "/?id=100000", port);
            HttpEntity<CategoriaDto> requestUpdate = new HttpEntity<>(null, null);
            ResponseEntity<String> response = restTemplate.exchange(new URL(url).toString(), 
            HttpMethod.DELETE,
                    requestUpdate, String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
        }
    }

    @Nested
    @DisplayName(value = "Testes de integração categorias")
    class CategoriaValid {

        @ParameterizedTest
        @ValueSource(strings = { "bebida", "alimentacao" })
        @DisplayName("Deve salvar; listar; alterar; buscar e deletar")
        public void save_ok(String parametro) throws Exception {
            // SAVE
            String urlSave = String.format(URL_CONSTANTS_TEST.CategoriaSave, port);
            CategoriaDto entity = CategoriaDto.builder().id(0).nome(parametro).build();

            HttpEntity<CategoriaDto> requestSave = new HttpEntity<>(entity, null);
            ResponseEntity<CategoriaDto> response = restTemplate.exchange(new URL(urlSave).toString(), HttpMethod.POST,
                    requestSave, CategoriaDto.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());

            // LIST
            String urlList = String.format(URL_CONSTANTS_TEST.CategoriaList + "/?page=1", port);

            ResponseEntity<CategoriaUtilsPageMock> responselist = restTemplate.getForEntity(new URL(urlList).toString(),
                    CategoriaUtilsPageMock.class);

                    CategoriaUtilsPageMock page =   responselist.getBody();
            assertEquals(HttpStatus.OK, responselist.getStatusCode());
            assertTrue(page.totalElements > 0);
            assertEquals(1, page.totalPages);

            // UPDATE
            String urlUpdate = String.format(URL_CONSTANTS_TEST.CategoriaUpdate, port);

            String nomeUpdate = page.content.get(0).nome+"teste";
            entity = CategoriaDto.builder().id(page.content.get(0).id)
            .nome(nomeUpdate).build();

            HttpEntity<CategoriaDto> requestUpdate = new HttpEntity<>(entity, null);

            ResponseEntity<CategoriaDto> responseUpdate = restTemplate.exchange(new URL(urlUpdate).toString(), HttpMethod.PUT,
                    requestUpdate, CategoriaDto.class);

            assertEquals(HttpStatus.OK, responseUpdate.getStatusCode());

            // FIND
            String urlFind = String.format(URL_CONSTANTS_TEST.CategoriaFind + "/?id="+page.content.get(0).id, port);

            ResponseEntity<CategoriaDto> responseFind = restTemplate
            .getForEntity(new URL(urlFind).toString(), CategoriaDto.class);

            assertEquals(HttpStatus.OK, responseFind.getStatusCode());
            assertEquals(nomeUpdate, responseFind.getBody().nome);

            // DELETE
            String urlDelete = String.format(URL_CONSTANTS_TEST.CategoriaDelete + "/?id="+page.content.get(0).id, port);

            HttpEntity<CategoriaDto> responseurlDelete = new HttpEntity<>(null, null);
            ResponseEntity<String> responseDelete = restTemplate.exchange(
                new URL(urlDelete).toString(), 
            HttpMethod.DELETE,
            responseurlDelete, String.class);

            assertEquals(HttpStatus.OK, responseDelete.getStatusCode());
        }
    }

}