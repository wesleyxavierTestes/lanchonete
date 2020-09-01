package com.lanchonete.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.lanchonete.apllication.dto.cardapio.CardapioDto;
import com.lanchonete.apllication.dto.cardapio.CardapioListDto;
import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.apllication.dto.cliente.ClienteGenericDto;
import com.lanchonete.apllication.dto.combo.ComboDto;
import com.lanchonete.apllication.dto.lanche.IngredienteDto;
import com.lanchonete.apllication.dto.lanche.LancheDto;
import com.lanchonete.apllication.dto.pedido.PedidoDto;
import com.lanchonete.apllication.dto.pedido.PedidoListDto;
import com.lanchonete.apllication.dto.produto.ProdutoDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.cliente.Cliente;
import com.lanchonete.domain.entities.pedido.PedidoAguardando;
import com.lanchonete.domain.entities.pedido.PedidoNovo;
import com.lanchonete.domain.services.cardapio.CardapioService;
import com.lanchonete.domain.services.cliente.ClienteService;
import com.lanchonete.domain.services.pedido.PedidoService;
import com.lanchonete.mocks.entities.CardapioMock;
import com.lanchonete.mocks.entities.CategoriaMock;
import com.lanchonete.mocks.entities.ClienteMock;
import com.lanchonete.mocks.entities.ComboMock;
import com.lanchonete.mocks.entities.EstoqueMock;
import com.lanchonete.mocks.entities.LancheMock;
import com.lanchonete.mocks.entities.PedidoMock;
import com.lanchonete.mocks.entities.ProdutoMock;
import com.lanchonete.mocks.pages.CardapioUtilsPageMock;
import com.lanchonete.mocks.pages.PedidoUtilsPageMock;
import com.lanchonete.utils.URL_CONSTANTS_TEST;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
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

    @Autowired
    private CardapioService _cardapioService;

    @Autowired
    private ClienteService _clienteService;

    @Test
    @DisplayName("Deve converter uma PedidoDto para Pedido incluindo Endereco")
    public void converterClientDto() {

        PedidoNovo pedido1 = Mapper.map(new PedidoDto(), PedidoNovo.class);
        assertNotNull(pedido1);

        PedidoAguardando pedido2 = Mapper.map(new PedidoDto(), PedidoAguardando.class);
        assertNotNull(pedido2);

        PedidoNovo pedido = Mapper.map(new PedidoDto(), PedidoNovo.class);
        assertNotNull(pedido);
    }

    @Nested
    @DisplayName(value = "Testes com pedidos Invalidos")
    class PedidoInvalid {
        @Test
        @DisplayName("Deve listar todos pedidos com lista vazia")
        public void listar() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.PedidoList + "/?page=1", port);

            ResponseEntity<PedidoUtilsPageMock> response = restTemplate.getForEntity(url, PedidoUtilsPageMock.class);
            PedidoUtilsPageMock page = response.getBody();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(page);
            assertNotNull(page.content);
        }

        @Test
        @DisplayName("Deve buscar um pedido")
        public void find_inexistente() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.PedidoFind + "/?id=100000", port);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @Test
        @DisplayName("Deve tentar salvar pedido invalido")
        public void save_invalid_test() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.PedidoSave, port);

            PedidoDto entity = new PedidoDto();
            HttpEntity<PedidoDto> requestSave = new HttpEntity<>(entity, null);

            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, requestSave, Object.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
            // // assertTrue(response.getBody().length > 0);
        }

        @Test
        @DisplayName("Deve tentar alterar pedido inexistente")
        public void cancel() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.PedidoCancel + "/?id=100000", port);
            HttpEntity<PedidoDto> requestActive = new HttpEntity<>(null, null);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestActive,
                    String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
        }
    }

    @Nested
    @DisplayName(value = "Testes de integração pedidos")
    class PedidoValid {
        private PedidoUtilsPageMock page;
        private PedidoDto entity;

        @Test
        @DisplayName("Deve salvar; listar; alterar; buscar e deletar")
        public void save_ok() {
            List<CategoriaDto> categorias = CATEGORIA();
            List<ProdutoDto> produtos = PRODUTO(categorias);
            List<LancheDto> lanches = LANCHE(categorias, produtos);
            List<ComboDto> combos = COMBO(categorias, produtos, lanches);
            CardapioDto cardapio = CARDAPIO(produtos, lanches, combos);
            ClienteGenericDto clienteGeneric = CLIENTE();

            PedidoMock pedidoMock = new PedidoMock(restTemplate, port);
            entity = pedidoMock.PEDIDO("PedidoTest: PedidoDto1 Save_ok", clienteGeneric, cardapio);
            entity = pedidoMock.PEDIDO("PedidoTest: PedidoDto2 Save_ok", clienteGeneric, cardapio);
            entity = pedidoMock.PEDIDO("PedidoTest: PedidoDto3 Save_ok", clienteGeneric, cardapio);

            LIST();
            FIND();
            // AGUARDANDO();
            // // FIND_AGUARDANDO();
            // CANCEL();
            // FIND_CANCEL();
        }

        private CardapioDto CARDAPIO(List<ProdutoDto> produtos, List<LancheDto> lanches, List<ComboDto> combos) {
            CardapioMock cardapioMock = new CardapioMock(restTemplate, port);
            cardapioMock.CARDAPIO("PedidoTest: cardapioDto Save_ok", produtos, lanches, combos);

            CardapioDto cardapio = GET_CARDAPIO_COM_ESTOQUE();
            return cardapio;
        }

        private CardapioDto GET_CARDAPIO_COM_ESTOQUE() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.CardapioList + "/?page=1", port);

            ResponseEntity<CardapioUtilsPageMock> response = restTemplate.getForEntity(url,
                    CardapioUtilsPageMock.class);
            CardapioUtilsPageMock pageCardapio = response.getBody();

            assertNotNull(pageCardapio);
            assertNotNull(pageCardapio.content);
            List<CardapioListDto> content = pageCardapio.content;
            CardapioDto cardapio = Mapper.map(content.get(0), CardapioDto.class);
            assertNotNull(cardapio);
            return cardapio;
        }

        private List<ComboDto> COMBO(List<CategoriaDto> categorias, List<ProdutoDto> produtos,
                List<LancheDto> lanches) {
            List<ComboDto> combos = new ArrayList<>();
            ComboMock comboMock = new ComboMock(restTemplate, port);
            for (int i = 0; i < 7; i++) {
                int indexLanche = new Random().nextInt(10);
                int indexCategoria = new Random().nextInt(15);
                int indexProduto = new Random().nextInt(25);
                ComboDto combo = comboMock.COMBO("PedidoTest: ComboDto" + i + " Save_ok",
                        categorias.get(indexCategoria), lanches.get(indexLanche), produtos.get(indexProduto));
                combos.add(combo);
            }
            return combos;
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
                LancheDto lanche = lancheMock.LANCHE("PedidoTest: Lanche" + i + "  Save_ok",
                        categorias.get(indexCategoria), ingredientes);
                lanches.add(lanche);
            }
            return lanches;
        }

        private List<CategoriaDto> CATEGORIA() {
            List<CategoriaDto> categorias = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                CategoriaDto categoria = new CategoriaMock(restTemplate, port)
                        .CATEGORIA("PedidoTest: Categoria" + i + " Save_ok");
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
                ProdutoDto produto = produtoMock.PRODUTO("PedidoTest: Produto" + i + " Save_ok", categorias.get(index));
                // if (index % 2 == 0) {
                estoqueMock.ESTOQUE(produto);
                estoqueMock.ESTOQUE(produto);
                estoqueMock.ESTOQUE(produto);
                // } else if (i == 10) {
                estoqueMock.ESTOQUE(produto);
                estoqueMock.ESTOQUE(produto);
                estoqueMock.ESTOQUE(produto);
                estoqueMock.ESTOQUE(produto);
                // }
                produtos.add(produto);
            }
            return produtos;
        }

        private ClienteGenericDto CLIENTE() {
            Cliente cliente = _clienteService.save(ClienteMock.by("nome"));
            ClienteGenericDto clienteGeneric = Mapper.map(cliente, ClienteGenericDto.class);
            return clienteGeneric;
        }

        // private void FIND_CANCEL() {
        // ResponseEntity<PedidoDto> responseFind;
        // // FIND DESACTIVE
        // String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.PedidoFindCancel +
        // "/?id=" + entity.id, port);
        // responseFind = restTemplate.getForEntity(url, PedidoDto.class);

        // assertEquals(HttpStatus.OK, responseFind.getStatusCode());
        // assertEquals(false, responseFind.getBody().ativo);
        // }

        // private void CANCEL() {
        // // DESACTIVE
        // String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.PedidoCancel +
        // "/?id=" + entity.id, port);

        // HttpEntity<PedidoDto> responseurl = new HttpEntity<>(null, null);
        // ResponseEntity<Object> response = restTemplate.exchange(url,
        // HttpMethod.DELETE, responseurl, Object.class);

        // assertNotNull(response);
        // assertEquals(HttpStatus.OK, response.getStatusCode());
        // assertNotNull(response.getBody());

        // String json = ObjectMapperUtils.toJson(response.getBody());
        // PedidoCancelamento pedido = ObjectMapperUtils.jsonTo(json,
        // PedidoCancelamento.class);

        // assertNotNull(pedido);
        // }

        private void FIND() {
            // FIND
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.PedidoFind + "/?id=" + entity.id, port);

            ResponseEntity<PedidoDto> responseFind = restTemplate.getForEntity(url, PedidoDto.class);

            assertEquals(HttpStatus.OK, responseFind.getStatusCode());
        }

        private void LIST() {
            // LIST
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.PedidoList + "/?page=1", port);
            ResponseEntity<PedidoUtilsPageMock> responselist = null;
            try {
                responselist = restTemplate.getForEntity(url, PedidoUtilsPageMock.class);
            } catch (Exception e) {
                System.out.print(e);
            }

            page = responselist.getBody();
            assertEquals(HttpStatus.OK, responselist.getStatusCode());
            assertTrue(page.totalElements > 0);
            assertTrue(page.totalPages >= 1);

            PedidoListDto o = page.content.get(0);
            entity = Mapper.map(o, PedidoDto.class);
        }

    }

}