package com.lanchonete.mocks;

import com.lanchonete.apllication.dto.cliente.ClienteDto;
import com.lanchonete.apllication.dto.cliente.EnderecoDto;
import com.lanchonete.domain.enuns.cliente.EnumTipoCliente;
import com.lanchonete.domain.enuns.cliente.EnumTipoPessoa;

public class ClienteMock {
    
    public static ClienteDto dto() {
        EnderecoDto enderecoDtomock = EnderecoDto.builder()
                                    
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

                                    return clienteDtoMock;
    }
}