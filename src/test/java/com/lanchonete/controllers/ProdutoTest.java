package com.lanchonete.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.net.URL;

import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.cardapio.lanche.Ingrediente;
import com.lanchonete.domain.entities.produto.entities.Produto;
import com.lanchonete.domain.services.produto.ProdutoService;
import com.lanchonete.utils.URL_CONSTANTS_TEST;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
}