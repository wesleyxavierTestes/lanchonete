package com.lanchonete.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;

import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.apllication.validations.CustomErro;
import com.lanchonete.domain.entities.categoria.Categoria;
import com.lanchonete.domain.services.categoria.CategoriaService;
import com.lanchonete.mocks.pages.CategoriaUtilsPageMock;
import com.lanchonete.utils.ObjectMapperUtils;
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
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.CategoriaList + "/?page=1", port);

            ResponseEntity<CategoriaUtilsPageMock> response = restTemplate.getForEntity(url,
                    CategoriaUtilsPageMock.class);
            CategoriaUtilsPageMock page = response.getBody();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(page);
            assertNotNull(page.content);
        }

        @Test
        @DisplayName("Deve buscar um categoria")
        public void find_inexistente() throws Exception {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.CategoriaFind + "/?id=100000", port);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @Test
        @DisplayName("Deve tentar salvar categoria invalido")
        public void save_invalid_test() throws Exception {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.CategoriaSave, port);

            CategoriaDto entity = new CategoriaDto();
            HttpEntity<CategoriaDto> requestUpdate = new HttpEntity<>(entity, null);

            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, requestUpdate,
                    Object.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
            // // // assertTrue(response.getBody().length > 0);
        }

        @ParameterizedTest
        @ValueSource(strings = { "bebida", "alimentacao" })
        @DisplayName("Deve tentar alterar categoria inexistente")
        public void update(String parametro) throws Exception {

            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.CategoriaUpdate, port);

            CategoriaDto entity = CategoriaDto.builder().id(0).nome(parametro).build();

            HttpEntity<CategoriaDto> requestUpdate = new HttpEntity<>(entity, null);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestUpdate, String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
        }
    }

    @Nested
    @DisplayName(value = "Testes de integração categorias")
    class CategoriaValid {
        CategoriaDto entity;

        @ParameterizedTest
        @ValueSource(strings = { "bebida", "alimentacao" })
        @DisplayName("Deve salvar; listar; alterar; buscar e deletar")
        public void save_ok(String parametro) throws Exception {
            entity = SAVE(parametro);

            CategoriaUtilsPageMock page = LIST();

            String nomeUpdate = UPDATE(page);

            FIND(page, nomeUpdate);

            DESACTIVE();
            FIND_DESACTIVE();
            ACTIVE();
            FIND_ACTIVE();
        }

        private void ACTIVE() {
            // ACTIVE
            HttpEntity<CategoriaDto> responseurl = new HttpEntity<>(null, null);
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.CategoriaActive + "/?id=" + entity.id, port);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, responseurl, String.class);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        private void FIND_DESACTIVE() {

            // FIND DESACTIVE
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.CategoriaFind + "/?id=" + entity.id, port);

            ResponseEntity<CategoriaDto> response = restTemplate.getForEntity(url, CategoriaDto.class);

            assertNotNull(response);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(false, response.getBody().ativo);
        }

        private void FIND_ACTIVE() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.CategoriaFind + "/?id=" + entity.id, port);

            // FIND DESACTIVE
            ResponseEntity<CategoriaDto> response = restTemplate.getForEntity(url, CategoriaDto.class);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(true, response.getBody().ativo);
        }

        private void DESACTIVE() {
            // DESACTIVE
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.CategoriaDesactive + "/?id=" + entity.id, port);

            HttpEntity<CategoriaDto> responseurl = new HttpEntity<>(null, null);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, responseurl, String.class);

            assertNotNull(response);

            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        private void FIND(CategoriaUtilsPageMock page, String nomeUpdate) {
            // FIND
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.CategoriaFind + "/?id=" + page.content.get(0).id,
                    port);

            ResponseEntity<CategoriaDto> responseFind = restTemplate.getForEntity(url, CategoriaDto.class);

            assertEquals(HttpStatus.OK, responseFind.getStatusCode());
            assertEquals(nomeUpdate, responseFind.getBody().nome);
        }

        private String UPDATE(CategoriaUtilsPageMock page) throws MalformedURLException {
            // UPDATE

            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.CategoriaUpdate, port);

            String nomeUpdate = page.content.get(0).nome + "teste";
            entity = CategoriaDto.builder().id(page.content.get(0).id).nome(nomeUpdate).build();

            HttpEntity<CategoriaDto> requestUpdate = new HttpEntity<>(entity, null);

            ResponseEntity<CategoriaDto> responseUpdate = restTemplate.exchange(url, HttpMethod.PUT, requestUpdate,
                    CategoriaDto.class);

            assertEquals(HttpStatus.OK, responseUpdate.getStatusCode());
            return nomeUpdate;
        }

        private CategoriaUtilsPageMock LIST() {
            // LIST
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.CategoriaList + "/?page=1", port);

            ResponseEntity<CategoriaUtilsPageMock> responselist = restTemplate.getForEntity(url,
                    CategoriaUtilsPageMock.class);

            CategoriaUtilsPageMock page = responselist.getBody();
            assertEquals(HttpStatus.OK, responselist.getStatusCode());
            assertTrue(page.totalElements > 0);
            assertTrue(page.totalPages >= 1);
            return page;
        }

        private CategoriaDto SAVE(String parametro) {
            // SAVE
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.CategoriaSave, port);
            CategoriaDto entity = CategoriaDto.builder().id(0).nome(parametro).build();

            HttpEntity<CategoriaDto> requestSave = new HttpEntity<>(entity, null);
            ResponseEntity<CategoriaDto> response = restTemplate.exchange(url, HttpMethod.POST, requestSave,
                    CategoriaDto.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());

            String json = ObjectMapperUtils.toJson(response.getBody());
            CategoriaDto categoriaSave = ObjectMapperUtils.jsonTo(json, CategoriaDto.class);

            assertNotNull(categoriaSave);

            return categoriaSave;
        }
    }

}