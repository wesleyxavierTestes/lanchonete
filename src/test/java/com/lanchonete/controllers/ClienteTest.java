package com.lanchonete.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.net.URL;

import com.lanchonete.apllication.dto.cliente.ClienteDefaultDto;
import com.lanchonete.apllication.dto.cliente.ClienteDto;
import com.lanchonete.domain.entities.cardapio.lanche.Ingrediente;
import com.lanchonete.domain.entities.cliente.Cliente;
import com.lanchonete.domain.entities.produtos.baseentity.IProduto;
import com.lanchonete.domain.entities.produtos.entities.Produto;
import com.lanchonete.domain.services.cliente.ClienteService;
import com.lanchonete.utils.URL_CONSTANTS_TEST;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.modelmapper.ModelMapper;
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
public class ClienteTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    protected ClienteService _repository;

    @Test
    @DisplayName("Deve listar todos clientes com lista vazia")
    public void listar() throws Exception {
        String url = String.format(URL_CONSTANTS_TEST.ClienteList + "/?page=1", port);

        ResponseEntity<String> response = restTemplate.getForEntity(new URL(url).toString(), String.class);
        String json = response.getBody();
        Page<Cliente> page = new PageImpl<>(new ArrayList<Cliente>());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(json);
    }

    @Test
    @DisplayName("Deve buscar um cliente")
    public void find_inexistente() throws Exception {
        String url = String.format(URL_CONSTANTS_TEST.ClienteFind + "/?id=100000", port);

        ResponseEntity<Object> response = restTemplate.getForEntity(new URL(url).toString(), Object.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Deve tentar salvar cliente invalido")
    public void save_invalid_test() throws Exception {
        String url = String.format(URL_CONSTANTS_TEST.ClienteSave, port);

        HttpEntity<ClienteDto> requestUpdate = new HttpEntity<>(new ClienteDto(), null);

        ResponseEntity<ClienteDto> response = restTemplate.exchange(new URL(url).toString(), HttpMethod.POST,
                requestUpdate, ClienteDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "8" })
    @DisplayName("Deve tentar alterar cliente inexisnte")
    public void update(String parametro) throws Exception {

        String url = String.format(URL_CONSTANTS_TEST.ClienteUpdate, port);

        HttpEntity<ClienteDto> requestUpdate = new HttpEntity<>(ClienteDto.builder().nome(parametro).build(), null);

        ResponseEntity<ClienteDto> response = restTemplate.exchange(new URL(url).toString(), HttpMethod.PUT,
                requestUpdate, ClienteDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Deve tentar salvar cliente default")
    public void save_default() throws Exception {
        String url = String.format(URL_CONSTANTS_TEST.ClienteSaveDefault, port);

        ResponseEntity<ClienteDto> response = restTemplate.postForEntity(new URL(url).toString(),
                new ClienteDefaultDto("teste"), ClienteDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}