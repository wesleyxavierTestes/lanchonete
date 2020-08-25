package com.lanchonete.mocks.entities;

import com.lanchonete.apllication.dto.cliente.ClienteDto;
import com.lanchonete.domain.entities.cliente.Cliente;
import com.lanchonete.domain.enuns.cliente.EnumTipoCliente;
import com.lanchonete.domain.enuns.cliente.EnumTipoPessoa;

import org.springframework.boot.test.web.client.TestRestTemplate;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ClienteMock {
    private TestRestTemplate restTemplate;
    private int port;

    public static ClienteDto dto(String nome) {
            ClienteDto clienteDtoMock = ClienteDto.builder()
                    .nome(nome)
                    .cnpj("96864231000112")
                    .email("teste@gmail.com")
                    .cpf(null)
                    .id(1)
                    .tipoCliente(EnumTipoCliente.GeraFisco)
                    .tipoPessoa(EnumTipoPessoa.Juridica)
                    .endereco(EnderecoMock.dto())
                    .build();
                                    return clienteDtoMock;
    }

    public static Cliente by(String nome) {
        Cliente clienteDtoMock = Cliente.builder()
                .nome(nome)
                .cnjp("0000000000")
                .email("teste@gmail.com")
                .cpf(null)
                .tipoCliente(EnumTipoCliente.GeraFisco)
                .tipoPessoa(EnumTipoPessoa.Juridica)
                .endereco(EnderecoMock.by())
                .build();
                                return clienteDtoMock;
    }
}