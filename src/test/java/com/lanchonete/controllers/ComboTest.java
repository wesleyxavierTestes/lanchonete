package com.lanchonete.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.apllication.dto.combo.ComboDto;
import com.lanchonete.apllication.dto.lanche.IngredienteDto;
import com.lanchonete.apllication.dto.lanche.LancheDto;
import com.lanchonete.apllication.dto.produto.ProdutoDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.apllication.validations.CustomErro;
import com.lanchonete.domain.entities.combo.Combo;
import com.lanchonete.domain.services.combo.ComboService;
import com.lanchonete.mocks.entities.CategoriaMock;
import com.lanchonete.mocks.entities.ComboMock;
import com.lanchonete.mocks.entities.EstoqueMock;
import com.lanchonete.mocks.entities.LancheMock;
import com.lanchonete.mocks.entities.ProdutoMock;
import com.lanchonete.mocks.pages.ComboUtilsPageMock;
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
public class ComboTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    protected ComboService _service;

    @Test
    @DisplayName("Deve converter uma ComboDto para Combo incluindo Endereco")
    public void converterClientDto() {

        Combo combo = Mapper.map(new ComboDto());
        assertNotNull(combo);
    }

    @Nested
    @DisplayName(value = "Testes com combos Invalidos")
    class ComboInvalid {
        @Test
        @DisplayName("Deve listar todos combos com lista vazia")
        public void listar() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ComboList + "/?page=1", port);

            ResponseEntity<ComboUtilsPageMock> response = restTemplate.getForEntity(url, ComboUtilsPageMock.class);
            ComboUtilsPageMock page = response.getBody();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(page);
            assertNotNull(page.content);
        }

        @Test
        @DisplayName("Deve buscar um combo")
        public void find_inexistente() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ComboFind + "/?id=100000", port);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @Test
        @DisplayName("Deve tentar salvar combo invalido")
        public void save_invalid_test() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ComboSave, port);

            ComboDto entity = new ComboDto();
            HttpEntity<ComboDto> requestSave = new HttpEntity<>(entity, null);

            ResponseEntity<CustomErro[]> response = restTemplate.exchange(url, HttpMethod.POST, requestSave,
                    CustomErro[].class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
            assertTrue(response.getBody().length > 0);
        }

        @Test
        @DisplayName("Deve tentar alterar combo inexistente")
        public void active() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ComboActive + "/?id=100000", port);
            HttpEntity<ComboDto> requestActive = new HttpEntity<>(null, null);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestActive,
                    String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
        }

        @Test
        @DisplayName("Deve tentar excluir combo inexistente")
        public void desactive() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ComboDesactive + "/?id=100000", port);
            HttpEntity<ComboDto> requestDesactive = new HttpEntity<>(null, null);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestDesactive,
                    String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
        }
    }

    @Nested
    @DisplayName(value = "Testes de integração combos")
    class ComboValid {
        private ComboUtilsPageMock page;
        private ComboDto entity;

        @Test
        @DisplayName("Deve salvar; listar; alterar; buscar e deletar")
        public void save_ok() {
            List<CategoriaDto> categorias = CATEGORIA();
            List<ProdutoDto> produtos = PRODUTO(categorias);
            List<LancheDto> lanches = LANCHE(categorias, produtos);

            ComboMock comboMock = new ComboMock(restTemplate, port);
            entity = comboMock.COMBO("ComboTest: ComboDto Save_ok", categorias.get(2),  lanches.get(2),  produtos.get(2));

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
                LancheDto lanche = lancheMock.LANCHE("ComboTest: Lanche" + i + "  Save_ok",
                        categorias.get(indexCategoria), ingredientes);
                lanches.add(lanche);
            }
            
            return lanches;
        }

        private List<CategoriaDto> CATEGORIA() {
            List<CategoriaDto> categorias = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                CategoriaDto categoria = new CategoriaMock(restTemplate, port)
                        .CATEGORIA("ComboTest: Categoria" + i + " Save_ok");
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
                ProdutoDto produto = produtoMock.PRODUTO("ComboTest: Produto" + i + " Save_ok", categorias.get(index));
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
            HttpEntity<ComboDto> responseurl = new HttpEntity<>(null, null);
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ComboActive + "/?id=" + entity.id, port);

            ResponseEntity<String> responseActive = restTemplate.exchange(url, HttpMethod.DELETE, responseurl,
                    String.class);

            assertEquals(HttpStatus.OK, responseActive.getStatusCode());
        }

        private void FIND_DESACTIVE() {
            ResponseEntity<ComboDto> responseFind;
            // FIND DESACTIVE
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ComboFind + "/?id=" + entity.id, port);
            responseFind = restTemplate.getForEntity(url, ComboDto.class);

            assertEquals(HttpStatus.OK, responseFind.getStatusCode());
            assertEquals(false, responseFind.getBody().ativo);
        }

        private void FIND_ACTIVE() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ComboFind + "/?id=" + entity.id, port);

            // FIND DESACTIVE
            ResponseEntity<ComboDto> responseFind = restTemplate.getForEntity(url, ComboDto.class);

            assertEquals(HttpStatus.OK, responseFind.getStatusCode());
            assertEquals(true, responseFind.getBody().ativo);
        }

        private void DESACTIVE() {
            // DESACTIVE
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ComboDesactive + "/?id=" + entity.id, port);

            HttpEntity<ComboDto> responseurl = new HttpEntity<>(null, null);
            ResponseEntity<String> responseDesactive = restTemplate.exchange(url, HttpMethod.DELETE, responseurl,
                    String.class);

            assertEquals(HttpStatus.OK, responseDesactive.getStatusCode());
        }

        private void FIND() {
            // FIND
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ComboFind + "/?id=" + entity.id, port);

            ResponseEntity<ComboDto> responseFind = restTemplate.getForEntity(url, ComboDto.class);

            assertEquals(HttpStatus.OK, responseFind.getStatusCode());
            assertEquals(entity.nome, responseFind.getBody().nome);
        }

        private void LIST() {
            // LIST
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ComboList + "/?page=1", port);

            ResponseEntity<ComboUtilsPageMock> responselist = restTemplate.getForEntity(url, ComboUtilsPageMock.class);

            page = responselist.getBody();
            assertEquals(HttpStatus.OK, responselist.getStatusCode());
            assertTrue(page.totalElements > 0);
            assertTrue(page.totalPages >= 1);

            entity = Mapper.map(page.content.get(0), ComboDto.class);
        }

    }

}