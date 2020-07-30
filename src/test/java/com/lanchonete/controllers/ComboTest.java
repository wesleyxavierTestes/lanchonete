package com.lanchonete.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.apllication.dto.combo.ComboDto;
import com.lanchonete.apllication.dto.combo.ComboItemDto;
import com.lanchonete.apllication.dto.estoque.EstoqueDto;
import com.lanchonete.apllication.dto.estoque.EstoqueProdutoDto;
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
            CategoriaDto categoria1 = CATEGORIA("ComboTest: Categoria1 Save_ok");
            CategoriaDto categoria2 = CATEGORIA("ComboTest: Categoria2 Save_ok");
            CategoriaDto categoria3 = CATEGORIA("ComboTest: Categoria3 Save_ok");
            CategoriaDto categoria4 = CATEGORIA("ComboTest: Categoria4 Save_ok");
            ProdutoDto produto1 = PRODUTO("ComboTest: Produto1 Save_ok", categoria1);
            ProdutoDto produto2 = PRODUTO("ComboTest: Produto2 Save_ok", categoria2);
            ProdutoDto produto3 = PRODUTO("ComboTest: Produto3 Save_ok", categoria3);
            ESTOQUE(produto1);
            ESTOQUE(produto2);
            ESTOQUE(produto3);

            List<IngredienteDto> ingredientes = new ArrayList<>();
            ingredientes.add(Mapper.map(produto1, IngredienteDto.class));
            ingredientes.add(Mapper.map(produto2, IngredienteDto.class));

            LancheDto lanche1 = LANCHE("ComboTest: Lanche Save_ok", categoria1, ingredientes);

            COMBO("ComboTest: ComboDto Save_ok", categoria4, lanche1, produto3);
            LIST();
            FIND();
            DESACTIVE();
            FIND_DESACTIVE();
            ACTIVE();
            FIND_ACTIVE();
        }

        private ComboDto COMBO(String nome, CategoriaDto categoria, LancheDto lanche, ProdutoDto comboBebida) {

            ComboDto combo = ComboMock.dto(nome);
            combo.categoria = categoria;
            combo.lanche = Mapper.map(lanche, ComboItemDto.class);
            combo.bebida = Mapper.map(comboBebida, ComboItemDto.class);
            combo.valor = "123";
            combo.valorTotal = "123";

            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ComboSave, port);
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(combo, null),
                    Object.class);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());

            String json = ObjectMapperUtils.toJson(response.getBody());
            combo = ObjectMapperUtils.jsonTo(json, ComboDto.class);

            assertNotNull(lanche.codigo);

            return combo;

        }

        private LancheDto LANCHE(String nome, CategoriaDto categorialanche, List<IngredienteDto> ingredientes) {
            // SAVE
            LancheDto lanche = (LancheDto) LancheMock.dto(nome);
            lanche.categoria = categorialanche;
            lanche.ingredientesLanche = ingredientes;

            BigDecimal valorCalculo = BigDecimal.ZERO;

            for (IngredienteDto ingredienteDto : ingredientes)
                valorCalculo = new BigDecimal(ingredienteDto.valor).add(valorCalculo);

            lanche.valor = valorCalculo.toString();

            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.LancheSave, port);
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST,
                    new HttpEntity<>(lanche, null), Object.class);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());

            String json = ObjectMapperUtils.toJson(response.getBody());
            lanche = ObjectMapperUtils.jsonTo(json, LancheDto.class);

            assertNotNull(lanche.codigo);

            return lanche;
        }

        private ProdutoDto PRODUTO(String nome, CategoriaDto categoria) {
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

        private void ESTOQUE(ProdutoDto produto) {
            EstoqueDto estoque = EstoqueMock.dto();
            estoque.produto = Mapper.map(produto, EstoqueProdutoDto.class);
            estoque.quantidade = 1;
            estoque.data = LocalDateTime.now().toString();

            HttpEntity<EstoqueDto> requestSave = new HttpEntity<>(estoque, null);

            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.EstoqueSaveAdd, port);
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, requestSave, Object.class);

            assertNotNull(response);
            assertNotNull(response.getBody());
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        private CategoriaDto CATEGORIA(String nome) {
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
            assertEquals(1, page.totalPages);

            entity = Mapper.map(page.content.get(0), ComboDto.class);
        }

    }

}