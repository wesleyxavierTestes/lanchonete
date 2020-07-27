package com.lanchonete.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.net.URL;

import com.lanchonete.apllication.dto.produto.ProdutoDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.apllication.validations.CustomErro;
import com.lanchonete.domain.entities.cardapio.lanche.Ingrediente;
import com.lanchonete.domain.entities.produto.entities.Produto;
import com.lanchonete.domain.services.produto.ProdutoService;
import com.lanchonete.mocks.entities.ProdutoMock;
import com.lanchonete.mocks.pages.ProdutoUtilsPageMock;
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
    protected ProdutoService _repository;

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

            ResponseEntity<String> response = restTemplate
            .getForEntity(new URL(url).toString(), String.class);

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

            ProdutoDto entity = ProdutoMock.dto();
            entity.id = 0;

            HttpEntity<ProdutoDto> requestUpdate = new HttpEntity<>(entity, null);

            ResponseEntity<String> response = restTemplate.exchange(new URL(url).toString(), 
            HttpMethod.PUT,
                    requestUpdate, String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }
    }
}