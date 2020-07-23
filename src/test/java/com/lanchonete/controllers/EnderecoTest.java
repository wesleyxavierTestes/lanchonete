package com.lanchonete.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.net.URL;

import com.lanchonete.apllication.dto.cliente.EnderecoDto;
import com.lanchonete.domain.entities.cliente.Endereco;
import com.lanchonete.domain.services.cliente.EnderecoService;
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
public class EnderecoTest {
    
	@LocalServerPort
	private int port;

	@Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    protected EnderecoService _repository;

    @Test
    @DisplayName("Deve listar todos enderecos com lista vazia")
	public void listar() throws Exception {
        String url = String.format(URL_CONSTANTS_TEST.EnderecoList+"/?page=1", port);
    
        ResponseEntity<String> response = restTemplate
        .getForEntity(new URL(url).toString(), String.class);
        String json = response.getBody();
       Page<Endereco> page = new PageImpl<>(new ArrayList<Endereco>());
       
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(json);
	}

    @Test
    @DisplayName("Deve buscar um endereco")
    public void find_inexistente() throws Exception {
        String url = String.format(URL_CONSTANTS_TEST.EnderecoFind+"/?id=100000", port);

        ResponseEntity<Object> response = restTemplate
                .getForEntity(new URL(url).toString(), Object.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Deve tentar salvar endereco invalido")
    public void save_invalid_test() throws Exception {
        String url = String.format(URL_CONSTANTS_TEST.EnderecoSave, port);

        HttpEntity<EnderecoDto> requestUpdate = new HttpEntity<>(new EnderecoDto(), null);

        ResponseEntity<EnderecoDto> response = restTemplate
                .exchange(new URL(url).toString(), HttpMethod.POST, requestUpdate, 
                EnderecoDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "8"})
    @DisplayName("Deve tentar alterar endereco inexisnte")
    public void update(String parametro) throws Exception {

        String url = String.format(URL_CONSTANTS_TEST.EnderecoUpdate, port);

        HttpEntity<EnderecoDto> requestUpdate = new HttpEntity<>(EnderecoDto.builder().cep(parametro).build(), null);

        ResponseEntity<EnderecoDto> response = restTemplate
                .exchange(new URL(url).toString(), HttpMethod.PUT, requestUpdate, EnderecoDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());        
    }

    @Test
    @DisplayName("Deve tentar excluir endereco inexistente")
    public void delete_inexistente() throws Exception {
        String url = String.format(URL_CONSTANTS_TEST.EnderecoDelete+"?id=0", port);

        HttpEntity<EnderecoDto> requestUpdate = new HttpEntity<>(null);
        
        ResponseEntity<EnderecoDto> response = restTemplate
                .exchange(new URL(url).toString(), HttpMethod.DELETE, requestUpdate, EnderecoDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}