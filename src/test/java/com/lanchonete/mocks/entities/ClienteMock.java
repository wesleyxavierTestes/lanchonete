package com.lanchonete.mocks.entities;

import com.lanchonete.apllication.dto.cliente.ClienteDto;
import com.lanchonete.domain.entities.cliente.Cliente;
import com.lanchonete.domain.enuns.cliente.EnumTipoCliente;
import com.lanchonete.domain.enuns.cliente.EnumTipoPessoa;

public class ClienteMock {
    
    public static ClienteDto dto() {
            ClienteDto clienteDtoMock = ClienteDto.builder()
                    .nome("wesley xavier")
                    .cnjp("96864231000112")
                    .cpf(null)
                    .id(1)
                    .tipoCliente(EnumTipoCliente.GeraFisco)
                    .tipoPessoa(EnumTipoPessoa.Juridica)
                    .endereco(EnderecoMock.dto())
                    .build();
                                    return clienteDtoMock;
    }

    public static Cliente by() {
        Cliente clienteDtoMock = Cliente.builder()
                .nome("wesley xavier")
                .cnjp("0000000000")
                .cpf(null)
                .tipoCliente(EnumTipoCliente.GeraFisco)
                .tipoPessoa(EnumTipoPessoa.Juridica)
                .endereco(EnderecoMock.by())
                .build();
                                return clienteDtoMock;
    }
}