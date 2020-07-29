package com.lanchonete.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.apllication.dto.produto.ProdutoDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.apllication.validations.CustomErro;
import com.lanchonete.domain.entities.cardapio.lanche.Ingrediente;
import com.lanchonete.domain.entities.produto.entities.Produto;
import com.lanchonete.domain.services.produto.ProdutoService;
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

    @Test
    public void converter()  {
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
        public void listar()  {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ProdutoList + "/?page=1", port);

            ResponseEntity<ProdutoUtilsPageMock> response = restTemplate.getForEntity(url,
                    ProdutoUtilsPageMock.class);
            ProdutoUtilsPageMock page = response.getBody();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(page);
            assertNotNull(page.content);
        }

        @Test
        @DisplayName("Deve buscar um cliente")
        public void find_inexistente()  {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ProdutoFind + "/?id=100000", port);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @Test
        @DisplayName("Deve tentar salvar cliente invalido")
        public void save_invalid_test()  {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ProdutoSave, port);

            ProdutoDto entity = new ProdutoDto();
            entity.nome = "teste1";
            HttpEntity<ProdutoDto> requestUpdate = new HttpEntity<>(entity, null);

            ResponseEntity<CustomErro[]> response = restTemplate.exchange(url, HttpMethod.POST,
                    requestUpdate, CustomErro[].class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
            assertTrue(response.getBody().length > 0);
        }

        @Test
        @DisplayName("Deve tentar alterar cliente inexistente")
        public void update()  {

            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ProdutoUpdate, port);

            ProdutoDto entity = ProdutoMock.dto("ProdutoTest: Produto Update_error");
            entity.categoria = CategoriaMock.dto("ProdutoTest: Produto Update_error");
            entity.id = 0;

            HttpEntity<ProdutoDto> requestUpdate = new HttpEntity<>(entity, null);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT,
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
        public void save_ok()  {
            CategoriaDto categoria = CATEGORIA("ProdutoTest: Categoria Save_ok");
            PRODUTO("ProdutoTest: Produto Save_ok", categoria);
            LIST();
            FIND();
            DESACTIVE();
            FIND_DESACTIVE();
            ACTIVE();
            FIND_ACTIVE();
        }

        private void FIND_ACTIVE() {
            // FIND ACTIVE

            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ProdutoFind + "/?id=" + entity.id, port);
            ResponseEntity<ProdutoDto> responseFind = restTemplate.getForEntity(url,
                    ProdutoDto.class);

            assertEquals(HttpStatus.OK, responseFind.getStatusCode(), "FIND ACTIVE expect Error");
            assertEquals(true, responseFind.getBody().ativo);
        }

        private void ACTIVE() {
            // ACTIVE
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ProdutoActive + "/?id=" + entity.id, port);

            HttpEntity<ProdutoDto> responseurl = new HttpEntity<>(null, null);
            ResponseEntity<String> responseActive = restTemplate.exchange(url,
                    HttpMethod.DELETE, responseurl, String.class);

            assertEquals(HttpStatus.OK, responseActive.getStatusCode(), "ACTIVE expect Error");
        }

        private String FIND_DESACTIVE() {
            // FIND DESACTIVE

            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ProdutoFind + "/?id=" + entity.id, port);

            ResponseEntity<ProdutoDto> responseFind = restTemplate.getForEntity(url,
                    ProdutoDto.class);

            assertEquals(HttpStatus.OK, responseFind.getStatusCode(), "FIND DESACTIVE expect Error");
            assertEquals(false, responseFind.getBody().ativo);
            return url;
        }

        private void DESACTIVE() {
            // DESACTIVE
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ProdutoDesactive + "/?id=" + entity.id,
                    port);

            HttpEntity<ProdutoDto> responseurl = new HttpEntity<>(null, null);
            ResponseEntity<String> responseDesactive = restTemplate.exchange(url,
                    HttpMethod.DELETE, responseurl, String.class);

            assertEquals(HttpStatus.OK, responseDesactive.getStatusCode(), "DESACTIVE expect Error");
        }

        private void FIND() {
            // FIND
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ProdutoFind + "/?id=" + entity.id, port);

            ResponseEntity<ProdutoDto> responseFind = restTemplate.getForEntity(url,
                    ProdutoDto.class);

            assertEquals(HttpStatus.OK, responseFind.getStatusCode(), "FIND expect Error");
            assertEquals(entity.nome, responseFind.getBody().nome);
        }

        private void LIST() {
            // LIST
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ProdutoList + "/?page=1", port);

            ResponseEntity<ProdutoUtilsPageMock> responselist = restTemplate.getForEntity(url,
                    ProdutoUtilsPageMock.class);

            page = responselist.getBody();
            assertEquals(HttpStatus.OK, responselist.getStatusCode(), "LIST expect Error");
            assertTrue(page.totalElements > 0);
            assertEquals(1, page.totalPages);

            entity = Mapper.map(page.content.get(0), ProdutoDto.class);
        }

        private ProdutoDto PRODUTO(String nome, CategoriaDto categoria) {
            // SAVE
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ProdutoSave, port);
            ProdutoDto entity = ProdutoMock.dto(nome);
            entity.categoria = categoria;

            HttpEntity<ProdutoDto> requestSave = new HttpEntity<>(entity, null);
            ResponseEntity<Object> response = restTemplate.exchange(url, 
                    HttpMethod.POST,
                    requestSave, Object.class);

            assertEquals(HttpStatus.OK, response.getStatusCode(), "SAVE expect Error");
            assertNotNull(response.getBody());
                        
            String json = ObjectMapperUtils.toJson(response.getBody());
            entity = ObjectMapperUtils.jsonTo(json, ProdutoDto.class);

            assertNotNull(entity.codigo);

            return entity;
        }

        private CategoriaDto CATEGORIA(String nome) {
            CategoriaDto categoria = CategoriaMock.dto(nome);

            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.CategoriaSave, port);
               
            HttpEntity<CategoriaDto> requestSave = new HttpEntity<>(categoria, null);
            ResponseEntity<Object> response = restTemplate.exchange(url,
                    HttpMethod.POST, requestSave, Object.class);

            assertNotNull(response.getBody());

            String json = ObjectMapperUtils.toJson(response.getBody());
            CategoriaDto categoriaNew = ObjectMapperUtils.jsonTo(json, CategoriaDto.class);

            return categoriaNew;
        }
    
    }
}