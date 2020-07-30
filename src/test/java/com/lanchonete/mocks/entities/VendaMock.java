package com.lanchonete.mocks.entities;

import com.lanchonete.apllication.dto.venda.VendaDto;
import com.lanchonete.domain.entities.venda.Venda;

public class VendaMock {

    public static VendaDto dto(String nome) {
        VendaDto clienteDtoMock = VendaDto.builder()
        
        .build();
        return clienteDtoMock;
    }

    public static Venda by(String nome) {
        Venda clienteDtoMock = new Venda();

        return clienteDtoMock;
    }
}