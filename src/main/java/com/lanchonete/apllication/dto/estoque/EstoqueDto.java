package com.lanchonete.apllication.dto.estoque;

import com.lanchonete.apllication.dto.BaseValidate;
import com.lanchonete.domain.entities.estoque.AbstractEstoque;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueDto extends BaseValidate {

    public long id;
    public String nome;

    @Override
    public boolean getIsValid() {
        if (this.nome == null) {
            this.SetValidation("nome", "nome está nullo");
        }
        return this.valid;
    }

    public AbstractEstoque updateEntity(AbstractEstoque entity) {
        

        return entity;
    }
}
