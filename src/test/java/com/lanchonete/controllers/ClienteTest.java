package com.lanchonete.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.net.URL;

import com.lanchonete.apllication.dto.cliente.ClienteDefaultDto;
import com.lanchonete.apllication.dto.cliente.ClienteDto;
import com.lanchonete.apllication.dto.cliente.EnderecoDto;
import com.lanchonete.domain.entities.cliente.Cliente;
import com.lanchonete.domain.enuns.cliente.EnumTipoCliente;
import com.lanchonete.domain.enuns.cliente.EnumTipoPessoa;
import com.lanchonete.domain.services.cliente.ClienteService;
import com.lanchonete.utils.URL_CONSTANTS_TEST;
import com.lanchonete.utils.pages.ClienteUtilsPageMock;

import org.junit.jupiter.api.DisplayName;
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
    protected ClienteService _repository;

    @Test
    @DisplayName("Deve converter uma ClienteDto para Cliente incluindo Endereco")
    public void converterClientDto() throws Exception {
        EnderecoDto enderecoDtomock = EnderecoDto.builder()
                                    .cep("00000000")
                                    .numero("123456")       
                                    .logradouro("i.endereco.logradouro")   
                                    .complemento("i.endereco.complemento") 
                                    .bairro("i.endereco.bairro")    
                                    .localidade("i.endereco.localidade")
                                    .uf("i.endereco.uf")
                                    .unidade("i.endereco.unidad")
                                    .ibge("i.endereco.ibge")
                                    .gia("i.endereco.gia")
                                    .build();
        ClienteDto clienteDtoMock = ClienteDto.builder()
                                    .nome("wesley xavier")
                                    .cnjp("0000000000")
                                    .cpf(null)
                                    .id(1)
                                    .tipoCliente(EnumTipoCliente.GeraFisco)
                                    .tipoPessoa(EnumTipoPessoa.Juridica)
                                    .endereco(enderecoDtomock)
                                    .build();

        Cliente cliente = new Cliente();
        clienteDtoMock.updateEntity(cliente);

        assertEquals(clienteDtoMock.nome, cliente.getNome());
    }

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

        ResponseEntity<ClienteDto> response = restTemplate
        .exchange(new URL(url).toString(), HttpMethod.PUT,
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