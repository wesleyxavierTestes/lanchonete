package com.lanchonete.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.print.DocFlavor.STRING;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.apllication.dto.produto.ProdutoDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.apllication.validations.CustomErro;
import com.lanchonete.domain.entities.cardapio.lanche.Ingrediente;
import com.lanchonete.domain.entities.categoria.Categoria;
import com.lanchonete.domain.entities.produto.entities.Produto;
import com.lanchonete.domain.services.produto.ProdutoService;
import com.lanchonete.infra.repositorys.categoria.ICategoriaRepository;
import com.lanchonete.mocks.entities.CategoriaMock;
import com.lanchonete.mocks.entities.ProdutoMock;
import com.lanchonete.mocks.pages.ProdutoUtilsPageMock;
import com.lanchonete.utils.ObjectMapperUtils;
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
public class ProdutoTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    protected ProdutoService _service;

    @Autowired
    private ICategoriaRepository _serviceRepository;

    @Test
    public void converter() throws Exception {
        Produto produto = new Produto();
        produto.setNome("Marcelo");
        Ingrediente i = Mapper.map(produto, Ingrediente.class);
        i.setNome("Teste");
        assertEquals("Teste", i.getNome());
    }

    @Nested
    @DisplayName(value = "Testes com clientes Invalidos")
    class ProdutoInvalid {
        @Test
        @DisplayName("Deve listar todos clientes com lista vazia")
        public void listar() throws Exception {
            String url = String.format(URL_CONSTANTS_TEST.ProdutoList + "/?page=1", port);

            ResponseEntity<ProdutoUtilsPageMock> response = restTemplate.getForEntity(new URL(url).toString(),
                    ProdutoUtilsPageMock.class);
            ProdutoUtilsPageMock page = response.getBody();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(page);
            assertNotNull(page.content);
        }

        @Test
        @DisplayName("Deve buscar um cliente")
        public void find_inexistente() throws Exception {
            String url = String.format(URL_CONSTANTS_TEST.ProdutoFind + "/?id=100000", port);

            ResponseEntity<String> response = restTemplate.getForEntity(new URL(url).toString(), String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @Test
        @DisplayName("Deve tentar salvar cliente invalido")
        public void save_invalid_test() throws Exception {
            String url = String.format(URL_CONSTANTS_TEST.ProdutoSave, port);

            ProdutoDto entity = new ProdutoDto();
            entity.nome = "teste1";
            HttpEntity<ProdutoDto> requestUpdate = new HttpEntity<>(entity, null);

            ResponseEntity<CustomErro[]> response = restTemplate.exchange(new URL(url).toString(), HttpMethod.POST,
                    requestUpdate, CustomErro[].class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
            assertTrue(response.getBody().length > 0);
        }

        @Test
        @DisplayName("Deve tentar alterar cliente inexistente")
        public void update() throws Exception {

            String url = String.format(URL_CONSTANTS_TEST.ProdutoUpdate, port);

            ProdutoDto entity = ProdutoMock.dto("ProdutoTest: Produto Update_error");
            entity.categoria = CategoriaMock.dto("ProdutoTest: Produto Update_error");
            entity.id = 0;

            HttpEntity<ProdutoDto> requestUpdate = new HttpEntity<>(entity, null);

            ResponseEntity<String> response = restTemplate.exchange(new URL(url).toString(), HttpMethod.PUT,
                    requestUpdate, String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName(value = "Testes de integração produtos")
    class ProdutoValid {
        private ProdutoDto entity;
        private ProdutoUtilsPageMock page;

        @Test
        @DisplayName("Deve salvar; listar; alterar; buscar e deletar")
        public void save_ok() throws Exception {
            CategoriaDto categoria = CATEGORIA("ProdutoTest: Categoria Save_ok");
            PRODUTO("ProdutoTest: Produto Save_ok", categoria);
            LIST();
            FIND();
            DESACTIVE();
            FIND_DESACTIVE();
            ACTIVE();
            FIND_ACTIVE();
        }

        private void FIND_ACTIVE() throws MalformedURLException {
            // FIND ACTIVE

            String urlFind = String.format(URL_CONSTANTS_TEST.ProdutoFind + "/?id=" + entity.id, port);
            ResponseEntity<ProdutoDto> responseFind = restTemplate.getForEntity(new URL(urlFind).toString(),
                    ProdutoDto.class);

            assertEquals(HttpStatus.OK, responseFind.getStatusCode(), "FIND ACTIVE expect Error");
            assertEquals(true, responseFind.getBody().ativo);
        }

        private void ACTIVE() throws MalformedURLException {
            // ACTIVE
            String urlActive = String.format(URL_CONSTANTS_TEST.ProdutoActive + "/?id=" + entity.id, port);

            HttpEntity<ProdutoDto> responseurl = new HttpEntity<>(null, null);
            ResponseEntity<String> responseActive = restTemplate.exchange(new URL(urlActive).toString(),
                    HttpMethod.DELETE, responseurl, String.class);

            assertEquals(HttpStatus.OK, responseActive.getStatusCode(), "ACTIVE expect Error");
        }

        private String FIND_DESACTIVE() throws MalformedURLException {
            // FIND DESACTIVE

            String urlFind = String.format(URL_CONSTANTS_TEST.ProdutoFind + "/?id=" + entity.id, port);

            ResponseEntity<ProdutoDto> responseFind = restTemplate.getForEntity(new URL(urlFind).toString(),
                    ProdutoDto.class);

            assertEquals(HttpStatus.OK, responseFind.getStatusCode(), "FIND DESACTIVE expect Error");
            assertEquals(false, responseFind.getBody().ativo);
            return urlFind;
        }

        private void DESACTIVE() throws MalformedURLException {
            // DESACTIVE
            String urlDesactive = String.format(URL_CONSTANTS_TEST.ProdutoDesactive + "/?id=" + entity.id,
                    port);

            HttpEntity<ProdutoDto> responseurl = new HttpEntity<>(null, null);
            ResponseEntity<String> responseDesactive = restTemplate.exchange(new URL(urlDesactive).toString(),
                    HttpMethod.DELETE, responseurl, String.class);

            assertEquals(HttpStatus.OK, responseDesactive.getStatusCode(), "DESACTIVE expect Error");
        }

        private void FIND() throws MalformedURLException {
            // FIND
            String urlFind = String.format(URL_CONSTANTS_TEST.ProdutoFind + "/?id=" + entity.id, port);

            ResponseEntity<ProdutoDto> responseFind = restTemplate.getForEntity(new URL(urlFind).toString(),
                    ProdutoDto.class);

            assertEquals(HttpStatus.OK, responseFind.getStatusCode(), "FIND expect Error");
            assertEquals(entity.nome, responseFind.getBody().nome);
        }

        private void LIST() throws MalformedURLException {
            // LIST
            String urlList = String.format(URL_CONSTANTS_TEST.ProdutoList + "/?page=1", port);

            ResponseEntity<ProdutoUtilsPageMock> responselist = restTemplate.getForEntity(new URL(urlList).toString(),
                    ProdutoUtilsPageMock.class);

            page = responselist.getBody();
            assertEquals(HttpStatus.OK, responselist.getStatusCode(), "LIST expect Error");
            assertTrue(page.totalElements > 0);
            assertEquals(1, page.totalPages);

            entity = Mapper.map(page.content.get(0), ProdutoDto.class);
        }

        private ProdutoDto PRODUTO(String nome, CategoriaDto categoria) throws MalformedURLException, JsonMappingException, JsonProcessingException {
            // SAVE
            String urlSave = String.format(URL_CONSTANTS_TEST.ProdutoSave, port);
            ProdutoDto entity = ProdutoMock.dto(nome);
            entity.categoria = categoria;

            HttpEntity<ProdutoDto> requestSave = new HttpEntity<>(entity, null);
            ResponseEntity<Object> response = restTemplate.exchange(new URL(urlSave).toString(), 
                    HttpMethod.POST,
                    requestSave, Object.class);

            assertEquals(HttpStatus.OK, response.getStatusCode(), "SAVE expect Error");
            assertNotNull(response.getBody());
                        
            String json = ObjectMapperUtils.toJson(response.getBody());
            entity = ObjectMapperUtils.jsonTo(json, ProdutoDto.class);

            assertNotNull(entity.codigo);

            return entity;
        }

        private CategoriaDto CATEGORIA(String nome) throws MalformedURLException {
            CategoriaDto categoria = CategoriaMock.dto(nome);

            String urlSave = String.format(URL_CONSTANTS_TEST.CategoriaSave, port);
               
            HttpEntity<CategoriaDto> requestSave = new HttpEntity<>(categoria, null);
            ResponseEntity<Object> response = restTemplate.exchange(new URL(urlSave).toString(),
                    HttpMethod.POST, requestSave, Object.class);

            assertNotNull(response.getBody());

            String json = ObjectMapperUtils.toJson(response.getBody());
            CategoriaDto categoriaNew = ObjectMapperUtils.jsonTo(json, CategoriaDto.class);

            return categoriaNew;
        }
    
    }
}