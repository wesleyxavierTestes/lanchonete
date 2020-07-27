package com.lanchonete.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;

import com.lanchonete.apllication.dto.cliente.ClienteDefaultDto;
import com.lanchonete.apllication.dto.cliente.ClienteDto;
import com.lanchonete.apllication.dto.cliente.EnderecoDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.domain.entities.cliente.Cliente;
import com.lanchonete.domain.services.cliente.ClienteService;
import com.lanchonete.mocks.entities.ClienteMock;
import com.lanchonete.mocks.pages.ClienteUtilsPageMock;
import com.lanchonete.utils.URL_CONSTANTS_TEST;
import com.lanchonete.apllication.validations.CustomErro;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
public class ClienteTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    protected ClienteService _service;

    @Test
    @DisplayName("Deve converter uma ClienteDto para Cliente incluindo Endereco")
    public void converterClientDto() throws Exception {

        Cliente cliente = Mapper.map(ClienteMock.dto());

        assertEquals(ClienteMock.dto().nome, cliente.getNome());
    }

    @Test
    @DisplayName("Deve converter uma Cliente para ClienteDto incluindo Endereco")
    public void converterDtoClient() throws Exception {

        ClienteDto cliente = Mapper.map(ClienteMock.by());

        assertEquals(ClienteMock.by().getNome(), cliente.nome);
    }

    @Nested
    @DisplayName(value = "Testes com clientes Invalidos")
    class ClienteInvalid {
        @Test
        @DisplayName("Deve listar todos clientes com lista vazia")
        public void listar() throws Exception {
            String url = String.format(URL_CONSTANTS_TEST.ClienteList + "/?page=1", port);

            ResponseEntity<ClienteUtilsPageMock> response = restTemplate.getForEntity(new URL(url).toString(),
                    ClienteUtilsPageMock.class);
            ClienteUtilsPageMock page = response.getBody();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(page);
            assertNotNull(page.content);
        }

        @Test
        @DisplayName("Deve buscar um cliente")
        public void find_inexistente() throws Exception {
            String url = String.format(URL_CONSTANTS_TEST.ClienteFind + "/?id=100000", port);

            ResponseEntity<String> response = restTemplate
            .getForEntity(new URL(url).toString(), String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @Test
        @DisplayName("Deve tentar salvar cliente invalido")
        public void save_invalid_test() throws Exception {
            String url = String.format(URL_CONSTANTS_TEST.ClienteSave, port);

            ClienteDto entity = new ClienteDto();
            entity.nome = "teste1";
            entity.endereco = new EnderecoDto();
            HttpEntity<ClienteDto> requestUpdate = new HttpEntity<>(entity, null);

            ResponseEntity<CustomErro[]> response = restTemplate.exchange(new URL(url).toString(), HttpMethod.POST,
                    requestUpdate, CustomErro[].class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
            assertTrue(response.getBody().length > 0);
        }

        @Test
        @DisplayName("Deve tentar alterar cliente inexistente")
        public void update() throws Exception {

            String url = String.format(URL_CONSTANTS_TEST.ClienteUpdate, port);

            ClienteDto entity = ClienteMock.dto();
            entity.id = 0;

            HttpEntity<ClienteDto> requestUpdate = new HttpEntity<>(entity, null);

            ResponseEntity<String> response = restTemplate.exchange(new URL(url).toString(), HttpMethod.PUT,
                    requestUpdate, String.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @Test
        @DisplayName("Deve tentar salvar cliente default")
        public void save_default() throws Exception {
            String url = String.format(URL_CONSTANTS_TEST.ClienteSaveDefault, port);

            ResponseEntity<ClienteDto> response = restTemplate.postForEntity(new URL(url).toString(),
                    new ClienteDefaultDto("teste"), ClienteDto.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName(value = "Testes de integração clientes")
    class ClienteValid {

        @Test
        @DisplayName("Deve listar todos clientes com lista vazia")
        public void save_ok() throws Exception {

            assertTrue(true);
        }
    }
}