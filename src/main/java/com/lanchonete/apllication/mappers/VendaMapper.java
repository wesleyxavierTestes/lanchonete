package com.lanchonete.apllication.mappers;

import com.lanchonete.apllication.dto.venda.VendaDto;
import com.lanchonete.domain.entities.venda.Venda;

final class VendaMapper {
    private VendaMapper() {
    }

    public static Venda update(VendaDto entityDto, Venda entity) {

        return entity;
    }
}