package com.lanchonete.mocks.entities;

import com.lanchonete.apllication.dto.cliente.EnderecoDto;
import com.lanchonete.domain.entities.cliente.Endereco;

public class EnderecoMock {
    
    public static EnderecoDto dto() {
        EnderecoDto enderecoDtomock = EnderecoDto.builder()
                            .cep("77813525")
                            .numero("123456")       
                            .logradouro("i.endereco.logradouro")   
                            .complemento("Rua Urutaí") 
                            .bairro("Senador")    
                            .localidade("Araguaína")
                            .uf("TO")
                            .unidade("i.endereco.unidad")
                            .ibge("i.ibge")
                            .gia("i.gia")
                            .build();
        return enderecoDtomock;
    }

    public static Endereco by() {
        Endereco enderecoDtomock = Endereco.builder()
                            .cep("77813525")
                            .numero("123456")       
                            .logradouro("i.endereco.logradouro")   
                            .complemento("Rua Urutaí") 
                            .bairro("Senador")    
                            .localidade("Araguaína")
                            .uf("TO")
                            .unidade("i.endereco.unidad")
                            .ibge("i.ibge")
                            .gia("i.gia")
                            .build();
        return enderecoDtomock;
    }
}