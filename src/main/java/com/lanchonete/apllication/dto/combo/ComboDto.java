package com.lanchonete.apllication.dto.combo;

import com.lanchonete.apllication.dto.BaseValidate;
import com.lanchonete.domain.entities.cardapio.combo.Combo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComboDto extends BaseValidate {

    public long id;
    public String nome;

    @Override
    public boolean getIsValid() {
        if (this.nome == null) {
            this.SetValidation("nome", "nome está nullo");
        }
        return this.valid;
    }

    public Combo updateEntity(Combo entity) {
        

        return entity;
    }
}
