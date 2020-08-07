package com.lanchonete.apllication.mappers;

import com.lanchonete.apllication.dto.combo.ComboDto;
import com.lanchonete.domain.entities.combo.Combo;

final class ComboMapper {
    private ComboMapper() {}
    public static Combo update(ComboDto entityDto, Combo entity) {

        return entity;
    }
}