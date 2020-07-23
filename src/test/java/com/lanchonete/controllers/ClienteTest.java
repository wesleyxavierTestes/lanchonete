package com.lanchonete.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lanchonete.domain.entities.cliente.Cliente;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ClienteTest {
    
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Deve listar todos clientes com lista vazia")
	public void listar() throws Exception {
        
        ResponseEntity<Object> response = restTemplate
        .getForEntity(
            new URL("http://localhost:" + port + "/api/cliente/listar").toString(),
            Object.class
            );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        assertTrue(true);
	}
}