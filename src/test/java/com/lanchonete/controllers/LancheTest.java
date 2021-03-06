package com.lanchonete.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.apllication.dto.lanche.IngredienteDto;
import com.lanchonete.apllication.dto.lanche.LancheDto;
import com.lanchonete.apllication.dto.produto.ProdutoDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.apllication.validations.CustomErro;
import com.lanchonete.domain.entities.lanche.Lanche;
import com.lanchonete.domain.services.lanche.LancheService;
import com.lanchonete.mocks.entities.CategoriaMock;
import com.lanchonete.mocks.entities.EstoqueMock;
import com.lanchonete.mocks.entities.LancheMock;
import com.lanchonete.mocks.entities.ProdutoMock;
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
    public void converterClientDto() {

        Lanche lanche = Mapper.map(new LancheDto());
        assertNotNull(lanche);
    }

    @Nested
    @DisplayName(value = "Testes com lanches Invalidos")
    class LancheInvalid {
        @Test
        @DisplayName("Deve listar todos lanches com lista vazia")
        public void listar() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.LancheList + "/?page=1", port);

            ResponseEntity<LancheUtilsPageMock> response = restTemplate.getForEntity(url, LancheUtilsPageMock.class);
            LancheUtilsPageMock page = response.getBody();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(page);
            assertNotNull(page.content);
        }

        @Test
        @DisplayName("Deve buscar um lanche")
        public void find_inexistente() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.LancheFind + "/?id=100000", port);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @Test
        @DisplayName("Deve tentar salvar lanche invalido")
        public void save_invalid_test() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.LancheSave, port);

            LancheDto entity = new LancheDto();
            HttpEntity<LancheDto> requestSave = new HttpEntity<>(entity, null);

            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, requestSave,
                    Object.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
            // // assertTrue(response.getBody().length > 0);
        }

        @Test
        @DisplayName("Deve tentar alterar lanche inexistente")
        public void active() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.LancheActive + "/?id=100000", port);
            HttpEntity<LancheDto> requestActive = new HttpEntity<>(null, null);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestActive,
                    String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
        }

        @Test
        @DisplayName("Deve tentar excluir lanche inexistente")
        public void desactive() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.LancheDesactive + "/?id=100000", port);
            HttpEntity<LancheDto> requestDesactive = new HttpEntity<>(null, null);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestDesactive,
                    String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
        }
    }

    @Nested
    @DisplayName(value = "Testes de integração lanches")
    class LancheValid {
        private LancheUtilsPageMock page;
        private LancheDto entity;

        @Test
        @DisplayName("Deve salvar; listar; alterar; buscar e deletar")
        public void save_ok() {
            List<CategoriaDto> categorias = CATEGORIA();
            List<ProdutoDto> produtos = PRODUTO(categorias);
            LANCHE(categorias, produtos);
            
            LIST();
            FIND();
            DESACTIVE();
            FIND_DESACTIVE();
            ACTIVE();
            FIND_ACTIVE();
        }

        private List<LancheDto> LANCHE(List<CategoriaDto> categorias, List<ProdutoDto> produtos) {
            List<LancheDto> lanches = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                int indexProduto = new Random().nextInt(25);
                int indexCategoria = new Random().nextInt(10);

                List<IngredienteDto> ingredientes = new ArrayList<>();
                ingredientes.add(Mapper.map(produtos.get(indexProduto + 1), IngredienteDto.class));
                ingredientes.add(Mapper.map(produtos.get(indexProduto), IngredienteDto.class));

                LancheMock lancheMock = new LancheMock(restTemplate, port);
                LancheDto lanche = lancheMock.LANCHE("LancheTest: Lanche" + i + "  Save_ok",
                        categorias.get(indexCategoria), ingredientes);
                lanches.add(lanche);
            }
            
            entity = lanches.get(0);

            return lanches;
        }

        private List<CategoriaDto> CATEGORIA() {
            List<CategoriaDto> categorias = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                CategoriaDto categoria = new CategoriaMock(restTemplate, port)
                        .CATEGORIA("LancheTest: Categoria" + i + " Save_ok");
                categorias.add(categoria);
            }
            return categorias;
        }

        private List<ProdutoDto> PRODUTO(List<CategoriaDto> categorias) {
            List<ProdutoDto> produtos = new ArrayList<>();

            ProdutoMock produtoMock = new ProdutoMock(restTemplate, port);
            EstoqueMock estoqueMock = new EstoqueMock(restTemplate, port);
            for (int i = 0; i < 30; i++) {
                int index = new Random().nextInt(20);
                ProdutoDto produto = produtoMock.PRODUTO("LancheTest: Produto" + i + " Save_ok", categorias.get(index));
                if (index % 2 == 0) {
                    estoqueMock.ESTOQUE(produto);
                    estoqueMock.ESTOQUE(produto);
                    estoqueMock.ESTOQUE(produto);
                } else if (i == 10) {
                    estoqueMock.ESTOQUE(produto);
                    estoqueMock.ESTOQUE(produto);
                    estoqueMock.ESTOQUE(produto);
                    estoqueMock.ESTOQUE(produto);
                }
                produtos.add(produto);
            }
            return produtos;
        }


        private void ACTIVE() {
            // ACTIVE
            HttpEntity<LancheDto> responseurl = new HttpEntity<>(null, null);
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.LancheActive + "/?id=" + entity.id, port);

            ResponseEntity<String> responseActive = restTemplate.exchange(url, HttpMethod.DELETE, responseurl,
                    String.class);

            assertEquals(HttpStatus.OK, responseActive.getStatusCode());
        }

        private void FIND_DESACTIVE() {
            ResponseEntity<LancheDto> responseFind;
            // FIND DESACTIVE
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.LancheFind + "/?id=" + entity.id, port);
            responseFind = restTemplate.getForEntity(url, LancheDto.class);

            assertEquals(HttpStatus.OK, responseFind.getStatusCode());
            assertEquals(false, responseFind.getBody().ativo);
        }

        private void FIND_ACTIVE() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.LancheFind + "/?id=" + entity.id, port);

            // FIND DESACTIVE
            ResponseEntity<LancheDto> responseFind = restTemplate.getForEntity(url, LancheDto.class);

            assertEquals(HttpStatus.OK, responseFind.getStatusCode());
            assertEquals(true, responseFind.getBody().ativo);
        }

        private void DESACTIVE() {
            // DESACTIVE
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.LancheDesactive + "/?id=" + entity.id, port);

            HttpEntity<LancheDto> responseurl = new HttpEntity<>(null, null);
            ResponseEntity<String> responseDesactive = restTemplate.exchange(url, HttpMethod.DELETE, responseurl,
                    String.class);

            assertEquals(HttpStatus.OK, responseDesactive.getStatusCode());
        }

        private void FIND() {
            // FIND
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.LancheFind + "/?id=" + entity.id, port);

            ResponseEntity<LancheDto> responseFind = restTemplate.getForEntity(url, LancheDto.class);

            assertEquals(HttpStatus.OK, responseFind.getStatusCode());
            assertEquals(entity.nome, responseFind.getBody().nome);
        }

        private void LIST() {
            // LIST
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.LancheList + "/?page=1", port);

            ResponseEntity<LancheUtilsPageMock> responselist = restTemplate.getForEntity(url,
                    LancheUtilsPageMock.class);

            page = responselist.getBody();
            assertEquals(HttpStatus.OK, responselist.getStatusCode());
            assertTrue(page.totalElements > 0);
            assertTrue(page.totalPages >= 1);

            entity = Mapper.map(page.content.get(0), LancheDto.class);
        }

    }

}