package com.lanchonete.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.lanchonete.apllication.dto.pedido.PedidoDto;
import com.lanchonete.apllication.dto.pedido.PedidoItemDto;
import com.lanchonete.apllication.dto.cardapio.CardapioDto;
import com.lanchonete.apllication.dto.cardapio.CardapioItemDto;
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
import com.lanchonete.domain.entities.pedido.Pedido;
import com.lanchonete.domain.services.pedido.PedidoService;
import com.lanchonete.mocks.entities.PedidoMock;
import com.lanchonete.mocks.entities.CardapioMock;
import com.lanchonete.mocks.entities.CategoriaMock;
import com.lanchonete.mocks.entities.ComboMock;
import com.lanchonete.mocks.entities.EstoqueMock;
import com.lanchonete.mocks.entities.LancheMock;
import com.lanchonete.mocks.entities.ProdutoMock;
import com.lanchonete.mocks.pages.PedidoUtilsPageMock;
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
public class PedidoTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    protected PedidoService _service;

    @Test
    @DisplayName("Deve converter uma PedidoDto para Pedido incluindo Endereco")
    public void converterClientDto() {

        Pedido combo = Mapper.map(new PedidoDto());
        assertNotNull(combo);
    }

    @Nested
    @DisplayName(value = "Testes com combos Invalidos")
    class PedidoInvalid {
        @Test
        @DisplayName("Deve listar todos combos com lista vazia")
        public void listar() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.PedidoList + "/?page=1", port);

            ResponseEntity<PedidoUtilsPageMock> response = restTemplate.getForEntity(url,
                    PedidoUtilsPageMock.class);
            PedidoUtilsPageMock page = response.getBody();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(page);
            assertNotNull(page.content);
        }

        @Test
        @DisplayName("Deve buscar um combo")
        public void find_inexistente() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.PedidoFind + "/?id=100000", port);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @Test
        @DisplayName("Deve tentar salvar combo invalido")
        public void save_invalid_test() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.PedidoSave, port);

            PedidoDto entity = new PedidoDto();
            HttpEntity<PedidoDto> requestSave = new HttpEntity<>(entity, null);

            ResponseEntity<CustomErro[]> response = restTemplate.exchange(url, HttpMethod.POST, requestSave,
                    CustomErro[].class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
            assertTrue(response.getBody().length > 0);
        }

        @Test
        @DisplayName("Deve tentar alterar combo inexistente")
        public void active() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.PedidoActive + "/?id=100000", port);
            HttpEntity<PedidoDto> requestActive = new HttpEntity<>(null, null);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestActive,
                    String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
        }

        @Test
        @DisplayName("Deve tentar excluir combo inexistente")
        public void desactive() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.PedidoDesactive + "/?id=100000", port);
            HttpEntity<PedidoDto> requestDesactive = new HttpEntity<>(null, null);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestDesactive,
                    String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
        }
    }

    @Nested
    @DisplayName(value = "Testes de integração combos")
    class PedidoValid {
        private PedidoUtilsPageMock page;
        private PedidoDto entity;

        @Test
        @DisplayName("Deve salvar; listar; alterar; buscar e deletar")
        public void save_ok() {
            List<CategoriaDto> categorias = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                CategoriaDto categoria = CATEGORIA("PedidoTest: Categoria" + i + " Save_ok");
                categorias.add(categoria);
            }

            List<ProdutoDto> produtos = new ArrayList<>();

            for (int i = 0; i < 30; i++) {
                int index = new Random().nextInt(20);
                ProdutoDto produto = PRODUTO("PedidoTest: Produto" + i + " Save_ok", categorias.get(index));
                if (index % 2 == 0)
                    ESTOQUE(produto);
                produtos.add(produto);
            }

            List<LancheDto> lanches = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                int indexProduto = new Random().nextInt(25);
                int indexCategoria = new Random().nextInt(10);

                List<IngredienteDto> ingredientes = new ArrayList<>();
                ingredientes.add(Mapper.map(produtos.get(indexProduto + 1), IngredienteDto.class));
                ingredientes.add(Mapper.map(produtos.get(indexProduto), IngredienteDto.class));

                LancheDto lanche = LANCHE("PedidoTest: Lanche" + i + "  Save_ok", categorias.get(indexCategoria),
                        ingredientes);
                lanches.add(lanche);
            }

            List<ComboDto> combos = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                int indexLanche = new Random().nextInt(10);
                int indexCategoria = new Random().nextInt(15);
                int indexProduto = new Random().nextInt(25);
                ComboDto combo = COMBO("PedidoTest: ComboDto" + i + " Save_ok", categorias.get(indexCategoria),
                        lanches.get(indexLanche), produtos.get(indexProduto));
                combos.add(combo);
            }

            CARDAPIO("PedidoTest: PedidoDto Save_ok", produtos, lanches, combos);

            LIST();
            FIND();
            DESACTIVE();
            FIND_DESACTIVE();
            ACTIVE();
            FIND_ACTIVE();
        }

        private CardapioDto CARDAPIO(String nome, List<ProdutoDto> produtos, List<LancheDto> lanches,
                List<ComboDto> combos) {
            // SAVE
            CardapioDto cardapio = (CardapioDto) CardapioMock.dto(nome);
            cardapio.itensDisponiveis = new ArrayList<>();
            CardapioItemDto produto = null;
            for (int i = 0; i < 7; i++) {
                int index = new Random().nextInt(9);
                if (index % 3 == 0)
                    produto = Mapper.map(produtos.get(i), CardapioItemDto.class);
                else if (index % 2 == 0) {
                    produto = Mapper.map(lanches.get(i), CardapioItemDto.class);
                } else {
                    produto = Mapper.map(combos.get(i), CardapioItemDto.class);
                }
                cardapio.itensDisponiveis.add(produto);
            }

            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.CardapioSave, port);
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST,
                    new HttpEntity<>(cardapio, null), Object.class);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());

            String json = ObjectMapperUtils.toJson(response.getBody());
            cardapio = ObjectMapperUtils.jsonTo(json, CardapioDto.class);

            assertNotNull(cardapio);

            return cardapio;
        }

        
        private ComboDto COMBO(String nome, CategoriaDto categoria, LancheDto lanche, ProdutoDto comboBebida) {

            ComboDto combo = null;
            try {
                combo = ComboMock.dto(nome);
                combo.categoria = categoria;
                combo.lanche = Mapper.map(lanche, ComboItemDto.class);
                combo.bebida = Mapper.map(comboBebida, ComboItemDto.class);
                combo.valor = "123";
                combo.valorTotal = "123";
            } catch (Exception e) {
                System.out.print(e);
            }

            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ComboSave, port);
            ResponseEntity<Object> response = null;
            try {
                response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(combo, null), Object.class);
            } catch (Exception e) {
                System.out.print(e);
            }
            if (response == null)
                System.out.print("");

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            try {
                String json = ObjectMapperUtils.toJson(response.getBody());
                combo = ObjectMapperUtils.jsonTo(json, ComboDto.class);

                assertNotNull(lanche.codigo);
            } catch (Exception e) {
                System.out.print(e);
            }
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
            HttpEntity<PedidoDto> responseurl = new HttpEntity<>(null, null);
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.PedidoActive + "/?id=" + entity.id, port);

            ResponseEntity<String> responseActive = restTemplate.exchange(url, HttpMethod.DELETE, responseurl,
                    String.class);

            assertEquals(HttpStatus.OK, responseActive.getStatusCode());
        }

        private void FIND_DESACTIVE() {
            ResponseEntity<PedidoDto> responseFind;
            // FIND DESACTIVE
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.PedidoFind + "/?id=" + entity.id, port);
            responseFind = restTemplate.getForEntity(url, PedidoDto.class);

            assertEquals(HttpStatus.OK, responseFind.getStatusCode());
            assertEquals(false, responseFind.getBody().ativo);
        }

        private void FIND_ACTIVE() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.PedidoFind + "/?id=" + entity.id, port);

            // FIND DESACTIVE
            ResponseEntity<PedidoDto> responseFind = restTemplate.getForEntity(url, PedidoDto.class);

            assertEquals(HttpStatus.OK, responseFind.getStatusCode());
            assertEquals(true, responseFind.getBody().ativo);
        }

        private void DESACTIVE() {
            // DESACTIVE
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.PedidoDesactive + "/?id=" + entity.id, port);

            HttpEntity<PedidoDto> responseurl = new HttpEntity<>(null, null);
            ResponseEntity<String> responseDesactive = restTemplate.exchange(url, HttpMethod.DELETE, responseurl,
                    String.class);

            assertEquals(HttpStatus.OK, responseDesactive.getStatusCode());
        }

        private void FIND() {
            // FIND
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.PedidoFind + "/?id=" + entity.id, port);

            ResponseEntity<PedidoDto> responseFind = restTemplate.getForEntity(url, PedidoDto.class);

            assertEquals(HttpStatus.OK, responseFind.getStatusCode());
        }

        private void LIST() {
            // LIST
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.PedidoList + "/?page=1", port);

            ResponseEntity<PedidoUtilsPageMock> responselist = restTemplate.getForEntity(url,
                    PedidoUtilsPageMock.class);

            page = responselist.getBody();
            assertEquals(HttpStatus.OK, responselist.getStatusCode());
            assertTrue(page.totalElements > 0);
            assertTrue(page.totalPages >= 1);

            entity = Mapper.map(page.content.get(0), PedidoDto.class);
        }

    }

}