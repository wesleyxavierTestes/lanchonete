package com.lanchonete.apllication.mappers;

import com.lanchonete.apllication.dto.lanche.LancheDto;
import com.lanchonete.domain.entities.cardapio.lanche.Lanche;

final class LancheMapper {
    private LancheMapper() {}
    public static Lanche update(LancheDto entityDto, Lanche entity) {
        
        return entity;
    }
}