package com.lanchonete.apllication.dto.cardapio;

import com.lanchonete.apllication.dto.BaseValidate;
import com.lanchonete.domain.entities.cardapio.Cardapio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardapioDto extends BaseValidate {

    public long id;
    public String nome;

    @Override
    public boolean getIsValid() {
        if (this.nome == null) {
            this.SetValidation("nome", "nome está nullo");
        }
        return this.valid;
    }

    public Cardapio updateEntity(final Cardapio entity) {
        

        return entity;
    }
}
