package com.lanchonete.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.net.URL;

import com.lanchonete.domain.entities.cardapio.lanche.Ingrediente;
import com.lanchonete.domain.entities.produto.entities.Produto;
import com.lanchonete.domain.services.produto.ProdutoService;
import com.lanchonete.utils.URL_CONSTANTS_TEST;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
        Ingrediente i = produto.convert(Ingrediente.class);
        i.setObservacao("Teste");
        assertEquals("Teste", i.getObservacao());
    }
    
    @Test
    @DisplayName("Deve listar todos Produtos com lista vazia")
	public void listar() throws Exception {
        String url = String.format(URL_CONSTANTS_TEST.ProdutoList+"/?page=1", port);
    
        ResponseEntity<String> response = restTemplate
        .getForEntity(new URL(url).toString(), String.class);
        String json = response.getBody();
       Page<Produto> page = new PageImpl<>(new ArrayList<Produto>());
       
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(json);
	}
/*
    @Test
    @DisplayName("Deve buscar um Produto")
    public void find_inexistente() throws Exception {
        String url = String.format(URL_CONSTANTS_TEST.ProdutoFind+"/?id=100000", port);

        ResponseEntity<Object> response = restTemplate
                .getForEntity(new URL(url).toString(), Object.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Deve tentar salvar Produto invalido")
    public void save_invalid_test() throws Exception {
        String url = String.format(URL_CONSTANTS_TEST.ProdutoSave, port);

        HttpEntity<ProdutoDto> requestUpdate = new HttpEntity<>(new ProdutoDto(), null);

        ResponseEntity<ProdutoDto> response = restTemplate
                .exchange(new URL(url).toString(), HttpMethod.POST, requestUpdate, ProdutoDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "8"})
    @DisplayName("Deve tentar alterar Produto inexisnte")
    public void update(String parametro) throws Exception {

        String url = String.format(URL_CONSTANTS_TEST.ProdutoUpdate, port);

        HttpEntity<ProdutoDto> requestUpdate = new HttpEntity<>(ProdutoDto.builder().nome(parametro).build(), null);

        ResponseEntity<ProdutoDto> response = restTemplate
                .exchange(new URL(url).toString(), HttpMethod.PUT, requestUpdate, ProdutoDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());        
    }

    @Test
    @DisplayName("Deve tentar excluir Produto inexistente")
    public void delete_inexistente() throws Exception {
        String url = String.format(URL_CONSTANTS_TEST.ProdutoDelete+"?id=0", port);

        HttpEntity<ProdutoDto> requestUpdate = new HttpEntity<>(null);
        
        ResponseEntity<ProdutoDto> response = restTemplate
                .exchange(new URL(url).toString(), HttpMethod.DELETE, requestUpdate, ProdutoDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }*/
}