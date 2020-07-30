package com.lanchonete.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.lanchonete.apllication.dto.cliente.ClienteDefaultDto;
import com.lanchonete.apllication.dto.cliente.ClienteDto;
import com.lanchonete.apllication.dto.cliente.ClienteListDto;
import com.lanchonete.apllication.dto.cliente.EnderecoDto;
import com.lanchonete.apllication.mappers.Mapper;
import com.lanchonete.apllication.validations.CustomErro;
import com.lanchonete.domain.entities.cliente.Cliente;
import com.lanchonete.domain.services.cliente.ClienteService;
import com.lanchonete.mocks.entities.ClienteMock;
import com.lanchonete.mocks.pages.ClienteUtilsPageMock;
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
    public void converterClientDto() {

        Cliente cliente = Mapper.map(ClienteMock.dto("converterClientDto"));

        assertEquals("converterClientDto", cliente.getNome());
    }

    @Test
    @DisplayName("Deve converter uma Cliente para ClienteDto incluindo Endereco")
    public void converterDtoClient() {

        ClienteDto cliente = Mapper.map(ClienteMock.by("converterDtoClient"));

        assertEquals("converterDtoClient", cliente.nome);
    }

    @Nested
    @DisplayName(value = "Testes com clientes Invalidos")
    class ClienteInvalid {
        @Test
        @DisplayName("Deve listar todos clientes com lista vazia")
        public void listar() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ClienteList + "/?page=1", port);

            ResponseEntity<ClienteUtilsPageMock> response = restTemplate.getForEntity(url, ClienteUtilsPageMock.class);

            assertNotNull(response);
            assertNotNull(response.getBody());

            ClienteUtilsPageMock page = response.getBody();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(page);
            assertNotNull(page.content);
        }

        @Test
        @DisplayName("Deve buscar um cliente")
        public void find_inexistente() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ClienteFind + "/?id=100000", port);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            assertNotNull(response);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @Test
        @DisplayName("Deve tentar salvar cliente invalido")
        public void save_invalid_test() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ClienteSave, port);

            ClienteDto entity = new ClienteDto();
            entity.nome = "teste1";
            entity.endereco = new EnderecoDto();
            HttpEntity<ClienteDto> requestUpdate = new HttpEntity<>(entity, null);

            ResponseEntity<CustomErro[]> response = restTemplate.exchange(url, HttpMethod.POST, requestUpdate,
                    CustomErro[].class);

            assertNotNull(response);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
            assertTrue(response.getBody().length > 0);
        }

        @Test
        @DisplayName("Deve tentar alterar cliente inexistente")
        public void update() {

            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ClienteUpdate, port);

            ClienteDto entity = ClienteMock.dto("ClienteTest: update");
            entity.id = 0;

            HttpEntity<ClienteDto> requestUpdate = new HttpEntity<>(entity, null);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestUpdate, String.class);

            assertNotNull(response);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @Test
        @DisplayName("Deve tentar salvar cliente default")
        public void save_default() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ClienteSaveDefault, port);

            ResponseEntity<ClienteDto> response = restTemplate.postForEntity(url, new ClienteDefaultDto("teste"),
                    ClienteDto.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName(value = "Testes de integração clientes")
    class ClienteValid {
        private ClienteDto entity;

        //@Test
        @DisplayName("Deve salvar; listar; alterar; buscar e deletar")
        public void save_ok() {
            entity = CLIENTE("ClienteTest: Cliente Save_ok");
            LIST();
            FIND();
            DESACTIVE();
            FIND_DESACTIVE();
            ACTIVE();
            FIND_ACTIVE();
        }

        private void ACTIVE() {
            // ACTIVE
            HttpEntity<ClienteDto> responseurl = new HttpEntity<>(null, null);
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ClienteActive + "/?id=" + entity.id, port);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, responseurl, String.class);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        private void FIND_DESACTIVE() {

            // FIND DESACTIVE
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ClienteFind + "/?id=" + entity.id, port);

            ResponseEntity<ClienteDto> response = restTemplate.getForEntity(url, ClienteDto.class);

            assertNotNull(response);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(false, response.getBody().ativo);
        }

        private void FIND_ACTIVE() {
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ClienteFind + "/?id=" + entity.id, port);

            // FIND DESACTIVE
            ResponseEntity<ClienteDto> response = restTemplate.getForEntity(url, ClienteDto.class);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(true, response.getBody().ativo);
        }

        private void DESACTIVE() {
            // DESACTIVE
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ClienteDesactive + "/?id=" + entity.id, port);

            HttpEntity<ClienteDto> responseurl = new HttpEntity<>(null, null);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, responseurl, String.class);

            assertNotNull(response);

            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        private void FIND() {
            // FIND
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ClienteFind + "/?id=" + entity.id, port);

            ResponseEntity<ClienteDto> responseFind = restTemplate.getForEntity(url, ClienteDto.class);

            assertEquals(HttpStatus.OK, responseFind.getStatusCode());
            assertEquals(entity.nome, responseFind.getBody().nome);
        }

        private void LIST() {
            // LIST
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ClienteList + "/?page=1", port);

            ResponseEntity<ClienteUtilsPageMock> responselist = restTemplate.getForEntity(url,
                    ClienteUtilsPageMock.class);

            ClienteUtilsPageMock page = responselist.getBody();
            assertEquals(HttpStatus.OK, responselist.getStatusCode());
            assertTrue(page.totalElements > 0);
            assertTrue(page.totalPages >= 1);

            ClienteListDto contains = null;

            for (ClienteListDto clienteListDto : page.content) {
                if (clienteListDto.nome.equals("ClienteTest: Cliente Save_ok")) {
                    contains = clienteListDto;
                    break;
                }
            }

            assertNotNull(contains);

            entity = Mapper.map(page.content.get(0), ClienteDto.class);
        }

        private ClienteDto CLIENTE(String nome) {
            // SAVE
            String url = URL_CONSTANTS_TEST.getUrl(URL_CONSTANTS_TEST.ClienteSave, port);
            entity = ClienteMock.dto(nome);

            HttpEntity<ClienteDto> requestSave = new HttpEntity<>(entity, null);
            ResponseEntity<Object> response = null;
            ClienteDto clienteSave = null;
            try {
                Thread.sleep(1000);
                response = restTemplate.postForEntity(url, requestSave, Object.class);

                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNotNull(response.getBody());

                String json = ObjectMapperUtils.toJson(response.getBody());
                clienteSave = ObjectMapperUtils.jsonTo(json, ClienteDto.class);

                assertNotNull(clienteSave);
            } catch (Exception e) {
                System.out.print("MINHAS RESPOSTA " + response);
            }

            return clienteSave;
        }
    
    }
}