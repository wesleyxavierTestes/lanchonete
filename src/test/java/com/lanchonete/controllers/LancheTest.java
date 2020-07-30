package com.lanchonete.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.apllication.dto.estoque.EstoqueDto;
import com.lanchonete.apllication.dto.estoque.EstoqueProdutoDto;
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

            ResponseEntity<CustomErro[]> response = restTemplate.exchange(url, HttpMethod.POST, requestSave,
                    CustomErro[].class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
            assertTrue(response.getBody().length > 0);
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
            CategoriaDto categoria1 = CATEGORIA("LancheTest: Categoria1 Save_ok");
            CategoriaDto categoria2 = CATEGORIA("LancheTest: Categoria2 Save_ok");
            ProdutoDto produto1 = PRODUTO("LancheTest: Produto1 Save_ok", categoria1);
            ProdutoDto produto2 = PRODUTO("LancheTest: Produto2 Save_ok", categoria2);
            ESTOQUE(produto1);
            ESTOQUE(produto2);

            List<IngredienteDto> ingredientes = new ArrayList<>();
            ingredientes.add(Mapper.map(produto1, IngredienteDto.class));
            ingredientes.add(Mapper.map(produto2, IngredienteDto.class));

            entity = LANCHE("LancheTest: Lanche Save_ok", categoria1, ingredientes);
            LIST();
            FIND();
            DESACTIVE();
            FIND_DESACTIVE();
            ACTIVE();
            FIND_ACTIVE();
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
            HttpEntity<LancheDto> httpEntity = new HttpEntity<>(lanche, null);
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Object.class);

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
            assertEquals(1, page.totalPages);

            entity = Mapper.map(page.content.get(0), LancheDto.class);
        }

    }

}