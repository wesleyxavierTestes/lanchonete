package com.lanchonete.apllication.dto.lanche;

import com.lanchonete.apllication.dto.BaseValidate;
import com.lanchonete.domain.entities.cardapio.lanche.Lanche;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LancheDto extends BaseValidate {

    public long id;
    public String nome;

    @Override
    public boolean getIsValid() {
        if (this.nome == null) {
            this.SetValidation("nome", "nome está nullo");
        }
        return this.valid;
    }

    public Lanche updateEntity(Lanche entity) {
        

        return entity;
    }
}
