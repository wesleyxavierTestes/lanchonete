package com.lanchonete.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import com.lanchonete.apllication.dto.lanche.LancheDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.apllication.validations.CustomErro;
import com.lanchonete.domain.entities.cardapio.lanche.Lanche;
import com.lanchonete.domain.services.lanche.LancheService;
import com.lanchonete.mocks.entities.LancheMock;
import com.lanchonete.mocks.pages.LancheUtilsPageMock;
import com.lanchonete.utils.URL_CONSTANTS_TEST;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
public class LancheTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    protected LancheService _service;

    @Test
    @DisplayName("Deve converter uma LancheDto para Lanche incluindo Endereco")
    public void converterClientDto() throws Exception {

        Lanche lanche = Mapper.map(new LancheDto());
        assertNotNull(lanche);
    }

    @Nested
    @DisplayName(value = "Testes com lanches Invalidos")
    class LancheInvalid {
        @Test
        @DisplayName("Deve listar todos lanches com lista vazia")
        public void listar() throws Exception {
            String url = String.format(URL_CONSTANTS_TEST.LancheList + "/?page=1", port);

            ResponseEntity<LancheUtilsPageMock> response = restTemplate.getForEntity(new URL(url).toString(),
                    LancheUtilsPageMock.class);
            LancheUtilsPageMock page = response.getBody();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(page);
            assertNotNull(page.content);
        }

        @Test
        @DisplayName("Deve buscar um lanche")
        public void find_inexistente() throws Exception {
            String url = String.format(URL_CONSTANTS_TEST.LancheFind + "/?id=100000", port);

            ResponseEntity<String> response = restTemplate.getForEntity(new URL(url).toString(), String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @Test
        @DisplayName("Deve tentar salvar lanche invalido")
        public void save_invalid_test() throws Exception {
            String url = String.format(URL_CONSTANTS_TEST.LancheSave, port);

            LancheDto entity = new LancheDto();
            HttpEntity<LancheDto> requestSave = new HttpEntity<>(entity, null);

            ResponseEntity<CustomErro[]> response = restTemplate.exchange(new URL(url).toString(), HttpMethod.POST,
                    requestSave, CustomErro[].class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
            assertTrue(response.getBody().length > 0);
        }

        @Test
        @DisplayName("Deve tentar alterar lanche inexistente")
        public void active() throws Exception {
            String url = String.format(URL_CONSTANTS_TEST.LancheActive + "/?id=100000", port);
            HttpEntity<LancheDto> requestActive = new HttpEntity<>(null, null);
            ResponseEntity<String> response = restTemplate.exchange(new URL(url).toString(), HttpMethod.DELETE,
                    requestActive, String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
        }

        @Test
        @DisplayName("Deve tentar excluir lanche inexistente")
        public void desactive() throws Exception {
            String url = String.format(URL_CONSTANTS_TEST.LancheDesactive + "/?id=100000", port);
            HttpEntity<LancheDto> requestDesactive = new HttpEntity<>(null, null);
            ResponseEntity<String> response = restTemplate.exchange(new URL(url).toString(), HttpMethod.DELETE,
                    requestDesactive, String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
        }
    }

    @Nested
    @DisplayName(value = "Testes de integração lanches")
    class LancheValid {
        private LancheUtilsPageMock page;
        private LancheDto entity;

        // @Test
        @DisplayName("Deve salvar; listar; alterar; buscar e deletar")
        public void save_ok() throws Exception {
            SAVE();
            LIST();
            FIND();
            DESACTIVE();
            FIND_DESACTIVE();
            ACTIVE();
            FIND_ACTIVE();
        }

        private void ACTIVE() throws MalformedURLException {
            // ACTIVE
            HttpEntity<LancheDto> responseurl = new HttpEntity<>(null, null);
            String urlActive = String.format(URL_CONSTANTS_TEST.LancheActive + "/?id=" + page.content.get(0).id, port);

            ResponseEntity<String> responseActive = restTemplate.exchange(new URL(urlActive).toString(),
                    HttpMethod.DELETE, responseurl, String.class);

            assertEquals(HttpStatus.OK, responseActive.getStatusCode());
        }

        private void FIND_DESACTIVE() throws MalformedURLException {
            ResponseEntity<LancheDto> responseFind;
            // FIND DESACTIVE
            String urlFind = String.format(URL_CONSTANTS_TEST.LancheFind + "/?id=" + page.content.get(0).id, port);
            responseFind = restTemplate.getForEntity(new URL(urlFind).toString(), LancheDto.class);

            assertEquals(HttpStatus.OK, responseFind.getStatusCode());
            assertEquals(false, responseFind.getBody().ativo);
        }

        private void FIND_ACTIVE() throws MalformedURLException {
            String urlFind = String.format(URL_CONSTANTS_TEST.LancheFind + "/?id=" + page.content.get(0).id, port);
            
            // FIND DESACTIVE
            ResponseEntity<LancheDto> responseFind = restTemplate.getForEntity(new URL(urlFind).toString(), LancheDto.class);

            assertEquals(HttpStatus.OK, responseFind.getStatusCode());
            assertEquals(true, responseFind.getBody().ativo);
        }

        private void DESACTIVE() throws MalformedURLException {
            // DESACTIVE
            String urlDesactive = String.format(URL_CONSTANTS_TEST.LancheDesactive + "/?id=" + page.content.get(0).id,
                    port);

            HttpEntity<LancheDto> responseurl = new HttpEntity<>(null, null);
            ResponseEntity<String> responseDesactive = restTemplate.exchange(new URL(urlDesactive).toString(),
                    HttpMethod.DELETE, responseurl, String.class);

            assertEquals(HttpStatus.OK, responseDesactive.getStatusCode());
        }

        private void FIND() throws MalformedURLException {
            // FIND
            String urlFind = String.format(URL_CONSTANTS_TEST.LancheFind + "/?id=" + page.content.get(0).id, port);

            ResponseEntity<LancheDto> responseFind = restTemplate.getForEntity(new URL(urlFind).toString(),
                    LancheDto.class);

            assertEquals(HttpStatus.OK, responseFind.getStatusCode());
            assertEquals(entity.nome, responseFind.getBody().nome);
        }

        private void LIST() throws MalformedURLException {
            // LIST
            String urlList = String.format(URL_CONSTANTS_TEST.LancheList + "/?page=1", port);

            ResponseEntity<LancheUtilsPageMock> responselist = restTemplate.getForEntity(new URL(urlList).toString(),
                    LancheUtilsPageMock.class);

            page = responselist.getBody();
            assertEquals(HttpStatus.OK, responselist.getStatusCode());
            assertTrue(page.totalElements > 0);
            assertEquals(1, page.totalPages);
        }

        private void SAVE() throws MalformedURLException {
            // SAVE
            String urlSave = String.format(URL_CONSTANTS_TEST.LancheSave, port);
            entity = LancheMock.dto();

            HttpEntity<LancheDto> requestSave = new HttpEntity<>(entity, null);
            ResponseEntity<LancheDto> response = restTemplate.exchange(new URL(urlSave).toString(), HttpMethod.POST,
                    requestSave, LancheDto.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
        }

    }

}