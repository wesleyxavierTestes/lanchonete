package com.lanchonete.mocks;

import com.lanchonete.apllication.dto.cliente.EnderecoDto;

public class EnderecoMock {
    
    public static EnderecoDto dto() {
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
        return enderecoDtomock;
    }
}