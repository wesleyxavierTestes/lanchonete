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
import com.lanchonete.apllication.dto.venda.VendaDto;
import com.lanchonete.apllication.dto.venda.VendaListDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.apllication.validations.CustomErro;
import com.lanchonete.domain.entities.cliente.Cliente;
import com.lanchonete.domain.entities.venda.Venda;
import com.lanchonete.domain.services.cardapio.CardapioService;
import com.lanchonete.domain.services.cliente.ClienteService;
import com.lanchonete.domain.services.pedido.PedidoService;
import com.lanchonete.domain.services.venda.VendaService;
import com.lanchonete.mocks.entities.CardapioMock;
import com.lanchonete.mocks.entities.CategoriaMock;
import com.lanchonete.mocks.entities.ClienteMock;
import com.lanchonete.mocks.entities.ComboMock;
import com.lanchonete.mocks.entities.EstoqueMock;
import com.lanchonete.mocks.entities.LancheMock;
import com.lanchonete.mocks.entities.PedidoMock;
import com.lanchonete.mocks.entities.ProdutoMock;
import com.lanchonete.mocks.entities.VendaMock;
import com.lanchonete.mocks.pages.VendaUtilsPageMock;
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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class VendaTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    protected VendaService _service;

    @Autowired
    private CardapioService _cardapioService;

    @Autowired
    private PedidoService _pedidoService;

    @Autowired
    private ClienteService _clienteService;

    @Test
    @DisplayName("Deve converter uma VendaDto para Venda incluindo Endereco")
    public void converterClientDto() {

        Venda venda1 = Mapper.map(new VendaDto(), Venda.class);
        assertNotNull(venda1);
    }

    @Nested
    @DisplayName(value = "Testes com vendas Invalidos")
    class VendaInvalid {
        @Test
        @DisplayName("Deve listar todos vendas com lista vazia")
        public void listar() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.VendaList + "/?page=1", port);

            ResponseEntity<VendaUtilsPageMock> response = restTemplate.getForEntity(url, VendaUtilsPageMock.class);
            VendaUtilsPageMock page = response.getBody();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(page);
            assertNotNull(page.content);
        }

        @Test
        @DisplayName("Deve buscar um venda")
        public void find_inexistente() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.VendaFind + "/?id=100000", port);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @Test
        @DisplayName("Deve tentar salvar venda invalido")
        public void save_invalid_test() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.VendaSave, port);

            VendaDto entity = new VendaDto();
            HttpEntity<VendaDto> requestSave = new HttpEntity<>(entity, null);

            ResponseEntity<CustomErro[]> response = restTemplate.exchange(url, HttpMethod.POST, requestSave,
                    CustomErro[].class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
            assertTrue(response.getBody().length > 0);
        }

        @Test
        @DisplayName("Deve tentar alterar venda inexistente")
        public void cancel() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.VendaCancel + "/?id=100000", port);
            HttpEntity<VendaDto> requestActive = new HttpEntity<>(null, null);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestActive,
                    String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
        }
    }

    @Nested
    @DisplayName(value = "Testes de integração vendas")
    class VendaValid {
        private VendaUtilsPageMock page;
        private VendaDto entity;

        // @Test
        @DisplayName("Deve salvar; listar; alterar; buscar e deletar")
        public void save_ok() {
            List<CategoriaDto> categorias = CATEGORIA();
            List<ProdutoDto> produtos = PRODUTO(categorias);
            List<LancheDto> lanches = LANCHE(categorias, produtos);
            List<ComboDto> combos = COMBO(categorias, produtos, lanches);
            CardapioDto cardapio = CARDAPIO(produtos, lanches, combos);
            ClienteGenericDto clienteGeneric = CLIENTE();
            PEDIDO(cardapio, clienteGeneric);
            List<PedidoListDto> pedidos = GET_PEDIDOS();

            Venda(pedidos);
            // LIST();
            // FIND();
            // // AGUARDANDO();
            // // FIND_AGUARDANDO();
            // CANCEL();
            // FIND_CANCEL();
        }

        private VendaDto Venda(List<PedidoListDto> pedidos) {
            VendaMock vendaMock = new VendaMock(restTemplate, port);
            entity = vendaMock.VENDA("nome", pedidos);
            return entity;
        }

        private List<PedidoDto> PEDIDO(CardapioDto cardapio, ClienteGenericDto clienteGeneric) {
            PedidoMock pedidoMock = new PedidoMock(restTemplate, port);
            List<PedidoDto> lista = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                PedidoDto pedido = pedidoMock.PEDIDO("VendaTest: PedidoDto" + i + " Save_ok", clienteGeneric, cardapio);
                lista.add(pedido);
            }

            return lista;
        }

        private List<PedidoListDto> GET_PEDIDOS() {
            Page<PedidoListDto> pagePedido = _pedidoService.listDto(1);
            assertNotNull(pagePedido);
            assertNotNull(pagePedido.getContent());
            List<PedidoListDto> content = pagePedido.getContent();
            PedidoDto pedido = Mapper.map(content.get(0), PedidoDto.class);
            assertNotNull(pedido);
            return content;
        }

        private CardapioDto CARDAPIO(List<ProdutoDto> produtos, List<LancheDto> lanches, List<ComboDto> combos) {
            CardapioMock cardapioMock = new CardapioMock(restTemplate, port);
            cardapioMock.CARDAPIO("VendaTest: cardapioDto Save_ok", produtos, lanches, combos);

            CardapioDto cardapio = GET_CARDAPIO_COM_ESTOQUE();
            return cardapio;
        }

        private CardapioDto GET_CARDAPIO_COM_ESTOQUE() {
            Page<CardapioListDto> pageCardapio = _cardapioService.listDto(1);
            assertNotNull(pageCardapio);
            assertNotNull(pageCardapio.getContent());
            List<CardapioListDto> content = pageCardapio.getContent();
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
                ComboDto combo = comboMock.COMBO("VendaTest: ComboDto" + i + " Save_ok", categorias.get(indexCategoria),
                        lanches.get(indexLanche), produtos.get(indexProduto));
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
                LancheDto lanche = lancheMock.LANCHE("VendaTest: Lanche" + i + "  Save_ok",
                        categorias.get(indexCategoria), ingredientes);
                lanches.add(lanche);
            }
            return lanches;
        }

        private List<CategoriaDto> CATEGORIA() {
            List<CategoriaDto> categorias = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                CategoriaDto categoria = new CategoriaMock(restTemplate, port)
                        .CATEGORIA("VendaTest: Categoria" + i + " Save_ok");
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
                ProdutoDto produto = produtoMock.PRODUTO("VendaTest: Produto" + i + " Save_ok", categorias.get(index));
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

        private void FIND_CANCEL() {
            ResponseEntity<VendaDto> responseFind;
            // FIND DESACTIVE
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.VendaFindCancel + "/?id=" + entity.id, port);
            responseFind = restTemplate.getForEntity(url, VendaDto.class);

            assertEquals(HttpStatus.OK, responseFind.getStatusCode());
            assertEquals(false, responseFind.getBody().ativo);
        }

        private void CANCEL() {
            // DESACTIVE
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.VendaCancel + "/?id=" + entity.id, port);

            HttpEntity<VendaDto> responseurl = new HttpEntity<>(null, null);
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.DELETE, responseurl, Object.class);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());

            String json = ObjectMapperUtils.toJson(response.getBody());
            Venda venda = ObjectMapperUtils.jsonTo(json, Venda.class);

            assertNotNull(venda);
        }

        private void FIND() {
            // FIND
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.VendaFind + "/?id=" + entity.id, port);

            ResponseEntity<VendaDto> responseFind = restTemplate.getForEntity(url, VendaDto.class);

            assertEquals(HttpStatus.OK, responseFind.getStatusCode());
        }

        private void LIST() {
            // LIST
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.VendaList + "/?page=1", port);
            ResponseEntity<VendaUtilsPageMock> responselist = null;
            try {
                responselist = restTemplate.getForEntity(url, VendaUtilsPageMock.class);
            } catch (Exception e) {
                System.out.print(e);
            }

            page = responselist.getBody();
            assertEquals(HttpStatus.OK, responselist.getStatusCode());
            assertTrue(page.totalElements > 0);
            assertTrue(page.totalPages >= 1);

            VendaListDto o = page.content.get(0);
            entity = Mapper.map(o, VendaDto.class);
        }

    }

}