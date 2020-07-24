package com.lanchonete.apllication.dto.cardapio;

import com.lanchonete.apllication.dto.BaseValidate;
import com.lanchonete.domain.entities.cardapio.Cardapio;

import org.modelmapper.ModelMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardapioDto extends BaseValidate<Cardapio> {

    public long id;
    public String nome;

    @Override
    public boolean getIsValid() {
        if (this.nome == null) {
            this.SetValidation("nome", "nome está nullo");
        }
        return this.valid;
    }

    @Override
    public Cardapio updateEntity(Cardapio entity) {

        return entity;
    }

    @Override
    public Cardapio createEntity(ModelMapper mapper) {
        
        return mapper.map(this, Cardapio.class);
    }
}
