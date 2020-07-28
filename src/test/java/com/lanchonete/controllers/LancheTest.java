package com.lanchonete.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;

import com.lanchonete.apllication.dto.categoria.CategoriaDto;
import com.lanchonete.apllication.dto.lanche.IngredienteDto;
import com.lanchonete.apllication.dto.lanche.LancheDto;
import com.lanchonete.apllication.dto.produto.ProdutoDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.apllication.validations.CustomErro;
import com.lanchonete.domain.entities.BaseEntity;
import com.lanchonete.domain.entities.cardapio.lanche.Ingrediente;
import com.lanchonete.domain.entities.cardapio.lanche.Lanche;
import com.lanchonete.domain.entities.categoria.Categoria;
import com.lanchonete.domain.entities.estoque.EstoqueEntrada;
import com.lanchonete.domain.entities.produto.baseentity.IProdutoComposicao;
import com.lanchonete.domain.entities.produto.entities.Produto;
import com.lanchonete.domain.services.lanche.LancheService;
import com.lanchonete.infra.repositorys.categoria.ICategoriaRepository;
import com.lanchonete.infra.repositorys.estoque.IEstoqueRepository;
import com.lanchonete.infra.repositorys.produto.IProdutoRepository;
import com.lanchonete.mocks.entities.CategoriaMock;
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
import org.springframework.data.jpa.repository.JpaRepository;
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

    @Autowired
    private ICategoriaRepository _categoriaRepository;

    @Autowired
    private IEstoqueRepository _estoqueRepository;

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

        private CategoriaDto categoria1;
        private CategoriaDto categoria2;
        private ProdutoDto produto1;
        private ProdutoDto produto2;

        @Test
        @DisplayName("Deve salvar; listar; alterar; buscar e deletar")
        public void save_ok() throws Exception {
            SetCategoria();
            SetProduto();
            SAVE();
            LIST();
            FIND();
            DESACTIVE();
            FIND_DESACTIVE();
            ACTIVE();
            FIND_ACTIVE();
        }

        private void SetProduto() throws MalformedURLException {
            produto1 = ProdutoMock.dto();
            produto1.categoria = categoria1;
            produto2 = ProdutoMock.dto();
            produto2.categoria = categoria2;

            List<ProdutoDto> list = new ArrayList<ProdutoDto>() {
                {
                    add(produto1);
                    add(produto2);
                }
            };
            List<ProdutoDto> listNew = new ArrayList<>();
            String urlSave = String.format(URL_CONSTANTS_TEST.ProdutoSave, port);

            for (ProdutoDto produto : list) {
                HttpEntity<ProdutoDto> requestSave = new HttpEntity<>(produto, null);
                ResponseEntity<Object> response = restTemplate.exchange(new URL(urlSave).toString(), HttpMethod.POST,
                        requestSave, Object.class);

                assertEquals(HttpStatus.OK, response.getStatusCode(), "SAVE expect Error");
                assertNotNull(response.getBody());

                String json = ObjectMapperUtils.toJson(response.getBody());
                produto = ObjectMapperUtils.jsonTo(json, ProdutoDto.class);

                assertNotNull(produto.codigo);
                listNew.add(produto);

                // ADD ESTOQUE
                EstoqueEntrada estoqueEntrada = EstoqueEntrada.ProdutoSave(Mapper.map(produto));
                estoqueEntrada.setQuantidade(1);
                _estoqueRepository.save(estoqueEntrada);
            }

            produto1.id = listNew.get(0).id;
            produto2.id = listNew.get(1).id;

            produto1.codigo = listNew.get(0).codigo;
            produto2.codigo = listNew.get(1).codigo;
        }

        private void SetCategoria() throws MalformedURLException {
            categoria1 = CategoriaMock.dto();
            categoria2 = CategoriaMock.dto();

            CategoriaDto[] list = { (categoria1), (categoria2) };

            String urlSave = String.format(URL_CONSTANTS_TEST.CategoriaSave, port);
            List<CategoriaDto> listNew = new ArrayList<>();
            for (CategoriaDto categoria : list) {

                HttpEntity<CategoriaDto> requestSave = new HttpEntity<>(categoria, null);
                ResponseEntity<CategoriaDto> response = restTemplate.exchange(new URL(urlSave).toString(),
                        HttpMethod.POST, requestSave, CategoriaDto.class);
                String json = ObjectMapperUtils.toJson(response.getBody());
                categoria = ObjectMapperUtils.jsonTo(json, CategoriaDto.class);

                listNew.add(categoria);
            }

            categoria1.id = listNew.get(0).id;
            categoria2.id = listNew.get(1).id;
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
            ResponseEntity<LancheDto> responseFind = restTemplate.getForEntity(new URL(urlFind).toString(),
                    LancheDto.class);

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
            ConfiguratEntity();

            ResponseEntity<Object> response = restTemplate.exchange(
                    new URL(String.format(URL_CONSTANTS_TEST.LancheSave, port)).toString(), HttpMethod.POST,
                    new HttpEntity<>(entity, null), Object.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());

            String json = ObjectMapperUtils.toJson(response.getBody());
            entity = ObjectMapperUtils.jsonTo(json, LancheDto.class);

            assertNotNull(entity.codigo);
        }

        private void ConfiguratEntity() {
            entity = (LancheDto) LancheMock.dto();
            entity.categoria = categoria1;
            entity.ingredientesLanche = new ArrayList<>();

            IngredienteDto ingrediente1 = Mapper.map(produto1, IngredienteDto.class);
            assertNotNull(ingrediente1);
            entity.ingredientesLanche.add(ingrediente1);
            IngredienteDto ingrediente2 = Mapper.map(produto2, IngredienteDto.class);
            assertNotNull(ingrediente2);
            entity.ingredientesLanche.add(ingrediente2);

            BigDecimal valorCalculo = new BigDecimal(ingrediente1.valor).add(new BigDecimal(ingrediente2.valor));

            Lanche map = Mapper.map(entity);
            map.calcularValorTotal();
            BigDecimal valorCalculoMap = map.getValor();
            assertEquals(valorCalculoMap, valorCalculo);

            entity.valor = valorCalculo.toString();
        }

    }

}