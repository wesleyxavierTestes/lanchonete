package com.lanchonete.apllication.dto.lanche;

import java.util.List;

import com.lanchonete.apllication.dto.BaseValidate;
import com.lanchonete.domain.entities.cardapio.lanche.Lanche;

import org.modelmapper.ModelMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LancheDto extends BaseValidate<Lanche> {

    public long id;
    public String nome;
    public List<IngredienteDto> ingredientes;

    @Override
    public boolean getIsValid() {
        if (this.nome == null) {
            this.SetValidation("nome", "nome está nullo");
        }
        return this.valid;
    }

    @Override
    public Lanche updateEntity(Lanche entity) {

        // TODO: LÓGICA UPDATE DTO
        return entity;
    }

    @Override
    public Lanche createEntity(ModelMapper mapper) {
        
        return mapper.map(this, Lanche.class);
    }
}
